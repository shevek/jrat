package org.shiftone.jrat.util.jmx.dynamic;


import java.util.Arrays;


/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
class OperationKey {

    private String name;
    private String[] signature;
    private int hashCode;

    public OperationKey(String action, String[] signature) {

        this.name = action;
        this.signature = signature;
        this.hashCode = name.hashCode();

        for (int i = 0; i < signature.length; i++) {
            hashCode += signature[i].hashCode();
        }
    }


    public String getName() {
        return name;
    }


    public String[] getSignature() {
        return signature;
    }


    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if ((o == null) || (getClass() != o.getClass())) {
            return false;
        }

        final OperationKey that = (OperationKey) o;

        if (!name.equals(that.name)) {
            return false;
        }

        if (!Arrays.equals(signature, that.signature)) {
            return false;
        }

        return true;
    }


    public int hashCode() {
        return hashCode;
    }
}
