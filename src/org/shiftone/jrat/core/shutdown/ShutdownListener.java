package org.shiftone.jrat.core.shutdown;



/**
 * Classes that implement this interface wish to know when the system is
 * shutting down.
 *
 * @author $Author: jeffdrost $
 * @version $Revision: 1.3 $
 */
public interface ShutdownListener {
    void shutdown();
}
