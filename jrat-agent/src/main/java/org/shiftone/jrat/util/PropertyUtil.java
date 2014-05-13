package org.shiftone.jrat.util;

import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.util.Map;
import org.shiftone.jrat.core.JRatException;
import org.shiftone.jrat.util.log.Logger;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class PropertyUtil {

    private static final Logger LOG = Logger.getLogger(PropertyUtil.class);

    public static void setProperties(Object instance, Map<String, String> properties) {
        Class<?> klass = instance.getClass();

        for (Map.Entry<String, String> entry : properties.entrySet()) {
            String propertyName = entry.getKey();
            String value = entry.getValue();

            try {

                PropertyDescriptor descriptor = new PropertyDescriptor(propertyName, klass);
                PropertyEditor propertyEditor = PropertyEditorManager.findEditor(descriptor.getPropertyType());

                if (propertyEditor == null) {
                    throw new Exception("no property editor found for " + descriptor.getPropertyType().getName());
                }

                propertyEditor.setValue(instance);

                LOG.info(klass.getName() + "." + propertyName + " = " + value);

                propertyEditor.setAsText(value);
                descriptor.getWriteMethod().invoke(instance, new Object[]{propertyEditor.getValue()});

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
