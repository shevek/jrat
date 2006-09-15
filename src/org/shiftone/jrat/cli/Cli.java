package org.shiftone.jrat.cli;



import org.shiftone.jrat.util.io.ResourceUtil;
import org.shiftone.jrat.util.log.Logger;

import java.io.PrintStream;

import java.lang.reflect.Method;

import java.util.Enumeration;
import java.util.Properties;


/**
 * @author $Author: jeffdrost $
 * @version $Revision: 1.26 $
 */
public class Cli {

    private static final Logger  LOG         = Logger.getLogger(Cli.class);
    private static final String  PROPS       = "org/shiftone/jrat/cli/cli.properties";
    private static final Class[] MAIN_PARAMS = { String[].class };
    private static PrintStream   OUT         = System.out;

    public static void main(String[] args) {

        try
        {
            runMain(args);
        }
        catch (Exception e)
        {
            LOG.error("error executing command", e);
        }
    }


    private static void runMain(String[] args) throws Exception {

        Properties properties = ResourceUtil.getResourceAsProperties(PROPS);
        Class      klass      = null;
        Method     method     = null;
        String     className  = null;
        String     classKey   = null;
        String[]   newArgs    = null;

        if (args.length == 0)
        {
            newArgs  = new String[0];
            classKey = properties.getProperty("default");
        }
        else
        {
            newArgs  = new String[args.length - 1];
            classKey = args[0];

            System.arraycopy(args, 1, newArgs, 0, newArgs.length);
        }

        classKey  = classKey.toLowerCase();
        className = properties.getProperty("main." + classKey + ".class");

        if (className == null)
        {
            printOptionsAndExit(classKey, properties);
        }

        LOG.debug("running " + className + ".main()");

        klass  = Class.forName(className);
        method = klass.getMethod("main", MAIN_PARAMS);

        method.invoke(null, new Object[]{ newArgs });
    }


    private static void printOptionsAndExit(String classKey, Properties properties) {

        OUT.println("Option '" + classKey + "' is not supported.");
        OUT.println("Please try one of the following:");

        Enumeration enumeration = properties.keys();

        while (enumeration.hasMoreElements())
        {
            String str = (String) enumeration.nextElement();

            if (str.startsWith("main.") && str.endsWith(".class"))
            {
                str = str.substring(5);
                str = str.substring(0, str.length() - 6);

                OUT.println("\t" + str);
            }
        }

        System.exit(3);
    }
}
