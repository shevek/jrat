package org.shiftone.jrat.provider.tree.command;

import org.shiftone.jrat.core.spi.Commandlet;
import org.shiftone.jrat.provider.tree.TreeMethodHandlerFactory;

import java.io.OutputStream;

/**
 * @author Jeff Drost
 */
public class WriteOutputCommandlet implements Commandlet {

    private final TreeMethodHandlerFactory treeMethodHandlerFactory;

    public WriteOutputCommandlet(TreeMethodHandlerFactory treeMethodHandlerFactory) {
        this.treeMethodHandlerFactory = treeMethodHandlerFactory;
    }

    public void execute(OutputStream output) {
      treeMethodHandlerFactory.writeOutputFile();
    }

    public String getContentType() {
        return ContentType.PLAIN;
    }

    public String getTitle() {
        return "Write Tree Output To File";
    }
}
