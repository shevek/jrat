package org.shiftone.jrat.simulate.stmt;



import org.shiftone.jrat.core.InternalHandler;
import org.shiftone.jrat.util.log.Logger;

import java.util.ArrayList;
import java.util.List;


/**
 * Class ListStatement
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.2 $
 */
public class ListStatement implements Statement {

    private static final Logger LOG      = Logger.getLogger(ListStatement.class);
    private List             children = new ArrayList();

    /**
     * method
     *
     * @param statement .
     */
    public void addChild(Statement statement) {
        children.add(statement);
    }


    /**
     * method
     */
    public void execute(InternalHandler internalHandler) {

        for (int i = 0; i < children.size(); i++)
        {
            ((Statement) children.get(i)).execute(internalHandler);
        }
    }
}
