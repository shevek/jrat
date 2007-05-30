package org.shiftone.jrat.api;



/**
 * @author Jeff Drost
 */
public interface Command {
    Object execute() throws Throwable;
}
