package org.shiftone.jrat.util;



import org.shiftone.jrat.core.ConfigurationException;
import org.shiftone.jrat.util.log.Logger;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import java.util.HashMap;
import java.util.Map;


/**
 * Class BeanWrapper
 *
 * @author $Author: jeffdrost $
 * @version $Revision: 1.21 $
 */
public class BeanWrapper {

    private static final Logger LOG     = Logger.getLogger(BeanWrapper.class);
    private Object              bean    = null;
    private Class               klass   = null;
    private Map                 setters = null;
    private Map                 getters = null;

    public BeanWrapper(Object obj) {
        Assert.assertNotNull("target", obj);
        initialize(obj);
    }


    public Object getBean() {
        return bean;
    }


    private void initialize(Object obj) {

        this.bean    = obj;
        this.klass   = obj.getClass();
        this.setters = new HashMap();
        this.getters = new HashMap();

        Method[] methods = klass.getMethods();
        Method   method  = null;

        for (int i = 0; i < methods.length; i++)
        {
            method = methods[i];

            if (Modifier.isPublic(method.getModifiers()) && (false == Modifier.isStatic(method.getModifiers())))
            {

                // TODO: add "is"
                if (method.getName().startsWith("get") && (method.getParameterTypes().length == 0)
                        && (method.getReturnType() != Void.TYPE))
                {
                    getters.put(method.getName().substring(3).toUpperCase(), method);
                }

                if (method.getName().startsWith("set") && (method.getParameterTypes().length == 1)
                        && (method.getReturnType() == Void.TYPE))
                {
                    setters.put(method.getName().substring(3).toUpperCase(), method);
                }
            }
        }
    }


    public void setProperty(String key, String value) throws ConfigurationException {

        Method method      = null;
        Class  targetType  = null;
        Object valueObject = null;

        LOG.debug("setProperty(" + key + "," + value + ")");

        method = (Method) setters.get(key.toUpperCase());

        if (method != null)
        {
            targetType  = method.getParameterTypes()[0];
            valueObject = convert(value, targetType);

            try
            {
                method.invoke(bean, new Object[]{ valueObject });
            }
            catch (Exception e)
            {
                throw new ConfigurationException("error setting property : " + key, e);
            }
        }
        else
        {
            throw new ConfigurationException("no set method for '" + key + "' in class '" + klass.getName() + "'");
        }
    }


    private Object convert(String val, Class type) throws ConfigurationException {

        Assert.assertNotNull("value", val);

        String v = val.trim();

        if (val == null)
        {
            return null;
        }
        else if (String.class.isAssignableFrom(type))
        {
            return val;
        }
        else if (Integer.TYPE.isAssignableFrom(type) || Integer.class.isAssignableFrom(type))
        {
            return new Integer(v);
        }
        else if (Long.TYPE.isAssignableFrom(type) || Long.class.isAssignableFrom(type))
        {
            return new Long(v);
        }
        else if (Boolean.TYPE.isAssignableFrom(type) || Boolean.class.isAssignableFrom(type))
        {
            if ("true".equalsIgnoreCase(v))
            {
                return Boolean.TRUE;
            }
            else if ("false".equalsIgnoreCase(v))
            {
                return Boolean.FALSE;
            }
        }

        throw new ConfigurationException("unable to convert '" + v + "' to '" + type.getClass().getName() + "'");
    }


    public String getProperty(String key) throws ConfigurationException {

        Method method      = null;
        Object valueObject = null;
        String stringValue = null;

        LOG.debug("getProperty(" + key + ")");

        method = (Method) getters.get(key.toUpperCase());

        if (method != null)
        {
            try
            {
                valueObject = method.invoke(bean, new Object[]{});

                if (valueObject != null)
                {
                    stringValue = String.valueOf(valueObject);
                }
            }
            catch (Exception e)
            {
                throw new ConfigurationException("error getting property : " + key, e);
            }
        }
        else
        {
            throw new ConfigurationException("no get method for '" + key + "' in class '" + klass.getName() + "'");
        }

        return stringValue;
    }
}
