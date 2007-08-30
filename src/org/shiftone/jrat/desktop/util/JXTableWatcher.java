package org.shiftone.jrat.desktop.util;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.table.TableColumnExt;
import org.shiftone.jrat.util.log.Logger;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Map;
import java.util.prefs.Preferences;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class JXTableWatcher {

    private static final Logger LOG = Logger.getLogger(JXTableWatcher.class);

    public static void initialize(JXTable table, Preferences preferences, List columnInfos) {

        List columns = table.getColumns(true);

        for (int i = 0; i < columns.size(); i ++) {
            TableColumnExt columnExt = (TableColumnExt)columns.get(i);
            String key  = "visible." + i;
            String visible = preferences.get(key, null);
            columnExt.addPropertyChangeListener(new VisibleListener(preferences, key)); // watch for changes

            if (visible != null) {

                columnExt.setVisible(Boolean.parseBoolean(visible)); // set saved visible

            } else  if (columnInfos.size() > i) {

                ColumnInfo columnInfo = (ColumnInfo)columnInfos.get(i);
                columnExt.setVisible(columnInfo.isDefaultVisible());

            }

        }
    }


    /**
     * when visibility changes.. record that to the map
     */
    private static class VisibleListener implements PropertyChangeListener {
        private final Preferences preferences;
        private final String key;

        public VisibleListener(Preferences preferences, String key) {
            this.preferences = preferences;
            this.key = key;
        }

        public void propertyChange(PropertyChangeEvent evt) {
            if ("visible".equals(evt.getPropertyName())) {
                LOG.info("propertyChange " + key + " " + evt.getNewValue());
                preferences.put(key, evt.getNewValue().toString());
            }
        }
    }
}
