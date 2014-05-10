package org.shiftone.jrat.core.command;

import java.util.Collections;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import org.shiftone.jrat.core.spi.Commandlet;
import org.shiftone.jrat.util.AtomicLong;
import org.shiftone.jrat.util.log.Logger;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class CommandletRegistry {

    private static final Logger LOG = Logger.getLogger(CommandletRegistry.class);
    private final SortedMap commandlets = new TreeMap();
    private final AtomicLong sequence = new AtomicLong();
    private final Commandlet defaultCommandlet = new ListRegistryCommandlet(this);

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
