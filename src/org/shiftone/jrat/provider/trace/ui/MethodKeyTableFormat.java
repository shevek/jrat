package org.shiftone.jrat.provider.trace.ui;



import ca.odell.glazedlists.gui.TableFormat;

import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.util.log.Logger;


/**
 * @author Jeff Drost
 * @version $Revision: 1.4 $
 */
public class MethodKeyTableFormat implements TableFormat {

    private static final Logger LOG          = Logger.getLogger(MethodKeyTableFormat.class);
    private String[]            COLUMN_NAMES = { "Class", "Method", "Parameters", "Return Type" };

    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }


    public String getColumnName(int i) {
        return COLUMN_NAMES[i];
    }


    public Object getColumnValue(Object object, int columnIndex) {

        MethodKey methodKey = (MethodKey) object;

        if (methodKey == null)
        {
            return null;
        }

        switch (columnIndex)
        {

        case 0 :
            return methodKey.getClassName();

        case 1 :
            return methodKey.getMethodName();

        case 2 :
            return methodKey.getSig().getLongText();

        case 3 :
            return methodKey.getSig().getReturnType();
        }

        throw new IllegalStateException();
    }
}
