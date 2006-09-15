package org.shiftone.jrat.simulate.stmt;



import org.shiftone.jrat.core.InternalHandler;


/**
 *
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.1 $
 */
public interface Statement {

    void execute(InternalHandler internalHandler);

    //void addChild(Statement statement);
}
