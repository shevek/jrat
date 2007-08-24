package org.shiftone.jrat.util.table;


import org.shiftone.jrat.util.log.Logger;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;


/**
 * Class Table
 *
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class Table {

    private static final Logger LOG = Logger.getLogger(Table.class);
    private int max = 0;
    private List rowList = new ArrayList();

    /**
     * Method addRow
     */
    public void addRow(Object[] row) {
        addRow(new ArrayTableRow(row));
    }


    /**
     * Method addRow
     */
    public void addRow(TableRow row) {

        max = Math.max(row.getValueCount(), max);

        rowList.add(row);
    }


    /**
     * Method print
     */
    public void print(PrintStream out) {

        int[] columnWidth = new int[max];
        TableCell[][] cells = new TableCell[rowList.size()][];

        for (int r = 0; r < rowList.size(); r++) {
            TableRow row = (TableRow) rowList.get(r);

            cells[r] = new TableCell[max];

            for (int c = 0; c < row.getValueCount(); c++) {
                cells[r][c] = new TableCell(row.getValueAt(c));
                columnWidth[c] = Math.max(cells[r][c].getMinWidth(), columnWidth[c]);
            }
        }

        for (int r = 0; r < cells.length; r++) {
            for (int c = 0; c < max; c++) {
                if (cells[r][c] != null) {
                    out.print(cells[r][c].toString(columnWidth[c]) + "  ");
                }
            }

            out.println();
        }
    }


    /**
     * Method main
     */
    public static void main(String[] args) {

        Table table = new Table();

        table.addRow(new Object[]{"test", "test2"});
        table.addRow(new Object[]{"sssssss", "zzzzz"});
        table.addRow(new Object[]{"323", "555"});
        table.addRow(new Object[]{new Integer(1), "adasdads"});
        table.addRow(new Object[]{new Integer(66), new Integer(66), new Integer(66)});
        table.addRow(new Object[]{new Integer(88), new Double(5.423)});
        table.print(System.out);
    }
}
