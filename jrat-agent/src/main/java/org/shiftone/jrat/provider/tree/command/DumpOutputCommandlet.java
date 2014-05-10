package org.shiftone.jrat.provider.tree.command;

import java.io.OutputStream;
import java.io.PrintWriter;
import org.shiftone.jrat.core.spi.Commandlet;
import org.shiftone.jrat.provider.tree.TreeMethodHandlerFactory;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class DumpOutputCommandlet implements Commandlet {

    private final TreeMethodHandlerFactory treeMethodHandlerFactory;

    public DumpOutputCommandlet(TreeMethodHandlerFactory treeMethodHandlerFactory) {
        this.treeMethodHandlerFactory = treeMethodHandlerFactory;
    }

    @Override
    public void execute(OutputStream output) {
        PrintWriter out = new PrintWriter(output);
        // treeMethodHandlerFactory.getRootNode().printXML(out);
    }

    @Override
    public String getContentType() {
        return ContentType.XML;
    }

    @Override
    public String getTitle() {
        return "Dump Tree Statistics (xml)";
    }
}
