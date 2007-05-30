package org.shiftone.jrat.core;

import org.shiftone.jrat.core.JRatException;
import org.shiftone.jrat.core.config.Configuration;
import org.shiftone.jrat.core.config.ConfigurationParser;
import org.shiftone.jrat.core.config.Settings;
import org.shiftone.jrat.util.io.IOUtil;
import org.shiftone.jrat.util.io.ResourceUtil;
import org.shiftone.jrat.util.log.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

/**
 * @author Jeff Drost
 */
public class Environment {

    private static final Logger LOG = Logger.getLogger(Environment.class);
    private static final String DEFAULT = "default.xml";
    public static final Environment INSTANCE = new Environment();
    private Configuration configuration;


    public Environment() {

        File file = new File("jrat.xml");

        configuration = ConfigurationParser.parse(getConfigurationStream(file));

    }

    private InputStream getConfigurationStream(File file) {


        if (!file.exists()) {

            LOG.info("Initializing configuration file with default...");
            LOG.info("Edit this file to further configure JRat.");
            copyDefaultFile(file);

        } else {

            LOG.info("Using existing configuration file.");

        }

        LOG.info("Loading JRat Configuration : " + file.getAbsolutePath() + "...");
        LOG.info("File was last modified " + new Date(file.lastModified()));
        return IOUtil.openInputStream(file);
    }


    private void copyDefaultFile(File file) {

        try {

            InputStream defaultStream = ResourceUtil.loadResourceAsStream(DEFAULT);
            OutputStream outputStream = new FileOutputStream(file);
            IOUtil.copy(defaultStream, outputStream);

        } catch (Exception e) {

            throw new JRatException("unable to copy default configuration file to : " + file.getAbsolutePath(), e);

        }
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public Settings getSettings() {
        return getConfiguration().getSettings();
    }
}
