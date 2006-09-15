package org.shiftone.jrat.ui.viewer.tsv;



import org.shiftone.jrat.core.spi.ui.OutputViewBuilder;
import org.shiftone.jrat.core.spi.ui.ViewContext;
import org.shiftone.jrat.ui.util.PercentTableCellRenderer;
import org.shiftone.jrat.util.Percent;
import org.shiftone.jrat.util.io.IOUtil;
import org.shiftone.jrat.util.log.Logger;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

import java.io.InputStream;


/**
 * @author $Author: jeffdrost $
 * @version $Revision: 1.6 $
 */
public class SimpleTsvOutputViewBuilder implements OutputViewBuilder {

    private static final Logger LOG = Logger.getLogger(SimpleTsvOutputViewBuilder.class);

    public void buildView(ViewContext context) throws Exception {

        InputStream   inputStream = null;
        TsvTableModel model       = new TsvTableModel();

        try
        {
            inputStream = context.openInputStream();

            model.load(inputStream);
        }
        finally
        {
            IOUtil.close(inputStream);
        }

        JTable       table      = new JTable(model);
        JScrollPane  scrollPane = new JScrollPane(table);
        JTableHeader header     = table.getTableHeader();

        table.setDefaultRenderer(Percent.class, new PercentTableCellRenderer());
        table.setDefaultRenderer(Integer.class, new PercentTableCellRenderer());
        table.setDefaultRenderer(Long.class, new PercentTableCellRenderer());
        table.setDefaultRenderer(Double.class, new PercentTableCellRenderer());
        table.setDefaultRenderer(Float.class, new PercentTableCellRenderer());

        //
        table.setColumnSelectionAllowed(false);
        header.addMouseListener(new TsvMouseAdapter(table));
        context.setComponent(scrollPane);
    }
}
