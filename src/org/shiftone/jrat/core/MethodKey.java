package org.shiftone.jrat.core;


import org.shiftone.jrat.util.Assert;
import org.shiftone.jrat.util.log.Logger;

import java.io.Serializable;
import java.util.*;


/**
 * Immutable object that can be used to uniquely identify a method - suitable
 * for use as a Map key.
 *
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class MethodKey implements Serializable, Comparable {

    private static final Logger LOG = Logger.getLogger(MethodKey.class);

    private static final long serialVersionUID = 1;

    private ClassKey classKey = null;
    private String methodName = null;
    private String signature = null;
    private int hashCode = 0;
    private transient String toStringValue = null;
    private transient Signature sig = null;

    private static Map CACHE = new HashMap(); //<MethodKey, MethodKey>


    public static MethodKey getInstance(String fullyQualifiedClassName, String methodName, String signature) {
        ClassKey classKey = ClassKey.getInstance(fullyQualifiedClassName);
        MethodKey key = new MethodKey(classKey, methodName, signature);
        MethodKey value = (MethodKey)CACHE.get(key);
        if (value == null) {
            CACHE.put(key, key);
            value = key;
        }
        return value;
    }

    private MethodKey() {
    }


    private MethodKey(ClassKey classKey, String methodName, String signature) {

        Assert.assertNotNull("classKey", classKey);
        Assert.assertNotNull("methodName", methodName);
        Assert.assertNotNull("signature", signature);

        this.classKey = classKey;
        this.methodName = methodName;
        this.signature = signature;

        hashCode = classKey.hashCode();
        hashCode = (29 * hashCode) + methodName.hashCode();
        hashCode = (29 * hashCode) + signature.hashCode();
    }


    public final String getMethodName() {
        return methodName;
    }


    public final String getSignature() {
        return signature;
    }


    public String getPackageName() {
        return classKey.getPackageName();
    }

    /**
     * Gets the package's name in pieces.
     */
    public String[] getPackageNameParts() {
        return classKey.getPackageNameParts();
    }

    public final String getClassName() {
        return classKey.getClassName();       // todo
    }


    public final String getShortClassName() {
        return classKey.getClassName();
    }


    public final boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof MethodKey)) {
            return false;
        }

        final MethodKey other = (MethodKey) o;

        if (!classKey.equals(classKey)) {
            return false;
        }

        if (!methodName.equals(other.methodName)) {
            return false;
        }

        if (!signature.equals(other.signature)) {
            return false;
        }

        return true;
    }


    public final String toString() {

        if (toStringValue == null) {
            toStringValue = classKey.getClassName() + '.' + methodName + getPrettySignature();
        }

        return toStringValue;
    }


    public Signature getSig() {

        if (sig == null) {
            sig = new Signature(signature);
        }

        return sig;
    }


    public int compareTo(Object o) {
        MethodKey other = (MethodKey) o;
        int c = classKey.compareTo(other.classKey);
        if (c == 0) {
            c = methodName.compareTo(other.methodName);
            if (c == 0) {
                c = signature.compareTo(other.signature);
            }
        }
        return c;
    }

    public final String getPrettySignature() {
        return "(" + getSig().getShortText() + ")";
    }


    public final int hashCode() {
        return hashCode;
    }

}
