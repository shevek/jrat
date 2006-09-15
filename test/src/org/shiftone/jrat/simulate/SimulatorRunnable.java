package org.shiftone.jrat.simulate;



import org.shiftone.jrat.core.InternalHandler;
import org.shiftone.jrat.simulate.stmt.Statement;


/**
 * Class SimulatorRunnable
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.1 $
 */
public class SimulatorRunnable implements Runnable {

    private InternalHandler internalHandler = null;
    private Statement       statement       = null;

    public SimulatorRunnable(InternalHandler internalHandler, Statement statement) {
        this.statement       = statement;
        this.internalHandler = internalHandler;
    }


    public void run() {
        statement.execute(internalHandler);
    }
}
