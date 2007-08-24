package org.shiftone.jrat.util.io.csv;


import org.shiftone.jrat.core.JRatException;
import org.shiftone.jrat.util.io.IOUtil;
import org.shiftone.jrat.util.io.csv.field.Field;
import org.shiftone.jrat.util.log.Logger;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.Date;
import java.util.StringTokenizer;


/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class DelimitedReader {

    private static final Logger LOG = Logger.getLogger(DelimitedReader.class);
    private String[] current;
    private final LineNumberReader lineReader;
    private final DelimitedFormat delimitedFormat;
    private final String delimiter;

    public DelimitedReader(Reader reader, DelimitedFormat delimitedFormat) {

        this.delimitedFormat = delimitedFormat;

        this.delimitedFormat.lock();

        this.delimiter = String.valueOf(delimitedFormat.getDelimiter());

        if (reader instanceof LineNumberReader) {
            this.lineReader = (LineNumberReader) reader;
        } else {
            this.lineReader = new LineNumberReader(reader);
        }
    }


    public boolean next() throws IOException {

        current = readRecord();

        return current != null;
    }


    public int getLineNumber() {
        return lineReader.getLineNumber();
    }


    private synchronized String[] readRecord() throws IOException {

        String[] values = null;
        String line = lineReader.readLine();

        if (line != null) {
            StringTokenizer st = new StringTokenizer(line, delimiter, true);

            values = new String[delimitedFormat.getFieldCount()];

            int i = 0;

            while (st.hasMoreTokens()) {
                String token = st.nextToken();

                if (token.equals(delimiter)) {
                    i++;
                } else {
                    values[i] = token;
                }
            }
        }

        return values;
    }


    public void close() {
        IOUtil.close(lineReader);
    }


    // -----------------------------------------------------
    public Object getValue(int columnIndex) {
        return getValue(columnIndex, true);
    }


    public Object getValue(int columnIndex, boolean nullable) {

        if (current == null) {
            throw new JRatException("current record is null");
        }

        Field field = delimitedFormat.getField(columnIndex);
        String textValue = current[columnIndex];
        Object value = field.parse(textValue);

        if ((value == null) && (!nullable)) {
            throw new JRatException("value is null on line " + getLineNumber() + " column " + columnIndex);
        }

        return value;
    }


    public Object getValue(String columnName) {
        return getValue(columnName, true);
    }


    public Object getValue(String columnName, boolean nullable) {

        int index = delimitedFormat.getFieldIndex(columnName);

        return getValue(index);
    }


    public String getString(int columnIndex) {
        return (String) getValue(columnIndex);
    }


    public String getString(String columnName) {
        return (String) getValue(columnName);
    }


    public Number getNumber(int columnIndex) {
        return getNumber(columnIndex, true);
    }


    private Number getNumber(int columnIndex, boolean nullable) {
        return (Number) getValue(columnIndex, nullable);
    }


    public Number getNumber(String columnName) {
        return getNumber(columnName, true);
    }


    private Number getNumber(String columnName, boolean nullable) {
        return (Number) getValue(columnName, nullable);
    }


    public Date getDate(int columnIndex) {
        return (Date) getValue(columnIndex);
    }


    public Date getDate(String columnName) {
        return (Date) getValue(columnName);
    }


    public long getLong(int columnIndex) {
        return getNumber(columnIndex, false).longValue();
    }


    public long getLong(String columnName) {
        return getNumber(columnName, false).longValue();
    }


    public double getDouble(int columnIndex) {
        return getNumber(columnIndex, false).doubleValue();
    }


    public double getDouble(String columnName) {
        return getNumber(columnName, false).doubleValue();
    }


    public boolean getBoolean(int columnIndex) {

        Boolean value = (Boolean) getValue(columnIndex, false);

        return value.booleanValue();
    }


    public boolean getBoolean(String columnName) {

        Boolean value = (Boolean) getValue(columnName, false);

        return value.booleanValue();
    }
}
