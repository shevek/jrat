package org.shiftone.jrat.util;

import org.shiftone.jrat.core.JRatException;
import org.shiftone.jrat.core.Environment;
import org.shiftone.jrat.core.config.Settings;
import org.shiftone.jrat.util.log.Logger;

import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.util.Iterator;
import java.util.Map;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class PropertyUtil {

    private static final Logger LOG = Logger.getLogger(PropertyUtil.class);

    public static void setProperties(Object instance, Map properties) {

        Iterator iterator = properties.entrySet().iterator();
        Class klass = instance.getClass();

        while (iterator.hasNext()) {

            Map.Entry entry = (Map.Entry) iterator.next();
            String propertyName = (String) entry.getKey();
            String value = (String) entry.getValue();

            try {

                PropertyDescriptor descriptor = new PropertyDescriptor(propertyName, klass);
                PropertyEditor propertyEditor = PropertyEditorManager.findEditor(descriptor.getPropertyType());

                if (propertyEditor == null) {
                    throw new Exception("no property editor found for " + descriptor.getPropertyType().getName());
                }

                propertyEditor.setValue(instance);
                
                LOG.info(klass.getName() + "." + propertyName + " = " + value);
                
                propertyEditor.setAsText(value);
                descriptor.getWriteMethod().invoke(instance, new Object[] { propertyEditor.getValue() } );

            } catch (Exception e) {

                throw new JRatException("failed to set property "
                        + propertyName
                        + " to value "
                        + value
                        + " on class "
                        + klass.getName(), e);

            }
        }

    }
}
