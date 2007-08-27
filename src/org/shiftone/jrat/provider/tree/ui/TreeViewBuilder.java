package org.shiftone.jrat.provider.tree.ui;

import org.shiftone.jrat.core.spi.ViewBuilder;
import org.shiftone.jrat.provider.tree.StackNode;
import org.shiftone.jrat.util.Assert;

import javax.swing.*;
import java.io.File;
import java.io.ObjectInputStream;
import java.util.Set;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class TreeViewBuilder implements ViewBuilder { //, Externalizable {

    private static final long serialVersionUID = 1;
    private StackNode root;
    private Set allMethodKeys;
    private long sessionStartMs;
    private long sessionEndMs;
    private String hostName;
    private String hostAddress;

    public TreeViewBuilder() {
    }

    public TreeViewBuilder(StackNode root, Set allMethodKeys, long sessionStartMs, long sessionEndMs, String hostName, String hostAddress) {

        Assert.assertNotNull(root);
        Assert.assertNotNull(allMethodKeys);
        Assert.assertNotNull(hostName);
        Assert.assertNotNull(hostAddress);

        this.root = root;
        this.allMethodKeys = allMethodKeys;
        this.sessionStartMs = sessionStartMs;
        this.sessionEndMs = sessionEndMs;
        this.hostName = hostName;
        this.hostAddress = hostAddress;
    }

    public JComponent buildView(ObjectInputStream input) throws Exception {
        return new MainViewPanel(root, allMethodKeys, sessionStartMs, sessionEndMs, hostName, hostAddress);
    }



//
//    public void writeExternal(ObjectOutput out) throws IOException {
//        root.writeExternal(out);
//    }
//
//    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
//        root = new StackNode();
//        root.readExternal(in);
//    }
}
