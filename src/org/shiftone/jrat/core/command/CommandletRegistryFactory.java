package org.shiftone.jrat.core.command;

import org.shiftone.jrat.core.Settings;
import org.shiftone.jrat.util.log.Logger;

/**
 * @author Jeff Drost
 */
public class CommandletRegistryFactory {
	private static final Logger LOG = Logger.getLogger(CommandletRegistryFactory.class);

	public static CommandletRegistry createCommandletRegistry() {

		CommandletRegistry registry = new CommandletRegistry();

		try {

			if (Settings.isHttpServerEnabled()) {
				TinyWebServer server = new TinyWebServer(registry);
				server.start();
			}

		} catch (Exception e) {
			LOG.error("failed to start tiny web server", e);
		}

		return registry;
	}
}
