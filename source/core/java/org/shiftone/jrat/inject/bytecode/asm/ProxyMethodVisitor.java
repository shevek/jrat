package org.shiftone.jrat.inject.bytecode.asm;


import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.GeneratorAdapter;
import org.objectweb.asm.commons.Method;
import org.shiftone.jrat.inject.bytecode.Modifier;
import org.shiftone.jrat.util.log.Logger;


/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class ProxyMethodVisitor extends GeneratorAdapter implements Constants, Opcodes {

    private static final Logger LOG = Logger.getLogger(ProxyMethodVisitor.class);
    private boolean isStatic;
    private boolean isVoidReturn;
    private Type classType;
    private String handlerFieldName;
    private String targetMethodName;
    private Method method;

    public ProxyMethodVisitor(int access, Method method, MethodVisitor mv, Type classType, String targetMethodName,
                              String handlerFieldName) {

        super(access, method, mv);

        this.method = method;
        this.isStatic = Modifier.isStatic(access);
        this.isVoidReturn = Type.VOID_TYPE.equals(method.getReturnType());
        this.classType = classType;
        this.targetMethodName = targetMethodName;
        this.handlerFieldName = handlerFieldName;
    }


    public void visitCode() {

        Label tryLabel = newLabel();

        // -------------------------------------------------------------------------------
        // HANDLER.onMethodStart(this);
        getStatic(classType, handlerFieldName, Constants.MethodHandler.TYPE);
        pushThis();
        invokeInterface(MethodHandler.TYPE, MethodHandler.onMethodStart);
        mark(tryLabel);

        // -------------------------------------------------------------------------------
        // long startTime = System.currentTimeNanos();
        int startTime = newLocal(Type.LONG_TYPE);

        invokeStatic(Clock.TYPE, Clock.currentTimeNanos);
        storeLocal(startTime, Type.LONG_TYPE);

        // -------------------------------------------------------------------------------
        // local var result is defined only if there is a non-void return type
        // Object result = method(args)
        Label tryStart = mark();    // try {

        if (isStatic) {
            loadArgs();    // push the args on the stack
            invokeStatic(classType, new Method(targetMethodName, method.getDescriptor()));
        } else {
            loadThis();    // push this on the stack (for non-static methods)
            loadArgs();    // push the args on the stack
            invokeVirtual(classType, new Method(targetMethodName, method.getDescriptor()));
        }

        int result = -1;

        if (!isVoidReturn) {
            result = newLocal(method.getReturnType());

            storeLocal(result);
        }

        // -------------------------------------------------------------------------------
        // HANDLER.onMethodFinish(this, System.currentTimeNanos - start, null);
        getStatic(classType, handlerFieldName, MethodHandler.TYPE);    // get the

        // MethodHandler
        pushThis();                                                    // param 1
        invokeStatic(Clock.TYPE, Clock.currentTimeNanos);              // param 2 : obtain

        // end time
        // (Clock.currentTimeNanos)
        loadLocal(startTime);                                          // param 2 : getPreferences the start time onto the stack
        math(GeneratorAdapter.SUB, Type.LONG_TYPE);                    // param 2 : subtract,

        // leaving the result on the
        // stack
        visitInsn(ACONST_NULL);                                        // param 2 : null (no exception)
        invokeInterface(MethodHandler.TYPE, MethodHandler.onMethodFinish);

        // -------------------------------------------------------------------------------
        // return result;
        if (!isVoidReturn) {
            loadLocal(result);
        }

        returnValue();

        Label tryEnd = mark();    // } catch (Throwable e) {

        // this is the beginning of the catch block
        catchException(tryStart, tryEnd, Throwable.TYPE);

        // -------------------------------------------------------------------------------
        // Throwable exception = e;
        int exception = newLocal(Throwable.TYPE);

        storeLocal(exception);

        // -------------------------------------------------------------------------------
        // HANDLER.onMethodFinish(this, System.currentTimeNanos - start,
        // exception);
        getStatic(classType, handlerFieldName, MethodHandler.TYPE);
        pushThis();                                          // param 1
        invokeStatic(Clock.TYPE, Clock.currentTimeNanos);    // param 2 : obtain

        // end time
        // (Clock.currentTimeNanos)
        loadLocal(startTime);                                // param 2 : getPreferences the start time back onto the

        // stack
        math(GeneratorAdapter.SUB, Type.LONG_TYPE);          // param 2 : subtract,

        // leaving the result on the
        // stack
        loadLocal(exception);                                // param 3 : getPreferences the exception
        invokeInterface(MethodHandler.TYPE, MethodHandler.onMethodFinish);
        loadLocal(exception);
        throwException();

        // -------------------------------------------------------------------------------
        endMethod();
    }


    private void pushThis() {

        if (isStatic) {
            push("test");
        } else {
            loadThis();
        }
    }
}
