package org.shiftone.jrat.inject.bytecode;

import org.objectweb.asm.Opcodes;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class Modifier extends java.lang.reflect.Modifier implements Opcodes {

    public static final int PUBLIC_STATIC = PUBLIC | STATIC;
    public static final int PUBLIC_STATIC_FINAL = PUBLIC_STATIC | FINAL;
    public static final int PRIVATE_STATIC = PRIVATE | STATIC;
    public static final int PRIVATE_STATIC_FINAL = PRIVATE_STATIC | FINAL;
    private static final int NO_PUBLIC_PRIVATE_PROTECTED = ~(PRIVATE | PUBLIC | PROTECTED);

    public static boolean isSynthetic(int modifier) {
        return (modifier & ACC_SYNTHETIC) != 0;
    }

    public static int makePrivate(int modifier) {
        return (modifier & NO_PUBLIC_PRIVATE_PROTECTED) | PRIVATE;
    }

    public static int makePublic(int modifier) {
        return (modifier & NO_PUBLIC_PRIVATE_PROTECTED) | PUBLIC;
    }

    public static int makeProtected(int modifier) {
        return (modifier & NO_PUBLIC_PRIVATE_PROTECTED) | PROTECTED;
    }

    public static int makeNonNative(int modifier) {
        return (modifier & ~(NATIVE));
    }
}
