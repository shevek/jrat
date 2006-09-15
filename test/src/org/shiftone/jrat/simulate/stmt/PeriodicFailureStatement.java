package org.shiftone.jrat.simulate.stmt;



import org.shiftone.jrat.core.InternalHandler;
import org.shiftone.jrat.util.log.Logger;


/**
 * Class PeriodicFailureStatement
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.2 $
 */
public class PeriodicFailureStatement implements Statement {

    private static final Logger LOG           = Logger.getLogger(PeriodicFailureStatement.class);
    private int              countDown     = 0;
    private int              failurePeriod = 100;

    /**
     * method
     *
     * @param failurePeriod .
     */
    public void setPeriod(int failurePeriod) {
        this.failurePeriod = failurePeriod;
        this.countDown     = failurePeriod;
    }


    /**
     * method
     *
     * @param internalHandler .
     *
     * @throws RuntimeException .
     */
    public void execute(InternalHandler internalHandler) {

        countDown--;

        if (countDown <= 0)
        {
            countDown = failurePeriod;

            throw new RuntimeException("PeriodicFailure");
        }
    }
}
