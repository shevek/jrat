package org.shiftone.jrat.inject.bytecode.asm;


import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.GeneratorAdapter;
import org.objectweb.asm.commons.Method;
import org.shiftone.jrat.core.JRatException;
import org.shiftone.jrat.inject.bytecode.InjectorStrategy;
import org.shiftone.jrat.inject.bytecode.Modifier;
import org.shiftone.jrat.util.VersionUtil;
import org.shiftone.jrat.util.log.Logger;

import java.util.Date;


public class InjectClassVisitor extends ClassAdapter implements Constants, Opcodes {

    private static final Logger LOG = Logger.getLogger(InjectClassVisitor.class);
    private int handlerCount;
    private Type classType;
    private GeneratorAdapter initializer;

    public InjectClassVisitor(ClassVisitor visitor) {
        super(visitor);
    }


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
        GeneratorAdapter initializer = new GeneratorAdapter(initMethodVisitor, access, initializeName,
                descriptor);

        initMethodVisitor.visitCode();

        return initializer;
    }


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


    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature,
                                     String[] exceptions) {

        if (name.equals("<clinit>") || name.equals("<init>") || Modifier.isAbstract(access)
                || Modifier.isNative(access)) {

            // LOG.debug("skipping " + name);
            return super.visitMethod(access, name, descriptor, signature, exceptions);
        }

        int index = (handlerCount++);
        String handlerFieldName = InjectorStrategy.HANDLER_PREFIX + index;
        String targetMethodName = name + InjectorStrategy.METHOD_POSTFIX;

        addMethodHandlerField(handlerFieldName, name, descriptor);

        // -- [ Proxy Method ] --
        {
            Method method = new Method(name, descriptor);
            MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
            ProxyMethodVisitor visitor = new ProxyMethodVisitor(access, method, mv, classType, targetMethodName,
                    handlerFieldName);

            visitor.visitCode();
            visitor.visitEnd();
        }

        // -- [ Target Method ] --
        return super.visitMethod(Modifier.makePrivate(access), targetMethodName, descriptor, signature, exceptions);
    }
}
