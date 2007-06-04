package org.shiftone.jrat.provider.tree.ui;

import org.shiftone.jrat.core.spi.ui.OutputViewBuilder;
import org.shiftone.jrat.core.spi.ui.ViewContext;
import org.shiftone.jrat.core.spi.ViewBuilder;
import org.shiftone.jrat.provider.tree.StackNode;

import javax.swing.JComponent;
import java.io.Serializable;
import java.io.Externalizable;
import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;

/**
 * @Author Jeff Drost
 */
public class TreeViewBuilder implements ViewBuilder, Externalizable {

    private StackNode root;

    public TreeViewBuilder() {
    }

    public TreeViewBuilder(StackNode root) {
        this.root = root;
    }

    public JComponent buildView() throws Exception {
        return new TreeViewerPanel(new StackTreeNode(root));
    }


    public void writeExternal(ObjectOutput out) throws IOException {
        root.writeExternal(out);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        root = new StackNode();
        root.readExternal(in);
    }
}
