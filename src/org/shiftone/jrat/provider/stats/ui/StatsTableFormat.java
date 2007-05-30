package org.shiftone.jrat.provider.stats.ui;


import ca.odell.glazedlists.gui.TableFormat;
import org.shiftone.jrat.core.MethodKeyAccumulator;
import org.shiftone.jrat.util.log.Logger;
import org.shiftone.jrat.util.time.TimeUnit;


/**
 * @author Jeff Drost
 *
 */
public class StatsTableFormat implements TableFormat {

    private static final Logger LOG          = Logger.getLogger(StatsTableFormat.class);
    private String[]            COLUMN_NAMES =
    {
        "Class", "Method", "Signature", "Enters", "Exits", "Errors", "Total ms", "Avg ms", "Std Dev", "Min ms",
        "Max ms",
    };

    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }


    public String getColumnName(int i) {
        return COLUMN_NAMES[i];
    }


    public Object getColumnValue(Object object, int columnIndex) {

        MethodKeyAccumulator accumulator = (MethodKeyAccumulator) object;

        if (accumulator == null)
        {
            return null;
        }

        if (accumulator.getMethodKey() == null)
        {
            return "?method?";
        }

        switch (columnIndex)
        {

        case 0 :
            return accumulator.getMethodKey().getClassName();

        case 1 :
            return accumulator.getMethodKey().getMethodName();

        case 2 :
            return accumulator.getMethodKey().getSignature();

        case 3 :
            return new Long(accumulator.getTotalEnters());

        case 4 :
            return new Long(accumulator.getTotalExits());

        case 5 :
            return new Long(accumulator.getTotalErrors());

        case 6 :
            return new Long(accumulator.getTotalDuration(TimeUnit.MS));

        case 7 :
            return accumulator.getAverageDuration(TimeUnit.MS);

        case 8 :
            return accumulator.getStdDeviation();

        case 9 :
            return accumulator.getMinDuration(TimeUnit.MS);

        case 10 :
            return accumulator.getMaxDuration(TimeUnit.MS);
        }

        throw new IllegalStateException();
    }
}
