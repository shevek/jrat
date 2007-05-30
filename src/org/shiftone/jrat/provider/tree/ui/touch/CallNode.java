package org.shiftone.jrat.provider.tree.ui.touch;


import com.touchgraph.graphlayout.Node;
import com.touchgraph.graphlayout.TGPanel;
import org.shiftone.jrat.provider.tree.ui.StackTreeNode;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;


/**
 * @author Jeff Drost
 *
 */
public class CallNode extends Node {

    private StackTreeNode treeNode;
    private String[]      lines;
    private Color         colorDark;
    private Color         color;

    public CallNode(String id, String label, StackTreeNode treeNode, Color color) {

        super(id, label);

        this.treeNode  = treeNode;
        this.colorDark = color.darker().darker().darker();
        this.color     = color;
        this.lines     = new String[2];
        this.lines[0]  = treeNode.getMethodKey().getShortClassName();
        this.lines[1]  = treeNode.getMethodKey().getMethodName();
        font           = new Font("SansSerif", Font.PLAIN, 9);
    }


    public void paintNodeBody(Graphics g, TGPanel tgPanel) {

        g.setFont(font);

        fontMetrics = g.getFontMetrics();

        int ix = (int) drawx;
        int iy = (int) drawy;
        int h  = getHeight();
        int w  = getWidth();
        int x  = ix - (w / 2);
        int y  = iy - (h / 2);

        g.setColor(colorDark);
        g.fillRect(x, y, w, h);
        g.setColor(color);
        g.drawRect(x, y, w, h);

        Graphics gg = g.create(x + 1, y + 1, w - 1, h - 1);

        gg.setColor(Color.WHITE);

        for (int i = 0; i < lines.length; i++)
        {
            String line = lines[i];

            gg.drawString(line, 5, (1 + i) * (h / lines.length) - 4);
        }
    }


    public int getHeight() {

        if (fontMetrics != null)
        {
            return fontMetrics.getHeight() * lines.length;
        }
        else
        {
            return 6;
        }
    }


    public int getWidth() {

        if (fontMetrics != null)
        {
            int max = 0;

            for (int i = 0; i < lines.length; i++)
            {
                max = Math.max(fontMetrics.stringWidth(lines[i]), max);
            }

            return max + 12;
        }
        else
        {
            return 10;
        }
    }
}
