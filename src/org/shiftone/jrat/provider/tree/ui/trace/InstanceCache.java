package org.shiftone.jrat.provider.tree.ui.trace;


import java.util.HashMap;
import java.util.Map;


/**
 * @author Jeff Drost
 */
public class InstanceCache {

    private Map cache = new HashMap();

    public synchronized Object cache(Object o) {

        Object value = cache.get(o);

        if (value == null) {
            cache.put(o, o);

            value = o;
        }

        return value;
    }
}
