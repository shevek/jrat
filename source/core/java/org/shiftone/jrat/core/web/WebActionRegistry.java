package org.shiftone.jrat.core.web;

import org.shiftone.jrat.core.spi.WebActionFactory;
import org.shiftone.jrat.core.Environment;
import org.shiftone.jrat.http.Server;
import org.shiftone.jrat.http.WebActionHandler;
import org.shiftone.jrat.util.log.Logger;

/**
 * @author (jeff@shiftone.org) Jeff Drost
 */
public class WebActionRegistry {

    private static final Logger LOG = Logger.getLogger(WebActionRegistry.class);
//    private final Server server;
    private final WebActionHandler handler;

    public WebActionRegistry() {
        LOG.info("WebActionRegistry");
        handler = new WebActionHandler();

//        if (Environment.getSettings().isHttpServerEnabled()) {
//            LOG.info("starting HTTP server...");
//            server = new Server(Environment.getSettings().getHttpPort(), handler);
//
//            server.start();
//        } else {
//            server = null;
//        }
    }

    public void add(WebActionFactory webActionFactory) {
          LOG.info("adding " + webActionFactory.getTitle());
        handler.add(webActionFactory);
    }


}
