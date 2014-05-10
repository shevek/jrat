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

    private void invoke() {
        if (isStatic) {
            loadArgs();    // push the args on the stack
            invokeStatic(classType, new Method(targetMethodName, method.getDescriptor()));
        } else {
            loadThis();    // push this on the stack (for non-static methods)
            loadArgs();    // push the args on the stack
            invokeVirtual(classType, new Method(targetMethodName, method.getDescriptor()));
        }
    }


    public void visitCode() {
        Label monitor = newLabel();
        int state = newLocal(ThreadState.TYPE);

        // ThreadState state = ThreadState.getInstance();
        invokeStatic(ThreadState.TYPE, ThreadState.getInstance);
        storeLocal(state);

        // if (!state.isInHandler) goto monitor
        if (false) {
            loadLocal(state);
            invokeVirtual(ThreadState.TYPE, ThreadState.isInHandler);

            ifZCmp(EQ, monitor);  //  not(isInHandler) == isInHandler is false == zero

            invoke();
            returnValue();
        }
        
        // -------------------------------------------------------------------------------
        mark(monitor);

        // long startTime = state.begin(METHOD_HANDLER);
        loadLocal(state);
        getStatic(classType, handlerFieldName, MethodHandler.TYPE);
        invokeVirtual(ThreadState.TYPE, ThreadState.begin);

        int startTime = newLocal(Type.LONG_TYPE);
        storeLocal(startTime, Type.LONG_TYPE);


        Label beginTry = mark();    // try {

        invoke();

        int result = -1;

        if (!isVoidReturn) {
            result = newLocal(method.getReturnType());
            storeLocal(result);
        }

        // -------------------------------------------------------------------------------
        //  state.end(METHOD_HANDLER, startTime, null);
        loadLocal(state);
        // end : param 1
        getStatic(classType, handlerFieldName, MethodHandler.TYPE);
        // end : param 2
        loadLocal(startTime);
        // end : param 3
        visitInsn(ACONST_NULL);
        invokeVirtual(ThreadState.TYPE, ThreadState.end);

        // -------------------------------------------------------------------------------
        // return result;
        if (!isVoidReturn) {
            loadLocal(result);
        }

        returnValue();

        Label endTry = mark();    // } catch (Throwable e) {

        // this is the beginning of the catch block
        catchException(beginTry, endTry, Throwable.TYPE);

        // -------------------------------------------------------------------------------
        // Throwable exception = e;
        int exception = newLocal(Throwable.TYPE);

        storeLocal(exception);

        // -------------------------------------------------------------------------------
        //  state.end(METHOD_HANDLER, startTime, null);
        loadLocal(state);
        // end : param 1
        getStatic(classType, handlerFieldName, MethodHandler.TYPE);
        // end : param 2
        loadLocal(startTime);
        // end : param 3
        loadLocal(exception);
        invokeVirtual(ThreadState.TYPE, ThreadState.end);


        // -------------------------------------------------------------------------------
        // throw e;
        loadLocal(exception);
        throwException();

        // -------------------------------------------------------------------------------

        endMethod();
    }


}
