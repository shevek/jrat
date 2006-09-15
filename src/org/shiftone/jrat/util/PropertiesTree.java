package org.shiftone.jrat.util;



import org.shiftone.jrat.core.ConfigurationException;
import org.shiftone.jrat.util.log.Logger;

import java.io.PrintStream;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;


/**
 * Class PropertiesTree holds a property file's data in a tree data structure,
 * making it easier to traverse, expecially when working with nodes with
 * children.
 *
 * @author $Author: jeffdrost $
 * @version $Revision: 1.23 $
 */
public class PropertiesTree {

    private static final Logger LOG  = Logger.getLogger(PropertiesTree.class);
    private Node                root = null;

    public PropertiesTree() {
        initialize(new Properties());
    }


    public PropertiesTree(Properties properties) {
        initialize(properties);
    }


    private void initialize(Properties properties) {

        Assert.assertNotNull("properties", properties);

        this.root = new Node("ROOT", null);

        Enumeration keys = properties.keys();

        while (keys.hasMoreElements())
        {
            String key   = (String) keys.nextElement();
            String value = properties.getProperty(key);

            root.createNode(key, value);
        }
    }


    public Properties getProperties() {

        Properties properties = new Properties();

        populateProperties(properties, root, "");

        return properties;
    }


    private void populateProperties(Properties properties, Node node, String prefix) {

        Collection children = node.getChildren();
        Iterator   i        = children.iterator();

        while (i.hasNext())
        {
            Node   child = (Node) i.next();
            String key   = prefix + child.getKey();
            String value = child.getValue();

            if (value != null)
            {
                properties.setProperty(key, value);
            }

            populateProperties(properties, child, key + ".");
        }
    }


    public Node getRoot() {
        return root;
    }


    public Node getNode(String key) throws ConfigurationException {
        return root.getNode(key);
    }


    /**
     * Prints out the tree in a cheezy XML like format
     */
    public void print(PrintStream out) {
        print(out, root, 0);
    }


    private void print(PrintStream out, Node node, int indentLevel) {

        Collection children = node.getChildren();

        if (children.size() == 0)
        {
            out.println(StringUtil.bufferString(indentLevel, '\t') + "<" + node.getKey() + " value=\""
                        + node.getValue() + "\"/>");
        }
        else
        {
            out.println(StringUtil.bufferString(indentLevel, '\t') + "<" + node.getKey() + " value=\""
                        + node.getValue() + "\">");

            Iterator i = children.iterator();

            while (i.hasNext())
            {
                print(out, (Node) i.next(), indentLevel + 1);
            }

            out.println(StringUtil.bufferString(indentLevel, '\t') + "</" + node.getKey() + ">");
        }
    }


    public static class Node {

        private Map    children = new HashMap();
        private String key      = null;
        private String value    = null;

        public Node(String key, String value) {
            this.value = value;
            this.key   = key;
        }


        public String getKey() {
            return key;
        }


        public String getValue() {
            return value;
        }


        public Collection getChildren() {
            return children.values();
        }


        public Node getNode(String key) throws ConfigurationException {
            return getNode(StringUtil.tokenize(key, ".", false), 0);
        }


        private Node getNode(String[] keyParts, int partIndex) throws ConfigurationException {

            if (keyParts.length == partIndex)
            {
                return this;
            }
            else
            {
                String part      = keyParts[partIndex];
                Node   nextChild = (Node) children.get(part);

                if (nextChild == null)
                {
                    throw new ConfigurationException("node not found : " + fullKey(keyParts, partIndex));
                }

                return nextChild.getNode(keyParts, partIndex + 1);
            }
        }


        private String fullKey(String[] keyParts, int partIndex) {

            String fullKey = keyParts[0];

            for (int i = 1; i < partIndex; i++)
            {
                fullKey += ("." + keyParts[i]);
            }

            return fullKey;
        }


        public void createNode(String key, String value) {
            createNode(StringUtil.tokenize(key, ".", false), 0, value);
        }


        private void createNode(String[] keyParts, int partIndex, String value) {

            if (keyParts.length == partIndex)
            {
                this.value = value;
            }
            else
            {
                Node   nextChild = null;
                String part      = keyParts[partIndex];

                if (children.containsKey(part))
                {
                    nextChild = (Node) children.get(part);
                }
                else
                {
                    nextChild = new Node(part, null);

                    children.put(part, nextChild);
                }

                nextChild.createNode(keyParts, partIndex + 1, value);
            }
        }
    }
}
