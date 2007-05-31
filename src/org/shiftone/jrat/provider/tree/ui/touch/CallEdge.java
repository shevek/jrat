package org.shiftone.jrat.provider.tree.ui.touch;


import com.touchgraph.graphlayout.Edge;
import com.touchgraph.graphlayout.Node;
import com.touchgraph.graphlayout.TGPanel;
import org.shiftone.jrat.util.log.Logger;

import java.awt.*;


/**
 * @author Jeff Drost
 */
public class CallEdge extends Edge {

    private static final Logger LOG = Logger.getLogger(TouchGraphAction.class);
    private Color color;

    public CallEdge(Node f, Node t, Color color) {

        super(f, t);

        this.color = color;
    }


    public static void paintArrow(Graphics g, int x1, int y1, int x2, int y2, Color c) {

        g.setColor(c);
        g.drawLine(x1, y1, x2, y2);
        if (true) {
            int x3 = x1;
            int y3 = y1;

            double dist = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
            if (dist > 10) {
                double adjustDistRatio = (dist - 10) / dist;
                x3 = (int) (x1 + (x2 - x1) * adjustDistRatio);
                y3 = (int) (y1 + (y2 - y1) * adjustDistRatio);
            }

            x3 = (int) ((x3 * 4 + x1) / 5.0);
            y3 = (int) ((y3 * 4 + y1) / 5.0);

            g.drawLine(x3, y3, x2, y2);
            g.drawLine(x1, y1, x3, y3);
            g.drawLine(x1 + 1, y1, x3, y3);
            g.drawLine(x1 + 2, y1, x3, y3);
            g.drawLine(x1 + 3, y1, x3, y3);
            g.drawLine(x1 + 4, y1, x3, y3);
            g.drawLine(x1 - 1, y1, x3, y3);
            g.drawLine(x1 - 2, y1, x3, y3);
            g.drawLine(x1 - 3, y1, x3, y3);
            g.drawLine(x1 - 4, y1, x3, y3);
            g.drawLine(x1, y1 + 1, x3, y3);
            g.drawLine(x1, y1 + 2, x3, y3);
            g.drawLine(x1, y1 + 3, x3, y3);
            g.drawLine(x1, y1 + 4, x3, y3);
            g.drawLine(x1, y1 - 1, x3, y3);
            g.drawLine(x1, y1 - 2, x3, y3);
            g.drawLine(x1, y1 - 3, x3, y3);
            g.drawLine(x1, y1 - 4, x3, y3);
        }
    }


    public void paint(Graphics g, TGPanel tgPanel) {

        Color c = (tgPanel.getMouseOverE() == this)
                ? color.brighter()
                : color;
        int x1 = (int) from.drawx;
        int y1 = (int) from.drawy;
        int x2 = (int) to.drawx;
        int y2 = (int) to.drawy;

        if (intersects(tgPanel.getSize())) {
            paintArrow(g, x1, y1, x2, y2, c);
        }
    }
}
