package org.shiftone.jrat.util;


import org.shiftone.jrat.util.log.Logger;
import org.shiftone.jrat.core.JRatException;

import java.io.File;
import java.lang.reflect.Method; 
import java.util.Date;


/**
 * Class IntrospectionUtil
 *
 * @author Jeff Drost
 *
 */
public class IntrospectionUtil {

    public static final Logger LOG = Logger.getLogger(IntrospectionUtil.class);

    /**
     * Method finds a method of the provided name with the provided number of
     * parameters. Note that this method does not take the method types. It is
     * possible for a class to have more than one method with the same name and
     * parameter count.
     *
     * <p>
     * Method will be found based on case insentive name.
     * </p>
     */
    public static Method getMethod(Class klass, String methodName, int paramCount) throws NoSuchMethodException {

        Method[] methods          = null;
        Method   method           = null;
        Method   foundMethod      = null;
        int      foundMethodCount = 0;

        LOG.debug("getMethod(" + klass.getName() + " , " + methodName + " , " + paramCount + ")");
        Assert.assertNotNull("klass", klass);
        Assert.assertNotNull("methodName", methodName);

        methods = klass.getMethods();

        for (int i = 0; i < methods.length; i++)
        {
            method = methods[i];

            if ((method.getName().equalsIgnoreCase(methodName)) && (method.getParameterTypes().length == paramCount))
            {
                foundMethod = method;

                foundMethodCount++;
            }
        }

        if (foundMethodCount == 0)
        {
            throw new NoSuchMethodException(klass.getName() + " has no method " + methodName + " with " + paramCount
                                            + " parameter(s)");
        }
        else if (foundMethodCount > 1)
        {
            throw new NoSuchMethodException(klass.getName() + " has " + foundMethodCount + " method " + methodName
                                            + " with " + paramCount + " parameter(s)");
        }

        return foundMethod;
    }


    public static Object convertSafe(String text, Class targetType) {

        try
        {
            return convert(text, targetType);
        }
        catch (Exception e)
        {
            return text;
        }
    }


    public static Object convert(String text, Class targetType)  {

        Object result = null;

        LOG.debug("convert(" + text + " , " + targetType.getName() + ")");

        try
        {
            if (targetType.equals(String.class))
            {
                result = text;
            }
            else if (Short.class.equals(targetType) || Short.TYPE.equals(targetType))
            {
                result = new Short(text);
            }
            else if (Integer.class.equals(targetType) || Integer.TYPE.equals(targetType))
            {
                result = new Integer(text);
            }
            else if (Long.class.equals(targetType) || Long.TYPE.equals(targetType))
            {
                result = new Long(text);
            }
            else if (Float.class.equals(targetType) || Float.TYPE.equals(targetType))
            {
                result = new Float(text);
            }
            else if (Double.class.equals(targetType) || Double.TYPE.equals(targetType))
            {
                result = new Double(text);
            }
            else if (Boolean.class.equals(targetType) || Boolean.TYPE.equals(targetType))
            {
                result = new Boolean(text);
            }
            else if (Date.class.equals(targetType))
            {
                result = new Date(text);
            }
            else if (File.class.equals(targetType))
            {
                File file = new File(text);

                result = file.getAbsoluteFile();
            }
            else if (Class.class.equals(targetType))
            {
                result = Class.forName(text);
            }
            else
            {
                throw new JRatException("conversion to " + targetType.getName() + " is not supported");
            }
        }
        catch (Exception e)
        {
            throw new JRatException("unable to convert " + text + " into a " + targetType.getName(), e);
        }

        return result;
    }
}
