package org.shiftone.jrat.api;



/**
 * @author $Author: jeffdrost $
 * @version $Revision: 1.4 $
 */
public interface Command {
    Object execute() throws Throwable;
}
