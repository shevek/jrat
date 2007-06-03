package org.shiftone.jrat.core;


import org.shiftone.jrat.util.Assert;
import org.shiftone.jrat.util.log.Logger;

import java.io.Externalizable;
import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;


/**
 * Immutable object that can be used to uniquely identify a method - suitable
 * for use as a Map key.
 *
 * @author Jeff Drost
 */
public class MethodKey implements Externalizable {

    private static final Logger LOG = Logger.getLogger(MethodKey.class);
    private String className = null;
    private String methodName = null;
    private String signature = null;
    private int hashCode = 0;
    private String toStringValue = null;
    private Signature sig = null;


    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(className);
        out.writeUTF(methodName);
        out.writeUTF(signature);
        out.writeInt(hashCode);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        className = in.readUTF();
        methodName = in.readUTF();
        signature = in.readUTF();
        hashCode = in.readInt();
    }


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
