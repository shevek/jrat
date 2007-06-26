package org.shiftone.jrat.provider.tree.ui;

import org.shiftone.jrat.provider.tree.StackNode;
import org.shiftone.jrat.provider.tree.ui.trace.TreeViewerPanel;
import org.shiftone.jrat.provider.tree.ui.hierarchy.GraphViewPanel;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;

/**
 * @Author Jeff Drost
 */
public class MainViewPanel extends JPanel {

    private final JTabbedPane tabbedPane = new JTabbedPane();
    private final StackNode rootNode;

    public MainViewPanel(StackNode rootNode) {
        this.rootNode = rootNode;
        setLayout(new BorderLayout());
        add(tabbedPane, BorderLayout.CENTER);

        tabbedPane.addTab("Trace", new TreeViewerPanel(rootNode));
        tabbedPane.addTab("Hierarchy", new GraphViewPanel(rootNode));
    }

}
