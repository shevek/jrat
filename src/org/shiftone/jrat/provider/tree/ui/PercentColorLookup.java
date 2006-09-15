package org.shiftone.jrat.provider.tree.ui;



import org.shiftone.jrat.ui.util.DotIcon;
import org.shiftone.jrat.util.log.Logger;

import javax.swing.Icon;

import java.awt.Color;


public class PercentColorLookup {

    private static final Logger LOG    = Logger.getLogger(PercentColorLookup.class);
    private Color[]             colors = new Color[100];
    private Icon[]              icons  = new Icon[100];
    private double              magic  = (double) 0xff / (double) (colors.length / 2);

    public PercentColorLookup() {

        LOG.info("magic " + magic);

        for (int i = 0; i < colors.length; i++)
        {
            int r = (int) Math.min(i * magic, 0xff);
            int g = (int) Math.min((100 - i) * magic, 0xff);
            int b = 0;

            colors[i] = new Color(r, g, b);
            icons[i]  = new DotIcon(10, colors[i]);

            // LOG.info(i + " = " + Integer.toHexString(r) + " " +
            // Integer.toHexString(g));
        }
    }


    public static void main(String[] args) {
        new PercentColorLookup();
    }


    private int getIndex(double pct) {
        return Math.min((int) Math.round(pct), colors.length - 1);
    }


    public Color getColor(double pct) {
        return colors[getIndex(pct)];
    }


    public Icon getIcon(double pct) {
        return icons[getIndex(pct)];
    }
}
