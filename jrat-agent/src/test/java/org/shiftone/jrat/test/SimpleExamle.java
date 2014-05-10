package org.shiftone.jrat.test;


import org.shiftone.jrat.core.HandlerFactory;
import org.shiftone.jrat.core.spi.MethodHandler;


public class SimpleExamle {

    private static final String JRat = "this class was injected by bla bla bla";
    private static final MethodHandler HANDLER = HandlerFactory.getMethodHandler("className", "methodName", "signature");

    public void __Shiftone_JRat_doText(String text) {
        System.out.println(text);
    }

    public void doText(String text) {

        long start;

        HANDLER.onMethodStart();
        start = System.currentTimeMillis();

        try {
            __Shiftone_JRat_doText(text);

            HANDLER.onMethodFinish((System.currentTimeMillis() - start), null);
            return;
        }
        catch (Throwable e) {
            HANDLER.onMethodFinish((System.currentTimeMillis() - start), e);
        }

    }
    /*
     package asm.org.shiftone.jrat.test;
     import java.util.*;
     import org.objectweb.asm.*;
     import org.objectweb.asm.attrs.*;
     public class SimpleExamleDump implements Opcodes {

     public static byte[] dump () throws Exception {

     ClassWriter cw = new ClassWriter(0);
     FieldVisitor fv;
     MethodVisitor mv;
     AnnotationVisitor av0;

     cw.visit(V1_2, ACC_PUBLIC + ACC_SUPER, "org/shiftone/jrat/test/SimpleExamle", null, "java/lang/Object", null);

     cw.visitSource("SimpleExamle.java", null);

     {
     fv = cw.visitField(ACC_PRIVATE + ACC_FINAL + ACC_STATIC, "JRat", "Ljava/lang/String;", null, "this class was injected by bla bla bla");
     fv.visitEnd();
     }
     {
     fv = cw.visitField(ACC_PRIVATE + ACC_FINAL + ACC_STATIC, "HANDLER", "Lorg/shiftone/jrat/core/spi/MethodHandler2;", null, null);
     fv.visitEnd();
     }
     {
     mv = cw.visitMethod(ACC_STATIC, "<clinit>", "()V", null, null);
     mv.visitCode();
     Label l0 = new Label();
     mv.visitLabel(l0);
     mv.visitLineNumber(11, l0);
     mv.visitLdcInsn("className");
     mv.visitLdcInsn("methodName");
     mv.visitLdcInsn("signature");
     mv.visitMethodInsn(INVOKESTATIC, "org/shiftone/jrat/core/HandlerFactory2", "getMethodHandler", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lorg/shiftone/jrat/core/spi/MethodHandler2;");
     mv.visitFieldInsn(PUTSTATIC, "org/shiftone/jrat/test/SimpleExamle", "HANDLER", "Lorg/shiftone/jrat/core/spi/MethodHandler2;");
     Label l1 = new Label();
     mv.visitLabel(l1);
     mv.visitLineNumber(8, l1);
     mv.visitInsn(RETURN);
     mv.visitMaxs(3, 0);
     mv.visitEnd();
     }
     {
     mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
     mv.visitCode();
     Label l0 = new Label();
     mv.visitLabel(l0);
     mv.visitLineNumber(8, l0);
     mv.visitVarInsn(ALOAD, 0);
     mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
     mv.visitInsn(RETURN);
     Label l1 = new Label();
     mv.visitLabel(l1);
     mv.visitLocalVariable("this", "Lorg/shiftone/jrat/test/SimpleExamle;", null, l0, l1, 0);
     mv.visitMaxs(1, 1);
     mv.visitEnd();
     }
     {
     mv = cw.visitMethod(ACC_PUBLIC, "__Shiftone_JRat_doText", "(Ljava/lang/String;)V", null, null);
     mv.visitCode();
     Label l0 = new Label();
     mv.visitLabel(l0);
     mv.visitLineNumber(14, l0);
     mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
     mv.visitVarInsn(ALOAD, 1);
     mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");
     Label l1 = new Label();
     mv.visitLabel(l1);
     mv.visitLineNumber(15, l1);
     mv.visitInsn(RETURN);
     Label l2 = new Label();
     mv.visitLabel(l2);
     mv.visitLocalVariable("this", "Lorg/shiftone/jrat/test/SimpleExamle;", null, l0, l2, 0);
     mv.visitLocalVariable("text", "Ljava/lang/String;", null, l0, l2, 1);
     mv.visitMaxs(2, 2);
     mv.visitEnd();
     }
     {
     mv = cw.visitMethod(ACC_PUBLIC, "doText", "(Ljava/lang/String;)V", null, null);
     mv.visitCode();
     Label l0 = new Label();
     Label l1 = new Label();
     mv.visitTryCatchBlock(l0, l1, l1, "java/lang/Throwable");
     Label l2 = new Label();
     mv.visitTryCatchBlock(l0, l2, l2, null);
     Label l3 = new Label();
     Label l4 = new Label();
     mv.visitTryCatchBlock(l3, l4, l2, null);
     Label l5 = new Label();
     mv.visitLabel(l5);
     mv.visitLineNumber(21, l5);
     mv.visitInsn(ICONST_0);
     mv.visitVarInsn(ISTORE, 6);
     Label l6 = new Label();
     mv.visitLabel(l6);
     mv.visitLineNumber(23, l6);
     mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J");
     mv.visitVarInsn(LSTORE, 4);
     mv.visitLabel(l0);
     mv.visitLineNumber(27, l0);
     mv.visitFieldInsn(GETSTATIC, "org/shiftone/jrat/test/SimpleExamle", "HANDLER", "Lorg/shiftone/jrat/core/spi/MethodHandler2;");
     mv.visitVarInsn(ALOAD, 0);
     mv.visitMethodInsn(INVOKEINTERFACE, "org/shiftone/jrat/core/spi/MethodHandler2", "onMethodStart", "(Ljava/lang/Object;)V");
     Label l7 = new Label();
     mv.visitLabel(l7);
     mv.visitLineNumber(29, l7);
     mv.visitVarInsn(ALOAD, 0);
     mv.visitVarInsn(ALOAD, 1);
     mv.visitMethodInsn(INVOKEVIRTUAL, "org/shiftone/jrat/test/SimpleExamle", "__Shiftone_JRat_doText", "(Ljava/lang/String;)V");
     Label l8 = new Label();
     mv.visitLabel(l8);
     mv.visitLineNumber(31, l8);
     mv.visitInsn(ICONST_1);
     mv.visitVarInsn(ISTORE, 6);
     mv.visitJumpInsn(GOTO, l3);
     mv.visitLabel(l1);
     mv.visitLineNumber(33, l1);
     mv.visitVarInsn(ASTORE, 7);
     Label l9 = new Label();
     mv.visitLabel(l9);
     mv.visitLineNumber(35, l9);
     mv.visitFieldInsn(GETSTATIC, "org/shiftone/jrat/test/SimpleExamle", "HANDLER", "Lorg/shiftone/jrat/core/spi/MethodHandler2;");
     mv.visitInsn(ACONST_NULL);
     mv.visitVarInsn(ALOAD, 7);
     mv.visitMethodInsn(INVOKEINTERFACE, "org/shiftone/jrat/core/spi/MethodHandler2", "onMethodError", "(Ljava/lang/Object;Ljava/lang/Throwable;)V");
     Label l10 = new Label();
     mv.visitLabel(l10);
     mv.visitJumpInsn(GOTO, l3);
     mv.visitLabel(l2);
     mv.visitLineNumber(38, l2);
     mv.visitVarInsn(ASTORE, 9);
     Label l11 = new Label();
     mv.visitJumpInsn(JSR, l11);
     Label l12 = new Label();
     mv.visitLabel(l12);
     mv.visitLineNumber(42, l12);
     mv.visitVarInsn(ALOAD, 9);
     mv.visitInsn(ATHROW);
     mv.visitLabel(l11);
     mv.visitLineNumber(38, l11);
     mv.visitVarInsn(ASTORE, 8);
     Label l13 = new Label();
     mv.visitLabel(l13);
     mv.visitLineNumber(39, l13);
     mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J");
     mv.visitVarInsn(LSTORE, 2);
     Label l14 = new Label();
     mv.visitLabel(l14);
     mv.visitLineNumber(41, l14);
     mv.visitFieldInsn(GETSTATIC, "org/shiftone/jrat/test/SimpleExamle", "HANDLER", "Lorg/shiftone/jrat/core/spi/MethodHandler2;");
     mv.visitVarInsn(ALOAD, 0);
     mv.visitVarInsn(LLOAD, 2);
     mv.visitVarInsn(LLOAD, 4);
     mv.visitInsn(LSUB);
     mv.visitVarInsn(ILOAD, 6);
     mv.visitMethodInsn(INVOKEINTERFACE, "org/shiftone/jrat/core/spi/MethodHandler2", "onMethodFinish", "(Ljava/lang/Object;JZ)V");
     Label l15 = new Label();
     mv.visitLabel(l15);
     mv.visitLineNumber(42, l15);
     mv.visitVarInsn(RET, 8);
     mv.visitLabel(l3);
     mv.visitJumpInsn(JSR, l11);
     mv.visitLabel(l4);
     mv.visitLineNumber(43, l4);
     mv.visitInsn(RETURN);
     Label l16 = new Label();
     mv.visitLabel(l16);
     mv.visitLocalVariable("this", "Lorg/shiftone/jrat/test/SimpleExamle;", null, l5, l16, 0);
     mv.visitLocalVariable("text", "Ljava/lang/String;", null, l5, l16, 1);
     mv.visitLocalVariable("finish", "J", null, l14, l16, 2);
     mv.visitLocalVariable("start", "J", null, l0, l16, 4);
     mv.visitLocalVariable("success", "Z", null, l6, l16, 6);
     mv.visitLocalVariable("e", "Ljava/lang/Throwable;", null, l9, l10, 7);
     mv.visitMaxs(6, 10);
     mv.visitEnd();
     }
     cw.visitEnd();

     return cw.toByteArray();
     }
     }

         */
}
