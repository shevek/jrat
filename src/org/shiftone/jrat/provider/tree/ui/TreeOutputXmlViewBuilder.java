package org.shiftone.jrat.provider.tree.ui;


import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.core.spi.ui.AbstractOutputXmlViewBuilder;
import org.shiftone.jrat.util.log.Logger;

import javax.swing.JComponent;
import java.util.Properties;


/**
 * Class TreeOutputXmlViewerFactory
 *
 * @author Jeff Drost
 */
public class TreeOutputXmlViewBuilder extends AbstractOutputXmlViewBuilder {

    private static final Logger LOG = Logger.getLogger(TreeOutputXmlViewBuilder.class);
    private StackTreeNode root = new StackTreeNode();
    private StackTreeNode currentNode = root;
    private InstanceCache stringCache = new InstanceCache();
    private InstanceCache methodKeyCache = new InstanceCache();

    public void startElement(String qName, Properties atts) throws Exception {

        if ("call".equals(qName)) {
            String c = (String) stringCache.cache(atts.getProperty("c"));
            String m = (String) stringCache.cache(atts.getProperty("m"));
            String s = (String) stringCache.cache(atts.getProperty("s"));
            MethodKey methodKey = (MethodKey) methodKeyCache.cache(new MethodKey(c, m, s));

            currentNode = (StackTreeNode) currentNode.getChild(methodKey);

            String minString = atts.getProperty("min");
            String maxString = atts.getProperty("max");
            long min = (minString != null)
                    ? Long.parseLong(minString)
                    : 0;
            long max = (maxString != null)
                    ? Long.parseLong(maxString)
                    : 0;

            // todo : remove the sos default - this just allows old XML files to
            // be read
            currentNode.setStatistics(Long.parseLong(atts.getProperty("ent")),            // totalEnters
                    Long.parseLong(atts.getProperty("xit")),            // totalExits
                    Long.parseLong(atts.getProperty("err")),            // totalErrors
                    Long.parseLong(atts.getProperty("dur")),            // totalDuration
                    Long.parseLong(atts.getProperty("sos", "0")),       // totalOfSquares
                    max,                                                // maxDuration
                    min,                                                // minDuration
                    Integer.parseInt(atts.getProperty("mct", "0")));    // <-

            // maxConcurThreads
        }
    }


    public void endElement(String qName) throws Exception {

        if ("call".equals(qName)) {
            currentNode.completeStats();
            currentNode = (StackTreeNode) currentNode.getParent();
        } else if ("view".equals(qName)) {
            currentNode.completeStats();
        }
    }


    public JComponent endDocumentCreateViewer() throws Exception {

        LOG.info("endDocumentCreateViewer");

        return new TreeViewerPanel(root, viewContext.getView());
    }


    public void startDocument() throws Exception {
    }


    public void textElement(String text) throws Exception {
    }
}
