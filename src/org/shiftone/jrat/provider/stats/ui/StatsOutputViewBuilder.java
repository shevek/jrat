package org.shiftone.jrat.provider.stats.ui;



import ca.odell.glazedlists.BasicEventList;

import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.core.MethodKeyAccumulator;
import org.shiftone.jrat.core.spi.ui.OutputViewBuilder;
import org.shiftone.jrat.core.spi.ui.ViewContext;
import org.shiftone.jrat.provider.stats.StatOutput;
import org.shiftone.jrat.util.io.IOUtil;
import org.shiftone.jrat.util.io.csv.DelimitedReader;
import org.shiftone.jrat.util.log.Logger;

import java.io.BufferedReader;
import java.io.Reader;

import java.util.List;


public class StatsOutputViewBuilder implements OutputViewBuilder {

    private static final Logger LOG = Logger.getLogger(StatsOutputViewBuilder.class);

    public void buildView(ViewContext context) throws Exception {

        Reader reader = context.openReader();

        try
        {
            BufferedReader lineReader            = new BufferedReader(reader);
            List           methodKeyAccumulators = new BasicEventList();
            String         line;

            // throw the first 2 lines away
            // lineReader.readLine();
            lineReader.readLine();

            DelimitedReader delimitedReader = new DelimitedReader(lineReader, StatOutput.getDelimitedFormat());

            while (delimitedReader.next())
            {
                MethodKeyAccumulator accumulator = readAccumulator(delimitedReader);

                methodKeyAccumulators.add(accumulator);
            }

            context.setComponent(new StatsViewerPanel(methodKeyAccumulators));
        }
        finally
        {
            IOUtil.close(reader);
        }
    }


    private MethodKeyAccumulator readAccumulator(DelimitedReader record) {

        String    className        = record.getString(StatOutput.FIELD_CLASS);
        String    methodName       = record.getString(StatOutput.FIELD_METHOD);
        String    signature        = record.getString(StatOutput.FIELD_SIGNATURE);
        long      totalEnters      = record.getLong(StatOutput.FIELD_TOTAL_ENTERS);
        long      totalExits       = record.getLong(StatOutput.FIELD_TOTAL_EXITS);
        long      totalErrors      = record.getLong(StatOutput.FIELD_TOTAL_ERRORS);
        long      totalDuration    = record.getLong(StatOutput.FIELD_TOTAL_DURATION);
        long      totalOfSquares   = record.getLong(StatOutput.FIELD_SUM_OF_SQUARES);
        int       maxConcurThreads = (int) record.getLong(StatOutput.FIELD_MAX_CONCUR_THREADS);
        long      maxDuration      = toLong(record.getNumber(StatOutput.FIELD_MAX_DURATION));
        long      minDuration      = toLong(record.getNumber(StatOutput.FIELD_MIN_DURATION));
        MethodKey methodKey        = new MethodKey(className, methodName, signature);

        return new MethodKeyAccumulator(methodKey, totalEnters, totalExits, totalErrors, totalDuration, totalOfSquares,
                                        maxDuration, minDuration, maxConcurThreads);
    }


    private long toLong(Number n) {

        return (n == null)
               ? 0
               : n.longValue();
    }
}
