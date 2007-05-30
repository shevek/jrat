package org.shiftone.jrat.provider.rate.ui;


import org.shiftone.jrat.core.Accumulator;
import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.core.JRatException;
import org.shiftone.jrat.provider.rate.RateOutput;
import org.shiftone.jrat.ui.util.ColorSet;
import org.shiftone.jrat.util.StringUtil;
import org.shiftone.jrat.util.io.IOUtil;
import org.shiftone.jrat.util.log.Logger;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;


/**
 * Data structure holds all samples for a particular run. This is an in-memory
 * version of the output file.
 *
 * @author Jeff Drost
 *
 */
public class RateModel {

    private static final Logger LOG             = Logger.getLogger(RateModel.class);
    private int                 handlerMax      = 0;
    private long                rateStartTimeMs = 0;
    private long                sysStartTimeMs  = 0;
    private long                periodMs        = 0;
    private boolean             wasShutdown     = false;    // the handler was shutdown
    private List                samples         = null;
    private List                methodKeys      = null;


    public void load(InputStream inputStream) throws IOException {

        Reader           reader     = new InputStreamReader(inputStream);
        LineNumberReader lineReader = new LineNumberReader(reader);
        String           line       = null;

        lineReader.readLine();    // this class name, ignore it

        handlerMax      = Integer.parseInt(lineReader.readLine());
        sysStartTimeMs  = Long.parseLong(lineReader.readLine());
        rateStartTimeMs = Long.parseLong(lineReader.readLine());
        periodMs        = Long.parseLong(lineReader.readLine());
        wasShutdown     = false;
        samples         = new ArrayList();
        methodKeys      = new ArrayList();

        // at any point in an output file, a method or sample may be defined
        while ((line = lineReader.readLine()) != null)
        {
            processLine(line);
        }

        IOUtil.close(inputStream);
    }



    private void processLine(String line) {

        String[] tokens = StringUtil.tokenize(line, "\t", false);

        if (tokens.length < 3)
        {
            LOG.info("line only has " + tokens.length + " tokens");

            return;
        }
        else if (false == tokens[tokens.length - 1].equals(RateOutput.POSTFIX_END))
        {
            LOG.info("line does not end with END token : " + line);

            return;
        }
        else if (tokens[0].equals(RateOutput.PREFIX_METHOD))
        {
            addMethod(tokens);
        }
        else if (tokens[0].equals(RateOutput.PREFIX_SAMPLE))
        {
            addSample(tokens);
        }
        else if (tokens[0].equals(RateOutput.PREFIX_SHUTDOWN))
        {
            wasShutdown = true;
        }
    }



    private void addMethod(String[] tokens) {

        // 0 METHOD, 1 index, 2 class, 3 method, 4 signature 5 END
        if (tokens.length != 6)
        {
            throw new JRatException("error in format of method key : " + tokens);
        }

        methodKeys.add(Integer.parseInt(tokens[1]), new MethodKey(tokens[2], tokens[3], tokens[4]));
    }



    private void addSample(String[] tokens) {
        samples.add(new RateModelSample(tokens));
    }


    public int getSampleCount() {
        return samples.size();
    }



    public int getMethodCount() {
        return methodKeys.size();
    }



    public MethodKey getMethodKey(int index) {
        return (MethodKey) methodKeys.get(index);
    }


    /**
     * Method getMethodColor
     *
     * @param index .
     *
     * @return .
     */
    public Color getMethodColor(int index) {
        return ColorSet.COLOR_SET_5.getColor(index);
    }


    /**
     * Method getSample
     *
     * @param sampleNumber .
     *
     * @return .
     */
    public RateModelSample getSample(int sampleNumber) {
        return (RateModelSample) samples.get(sampleNumber);
    }


    /**
     * Method getAccumulator
     *
     * @param sampleNumber .
     * @param methodNumber .
     *
     * @return .
     */
    public Accumulator getAccumulator(int sampleNumber, int methodNumber) {
        return getSample(sampleNumber).getAccumulator(methodNumber);
    }


    /**
     * Method getFreeMemory
     *
     * @param sampleNumber .
     *
     * @return .
     */
    public long getFreeMemory(int sampleNumber) {
        return getSample(sampleNumber).getFreeMemory();
    }


    /**
     * Method getMaxMemory
     *
     * @param sampleNumber .
     *
     * @return .
     */
    public long getMaxMemory(int sampleNumber) {
        return getSample(sampleNumber).getMaxMemory();
    }


    /**
     * Method getMaxAverageDuration
     *
     * @param methodNumber .
     *
     * @return .
     */
    public int getMaxDuration(int methodNumber) {

        Long currentMax = null;
        int  max        = 0;

        for (int i = 0; i < getSampleCount(); i++)
        {
            currentMax = getAccumulator(i, methodNumber).getMaxDurationNanos();

            if (currentMax != null)
            {
                max = Math.max(max, currentMax.intValue());
            }
        }

        return max;
    }


    // --------------------------------------

    /**
     * Method getRateStartTimeMs
     *
     * @return .
     */
    public long getRateStartTimeMs() {
        return rateStartTimeMs;
    }


    /**
     * Method getSysStartTimeMs
     *
     * @return .
     */
    public long getSysStartTimeMs() {
        return sysStartTimeMs;
    }


    /**
     * Method isWasShutdown
     *
     * @return .
     */
    public boolean isWasShutdown() {
        return wasShutdown;
    }


    /**
     * Method getPeriodMs
     *
     * @return .
     */
    public long getPeriodMs() {
        return periodMs;
    }
}
