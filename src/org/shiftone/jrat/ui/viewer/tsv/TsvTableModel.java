package org.shiftone.jrat.ui.viewer.tsv;


import org.shiftone.jrat.util.IntrospectionUtil;
import org.shiftone.jrat.util.Percent;
import org.shiftone.jrat.util.StringUtil;
import org.shiftone.jrat.util.log.Logger;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


/**
 * Class TsvTableModel
 *
 * @author Jeff Drost
 */
public class TsvTableModel implements TableModel {

    private static final Logger LOG = Logger.getLogger(TsvTableModel.class);
    public static final String PREFIX_LONG = "#";
    public static final String PREFIX_DOUBLE = "@";
    public static final String PREFIX_PERCENT = "%";
    private Object[][] rows = null;
    private String[] columnNames = null;
    private Class[] columnTypes = null;
    private Set listener = new HashSet();

    /**
     * Method load
     *
     * @param inputStream .
     * @throws IOException
     */
    public void load(InputStream inputStream) throws IOException {

        Reader reader = new InputStreamReader(inputStream);
        LineNumberReader lineReader = new LineNumberReader(reader);
        List list = new ArrayList();
        String line = null;

        lineReader.readLine();    // this class name, ignore it

        // read the column names (and types based on 1st char)
        columnNames = tokenize(lineReader.readLine());
        columnTypes = new Class[columnNames.length];

        for (int i = 0; i < columnNames.length; i++) {
            if (columnNames[i].startsWith(PREFIX_LONG)) {
                columnTypes[i] = Long.class;
                columnNames[i] = columnNames[i].substring(1);
            } else if (columnNames[i].startsWith(PREFIX_DOUBLE)) {
                columnTypes[i] = Double.class;
                columnNames[i] = columnNames[i].substring(1);
            } else if (columnNames[i].startsWith(PREFIX_PERCENT)) {
                columnTypes[i] = Percent.class;
                columnNames[i] = columnNames[i].substring(1);
            } else {
                columnTypes[i] = String.class;
            }
        }

        // read the data, convert to correct type
        while ((line = lineReader.readLine()) != null) {
            Object[] row = new Object[columnNames.length];
            String[] str = tokenize(line);

            for (int i = 0; i < Math.min(str.length, columnNames.length); i++) {
                row[i] = IntrospectionUtil.convertSafe(str[i], columnTypes[i]);
            }

            list.add(row);
        }

        // copy arrayList into array for easy sorting
        rows = new Object[list.size()][];

        for (int i = 0; i < list.size(); i++) {
            rows[i] = (Object[]) list.get(i);
        }
    }


    /**
     * Method tokenize
     *
     * @param string .
     * @return .
     */
    String[] tokenize(String string) {
        return StringUtil.tokenize(string, "\t,", false);
    }


    /**
     * Method addTableModelListener
     *
     * @param l .
     */
    public void addTableModelListener(TableModelListener l) {
        listener.add(l);
    }


    /**
     * Method removeTableModelListener
     *
     * @param l .
     */
    public void removeTableModelListener(TableModelListener l) {
        listener.remove(l);
    }


    /**
     * Method notifyListeners
     *
     * @param event .
     */
    public void notifyListeners(TableModelEvent event) {

        Iterator iterator = listener.iterator();

        while (iterator.hasNext()) {
            TableModelListener l = (TableModelListener) iterator.next();

            l.tableChanged(event);
        }
    }


    /**
     * Method getColumnClass
     *
     * @param columnIndex .
     * @return .
     */
    public Class getColumnClass(int columnIndex) {
        return String.class;
    }


    /**
     * Method getColumnCount
     *
     * @return .
     */
    public int getColumnCount() {
        return columnNames.length;
    }


    /**
     * Method getColumnName
     *
     * @param columnIndex .
     * @return .
     */
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }


    /**
     * Method getRowCount
     *
     * @return .
     */
    public int getRowCount() {
        return rows.length;
    }


    /**
     * Method getValueAt
     *
     * @param rowIndex    .
     * @param columnIndex .
     * @return .
     */
    public Object getValueAt(int rowIndex, int columnIndex) {

        Object[] row = rows[rowIndex];

        return row[columnIndex];
    }


    /**
     * Method isCellEditable
     *
     * @param rowIndex    .
     * @param columnIndex .
     * @return .
     */
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }


    /**
     * Method setValueAt
     *
     * @param aValue      .
     * @param rowIndex    .
     * @param columnIndex .
     */
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    }


    /**
     * Method sortByColumn
     *
     * @param column    .
     * @param ascending .
     */
    public void sortByColumn(int column, boolean ascending) {

        LOG.info("sortByColumn " + column + (ascending
                ? " assending"
                : " decending"));
        Arrays.sort(rows, new TsvComparator(column, ascending));
        notifyListeners(new TableModelEvent(this));
    }
}
