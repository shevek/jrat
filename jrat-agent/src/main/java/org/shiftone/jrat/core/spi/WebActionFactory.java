package org.shiftone.jrat.core.spi;

/**
 * @author (jeff@shiftone.org) Jeff Drost
 */
public interface WebActionFactory {

    WebAction createAction() throws Exception;

    String getTitle();

}
