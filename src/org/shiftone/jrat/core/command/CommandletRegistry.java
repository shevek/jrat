package org.shiftone.jrat.core.command;

import org.shiftone.jrat.core.spi.Commandlet;
import org.shiftone.jrat.util.AtomicLong;
import org.shiftone.jrat.util.log.Logger;

import java.util.Collections;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author Jeff Drost
 */
public class CommandletRegistry {

    private static final Logger LOG = Logger.getLogger(CommandletRegistry.class);
    private SortedMap commandlets = new TreeMap();
    private AtomicLong sequence = new AtomicLong();
    private Commandlet defaultCommandlet = new ListRegistryCommandlet(this);


    public CommandletRegistry() {
        register(defaultCommandlet);
        register(new SystemPropertiesCommandlet());
    }

    public void register(Commandlet commandlet) {
        LOG.info("register " + commandlet);
        commandlets.put(Long.toHexString(sequence.incrementAndGet()), commandlet);
    }

    public Map getCommandlets() {
        return Collections.unmodifiableMap(commandlets);
    }

    public Commandlet getDefaultCommandlet() {
        return defaultCommandlet;
    }
}
