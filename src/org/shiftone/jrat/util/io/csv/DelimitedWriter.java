package org.shiftone.jrat.util.io.csv;


 
import org.shiftone.jrat.util.io.csv.field.Field;
import org.shiftone.jrat.util.log.Logger;
import org.shiftone.jrat.core.JRatException;

import java.io.IOException;
import java.io.Writer;


/**
 * @author Jeff Drost
 *
 */
public class DelimitedWriter {

    private static final Logger   LOG = Logger.getLogger(DelimitedWriter.class);
    private final DelimitedFormat delimitedFormat;
    private final Writer          writer;
    private String[]              current;

    public DelimitedWriter(Writer writer, DelimitedFormat delimitedFormat) {

        this.writer          = writer;
        this.delimitedFormat = delimitedFormat;

        this.delimitedFormat.lock();

        this.current = new String[delimitedFormat.getFieldCount()];
    }


    public void write() {

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < current.length; i++)
        {
            if (i != 0)
            {
                sb.append(delimitedFormat.getDelimiter());
            }

            if (current[i] != null)
            {
                sb.append(current[i]);
            }
        }

        sb.append("\n");

        try
        {
            writer.write(sb.toString());
        }
        catch (IOException e)
        {
            throw new JRatException("failed to record", e);
        }

        current = new String[delimitedFormat.getFieldCount()];
    }


    public void setValue(int fieldIndex, Object value) {

        Field field = delimitedFormat.getField(fieldIndex);

        current[fieldIndex] = field.format(value);
    }


    public void setValue(String fieldTitle, Object value) {

        int fieldIndex = delimitedFormat.getFieldIndex(fieldTitle);

        setValue(fieldIndex, value);
    }


    public void setValue(int fieldIndex, long value) {
        setValue(fieldIndex, new Long(value));
    }


    public void setValue(String fieldTitle, long value) {
        setValue(fieldTitle, new Long(value));
    }


    public void setValue(int fieldIndex, double value) {
        setValue(fieldIndex, new Double(value));
    }


    public void setValue(String fieldTitle, double value) {
        setValue(fieldTitle, new Double(value));
    }


    public void setValue(int fieldIndex, boolean value) {
        setValue(fieldIndex, Boolean.valueOf(value));
    }


    public void setValue(String fieldTitle, boolean value) {
        setValue(fieldTitle, Boolean.valueOf(value));
    }
}
