package org.shiftone.jrat.ui.util;


import org.shiftone.jrat.util.log.Logger;

import java.awt.*;
import java.util.Arrays;
import java.util.Comparator;


/**
 * Class ColorSet
 *
 * @author Jeff Drost
 */
public class ColorSet {

    private static final Logger LOG = Logger.getLogger(ColorSet.class);
    private static final ColorComparator COLOR_COMPARATOR = new ColorComparator();
    public static ColorSet COLOR_SET_3 = new ColorSet(3);
    public static ColorSet COLOR_SET_4 = new ColorSet(4);
    public static ColorSet COLOR_SET_5 = new ColorSet(5);
    private Color[] colors = null;

    /**
     * Constructor ColorSet
     *
     * @param cube
     */
    public ColorSet(int cube) {

        int i = 0;
        int d = 255 / (cube - 1);
        int[] c = new int[cube];

        colors = new Color[cube * cube * cube];

        for (int z = 0; z < cube; z++) {
            c[z] = z * d;
        }

        for (int r = 0; r < cube; r++) {
            for (int g = 0; g < cube; g++) {
                for (int b = 0; b < cube; b++) {
                    colors[i++] = new Color(c[r], c[g], c[b]);
                }
            }
        }

        Arrays.sort(colors, COLOR_COMPARATOR);
    }


    /**
     * Method getColorCount
     *
     * @return .
     */
    public int getColorCount() {
        return colors.length;
    }


    /**
     * Method getColor
     *
     * @param index .
     * @return .
     */
    public Color getColor(int index) {
        return colors[index % colors.length];
    }
}

/**
 * Class ColorComparator
 *
 * @author Jeff Drost
 */
class ColorComparator implements Comparator {

    /**
     * Method compare
     *
     * @param o1 .
     * @param o2 .
     * @return .
     */
    public int compare(Object o1, Object o2) {

        Color c1 = (Color) o1;
        Color c2 = (Color) o2;

        return colorValue(c1) - colorValue(c2);
    }


    /**
     * Method colorValue
     *
     * @param c .
     * @return .
     */
    private int colorValue(Color c) {
        return c.getRed() + c.getGreen() + c.getBlue();
    }
}
