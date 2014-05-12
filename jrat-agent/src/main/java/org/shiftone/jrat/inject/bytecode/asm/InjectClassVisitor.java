package org.shiftone.jrat.inject.bytecode.asm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.GeneratorAdapter;
import org.objectweb.asm.commons.Method;
import org.objectweb.asm.tree.AnnotationNode;
import org.shiftone.jrat.core.JRatException;
import org.shiftone.jrat.inject.bytecode.InjectorStrategy;
import org.shiftone.jrat.inject.bytecode.Modifier;
import org.shiftone.jrat.util.VersionUtil;
import org.shiftone.jrat.util.log.Logger;

public class InjectClassVisitor extends ClassVisitor implements Constants, Opcodes {

    private static final Logger LOG = Logger.getLogger(InjectClassVisitor.class);
    private int handlerCount;
    private Type classType;
    private GeneratorAdapter initializer;

    public InjectClassVisitor(ClassVisitor visitor) {
        super(Opcodes.ASM5, visitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        handlerCount = 0;
        classType = Type.getType("L" + name.replace('.', '/') + ";");

        super.visit(version, access, name, signature, superName, interfaces);

        initializer = addInitializer();
    }

    private GeneratorAdapter addInitializer() {
        int access = Modifier.PRIVATE_STATIC;
        String descriptor = "()V";
        MethodVisitor initMethodVisitor = super.visitMethod(access, initializeName, descriptor, null, null);
        GeneratorAdapter initializer = new GeneratorAdapter(initMethodVisitor, access, initializeName, descriptor);

        initMethodVisitor.visitCode();

        return initializer;
    }

    @Override
    public void visitEnd() {
        initializer.returnValue();
        initializer.endMethod();
        addCommentField();
        super.visitEnd();
    }

    private void addCommentField() {
        FieldVisitor commentField = super.visitField(Modifier.PRIVATE_STATIC_FINAL,
                InjectorStrategy.COMMENT_FIELD_NAME, "Ljava/lang/String;", null,
                "Class enhanced on " + new Date() + " w/ version JRat v"
                + VersionUtil.getBuiltOn() + " built on " + VersionUtil.getBuiltOn());
        commentField.visitEnd();
    }

    @Override
    public FieldVisitor visitField(final int access, final String name, final String desc, final String signature,
            final Object value) {
        if (name.equals(InjectorStrategy.COMMENT_FIELD_NAME)) {
            throw new JRatException("this class was previously injected by JRat");
        }
        return super.visitField(access, name, desc, signature, value);
    }

    private void addMethodHandlerField(String fieldName, String methodName, String descriptor) {
        FieldVisitor handler = super.visitField(Modifier.PRIVATE_STATIC_FINAL, fieldName,
                MethodHandler.TYPE.getDescriptor(), null, null);
        handler.visitEnd();
        initializer.push(classType.getClassName());
        initializer.push(methodName);
        initializer.push(descriptor);
        initializer.invokeStatic(HandlerFactory.TYPE, HandlerFactory.getMethodHandler);
        initializer.putStatic(classType, fieldName, MethodHandler.TYPE);
    }

    public void pushThis(GeneratorAdapter adapter, boolean isStatic) {
        if (isStatic) {
            adapter.push("test");
        } else {
            adapter.loadThis();
        }
    }

    @Override
    public MethodVisitor visitMethod(final int access, final String name,
            final String descriptor, final String signature,
            final String[] exceptions) {
        if (name.equals("<clinit>") || name.equals("<init>") || Modifier.isAbstract(access) || Modifier.isNative(access)) {
            // LOG.debug("skipping " + name);
            return super.visitMethod(access, name, descriptor, signature, exceptions);
        }

        int index = (handlerCount++);
        final String handlerFieldName = InjectorStrategy.HANDLER_PREFIX + index;
        final String targetMethodName = name + InjectorStrategy.METHOD_POSTFIX;

        MethodVisitor parent = super.visitMethod(Modifier.makePrivate(access), targetMethodName, descriptor, signature, exceptions);
        MethodVisitor child = new MethodVisitor(Opcodes.ASM5, parent) {
            class Annotation extends AnnotationNode {

                private final boolean visible;

                public Annotation(String desc, boolean visible) {
                    super(Opcodes.ASM5, desc);
                    this.visible = visible;
                }

            }
            private final List<Annotation> annotations = new ArrayList<Annotation>();

            @Override
            public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
                Annotation annotation = new Annotation(desc, visible);
                annotations.add(annotation);
                return annotation;
            }

            @Override
            public void visitEnd() {
                super.visitEnd();

                addMethodHandlerField(handlerFieldName, name, descriptor);

                // -- [ Proxy Method ] --
                {
                    Method method = new Method(name, descriptor);
                    MethodVisitor mv = InjectClassVisitor.super.visitMethod(access, name, descriptor, signature, exceptions);
                    ProxyMethodVisitor visitor = new ProxyMethodVisitor(access, method, mv, classType, targetMethodName,
                            handlerFieldName);

                    for (Annotation annotation : annotations) {
                        AnnotationVisitor v = visitor.visitAnnotation(annotation.desc, annotation.visible);
                        annotation.accept(v);
                    }
                    visitor.visitCode();    // Calls visitEnd() via endMethod()
                }
            }
        };

        // -- [ Target Method ] --
        return child;
    }
}
