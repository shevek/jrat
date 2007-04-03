package org.shiftone.jrat.core.config;


import org.shiftone.jrat.util.log.Logger;
import org.shiftone.jrat.util.io.ResourceUtil;

import java.io.InputStream;
import java.io.File;

/**
 * @author Jeff Drost
 */
public class ConfigurationLoader {

	private static final Logger LOG = Logger.getLogger(ConfigurationLoader.class);
	private static Configuration configuration;
	public static final String CONFIG_FILE = "jrat.xml";


	public static synchronized Configuration getConfiguration() {

		if (configuration == null) {

			try {

				InputStream inputStream = ResourceUtil.loadResourceAsStream(CONFIG_FILE);

				configuration = ConfigurationParser.parse(inputStream);

			} catch (Exception e) {

				File cwd = new File("");

				LOG.info("unable to load configuration - using defaults",e);
				LOG.info("to configure JRat, create the file " + cwd.getAbsolutePath() + "/jrat.xml");

				configuration = new Configuration();

			}
		}

		return configuration;
	}
}
