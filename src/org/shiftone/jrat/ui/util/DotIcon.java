package org.shiftone.jrat.ui.util;



import org.shiftone.jrat.util.log.Logger;

import javax.swing.Icon;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;


/**
 * Class DotIcon
 *
 * @author $Author: jeffdrost $
 * @version $Revision: 1.12 $
 */
public class DotIcon implements Icon {

    private static final Logger LOG   = Logger.getLogger(DotIcon.class);
    private int                 size  = 0;
    private Color               color = null;

    /**
     * Constructor DotIcon
     *
     *
     * @param size
     * @param color
     */
    public DotIcon(int size, Color color) {
        this.color = color;
        this.size  = size;
    }


    /**
     * Method getIconHeight
     */
    public int getIconHeight() {
        return size;
    }


    /**
     * Method getIconWidth
     */
    public int getIconWidth() {
        return size;
    }


    /**
     * Method paintIcon
     */
    public void paintIcon(Component c, Graphics g, int x, int y) {

        g.setColor(Color.gray);
        g.fillOval(x, y, size, size);
        g.setColor(color);
        g.fillOval(x + 1, y + 1, size - 2, size - 2);
    }
}
