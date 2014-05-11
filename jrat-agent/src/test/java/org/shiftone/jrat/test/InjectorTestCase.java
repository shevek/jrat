package org.shiftone.jrat.test;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.shiftone.jrat.core.Environment;
import org.shiftone.jrat.core.criteria.IncludeExcludeMethodCriteria;
import org.shiftone.jrat.core.criteria.MatcherMethodCriteria;
import org.shiftone.jrat.inject.InjectorOptions;
import org.shiftone.jrat.inject.bytecode.Transformer;
import org.shiftone.jrat.test.dummy.CrashTestDummy;
import org.shiftone.jrat.test.dummy.JuniorDummy;
import org.shiftone.jrat.util.io.ResourceUtil;
import org.shiftone.jrat.util.log.Logger;
import static org.apache.commons.lang3.ArrayUtils.EMPTY_CLASS_ARRAY;
import static org.apache.commons.lang3.ArrayUtils.EMPTY_OBJECT_ARRAY;

/**
 * Class InjectorTestCase
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 */
public class InjectorTestCase {

    private static final Logger LOG = Logger.getLogger(InjectorTestCase.class);

    public static final String CLASS_NAME = CrashTestDummy.class.getName();
    protected Class<?> clazz = null;

    @Before
    public void setUp() throws Exception {
        //System.setProperty(Settings.BASE_DIRECTORY, "c:/temp") ;
        //System.setProperty(Settings.APPLICATION, "app") ;
        Environment.getSettings().setJmxEnabled(true);
        Environment.getSettings().setMBeanServerCreationEnabled(true);

        //System.setProperty(Settings.HANDLER_CLASS, TraceMethodHandlerFactory.class.getName()) ;
        //System.setProperty(Settings.HANDLER_CLASS, StatMethodHandlerFactory.class.getName());
        //System.setProperty(Settings.HANDLER_CLASS, TreeMethodHandlerFactory.class.getName());
        // System.setProperty(Settings.HANDLER_CLASS, SpringMethodHandlerFactory.class.getName());
        Transformer transformer = new Transformer();

        TestClassLoader loader = null;

        if (clazz == null) {

            byte[] crashTestDummy = ResourceUtil.loadResourceAsBytes("org/shiftone/jrat/test/dummy/CrashTestDummy.class");
            byte[] juniorDummy = ResourceUtil.loadResourceAsBytes("org/shiftone/jrat/test/dummy/JuniorDummy.class");

            InjectorOptions options = new InjectorOptions();
            IncludeExcludeMethodCriteria criteria = new IncludeExcludeMethodCriteria();
            MatcherMethodCriteria matcherMethodCriteria = new MatcherMethodCriteria();
            matcherMethodCriteria.setClassName("org.*");
            criteria.addPositive(matcherMethodCriteria);
            options.setCriteria(criteria);

            crashTestDummy = transformer.inject(crashTestDummy, options);
            juniorDummy = transformer.inject(juniorDummy, options);

            loader = new TestClassLoader();

            save(CLASS_NAME, crashTestDummy);
            loader.addOverrider(CrashTestDummy.class.getName(), crashTestDummy);

            //save(CLASS_NAME, CrashTestDummy);
            loader.addOverrider(JuniorDummy.class.getName(), juniorDummy);

            clazz = loader.loadClass(CLASS_NAME);
        }

    }

    private synchronized void save(String className, byte[] bytes) throws Exception {

        String shortName = className.substring(className.lastIndexOf('.') + 1);

        LOG.info("shortName = " + shortName);

        OutputStream out = new FileOutputStream(shortName + ".class");

        out.write(bytes);
        out.close();
    }

    private Object testMethodCall(String methodName, Class<?>[] argTypes, Object[] argValues) throws Exception {

        Method method = clazz.getMethod(methodName, argTypes);

        LOG.info("clazz = " + clazz.getName());
        LOG.info("methodName = " + methodName);
        for (Class<?> argType : argTypes) {
            LOG.info("argType " + argType);
        }
        LOG.info("method = " + method);

        Object o = method.invoke(clazz.newInstance(), argValues);
        LOG.info("testMethodCall " + methodName + " -> " + o);
        return o;
    }

    @Test
    public void testNoArgs() throws Exception {

        testMethodCall("noArgs", EMPTY_CLASS_ARRAY, EMPTY_OBJECT_ARRAY);
        testMethodCall("noArgsSynchronized", EMPTY_CLASS_ARRAY, EMPTY_OBJECT_ARRAY);
        testMethodCall("noArgsStatic", EMPTY_CLASS_ARRAY, EMPTY_OBJECT_ARRAY);
        testMethodCall("callAdd", EMPTY_CLASS_ARRAY, EMPTY_OBJECT_ARRAY);
        testMethodCall("callRecurse", EMPTY_CLASS_ARRAY, EMPTY_OBJECT_ARRAY);
        testMethodCall("callRecurse", EMPTY_CLASS_ARRAY, EMPTY_OBJECT_ARRAY);
        testMethodCall("callInLoop_1000", EMPTY_CLASS_ARRAY, EMPTY_OBJECT_ARRAY);
        testMethodCall("callInLoop_10000", EMPTY_CLASS_ARRAY, EMPTY_OBJECT_ARRAY);
        testMethodCall("callInLoop_50000", EMPTY_CLASS_ARRAY, EMPTY_OBJECT_ARRAY);
        testMethodCall("callInLoop_100000", EMPTY_CLASS_ARRAY, EMPTY_OBJECT_ARRAY);
        testMethodCall("callInLoop_1000000", EMPTY_CLASS_ARRAY, EMPTY_OBJECT_ARRAY);

        testMethodCall("noArgLocalStringList", EMPTY_CLASS_ARRAY, EMPTY_OBJECT_ARRAY);

        try {
            testMethodCall("throwIOException", EMPTY_CLASS_ARRAY, EMPTY_OBJECT_ARRAY);
        } catch (Exception e) {
        }

        try {
            testMethodCall("throwRuntimeException", EMPTY_CLASS_ARRAY, EMPTY_OBJECT_ARRAY);
        } catch (Exception e) {
        }
    }

    @Test
    public void testNoJRat() throws Exception {

        CrashTestDummy dummy = new CrashTestDummy();
        long start = System.currentTimeMillis();

        dummy.callInLoop_1000();
        LOG.info("callInLoop_1000 took " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();

        dummy.callInLoop_10000();
        LOG.info("callInLoop_10000 took " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();

        dummy.callInLoop_50000();
        LOG.info("callInLoop_50000 took " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();

        dummy.callInLoop_100000();
        LOG.info("callInLoop_100000 took " + (System.currentTimeMillis() - start));
        dummy.callInLoop_1000000();
    }

    @Test
    public void testOneArg() throws Exception {

        testMethodCall("oneBooleanArg", new Class[]{Boolean.TYPE}, new Object[]{Boolean.TRUE});
        testMethodCall("oneIntegerArg", new Class[]{Integer.TYPE}, new Object[]{new Integer(101)});
        testMethodCall("oneLongArg", new Class[]{Long.TYPE}, new Object[]{new Long(202)});
        testMethodCall("oneObjectArg", new Class[]{Object.class}, new Object[]{this});
        testMethodCall("oneStringArrayArg", new Class[]{String[].class},
                new Object[]{new String[]{"test", "123"}});

        List<String> stringList = new ArrayList<String>();

        testMethodCall("addString", new Class[]{String.class}, new Object[]{String.valueOf(System.currentTimeMillis())});
        testMethodCall("oneStringList", new Class[]{List.class}, new Object[]{stringList});
        //Thread.sleep(1000 * 60 * 10);
    }

    @Test
    public void testReturnArg() throws Exception {

        testMethodCall("returnBooleanArg", new Class[]{Boolean.TYPE}, new Object[]{Boolean.TRUE});
        testMethodCall("returnIntegerArg", new Class[]{Integer.TYPE}, new Object[]{new Integer(101)});
        testMethodCall("returnLongArg", new Class[]{Long.TYPE}, new Object[]{new Long(202)});
        testMethodCall("returnObjectArg", new Class[]{Object.class}, new Object[]{this});
        testMethodCall("returnStringArrayArg", new Class[]{String[].class}, new Object[]{new String[]{"one", "two",
            "three"}});
        testMethodCall("localVariableLength", new Class[]{String.class}, new Object[]{"test dummy string"});

        testMethodCall("returnStringsList", new Class[]{}, new Object[]{});
        testMethodCall("returnSex", new Class[]{}, new Object[]{});
    }

    @Test
    public void testTwos() throws Exception {

        testMethodCall("twoIntegerArg", new Class[]{Integer.TYPE, Integer.TYPE}, new Object[]{new Integer(1),
            new Integer(2)});
        testMethodCall("twoLongArg", new Class[]{Long.TYPE, Long.TYPE}, new Object[]{new Long(1), new Long(2)});
        testMethodCall("twoObjectArg", new Class[]{Object.class, Object.class}, new Object[]{this, "that"});
        testMethodCall("twoStringArrayArg", new Class[]{String[].class, String[].class},
                new Object[]{new String[]{"one",
                    "two",
                    "three"},
                new String[]{"test"}});
    }

    @Test
    public void testMoreArgs() throws Exception {

        boolean[] ba = new boolean[5];
        Class bac = ba.getClass();

        testMethodCall("argsByteBooleanArrayObject", //
                new Class[]{Byte.TYPE, bac, Object.class}, //
                new Object[]{new Byte((byte) 10), new boolean[]{true, false}, this});
        testMethodCall("argsLongObjectLongLongArg", //
                new Class[]{Long.TYPE, Object.class, Long.TYPE, Long.TYPE}, //
                new Object[]{new Long(1), this, new Long(2), new Long(123)});
    }
}
