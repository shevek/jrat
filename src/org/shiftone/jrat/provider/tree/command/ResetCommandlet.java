package org.shiftone.jrat.provider.tree.command;

import org.shiftone.jrat.core.spi.Commandlet;
import org.shiftone.jrat.provider.tree.TreeMethodHandlerFactory;

import java.io.OutputStream;

/**
 * @author Jeff Drost
 */
public class ResetCommandlet implements Commandlet {

    private final TreeMethodHandlerFactory treeMethodHandlerFactory;

    public ResetCommandlet(TreeMethodHandlerFactory treeMethodHandlerFactory) {
        this.treeMethodHandlerFactory = treeMethodHandlerFactory;
    }

    public void execute(OutputStream output) {
        treeMethodHandlerFactory.reset();
    }

    public String getContentType() {
        return ContentType.PLAIN;
    }

    public String getTitle() {
        return "Reset Tree Statistics";
    }
}
