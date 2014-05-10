package org.shiftone.jrat.util.log.target;


import org.shiftone.jrat.util.log.Logger;


/**
 * This is a LogTarget that uses a delegate LogTarget that lives on the thread.
 * This makes it possible to run a command within a scope, and have the
 * activities of that scope to be logged to a different LogTarget - but only
 * during the duration of that command.
 *
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class ThreadLocalLogTarget implements LogTarget {

    private static final Logger LOG = Logger.getLogger(ThreadLocalLogTarget.class);
    public ThreadLocal threadLocal;

    public ThreadLocalLogTarget() {
        this(NullLogTarget.INSTANCE);
    }


    public ThreadLocalLogTarget(final LogTarget target) {

        threadLocal = new ThreadLocal() {

            protected Object initialValue() {
                return target;
            }
        };
    }


    public boolean isLevelEnabled(String topic, int level) {
        return getLogTarget().isLevelEnabled(topic, level);
    }


    public void log(String topic, int level, Object message, Throwable throwable) {
        getLogTarget().log(topic, level, message, throwable);
    }


    private LogTarget getLogTarget() {
        return (LogTarget) threadLocal.get();
    }


    private void setLogTarget(LogTarget logTarget) {
        threadLocal.set(logTarget);
    }


    public void executeInScope(LogTarget newTarget, Runnable runnable) {

        LogTarget oldTarget = getLogTarget();

        try {
            setLogTarget(newTarget);
            runnable.run();
        } catch (Throwable e) {
            LOG.error("Process failed.", e);
        }
        finally {
            setLogTarget(oldTarget);
        }
    }
}
