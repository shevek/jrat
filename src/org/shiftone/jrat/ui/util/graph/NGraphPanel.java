package org.shiftone.jrat.ui.util.graph;



import org.shiftone.jrat.ui.util.NSplitPane;
import org.shiftone.jrat.util.log.Logger;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;


/**
 * Class NGraphPanel is a swing component that contains N graphs on top of each
 * other, each in a resizable pane, and a single scroll bar to move them back
 * and forth.
 *
 * @author $Author: jeffdrost $
 * @version $Revision: 1.14 $
 */
public class NGraphPanel extends JPanel {

    private static final Logger LOG                    = Logger.getLogger(NGraphPanel.class);
    private static final int    SCROLL_UNIT_INCREMENT  = 50;
    private static final int    SCROLL_BLOCK_INCREMENT = 150;
    private NSplitPane          splitPane              = null;
    private JScrollBar          scrollBar              = null;
    private GraphComponent[]    graphComponents        = null;
    private JScrollPane[]       scrollPanes            = null;

    /**
     * Constructor NGraphPanel
     *
     *
     * @param graphCount
     */
    public NGraphPanel(int graphCount) {

        this.graphComponents = new GraphComponent[graphCount];
        this.scrollPanes     = new JScrollPane[graphCount];
        this.splitPane       = new NSplitPane(NSplitPane.VERTICAL_SPLIT);
        this.scrollBar       = new JScrollBar(JScrollBar.HORIZONTAL);

        scrollBar.setUnitIncrement(SCROLL_UNIT_INCREMENT);
        scrollBar.setBlockIncrement(SCROLL_BLOCK_INCREMENT);

        for (int i = 0; i < graphCount; i++)
        {
            graphComponents[i] = new GraphComponent();
            scrollPanes[i]     = new JScrollPane(graphComponents[i]);

            scrollPanes[i].setHorizontalScrollBar(scrollBar);
            splitPane.add(scrollPanes[i]);
        }

        setLayout(new BorderLayout());
        add(scrollBar, BorderLayout.SOUTH);
        add(splitPane, BorderLayout.CENTER);
        setBorder(BorderFactory.createEtchedBorder());
    }


    /**
     * Method getGraphComponents
     */
    public GraphComponent[] getGraphComponents() {
        return graphComponents;
    }


    /**
     * Method getGraphCount
     */
    public int getGraphCount() {
        return graphComponents.length;
    }


    /**
     * Method getGraph
     */
    public GraphComponent getGraph(int index) {
        return graphComponents[index];
    }
}
