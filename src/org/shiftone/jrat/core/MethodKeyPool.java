package org.shiftone.jrat.core;


import org.shiftone.jrat.util.StringUtil;
import org.shiftone.jrat.util.log.Logger;

import java.io.PrintStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Class MethodKeyPool pools methodKey instances, and also assignes each a
 * uniqie ID string of the form m0, m1, m2, m3, m4. The id's are useful for
 * providers that would need to represent a single method multiple times in
 * their output. Instead of duplicating the className, methodName, and signature
 * in their output, the output can contain a method pool section, and methods
 * can be referenced by id elsewhere.
 *
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class MethodKeyPool {

    private static final Logger LOG = Logger.getLogger(MethodKeyPool.class);
    private int idSequence = 0;
    private Map method2IdMap = new HashMap();
    private Map id2MethodMap = new HashMap();

    public synchronized MethodKey getMethodKey(String className, String methodName, String signature) {

        // a new MethodKey object is created to use as key in a map
        // to test if the MethodKey exists.
        MethodKey key = MethodKey.create(className, methodName, signature);

        if (method2IdMap.containsKey(key)) {

            // it the method key does exist, DO NOT RETURN the newly created
            // key.
            // instead, return the key contained within the map. This keeps only
            // a single instance 'in play' of any one key, making the new one
            // (above)
            // avalible for garbage collection.
            KeyAndId entry = (KeyAndId) method2IdMap.get(key);

            key = entry.methodKey;
        } else {

            // this is a new method key, so we can add it to the mapping and
            // assign it a unique id.
            addMapping(key, "m" + idSequence);

            idSequence++;
        }

        return key;
    }


    public String getId(MethodKey methodKey) {

        KeyAndId keyAndId = (KeyAndId) method2IdMap.get(methodKey);

        return (keyAndId != null)
                ? keyAndId.id
                : null;
    }


    /**
     * Method setMapping adds a NEW mapping. It is assumed that the provided
     * methodKey and id are not yet being stored in the map.
     */
    public synchronized void addMapping(MethodKey methodKey, String id) {

        // santity check.. these should never happen if the pool is used
        // correctly.
        if (method2IdMap.containsKey(methodKey)) {
            throw new RuntimeException("method key already exists in pool : " + methodKey);
        }

        if (id2MethodMap.containsKey(id)) {
            throw new RuntimeException("method ID already exists in pool : " + id);
        }

        KeyAndId keyAndId = new KeyAndId();

        keyAndId.id = id;
        keyAndId.methodKey = methodKey;

        method2IdMap.put(methodKey, keyAndId);
        id2MethodMap.put(id, methodKey);
    }


    public void printXML(PrintStream out, int indent) {

        KeyAndId keyAndId = null;
        String indentString = StringUtil.bufferString(indent, ' ');
        Collection values = method2IdMap.values();
        Iterator iterator = values.iterator();

        out.println(indentString + "<method-pool>");

        while (iterator.hasNext()) {
            keyAndId = (KeyAndId) iterator.next();

            out.print(indentString + " ");
            out.print("<method id=\"" + keyAndId.id + "\"");
            out.print(" class=\"" + keyAndId.methodKey.getClassName() + "\"");
            out.print(" method=\"" + keyAndId.methodKey.getMethodName() + "\"");
            out.print(" signature=\"" + keyAndId.methodKey.getSignature() + "\" />");
            out.println();
        }

        out.println(indentString + "</method-pool>");
    }


    private static class KeyAndId {
        MethodKey methodKey = null;
        String id = null;
    }
}
