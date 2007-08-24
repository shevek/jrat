package org.shiftone.jrat.util.jmx.dynamic;


import org.shiftone.jrat.util.log.Logger;

import javax.management.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class ConfigurableMBean implements DynamicMBean {

    private static final Logger LOG = Logger.getLogger(ConfigurableMBean.class);
    private String className = ConfigurableMBean.class.getName();
    private String description;
    private Map attributeValues = new HashMap();
    private Map operations = new HashMap();

    // --- info cache ---
    private MBeanAttributeInfo[] attributeInfos;
    private MBeanOperationInfo[] operationInfos;
    private MBeanConstructorInfo[] constructors = null;
    private MBeanNotificationInfo[] notifications = null;

    public ConfigurableMBean(String description) {
        this.description = description;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public void add(String name, AttributeValue attributeValue) {

        if (attributeValues.put(name, attributeValue) != null) {
            LOG.warn("replacing attribute '" + name + "' with new value");
        }

        attributeInfos = null;
    }


    public void add(String name, Operation operation) {
        add(name, null, operation);
    }


    public void add(String name, String signature[], Operation operation) {

        if (signature == null) {
            signature = new String[0];
        }

        if (operations.put(new OperationKey(name, signature), operation) != null) {
            LOG.warn("replacing operation '" + name + "' with new operation");
        }

        operationInfos = null;
    }


    private AttributeValue getAttributeValue(String attributeName) throws AttributeNotFoundException {

        AttributeValue attribute = (AttributeValue) attributeValues.get(attributeName);

        if (attribute == null) {
            throw new AttributeNotFoundException(attributeName);
        }

        return attribute;
    }


    public Object getAttribute(String name) throws AttributeNotFoundException, MBeanException, ReflectionException {
        return getAttributeValue(name).getValue();
    }


    public void setAttribute(Attribute newValue)
            throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {

        AttributeValue attributeValue = getAttributeValue(newValue.getName());

        attributeValue.setValue(newValue.getValue());
    }


    public AttributeList getAttributes(String[] attributeNames) {

        AttributeList attributeList = new AttributeList();

        for (int i = 0; i < attributeNames.length; i++) {
            try {
                attributeList.add(getAttribute(attributeNames[i]));
            }
            catch (Exception ignore) {
            }
        }

        return null;
    }


    public AttributeList setAttributes(AttributeList attributes) {

        AttributeList setList = new AttributeList();

        for (int i = 0; i < attributes.size(); i++) {
            try {
                Attribute attribute = (Attribute) attributes.get(i);

                setAttribute(attribute);
                setList.add(attribute);
            }
            catch (Exception ignore) {
            }
        }

        return setList;
    }


    public Object invoke(String actionName, Object params[], String signature[])
            throws MBeanException, ReflectionException {

        OperationKey key = new OperationKey(actionName, signature);
        Operation operation = (Operation) operations.get(key);

        if (operation == null) {
            throw new MBeanException(new Exception("operation not found : " + key));
        }

        return operation.invoke(params);
    }


    private MBeanOperationInfo[] buildMBeanOperationInfo() {

        MBeanOperationInfo[] operationInfos = new MBeanOperationInfo[operations.size()];
        Iterator keys = operations.keySet().iterator();
        int i = 0;

        while (keys.hasNext()) {
            OperationKey key = (OperationKey) keys.next();
            Operation operation = (Operation) operations.get(key);
            MBeanParameterInfo[] parameterInfos = new MBeanParameterInfo[key.getSignature().length];

            for (int p = 0; i < parameterInfos.length; p++) {
                parameterInfos[p] = new MBeanParameterInfo(operation.getParameterName(p), key.getSignature()[p],
                        operation.getParameterDescription(p));
            }

            String returnType = operation.getReturnType();

            if (returnType == null) {
                returnType = Void.TYPE.getName();
            }

            operationInfos[i++] = new MBeanOperationInfo(key.getName(),    // name
                    operation.getDescription(),                            // description
                    parameterInfos,                                        // parameterInfos
                    returnType,                                            // type
                    MBeanOperationInfo.UNKNOWN                             // impact
            );
        }

        return operationInfos;
    }


    private MBeanAttributeInfo[] buildMBeanAttributeInfo() {

        MBeanAttributeInfo[] attributeInfos = new MBeanAttributeInfo[attributeValues.size()];
        Iterator keys = attributeValues.keySet().iterator();
        int i = 0;

        while (keys.hasNext()) {
            String key = (String) keys.next();
            AttributeValue value = (AttributeValue) attributeValues.get(key);

            attributeInfos[i++] = new MBeanAttributeInfo(key,    // name
                    value.getType(),                             // type
                    value.getDescription(),                      // description
                    value.isReadable(),                          // isReadable,
                    value.isWritable(),                          // isWritable,
                    false                                        // isIs)
            );
        }

        return attributeInfos;
    }


    public MBeanAttributeInfo[] getMBeanAttributeInfo() {

        if (attributeInfos == null) {
            attributeInfos = buildMBeanAttributeInfo();
        }

        return attributeInfos;
    }


    public MBeanOperationInfo[] getMBeanOperationInfo() {

        if (operationInfos == null) {
            operationInfos = buildMBeanOperationInfo();
        }

        return operationInfos;
    }


    public MBeanInfo getMBeanInfo() {
        return new MBeanInfo(className, description, getMBeanAttributeInfo(), constructors, getMBeanOperationInfo(),
                notifications);
    }


    public String toString() {
        return "DynamicAttributeMBean";
    }
}
