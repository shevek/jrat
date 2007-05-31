package org.shiftone.jrat.provider.trace.ui;


import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.ui.util.DotIcon;
import org.shiftone.jrat.util.log.Logger;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;


/**
 * @author Jeff Drost
 */
public class TraceTreeCellRenderer extends DefaultTreeCellRenderer implements TreeCellRenderer {

    private static final Logger LOG = Logger.getLogger(TraceTreeCellRenderer.class);
    private static Icon ICON_ROOT = new DotIcon(9, Color.DARK_GRAY);
    private static Icon ICON_METHOD = new DotIcon(9, Color.ORANGE);
    private TraceNode node;

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
                                                  boolean leaf, int row, boolean hasFocus) {

        this.node = (TraceNode) value;
        this.hasFocus = hasFocus;
        this.selected = sel;

        Icon icon;

        if (node.isRootNode()) {
            icon = ICON_ROOT;
        } else {
            icon = ICON_METHOD;
        }

        setIcon(icon);

        MethodKey methodKey = node.getMethodKey();
        String text;

        if (methodKey == null) {
            text = "";
        } else {
            text = methodKey.getClassName() + "." + methodKey.getMethodName() + methodKey.getPrettySignature() + " - "
                    + node.getDurationMs() + "ms";
        }

        setText(text);

        if (selected) {
            setForeground(Color.white);
        } else {
            setForeground(Color.black);
        }

        if (!tree.isEnabled()) {
            setEnabled(false);
            setDisabledIcon(icon);
        } else {
            setEnabled(true);
            setIcon(icon);
        }

        setComponentOrientation(tree.getComponentOrientation());

        return this;
    }
}
