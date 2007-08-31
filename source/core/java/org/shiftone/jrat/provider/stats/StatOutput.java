package org.shiftone.jrat.provider.stats;


import org.shiftone.jrat.core.JRatException;
import org.shiftone.jrat.core.MethodKeyAccumulator;
import org.shiftone.jrat.provider.stats.ui.StatsOutputViewBuilder;
import org.shiftone.jrat.util.io.csv.DelimitedFormat;
import org.shiftone.jrat.util.io.csv.DelimitedWriter;

import java.io.IOException;
import java.io.Writer;


/**
 * @author $author$
 */
public class StatOutput {

    private static final DelimitedFormat FORMAT = new DelimitedFormat();
    public static final int FIELD_CLASS = FORMAT.addStringField();
    public static final int FIELD_METHOD = FORMAT.addStringField();
    public static final int FIELD_SIGNATURE = FORMAT.addStringField();
    public static final int FIELD_TOTAL_ENTERS = FORMAT.addLongField();
    public static final int FIELD_TOTAL_EXITS = FORMAT.addLongField();
    public static final int FIELD_TOTAL_ERRORS = FORMAT.addLongField();
    public static final int FIELD_TOTAL_DURATION = FORMAT.addLongField();
    public static final int FIELD_MIN_DURATION = FORMAT.addLongField();
    public static final int FIELD_MAX_DURATION = FORMAT.addLongField();
    public static final int FIELD_STD_DEVIATION = FORMAT.addDoubleField();
    public static final int FIELD_SUM_OF_SQUARES = FORMAT.addLongField();
    public static final int FIELD_MAX_CONCUR_THREADS = FORMAT.addLongField();
    private DelimitedWriter writer;

    public StatOutput(Writer writer) {

        try {
            writer.write("viewer=\"");
            writer.write(StatsOutputViewBuilder.class.getName());
            writer.write("\"\n");
        }
        catch (IOException e) {
            throw new JRatException("failed to write header", e);
        }

        this.writer = new DelimitedWriter(writer, getDelimitedFormat());
    }


    public synchronized void printStats(StatMethodHandler[] handlers, boolean recordUnused) {

        for (int i = 0; i < handlers.length; i++) {
            if ((recordUnused) || (handlers[i].getTotalEnters() > 0)) {
                printStat(handlers[i]);
            }
        }
    }


    private synchronized void printStat(MethodKeyAccumulator handler) {

        writer.setValue(FIELD_CLASS, handler.getMethodKey().getClassName());
        writer.setValue(FIELD_METHOD, handler.getMethodKey().getMethodName());
        writer.setValue(FIELD_SIGNATURE, handler.getMethodKey().getSignature());
        writer.setValue(FIELD_TOTAL_ENTERS, handler.getTotalEnters());
        writer.setValue(FIELD_TOTAL_EXITS, handler.getTotalExits());
        writer.setValue(FIELD_TOTAL_ERRORS, handler.getTotalErrors());
        writer.setValue(FIELD_TOTAL_DURATION, handler.getTotalDuration());
        writer.setValue(FIELD_MIN_DURATION, handler.getMinDuration());
        writer.setValue(FIELD_MAX_DURATION, handler.getMaxDuration());
        writer.setValue(FIELD_STD_DEVIATION, handler.getStdDeviation());
        writer.setValue(FIELD_SUM_OF_SQUARES, handler.getSumOfSquares());
        writer.setValue(FIELD_MAX_CONCUR_THREADS, handler.getMaxConcurrentThreads());
        writer.write();
    }


    public static DelimitedFormat getDelimitedFormat() {
        return FORMAT;
    }
}
