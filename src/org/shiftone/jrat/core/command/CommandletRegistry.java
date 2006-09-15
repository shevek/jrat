package org.shiftone.jrat.core.command;

import org.shiftone.jrat.core.spi.Commandlet;
import org.shiftone.jrat.util.AtomicLong;
import org.shiftone.jrat.util.log.Logger;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jeff Drost
 */
public class CommandletRegistry {

    private static final Logger LOG = Logger.getLogger(CommandletRegistry.class);
    private Map commandlets = new HashMap();
    private AtomicLong sequence = new AtomicLong();

    public CommandletRegistry() {
       commandlets.put("list", new ListRegistryCommandlet(this)); 
    }

    public void register(Commandlet commandlet) {
        LOG.info("register " + commandlet);
        commandlets.put(Long.toHexString(sequence.get()), commandlet);
    }

    public Map getCommandlets() {
        return Collections.unmodifiableMap(commandlets);
    }

}
