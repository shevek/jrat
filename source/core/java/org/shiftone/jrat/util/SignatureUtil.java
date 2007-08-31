package org.shiftone.jrat.util;


import org.shiftone.jrat.util.log.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class SignatureUtil {

    private static final Logger LOG = Logger.getLogger(SignatureUtil.class);
    private static Map PRIMITIVE_TYPES = new HashMap();

    static {
        PRIMITIVE_TYPES.put(Boolean.TYPE, "Z");
        PRIMITIVE_TYPES.put(Byte.TYPE, "B");
        PRIMITIVE_TYPES.put(Character.TYPE, "C");
        PRIMITIVE_TYPES.put(Short.TYPE, "S");
        PRIMITIVE_TYPES.put(Integer.TYPE, "I");
        PRIMITIVE_TYPES.put(Long.TYPE, "J");
        PRIMITIVE_TYPES.put(Float.TYPE, "F");
        PRIMITIVE_TYPES.put(Double.TYPE, "D");
        PRIMITIVE_TYPES.put(Void.TYPE, "V");
        PRIMITIVE_TYPES.put(Float.TYPE, "F");
    }

    public static String getSignature(Class klass) {

        Assert.assertNotNull("class", klass);

        if (klass.isPrimitive()) {
            return (String) PRIMITIVE_TYPES.get(klass);
        } else {
            String sig = klass.getName();

            sig = sig.replace('.', '/');

            if (klass.isArray() == false) {
                sig = 'L' + sig + ';';
            }

            return sig;
        }
    }


    public static String getSignature(Method method) {

        Assert.assertNotNull("method", method);

        StringBuffer sb = new StringBuffer();
        Class[] args = method.getParameterTypes();
        Class ret = method.getReturnType();

        sb.append("(");

        for (int i = 0; i < args.length; i++) {
            sb.append(getSignature(args[i]));
        }

        sb.append(")");
        sb.append(getSignature(ret));

        return sb.toString();
    }


    public static String getSignature(Constructor constructor) {

        Assert.assertNotNull("constructor", constructor);

        StringBuffer sb = new StringBuffer();
        Class[] args = constructor.getParameterTypes();

        // Class ret = constructor.getReturnType();
        sb.append("(");

        for (int i = 0; i < args.length; i++) {
            sb.append(getSignature(args[i]));
        }

        sb.append(")");

        // sb.append(getSignature(ret));
        return sb.toString();
    }
}
