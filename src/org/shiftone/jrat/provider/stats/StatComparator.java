package org.shiftone.jrat.provider.stats;



import org.shiftone.jrat.util.log.Logger;

import java.util.Comparator;


/**
 * @author $Author: jeffdrost $
 * @version $Revision: 1.17 $
 */
public class StatComparator implements Comparator {

    private static final Logger    LOG      = Logger.getLogger(StatComparator.class);
    public static final Comparator INSTANCE = new StatComparator();

    public int compare(Object a, Object b) {

        StatMethodHandler handlerA = (StatMethodHandler) a;
        StatMethodHandler handlerB = (StatMethodHandler) b;
        int               ret;

        ret = convert(handlerA.getTotalDurationNanos() - handlerB.getTotalDurationNanos());

        if (ret == 0)
        {
            ret = convert(handlerA.getTotalExits() - handlerB.getTotalExits());
        }

        return ret;
    }


    /**
     * converts a long to the nearest int value
     */
    private static int convert(long input) {

        int result;

        if (input > Integer.MAX_VALUE)
        {
            result = Integer.MAX_VALUE;
        }
        else if (input < Integer.MIN_VALUE)
        {
            result = Integer.MIN_VALUE;
        }
        else
        {
            result = (int) input;
        }

        return result;
    }
}
