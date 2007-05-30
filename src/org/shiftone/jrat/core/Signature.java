package org.shiftone.jrat.core;


import org.shiftone.jrat.util.Assert;
import org.shiftone.jrat.util.log.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Jeff Drost
 */
public class Signature {

    private static final Logger LOG = Logger.getLogger(Signature.class);
    private static Map PRIM_CODES = new HashMap();
    private String returnType;
    private List parameterTypes = new ArrayList(5);

    static {
        PRIM_CODES.put("Z", "boolean");
        PRIM_CODES.put("B", "byte");
        PRIM_CODES.put("C", "char");
        PRIM_CODES.put("D", "double");
        PRIM_CODES.put("F", "float");
        PRIM_CODES.put("I", "int");
        PRIM_CODES.put("J", "long");
        PRIM_CODES.put("S", "short");
        PRIM_CODES.put("V", "void");
    }

    public Signature(String descriptors) {
        parseSig(new CharacterIterator(descriptors));
    }


    public String getReturnType() {
        return returnType;
    }


    public int getParameterCount() {
        return parameterTypes.size();
    }


    public String getParameterType(int index) {
        return (String) parameterTypes.get(index);
    }


    public String getShortParameterType(int index) {

        String type = getParameterType(index);
        int dotIndex = type.lastIndexOf('.');

        if (dotIndex != -1) {
            type = type.substring(dotIndex + 1);
        }

        return type;
    }


    public String getShortText() {

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < getParameterCount(); i++) {
            if (i != 0) {
                sb.append(",");
            }

            sb.append(getShortParameterType(i));
        }

        return sb.toString();
    }


    public String getLongText() {

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < getParameterCount(); i++) {
            if (i != 0) {
                sb.append(",");
            }

            sb.append(getParameterType(i));
        }

        return sb.toString();
    }


    private void parseSig(CharacterIterator in) {

        Assert.assertTrue("first char is (", in.next() && (in.get() == '('));

        boolean inParams = true;

        while (in.next()) {
            char c = in.get();

            if (c == ')') {
                inParams = false;
            }

            if (inParams) {
                parameterTypes.add(parseType(in));
            } else {
                returnType = parseType(in);
            }
        }
    }


    private String parseType(CharacterIterator in) {

        char c = in.get();

        if (c == 'L') {
            return parseClassType(in);
        } else if (c == '[') {
            return parseArrayType(in);
        } else {
            return (String) PRIM_CODES.get(String.valueOf(c));
        }
    }


    private String parseArrayType(CharacterIterator in) {

        StringBuffer sb = new StringBuffer("[]");

        while (in.next() && (in.get() == '[')) {
            sb.append("[]");
        }

        sb.insert(0, parseType(in));    // out the type at the start of the

        // [][][]
        return sb.toString();
    }


    private String parseClassType(CharacterIterator in) {

        StringBuffer className = new StringBuffer();

        while (in.next()) {
            char c = in.get();

            if (c == ';') {
                break;
            } else if (c == '/') {
                className.append('.');
            } else {
                className.append(c);
            }
        }

        return className.toString();
    }


    // todo - this is handy - make it common
    private static class CharacterIterator {

        private char[] chars;
        private int pos = -1;
        private char current;

        public CharacterIterator(String text) {
            this.chars = text.toCharArray();
        }


        public CharacterIterator(char[] chars) {
            this.chars = chars;
        }


        public boolean next() {

            pos++;

            if (pos >= chars.length) {
                return false;
            } else {
                current = chars[pos];

                return true;
            }
        }


        public char get() {
            return current;
        }
    }
}
