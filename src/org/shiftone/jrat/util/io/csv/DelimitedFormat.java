package org.shiftone.jrat.util.io.csv;


import org.shiftone.jrat.core.JRatException;
import org.shiftone.jrat.util.io.csv.field.DateField;
import org.shiftone.jrat.util.io.csv.field.DoubleField;
import org.shiftone.jrat.util.io.csv.field.Field;
import org.shiftone.jrat.util.io.csv.field.LongField;
import org.shiftone.jrat.util.io.csv.field.StringField;
import org.shiftone.jrat.util.log.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Jeff Drost
 */
public class DelimitedFormat {

    private static final Logger LOG = Logger.getLogger(DelimitedFormat.class);
    private boolean mutable = true;
    private List fields = new ArrayList();
    private Map columnNameMapping = new HashMap();
    private char delimiter = ',';

    public void lock() {
        mutable = false;
    }


    private void assertMutable(boolean expected) {

        if (mutable != expected) {
            throw new RuntimeException("record format expected to be " + (mutable
                    ? "mutable"
                    : "immutable"));
        }
    }


    public boolean isMutable() {
        return mutable;
    }


    public char getDelimiter() {
        return delimiter;
    }


    public void setDelimiter(char delimiter) {

        assertMutable(true);

        this.delimiter = delimiter;
    }


    public int getFieldCount() {
        return fields.size();
    }


    public Field getField(int index) {
        return (Field) fields.get(index);
    }


    public int getFieldIndex(String title) {

        Integer index = (Integer) columnNameMapping.get(title.toLowerCase());

        if (index == null) {
            throw new JRatException("field title is not known : " + title);
        }

        return index.intValue();
    }


    public synchronized int addField(Field field) {

        assertMutable(true);
        fields.add(field);

        return fields.size() - 1;
    }


    public int addField(String title, Field field) {

        int index = addField(field);

        columnNameMapping.put(title.toLowerCase(), new Integer(index));

        return index;
    }


    public int addStringField() {
        return addField(StringField.INSTANCE);
    }


    public int addStringField(String title) {
        return addField(title, StringField.INSTANCE);
    }


    public int addDoubleField() {
        return addField(DoubleField.INSTANCE);
    }


    public int addDoubleField(String title) {
        return addField(title, DoubleField.INSTANCE);
    }


    public int addLongField() {
        return addField(LongField.INSTANCE);
    }


    public int addLongField(String title) {
        return addField(title, LongField.INSTANCE);
    }


    public int addDateField() {
        return addField(DateField.INSTANCE_ISO8601);
    }


    public int addDateField(String title) {
        return addField(title, DateField.INSTANCE_ISO8601);
    }


    public int addDateFormatField(String title, String dateFormat) {
        return addField(title, new DateField(dateFormat));
    }
}
