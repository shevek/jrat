package org.shiftone.jrat.core;


import org.shiftone.jrat.util.Assert;
import org.shiftone.jrat.util.log.Logger;

import java.io.Serializable;


/**
 * Immutable object that can be used to uniquely identify a method - suitable
 * for use as a Map key.
 *
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class MethodKey implements Serializable, Comparable {

    private static final Logger LOG = Logger.getLogger(MethodKey.class);
    private static final long serialVersionUID = 1;
    private String packageName = null;
    private String className = null;
    private String methodName = null;
    private String signature = null;
    private int hashCode = 0;
    private transient String toStringValue = null;
    private transient Signature sig = null;

//  Do I need this to serialize? 
//    public MethodKey() {
//    }

    private MethodKey() {
    }

    public static MethodKey create(String fullyQualifiedClassName, String methodName, String signature) {
        int dot = fullyQualifiedClassName.lastIndexOf('.');
        return create(
                (dot == -1) ? "" : fullyQualifiedClassName.substring(0, dot),
                (dot == -1) ? fullyQualifiedClassName : fullyQualifiedClassName.substring(dot + 1),
                methodName,
                signature);
    }

    public static MethodKey create(String packageName, String className, String methodName, String signature) {
        return new MethodKey(packageName, className, methodName, signature);
    }

    private MethodKey(String packageName, String className, String methodName, String signature) {

        Assert.assertNotNull("packageName", packageName);
        Assert.assertNotNull("className", className);
        Assert.assertNotNull("methodName", methodName);
        Assert.assertNotNull("signature", signature);

        this.packageName = packageName.intern();
        this.className = className.intern();
        this.methodName = methodName;
        this.signature = signature;

        hashCode = packageName.hashCode();
        hashCode = (29 * hashCode) + className.hashCode();
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
        return packageName;
    }

    public final String getClassName() {
        return className;       // todo
    }


    public final String getShortClassName() {
        return className;
    }


    public final boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof MethodKey)) {
            return false;
        }

        final MethodKey methodKey = (MethodKey) o;

        if (!packageName.equals(methodKey.packageName)) {
            return false;
        }

        if (!className.equals(methodKey.className)) {
            return false;
        }

        if (!methodName.equals(methodKey.methodName)) {
            return false;
        }

        if (!signature.equals(methodKey.signature)) {
            return false;
        }

        return true;
    }


    public final String toString() {

        if (toStringValue == null) {
            toStringValue = className + '.' + methodName + getPrettySignature();
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
        int c = packageName.compareTo(other.packageName);
        if (c == 0) {
            c = className.compareTo(other.className);
            if (c == 0) {
                c = methodName.compareTo(other.methodName);
                if (c == 0) {
                    c = signature.compareTo(other.signature);
                }
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
