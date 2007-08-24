package org.shiftone.jrat.api;


/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public interface Command {
    Object execute() throws Throwable;
}
