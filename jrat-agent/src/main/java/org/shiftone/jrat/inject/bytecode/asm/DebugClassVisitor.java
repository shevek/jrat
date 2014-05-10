package org.shiftone.jrat.inject.bytecode.asm;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.shiftone.jrat.util.log.Logger;

public class DebugClassVisitor extends ClassVisitor {

    private static final Logger LOG = Logger.getLogger(DebugClassVisitor.class);

    public DebugClassVisitor(final ClassVisitor cv) {
        super(Opcodes.ASM5, cv);
    }

    @Override
    public void visit(final int version, final int access, final String name, final String signature,
            final String superName, final String[] interfaces) {

        LOG.info("visit " + version + ", " + access + ", " + name + ", " + signature + ", " + superName + ", "
                + interfaces);
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public void visitSource(final String source, final String debug) {
        LOG.info("visitSource " + source + " " + debug);
        super.visitSource(source, debug);
    }

    @Override
    public void visitOuterClass(final String owner, final String name, final String desc) {
        LOG.info("visitOuterClass " + owner + " " + name + " " + desc);
        super.visitOuterClass(owner, name, desc);
    }

    @Override
    public AnnotationVisitor visitAnnotation(final String desc, final boolean visible) {
        return super.visitAnnotation(desc, visible);
    }

    @Override
    public void visitAttribute(final Attribute attr) {
        LOG.info("visitAttribute " + attr);
        super.visitAttribute(attr);
    }

    @Override
    public void visitInnerClass(final String name, final String outerName, final String innerName, final int access) {
        LOG.info("visitInnerClass " + name + " " + outerName + " " + innerName + " " + access);
        super.visitInnerClass(name, outerName, innerName, access);
    }

    @Override
    public FieldVisitor visitField(final int access, final String name, final String desc, final String signature,
            final Object value) {

        LOG.info("visitField " + access + " " + name + " " + desc + " " + signature + " " + value);

        return super.visitField(access, name, desc, signature, value);
    }

    @Override
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature,
            final String[] exceptions) {

        LOG.info("visitMethod " + access + " " + name + " " + desc + " " + signature + " " + exceptions);

        return super.visitMethod(access, name, desc, signature, exceptions);
    }

    @Override
    public void visitEnd() {
        LOG.info("visitEnd");
        super.visitEnd();
    }
}
