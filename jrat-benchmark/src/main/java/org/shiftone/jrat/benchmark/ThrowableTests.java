package org.shiftone.jrat.benchmark;


/**
 * @author Jeff Drost
 */
public class ThrowableTests {

    private static final Throwable throwable = new Throwable();

    @Benchmark(title = "new Throwable")
    public void newThrowable() {
        new Throwable();
    }

    @Benchmark(title = "new Throwable().fillInStackTrace")
    public void newThrowablefillInStackTrace() {
        new Throwable().fillInStackTrace();
    }

    @Benchmark(title = "throwable.fillInStackTrace")
    public void fillInStackTrace() {
        throwable.fillInStackTrace();
    }

    @Benchmark(title = "throwable.getStackTrace")
    public void getStackTrace() {
        throwable.getStackTrace();
    }

    @Benchmark(title = "throw and catch throwable")
    public void throwThrowable() {
        try {
        throw throwable;
        } catch (Throwable e) {}
    }

    @Benchmark(title = "throw and catch new Throwable")
    public void throwNewThrowable() {
        try {
        throw new Throwable();
        } catch (Throwable e) {}
    }
    
}
