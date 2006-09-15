package org.shiftone.jrat.util.log.target;



import org.shiftone.jrat.util.Command;


/**
 * This is a LogTarget that uses a delegate LogTarget that lives on the thread.
 * This makes it possible to run a command within a scope, and have the
 * activities of that scope to be logged to a different LogTarget - but only
 * during the duration of that command.
 *
 * @author $Author: jeffdrost $
 * @version $Revision: 1.5 $
 */
public class ThreadLocalLogTarget implements LogTarget {

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


    public Object executeInScope(LogTarget newTarget, Command command) {

        LogTarget oldTarget = getLogTarget();

        try
        {
            setLogTarget(newTarget);

            return command.execute();
        }
        finally
        {
            setLogTarget(oldTarget);
        }
    }
}
