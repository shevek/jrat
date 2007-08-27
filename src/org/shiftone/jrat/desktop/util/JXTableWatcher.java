package org.shiftone.jrat.desktop.util;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.table.TableColumnExt;
import org.shiftone.jrat.util.log.Logger;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.util.Map;
import java.util.List;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class JXTableWatcher {

     private static final Logger LOG = Logger.getLogger(JXTableWatcher.class);

    public static void initialize(JXTable table, Map visiblity) {

        List columns = table.getColumns(true);

        for (int i = 0; i < columns.size(); i ++) {
            TableColumnExt columnExt = (TableColumnExt)columns.get(i);
            Boolean visible = (Boolean) visiblity.get(new Integer(i));  // get saved visible
            columnExt.addPropertyChangeListener(new VisibleListener(visiblity, i));  // watch for changes

            if (visible != null) {
              columnExt.setVisible(visible.booleanValue());    // set saved visible
            }

        }
    }


    /**
     * when visibility changes.. record that to the map
     */
    private static class VisibleListener implements PropertyChangeListener {
        private final Map visiblity;
        private final int index;

        public VisibleListener(Map visiblity, int index) {
            this.visiblity = visiblity;
            this.index = index;
        }

        public void propertyChange(PropertyChangeEvent evt) {
            if ("visible".equals(evt.getPropertyName())) {
                LOG.info("propertyChange " + index + " " + evt.getNewValue());
                visiblity.put(new Integer(index), evt.getNewValue());
            }
        }
    }
}
