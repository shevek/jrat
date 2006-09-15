package org.shiftone.jrat.ui.util;



import org.shiftone.jrat.util.log.Logger;

import java.util.Comparator;


/**
 * @author Jeff Drost
 * @version $Revision: 1.4 $
 */
public class NoOpComparator implements Comparator {

    private static final Logger    LOG      = Logger.getLogger(NoOpComparator.class);
    public static final Comparator INSTANCE = new NoOpComparator();

    public int compare(Object o1, Object o2) {
        return 0;
    }
}
