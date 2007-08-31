package org.shiftone.jrat.test;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 * This ClassLoader allows classes to be
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 */
public class TestClassLoader extends ClassLoader implements Serializable {

    private Map hackedMap = new HashMap();

    /**
     * Method loadClass
     *
     * @param name .
     * @return .
     * @throws ClassNotFoundException
     */
    public Class loadClass(String name) throws ClassNotFoundException {

        if (hackedMap.containsKey(name)) {
            byte[] data = (byte[]) hackedMap.get(name);

            return defineClass(name, data, 0, data.length);
        }

        return super.loadClass(name);
    }


    /**
     * Method add
     *
     * @param className .
     * @param classData .
     */
    public void addOverrider(String className, byte[] classData) {

        /*
OutputStream out = null;

try {

    out = new FileOutputStream(className);

    out.write(classData);
    out.close();

} catch (Exception e) {

}
*/
        hackedMap.put(className, classData);
    }
}
