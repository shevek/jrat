package org.shiftone.jrat.provider.tree.ui.touch;


import org.shiftone.jrat.provider.tree.ui.StackTreeNode;
import org.shiftone.jrat.ui.UIConstants;
import org.shiftone.jrat.ui.util.JRatFrame;
import org.shiftone.jrat.util.Assert;
import org.shiftone.jrat.util.log.Logger;
import org.shiftone.jrat.util.swing.popup.TreePopupMouseAdaptor;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.tree.TreePath;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * @author Jeff Drost
 */
public class TouchGraphAction implements ActionListener, UIConstants {

    private static final Logger LOG = Logger.getLogger(TouchGraphAction.class);
    private TreePopupMouseAdaptor treePopupMouseAdaptor;

    public TouchGraphAction(TreePopupMouseAdaptor treePopupMouseAdaptor) {
        this.treePopupMouseAdaptor = treePopupMouseAdaptor;
    }


    public void actionPerformed(ActionEvent e) {

        TreePath treePath = treePopupMouseAdaptor.getTreePath();

        LOG.info("actionPerformed " + treePath);

        StackTreeNode nodeModel = (StackTreeNode) treePath.getLastPathComponent();

        if (nodeModel.isRootNode()) {
            return;    // todo
        }

        try {
            Assert.assertNotNull("nodeModel", nodeModel);
            new Thread(new BuildGraphRunnable(nodeModel, nodeModel.getMethodKey().toString())).start();
        }
        catch (Exception x) {
            LOG.error("error launching touchgraph", x);
        }
    }


    private class BuildGraphRunnable implements Runnable {

        private StackTreeNode nodeModel;
        private String title;

        public BuildGraphRunnable(StackTreeNode nodeModel, String title) {
            this.nodeModel = nodeModel;
            this.title = title;
        }


        public void run() {

            final JRatFrame frame = new JRatFrame(title);
            final Container container = frame.getContentPane();
            try {
                container.setLayout(new BorderLayout());

                final JLabel label = new JLabel("Building TouchGraph...");

                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setVerticalAlignment(SwingConstants.CENTER);
                container.add(label, BorderLayout.CENTER);
                frame.setVisible(true);
                container.setCursor(new Cursor(Cursor.WAIT_CURSOR));

                final StackTreeNodeGLPanel panel = new StackTreeNodeGLPanel(nodeModel);

                SwingUtilities.invokeLater(new Runnable() {

                    public void run() {

                        container.remove(label);
                        container.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        container.add(panel, BorderLayout.CENTER);
                        panel.setVisible(true);
                        container.setVisible(true);
                        frame.setVisible(true);
                        panel.repaint();
                        container.repaint();
                        frame.repaint();
                    }
                });
            } catch (Exception e) {
                LOG.error("error building TouchGraph", e);
            }
        }
    }
}
