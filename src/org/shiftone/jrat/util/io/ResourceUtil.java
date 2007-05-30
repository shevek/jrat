package org.shiftone.jrat.util.io;


import org.shiftone.jrat.core.JRatException;
import org.shiftone.jrat.util.Assert;
import org.shiftone.jrat.util.log.Logger;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;


/**
 * Class ResourceUtil
 *
 * @author Jeff Drost
 */
public class ResourceUtil {

    private static final Logger LOG = Logger.getLogger(ResourceUtil.class);
    private static ClassLoader CLASS_LOADER = ResourceUtil.class.getClassLoader();
    private static Map resourceCache = new Hashtable();

    static {
        if (CLASS_LOADER == null) {
            CLASS_LOADER = Class.class.getClassLoader();
        }
    }

    public static Object newInstance(String className) {

        Class klass = null;
        Object instance = null;

        Assert.assertNotNull("className", className);
        LOG.debug("newInstance(" + className + ")");

        try {
            klass = CLASS_LOADER.loadClass(className);
        }
        catch (Exception e) {
            throw new JRatException("unable to load class '" + className + "'", e);
        }

        try {
            instance = klass.newInstance();
        }
        catch (Exception e) {
            throw new JRatException("unable to instantiate '" + className + "'", e);
        }

        return instance;
    }


    public static InputStream loadResourceAsStream(String resourceName) {

        InputStream inputStream = null;

        LOG.info("load resource : " + resourceName);
        Assert.assertNotNull("resourceName", resourceName);

        inputStream = CLASS_LOADER.getResourceAsStream(resourceName);

        if (inputStream == null) {
            LOG.info("resource not found on classpath, trying to open as file");

            try {
                inputStream = new FileInputStream(resourceName);

                LOG.debug("resource opened as file");
            }
            catch (Exception e) {
                throw new JRatException("unable to locate resource : " + resourceName);
            }
        } else {
            LOG.debug("resource opened from classpath");
        }

        return inputStream;
    }


    public static byte[] loadResourceAsBytes(String resourceName) {

        InputStream inputStream = loadResourceAsStream(resourceName);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            IOUtil.copy(inputStream, outputStream);
        }
        finally {
            IOUtil.close(inputStream);
        }

        return outputStream.toByteArray();
    }


    private static String fetchResource(String name) {

        Reader reader = null;
        StringBuffer sb = null;
        InputStream inputStream = null;
        int c = 0;
        char[] buffer = new char[1025 * 1];

        Assert.assertNotNull("name", name);
        LOG.debug("fetchResource : " + name);

        inputStream = loadResourceAsStream(name);
        reader = new InputStreamReader(inputStream);
        sb = new StringBuffer();

        try {
            for (c = 0; c >= 0; c = reader.read(buffer)) {
                sb.append(buffer, 0, c);
            }
        }
        catch (IOException e) {
            throw new JRatException("unable to read resource data : " + name, e);
        }

        return sb.toString();
    }


    public static String loadResource(String name) {

        String resource = null;

        Assert.assertNotNull("name", name);

        resource = (String) resourceCache.get(name);

        if (resource == null) {
            LOG.info("loading and caching resource : " + name);

            resource = fetchResource(name);

            resourceCache.put(name, resource);
        }

        return resource;
    }


    public static Properties getResourceAsProperties(String name) {

        InputStream inputStream = null;
        Properties props = null;

        Assert.assertNotNull("name", name);
        LOG.debug("getResourceAsProperties : " + name);

        inputStream = loadResourceAsStream(name);
        props = new Properties();

        try {
            props.load(inputStream);
        }
        catch (Exception e) {
            throw new JRatException("unable to load properties from resource : " + name, e);
        }

        return props;
    }
}
