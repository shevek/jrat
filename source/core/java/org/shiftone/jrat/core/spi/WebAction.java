package org.shiftone.jrat.core.spi;

import org.shiftone.jrat.http.Response;

/**
 * @author (jeff@shiftone.org) Jeff Drost
 */
public interface WebAction {

    public void execute(Response response) throws Exception;

}
