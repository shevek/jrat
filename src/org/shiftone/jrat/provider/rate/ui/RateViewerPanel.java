package org.shiftone.jrat.provider.rate.ui;


import org.shiftone.jrat.provider.rate.ui.graph.MaxMemoryGraph;
import org.shiftone.jrat.provider.rate.ui.graph.MeanDurationGraph;
import org.shiftone.jrat.provider.rate.ui.graph.ThroughputGraph;
import org.shiftone.jrat.provider.rate.ui.graph.UsedMemoryGraph;
import org.shiftone.jrat.ui.util.graph.GraphComponent;
import org.shiftone.jrat.ui.util.graph.GraphModelSet;
import org.shiftone.jrat.ui.util.graph.NGraphPanel;
import org.shiftone.jrat.ui.util.graph.SpacingChangeListener;
import org.shiftone.jrat.util.log.Logger;

import javax.swing.JPanel;
import javax.swing.event.ChangeListener;
import java.awt.BorderLayout;


/**
 * Class RateViewerPanel
 *
 * @author Jeff Drost
 *
 */
public class RateViewerPanel extends JPanel {

    private static final Logger LOG          = Logger.getLogger(RateViewerPanel.class);
    private NGraphPanel         graphPanel   = null;
    private ControlPanel        controlPanel = null;
    private RateModelTableModel tableModel   = null;

    /**
     * Constructor RateViewerPanel
     *
     *
     * @param rateModel
     */
    public RateViewerPanel(RateModel rateModel) {

        GraphComponent[] graphs        = null;
        GraphModelSet[]  graphSets     = null;
        ChangeListener[] spaceListener = null;

        tableModel   = new RateModelTableModel(rateModel);
        controlPanel = new ControlPanel(rateModel, tableModel);
        graphPanel   = new NGraphPanel(3);

        //
        graphs        = graphPanel.getGraphComponents();
        graphSets     = new GraphModelSet[graphs.length];
        spaceListener = new ChangeListener[graphs.length];

        for (int i = 0; i < graphs.length; i++)
        {
            graphSets[i]     = graphs[i].getGraphModelSet();
            spaceListener[i] = new SpacingChangeListener(graphs[i]);

            controlPanel.getBoundedRangeModel().addChangeListener(spaceListener[i]);
        }

        graphSets[0].setTitle("completions during sample (throughput)");
        graphSets[1].setTitle("mean duration during sample");
        graphSets[2].setTitle("memory");

        //
        graphSets[2].add("used", new UsedMemoryGraph(rateModel));
        graphSets[2].add("max", new MaxMemoryGraph(rateModel));

        for (int i = 0; i < rateModel.getMethodCount(); i++)
        {
            graphSets[0].add(new Integer(i), new MeanDurationGraph(rateModel, i));
            graphSets[1].add(new Integer(i), new ThroughputGraph(rateModel, i));
        }

        tableModel.addTableModelListener(new RateTableModelListener(tableModel, graphSets[0]));
        tableModel.addTableModelListener(new RateTableModelListener(tableModel, graphSets[1]));

        //
        setLayout(new BorderLayout());
        add(controlPanel, BorderLayout.SOUTH);
        add(graphPanel, BorderLayout.CENTER);
    }
}
