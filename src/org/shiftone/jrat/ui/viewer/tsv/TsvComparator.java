package org.shiftone.jrat.ui.viewer.tsv;



import org.shiftone.jrat.util.log.Logger;

import java.util.Comparator;


/**
 * Class TsvComparator
 *
 * @author $Author: jeffdrost $
 * @version $Revision: 1.9 $
 */
public class TsvComparator implements Comparator {

    private static final Logger LOG       = Logger.getLogger(TsvComparator.class);
    private int                 index     = 0;
    private boolean             ascending = true;

    /**
     * Constructor TsvComparator
     *
     *
     * @param index
     * @param ascending
     */
    public TsvComparator(int index, boolean ascending) {
        this.index     = index;
        this.ascending = ascending;
    }


    /**
     * Method compare
     */
    public int compare(Object o1, Object o2) {

        Object[]   a1         = (Object[]) o1;
        Object[]   a2         = (Object[]) o2;
        Comparable c1         = (Comparable) a1[index];
        Comparable c2         = (Comparable) a2[index];
        int        multiplier = (ascending)
                         ? 1
                         : -1;

        if ((c1 == null) && (c2 == null))
        {
            return 0;
        }

        if (c1 == null)
        {
            return -1 * multiplier;
        }

        if (c2 == null)
        {
            return 1 * multiplier;
        }

        return c1.compareTo(c2) * multiplier;
    }
}
