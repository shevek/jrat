package org.shiftone.jrat.util;


import java.lang.reflect.Method;


/**
 * This classes sole purpose in life is to change the hashCode calculation of a
 * method instance to perform better. In the standard implementation,
 * getDeclaringClass().getName() is called, which is a native method, and is
 * slower. This implementation uses getDeclaringClass().hashCode(), and never
 * calls getName().
 *
 * @author Jeff Drost
 */
public class MethodWrapper {

    private Method method;
    private int hashCode;

    public MethodWrapper(Method method) {

        this.method = method;
        this.hashCode = method.getDeclaringClass().hashCode() ^ method.getName().hashCode();

        // this.hashCode = method.getDeclaringClass().getName().hashCode() ^
        // method.getName().hashCode();
    }


    public int hashCode() {
        return hashCode;
    }


    public boolean equals(Object obj) {
        return method.equals(((MethodWrapper) obj).getMethod());
    }


    public Method getMethod() {
        return method;
    }
}
