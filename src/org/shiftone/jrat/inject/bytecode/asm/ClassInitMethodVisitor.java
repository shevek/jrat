package org.shiftone.jrat.inject.bytecode.asm;



import org.objectweb.asm.MethodAdapter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import org.shiftone.jrat.util.log.Logger;


/**
 * All this visitor does is add a single instruction to the start of the static
 * intitializer to call the JRat initializer method.
 *
 * @author $Author: jeffdrost $
 * @version $Revision: 1.3 $
 */
public class ClassInitMethodVisitor extends MethodAdapter implements Constants {

    private static final Logger LOG = Logger.getLogger(ClassInitMethodVisitor.class);
    private String              className;

    public ClassInitMethodVisitor(String className, MethodVisitor mv) {

        super(mv);

        this.className = className;
    }


    public void visitCode() {
        super.visitMethodInsn(Opcodes.INVOKESTATIC, className, initializeName, "()V");
        super.visitCode();
    }
}
