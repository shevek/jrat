package org.shiftone.jrat.ui.util;


import org.shiftone.jrat.util.Percent;
import org.shiftone.jrat.util.log.Logger;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.text.DecimalFormat;


/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class PercentTableCellRenderer extends DefaultTableCellRenderer {

    private static final Logger LOG = Logger.getLogger(PercentTableCellRenderer.class);
    private Object value = null;
    private DecimalFormat floatDecimalFormat = new DecimalFormat("#,##0.00");
    private DecimalFormat doubleDecimalFormat = new DecimalFormat("#,##0.00");
    private DecimalFormat longDecimalFormat = new DecimalFormat("###,###,###");
    private static final Color COLOR_XOR = Color.LIGHT_GRAY;

    public PercentTableCellRenderer() {
        super();
    }


    public static void setDefaultRenderer(JTable table) {

        table.setDefaultRenderer(Object.class, new PercentTableCellRenderer());
        table.setDefaultRenderer(Percent.class, new PercentTableCellRenderer());
        table.setDefaultRenderer(Number.class, new PercentTableCellRenderer());
        table.setDefaultRenderer(Integer.class, new PercentTableCellRenderer());
        table.setDefaultRenderer(Long.class, new PercentTableCellRenderer());
        table.setDefaultRenderer(Double.class, new PercentTableCellRenderer());
        table.setDefaultRenderer(Float.class, new PercentTableCellRenderer());
    }


    /**
     * Method getTableCellRendererComponent
     */
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                   int row, int column) {

        this.value = value;

        if (value instanceof Number) {
            setHorizontalAlignment(JLabel.RIGHT);
        } else {
            setHorizontalAlignment(JLabel.LEFT);
        }

        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // return this;
    }


    /**
     * Method paint
     */
    public void paint(Graphics g) {

        super.paint(g);

        if (value instanceof Percent) {
            double pct = ((Percent) value).doubleValue();

            if (pct > 100.0) {
                pct = 100.0;
            }

            int w = (int) ((getWidth() * pct) / 100.0);

            g.setXORMode(COLOR_XOR);
            g.fillRect(0, 0, w, getHeight());
        }
    }


    /**
     * Method setValue. synchronized because DecimalFormat is not thread safe
     */
    protected synchronized void setValue(Object value) {

        if (value == null) {
            value = "";
        } else if (value instanceof Number) {
            Number num = (Number) value;

            if ((value instanceof Integer) || (value instanceof Long)) {
                value = longDecimalFormat.format(num);
            } else if (value instanceof Float) {
                Float f = (Float) value;

                if (f.isNaN() || f.isInfinite()) {
                    value = String.valueOf(f);
                } else {
                    value = floatDecimalFormat.format(num);
                }
            } else if (value instanceof Double) {
                Double d = (Double) value;

                if (d.isNaN() || d.isInfinite()) {
                    value = String.valueOf(d);
                } else {
                    value = doubleDecimalFormat.format(num);
                }
            }
        }

        super.setValue(value);
    }
}
