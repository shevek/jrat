package org.shiftone.jrat.util;


import org.shiftone.jrat.util.io.IOUtil;
import org.shiftone.jrat.util.log.Logger;
import org.shiftone.jrat.util.regex.GlobMatcher;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;


/**
 * Class GlobClassLoader
 *
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class GlobClassLoader extends URLClassLoader {

    private static final Logger LOG = Logger.getLogger(GlobClassLoader.class);
    private ClassLoader parent = null;
    private GlobMatcher globMatcher = null;

    public GlobClassLoader(URL[] urls, String globPattern, ClassLoader parent) {

        super(urls, parent);

        this.parent = parent;
        this.globMatcher = new GlobMatcher(globPattern);
    }


    public GlobClassLoader(File file, String globPattern, ClassLoader parent) throws MalformedURLException {
        this(new URL[]{file.toURL()}, globPattern, parent);
    }


    public GlobClassLoader(URL[] urls, String globPattern) {

        super(urls);

        globMatcher = new GlobMatcher(globPattern);
    }


    protected synchronized Class loadClass(String name, boolean resolve) throws ClassNotFoundException {

        Class klass = null;

        if (globMatcher.isMatch(name)) {
            klass = loadClassHere(name);
        } else {
            klass = super.loadClass(name, resolve);
        }

        return klass;
    }


    private Class loadClassHere(String name) throws ClassNotFoundException {

        Class klass = null;
        String resourceName = null;
        byte[] bytes = null;

        LOG.debug("loadClassHere(" + name + ")");

        try {
            resourceName = name.replace('.', '/').concat(".class");
            bytes = loadClassData(resourceName);
            klass = defineClass(name, bytes, 0, bytes.length);
        }
        catch (Exception e) {
            throw new ClassNotFoundException("not found :" + name, e);
        }

        return klass;
    }


    private byte[] loadClassData(String resourceName) throws IOException {

        ClassLoader loader = null;
        ByteArrayOutputStream out = null;
        InputStream in = null;

        in = getResourceAsStream(resourceName);

        if (in == null) {
            throw new IOException("class resource not found : " + resourceName);
        }

        out = new ByteArrayOutputStream();

        IOUtil.copy(in, out);

        return out.toByteArray();
    }
}
