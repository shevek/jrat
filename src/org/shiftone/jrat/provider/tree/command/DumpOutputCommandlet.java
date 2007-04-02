package org.shiftone.jrat.provider.tree.command;

import org.shiftone.jrat.core.spi.Commandlet;
import org.shiftone.jrat.provider.tree.TreeMethodHandlerFactory;

import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Jeff Drost
 */
public class DumpOutputCommandlet implements Commandlet {
     private final TreeMethodHandlerFactory treeMethodHandlerFactory;

    public DumpOutputCommandlet(TreeMethodHandlerFactory treeMethodHandlerFactory) {
        this.treeMethodHandlerFactory = treeMethodHandlerFactory;
    }

    public void execute(OutputStream output) {
        PrintWriter out = new PrintWriter(output);
        treeMethodHandlerFactory.getRootNode().printXML(out);        
    }

    public String getContentType() {
        return ContentType.XML;
    }

    public String getTitle() {
        return "Dump Tree Statistics (xml)";
    }
}
