package org.shiftone.jrat.core;


import org.shiftone.jrat.util.Assert;
import org.shiftone.jrat.util.log.Logger;

import java.io.Externalizable;
import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.Serializable;


/**
 * Immutable object that can be used to uniquely identify a method - suitable
 * for use as a Map key.
 *
 * @author Jeff Drost
 */
public class MethodKey implements Serializable {

    private static final Logger LOG = Logger.getLogger(MethodKey.class);
    private static final long serialVersionUID = 1;
    private String className = null;
    private String methodName = null;
    private String signature = null;
    private int hashCode = 0;
    private transient String toStringValue = null;
    private transient Signature sig = null;


    public MethodKey() {
    }

    public MethodKey(String className, String methodName, String signature) {

        Assert.assertNotNull("className", className);
        Assert.assertNotNull("methodName", methodName);
        Assert.assertNotNull("signature", signature);

        this.className = className;
        this.methodName = methodName;
        this.signature = signature;
        hashCode = className.hashCode();
        hashCode = (29 * hashCode) + methodName.hashCode();
        hashCode = (29 * hashCode) + signature.hashCode();
    }


    public final String getMethodName() {
        return methodName;
    }


    public final String getSignature() {
        return signature;
    }


    public final String getClassName() {
        return className;
    }


    public final String getShortClassName() {

        int index = className.lastIndexOf('.');

        return (index == -1)
                ? className
                : className.substring(index + 1);
    }


    public final boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof MethodKey)) {
            return false;
        }

        final MethodKey methodKey = (MethodKey) o;

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


    public final String getPrettySignature() {
        return "(" + getSig().getShortText() + ")";
    }


    public final int hashCode() {
        return hashCode;
    }


    public static String toTSV(MethodKey methodKey) {
        return methodKey.getClassName() + "\t" + methodKey.getMethodName() + "\t" + methodKey.getSignature();
    }
}
