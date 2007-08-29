package org.shiftone.jrat.provider.tree.ui;

import org.shiftone.jrat.core.spi.ViewBuilder;
import org.shiftone.jrat.provider.tree.TreeNode;
import org.shiftone.jrat.util.Assert;

import javax.swing.*;
import java.io.ObjectInputStream;
import java.util.Properties;
import java.util.Set;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class TraceViewBuilder implements ViewBuilder { //, Externalizable {

    private static final long serialVersionUID = 1;
    private TreeNode root;
    private Set allMethodKeys;
    private long sessionStartMs;
    private long sessionEndMs;
    private Properties systemProperties;
    private String hostName;
    private String hostAddress;

    public TraceViewBuilder() {
    }

    public TraceViewBuilder(TreeNode root, Set allMethodKeys,
                           long sessionStartMs, long sessionEndMs,
                           Properties systemProperties,
                           String hostName, String hostAddress) {

        Assert.assertNotNull(root);
        Assert.assertNotNull(allMethodKeys);
        Assert.assertNotNull(systemProperties);
        Assert.assertNotNull(hostName);
        Assert.assertNotNull(hostAddress);

        this.root = root;
        this.allMethodKeys = allMethodKeys;
        this.sessionStartMs = sessionStartMs;
        this.sessionEndMs = sessionEndMs;
        this.systemProperties = systemProperties;
        this.hostName = hostName;
        this.hostAddress = hostAddress;
    }

    public JComponent buildView(ObjectInputStream input) throws Exception {

        return new TraceViewPanel(
                new TraceTreeNode(root),
                allMethodKeys,
                sessionStartMs, sessionEndMs,
                systemProperties,
                hostName, hostAddress);
    }

//
//    public void writeExternal(ObjectOutput out) throws IOException {
//        root.writeExternal(out);
//    }
//
//    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
//        root = new TreeNode();
//        root.readExternal(in);
//    }
}
