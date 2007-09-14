package org.shiftone.jrat.inject.bytecode.asm;

import org.shiftone.jrat.core.HandlerFactory;
import org.shiftone.jrat.core.ThreadState;
import org.shiftone.jrat.core.spi.MethodHandler;

/**
 * This class is not used, but serves as a rough example of what is
 * actually injected.
 * @author (jeff@shiftone.org) Jeff Drost
 */
public class Example {


    public int foo(int x) {
        System.out.println("foo");
        return x + 1;
    }


    public int foo$jrat(int x) throws Throwable {

        ThreadState state = ThreadState.getInstance();

        if (state.isInHandler()) {
            return foo(x);
        }

        long startTime = state.begin(METHOD_HANDLER);

        try {

            int result = foo(x);

            state.end(METHOD_HANDLER, state.now() - startTime, null);

            return result;
            
        } catch (Throwable e) {

            state.end(METHOD_HANDLER, state.now() - startTime, e);

            throw e;

        }

    }

    private static final MethodHandler METHOD_HANDLER = HandlerFactory.getMethodHandler("", "", "");

}
