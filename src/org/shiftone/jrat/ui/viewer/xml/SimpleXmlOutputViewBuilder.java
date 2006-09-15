package org.shiftone.jrat.ui.viewer.xml;



import org.shiftone.jrat.core.spi.ui.AbstractOutputXmlViewBuilder;
import org.shiftone.jrat.util.log.Logger;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTree;

import java.util.Properties;


/**
 * @author $Author: jeffdrost $
 * @version $Revision: 1.6 $
 */
public class SimpleXmlOutputViewBuilder extends AbstractOutputXmlViewBuilder {

    private static final Logger LOG         = Logger.getLogger(SimpleXmlOutputViewBuilder.class);
    private SimpleXmlTreeNode   root        = null;
    private SimpleXmlTreeNode   currentNode = null;

    public JComponent endDocumentCreateViewer() throws Exception {

        JTree tree = new JTree(root);

        return new JScrollPane(tree);
    }


    public void endElement(String qName) throws Exception {
        currentNode = (SimpleXmlTreeNode) currentNode.getParent();
    }


    public void startDocument() throws Exception {
        root        = new SimpleXmlTreeNode();
        currentNode = root;
    }


    public void startElement(String qName, Properties atts) throws Exception {

        SimpleXmlTreeNode node = new SimpleXmlTreeNode(currentNode, qName, atts);

        currentNode.addChild(node);

        currentNode = node;
    }


    public void textElement(String text) throws Exception {}
}
