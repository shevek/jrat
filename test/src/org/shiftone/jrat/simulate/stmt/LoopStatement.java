package org.shiftone.jrat.simulate.stmt;



import org.shiftone.jrat.core.InternalHandler;

import java.util.ArrayList;
import java.util.List;


/**
 * Class LoopSimCall
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.1 $
 */
public class LoopStatement extends ListStatement {

    private List children   = new ArrayList();
    private int  iterations = 1;

    /**
     * method
     *
     * @return .
     */
    public int getIterations() {
        return iterations;
    }


    /**
     * method
     *
     * @param iterations .
     */
    public void setIterations(int iterations) {
        this.iterations = iterations;
    }


    /**
     * method
     */
    public void execute(InternalHandler internalHandler) {

        int loop = getIterations();

        for (int i = 0; i < loop; i++)
        {
            super.execute(internalHandler);
        }
    }
}
