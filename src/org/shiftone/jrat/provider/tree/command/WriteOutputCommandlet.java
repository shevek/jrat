package org.shiftone.jrat.provider.tree.command;

import org.shiftone.jrat.core.spi.Commandlet;
import org.shiftone.jrat.provider.tree.TreeMethodHandlerFactory;
import org.shiftone.jrat.util.log.AbstractLogCommandlet;
import org.shiftone.jrat.util.log.Logger;

/**
 * @author Jeff Drost
 */
public class WriteOutputCommandlet extends AbstractLogCommandlet implements Commandlet {

    private static final Logger LOG = Logger.getLogger(WriteOutputCommandlet.class);
    private final TreeMethodHandlerFactory treeMethodHandlerFactory;

    public WriteOutputCommandlet(TreeMethodHandlerFactory treeMethodHandlerFactory) {
        this.treeMethodHandlerFactory = treeMethodHandlerFactory;
    }

    public void execute() {
        treeMethodHandlerFactory.writeOutputFile();
        LOG.info("output file written.");
    }


    public String getTitle() {
        return "Write Tree Output To File";
    }
}
