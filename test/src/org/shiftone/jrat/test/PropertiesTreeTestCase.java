package org.shiftone.jrat.test;



import junit.framework.TestCase;

import org.shiftone.jrat.util.PropertiesTree;
import org.shiftone.jrat.util.log.Logger;

import java.util.Properties;


/**
 * Class PropertiesTreeTestCase
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.2 $
 */
public class PropertiesTreeTestCase extends TestCase {

    private static final Logger LOG = Logger.getLogger(PropertiesTreeTestCase.class);

    /**
     * Method testSimple
     *
     * @throws Exception
     */
    public void testSimple() throws Exception {

        Properties origProps = new Properties();
        Properties newProps  = null;
        int        i         = 0;

        origProps.put("one.two.three", "value" + (i++));
        origProps.put("one.two.four", "value" + (i++));
        origProps.put("one.alpha.four", "value" + (i++));
        origProps.put("one.alpha.five", "value" + (i++));
        origProps.put("one.alpha.six.7.8.9", "value" + (i++));
        origProps.put("one.alpha.six.7.8.TARGET", "BULLSEYE");
        origProps.put("one.alpha.six.test", "value" + (i++));
        origProps.put("single", "value" + (i++));
        origProps.put("single.double", "value" + (i++));

        PropertiesTree tree = new PropertiesTree(origProps);

        tree.print(System.out);

        PropertiesTree.Node targetNode = tree.getNode("one.alpha.six.7.8.TARGET");
        PropertiesTree.Node sixNode    = tree.getNode("one.alpha.six");
        PropertiesTree.Node alphaNode  = tree.getNode("one.alpha");

        assertNotNull(targetNode);
        assertEquals("TARGET", targetNode.getKey());
        assertEquals("BULLSEYE", targetNode.getValue());

        //
        assertNotNull(sixNode);
        assertEquals(2, sixNode.getChildren().size());

        //
        assertNotNull(alphaNode);
        assertEquals(3, alphaNode.getChildren().size());

        //
        newProps = tree.getProperties();

        newProps.list(System.out);
        assertEquals(origProps, newProps);

        //
        alphaNode.createNode("newChild", "newChildValue");

        newProps = tree.getProperties();

        newProps.list(System.out);
        assertNotSame(origProps, newProps);
    }
}
