package org.shiftone.jrat.core.command;

import org.shiftone.jrat.core.Environment;
import org.shiftone.jrat.util.log.Logger;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class CommandletRegistryFactory {

    private static final Logger LOG = Logger.getLogger(CommandletRegistryFactory.class);

    public static CommandletRegistry createCommandletRegistry() {

        CommandletRegistry registry = new CommandletRegistry();

        try {

            if (Environment.getSettings().isHttpServerEnabled()) {

                LOG.info("Starting tiny web server...");
//                TinyWebServer server = new TinyWebServer(
//                        registry,
//                        Environment.getSettings().getHttpPort());
//
//                server.start();

            }

        } catch (Exception e) {

            LOG.error("failed to start tiny web server", e);

        }

        return registry;
    }
}
