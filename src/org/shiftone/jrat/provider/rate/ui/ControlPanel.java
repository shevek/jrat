package org.shiftone.jrat.provider.rate.ui;



import org.shiftone.jrat.ui.util.ColorTableCellRenderer;
import org.shiftone.jrat.util.StringUtil;
import org.shiftone.jrat.util.log.Logger;

import javax.swing.BorderFactory;
import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;


/**
 * Class ControlPanel
 *
 * @author $Author: jeffdrost $
 * @version $Revision: 1.18 $
 */
public class ControlPanel extends JPanel {

    private static final Logger    LOG               = Logger.getLogger(ControlPanel.class);
    private static final int       SPACING           = 10;
    private static final Dimension PS                = new Dimension(150, 150);
    private BoundedRangeModel      boundedRangeModel = new DefaultBoundedRangeModel(20, 5, 0, 50);
    private JComponent             rightPanel        = null;
    private JTable                 table             = null;
    private JScrollPane            scrollPane        = null;
    private RateModel              rateModel         = null;
    private JSlider                slider            = null;

    /**
     * Constructor ControlPanel
     *
     *
     * @param rateModel
     * @param tableModel
     */
    public ControlPanel(RateModel rateModel, RateModelTableModel tableModel) {

        this.rateModel  = rateModel;
        this.table      = new JTable(tableModel);
        this.scrollPane = new JScrollPane(table);
        this.rightPanel = new JPanel();

        //
        table.setDefaultRenderer(Color.class, new ColorTableCellRenderer());
        table.getColumnModel().getColumn(1).setMaxWidth(50);
        table.getColumnModel().getColumn(2).setMaxWidth(50);

        //
        setBorder(BorderFactory.createEmptyBorder(SPACING, SPACING, SPACING, SPACING));
        setLayout(new GridLayout(1, 2, SPACING, SPACING));

        //
        rightPanel.setLayout(new BorderLayout());
        rightPanel.add(createZoomPanel(), BorderLayout.EAST);
        rightPanel.add(createInfoPanel(), BorderLayout.CENTER);

        //
        add(scrollPane);
        add(rightPanel);
    }


    /**
     * Method getBoundedRangeModel
     */
    public BoundedRangeModel getBoundedRangeModel() {
        return boundedRangeModel;
    }


    /**
     * Method createInfoPanel
     */
    private JComponent createInfoPanel() {

        JPanel panel = new JPanel();

        panel.setLayout(new GridLayout(0, 2));

        //
        panel.add(new JLabel("Method Count"));
        panel.add(new JLabel(String.valueOf(rateModel.getMethodCount())));
        panel.add(new JLabel("Sample Count"));
        panel.add(new JLabel(String.valueOf(rateModel.getSampleCount())));
        panel.add(new JLabel("Period MS"));
        panel.add(new JLabel(StringUtil.durationToString(rateModel.getPeriodMs())));
        panel.add(new JLabel("System Start"));
        panel.add(new JLabel(StringUtil.dateToString(rateModel.getSysStartTimeMs())));
        panel.add(new JLabel("Handler Start"));
        panel.add(new JLabel(StringUtil.dateToString(rateModel.getRateStartTimeMs())));
        panel.add(new JLabel("Normal Shutdown"));
        panel.add(new JLabel(StringUtil.booleanToString(rateModel.isWasShutdown())));

        return panel;
    }


    /**
     * Method createZoomPanel
     */
    private JComponent createZoomPanel() {

        JPanel panel = new JPanel();

        panel.setLayout(new BorderLayout());

        slider = new JSlider(JSlider.VERTICAL);

        slider.setModel(boundedRangeModel);
        slider.setMajorTickSpacing(10);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        panel.add(new JLabel("Zoom"), BorderLayout.NORTH);
        panel.add(slider, BorderLayout.CENTER);

        return panel;
    }


    /**
     * Method getPreferredSize
     */
    public Dimension getPreferredSize() {
        return PS;
    }
}
