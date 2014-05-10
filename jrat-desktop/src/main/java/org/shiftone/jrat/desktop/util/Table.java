package org.shiftone.jrat.desktop.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author (jeff@shiftone.org) Jeff Drost
 */
public class Table {


    private List columns = new ArrayList();

    public synchronized Column column(String name) {
        return column(name, true);
    }

    public synchronized Column column(String name, boolean defaultVisible) {
        return column(name, Object.class, defaultVisible);
    }

    public synchronized Column column(String name, Class type, boolean defaultVisible) {
        Column column = new Column(columns.size(), name, type, defaultVisible);
        columns.add(column);
        return column;
    }

    public List getColumns() {
        return Collections.unmodifiableList(columns);
    }

    public Column getColumn(int index) {
        return (Column) columns.get(index);
    }

    public int getColumnCount() {
        return columns.size();
    }

    public class Column {
        private final int index;
        private final String name;
        private final Class type;
        private final boolean defaultVisible;


        public Column(int index, String name, Class type, boolean defaultVisible) {
            this.index = index;
            this.name = name;
            this.type = type;
            this.defaultVisible = defaultVisible;
        }

        public int getIndex() {
            return index;
        }

        public String getName() {
            return name;
        }

        public Class getType() {
            return type;
        }

        public boolean isDefaultVisible() {
            return defaultVisible;
        }
    }
}
