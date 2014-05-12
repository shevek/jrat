package org.shiftone.jrat.inject.bytecode.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;
import org.shiftone.jrat.inject.bytecode.Modifier;
import org.shiftone.jrat.util.log.Logger;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class IfInterfaceClassVisitor extends ClassVisitor {

    private static final Logger LOG = Logger.getLogger(IfInterfaceClassVisitor.class);
    private final ClassVisitor interfaceClassVisitor;
    private final ClassVisitor concreteClassVisitor;

    public IfInterfaceClassVisitor(ClassVisitor interfaceClassVisitor, ClassVisitor concreteClassVisitor) {
        super(Opcodes.ASM5, null);
        this.interfaceClassVisitor = interfaceClassVisitor;
        this.concreteClassVisitor = concreteClassVisitor;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        if (Modifier.isInterface(access)) {
            cv = interfaceClassVisitor;
            // LOG.debug("is interface " + name);
        } else {
            cv = concreteClassVisitor;
            // LOG.debug("is NOT interface " + name);
        }

        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
        cv = null;
    }
}
