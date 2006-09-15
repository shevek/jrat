package org.shiftone.jrat.test.dummy;



import org.shiftone.jrat.util.log.Logger;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Class CrashTestDummy
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.4 $
 */
public class CrashTestDummy implements Serializable {

    private static final Logger LOG        = Logger.getLogger(CrashTestDummy.class);
    private int              privateInt = 0;
    private List<String> strings = new ArrayList<String>();
    public enum SEX {BOY, GIRL};

    private JuniorDummy juniorDummy = new JuniorDummy();

    /* ========== JAVA 1.5 TESTS ==================== */
	// tested
	@SuppressWarnings("test")
	@Deprecated()
    public void oneStringList(List<String> strings) {
        this.strings = strings;
    }

	// tested
	 public void noArgLocalStringList() {
		List<String> local = new ArrayList<String>();
		local.addAll(strings);
        this.strings = local;
    }

	// tested
	public List<String> returnStringsList() {
        return strings;
    }

	// tested
    public void addString(String text) {
        strings.add(text);
    }

	// tested
    public SEX returnSex() {
        return SEX.GIRL;
    }
    /* ========== END JAVA 1.5 TESTS ==================== */

    /**
     * Method sleep
     *
     * @param ms .
     */
    private static void sleep(long ms) {

        try
        {
            Thread.sleep(ms);
        }
        catch (Exception e) {}
    }


    /**
     * Method nap
     */
    private static void nap() {
        sleep((long) (200 * Math.random()));
    }

	private static void sleep() {
        sleep((long) (600 * Math.random()));
    }

    /**
     * Method noArgs
     */
    public void noArgs() {
        LOG.debug("public void noArgs()");
        nap();
    }


    /**
     * Method oneBooleanArg
     *
     * @param b .
     */
    public void oneBooleanArg(boolean b) {
        LOG.debug("public void noOneBooleanArg(boolean b)");
	    try { Thread.sleep(50); } catch (Exception e) {}
        nap();
	    sleep();
    }


    /**
     * Method oneIntegerArg
     *
     * @param i .
     */
    public void oneIntegerArg(int i) {
        LOG.debug("public void oneIntegerArg(int i)");
        nap();
	    try { Thread.sleep(50); } catch (Exception e) {}
	    sleep();
    }


    /**
     * Method oneLongArg
     *
     * @param i .
     */
    public void oneLongArg(long i) {
        LOG.debug("public void oneLongArg(long i)");
	    sleep();
        nap();
	    try { Thread.sleep(50); } catch (Exception e) {}
    }


    /**
     * Method oneObjectArg
     *
     * @param obj .
     */
    public void oneObjectArg(Object obj) {
        LOG.debug("public void oneObjectArg(Object obj)");
        nap();
	    sleep();
    }


    /**
     * Method oneStringArrayArg
     *
     * @param args .
     */
    public void oneStringArrayArg(String[] args) {
        LOG.debug("public void oneStringArrayArg(String[] args))");
        nap();
	    sleep();
    }


    /**
     * Method returnBooleanArg
     *
     * @param b .
     *
     * @return .
     */
    public boolean returnBooleanArg(boolean b) {

        LOG.debug("public boolean returnBooleanArg(boolean b)");
        nap();
	    sleep();

        return b;
    }


    /**
     * Method returnIntegerArg
     *
     * @param i .
     *
     * @return .
     */
    public int returnIntegerArg(int i) {

        LOG.debug("public int returnIntegerArg(int i)");
        nap();

        return i;
    }


    /**
     * Method returnLongArg
     *
     * @param i .
     *
     * @return .
     */
    public long returnLongArg(long i) {

        LOG.debug("public long returnLongArg(long i)");
        nap();

        return i;
    }


    /**
     * Method returnObjectArg
     *
     * @param obj .
     *
     * @return .
     */
    public Object returnObjectArg(Object obj) {

        LOG.debug("public Object returnObjectArg(Object obj)");
        nap();

        return obj;
    }


    /**
     * Method returnStringArrayArg
     *
     * @param args .
     *
     * @return .
     */
    public String[] returnStringArrayArg(String[] args) {

        LOG.debug("public void oneStringArrayArg(String[] args))");
        nap();

        return args;
    }


    /**
     * Method noArgsSynchronized
     */
    public synchronized void noArgsSynchronized() {
        LOG.debug("public synchronized void noArgsSynchronized()");
        nap();
    }


    /**
     * Method noArgsStatic
     */
    public static void noArgsStatic() {
        LOG.debug("public static void noArgsStatic()");
        nap();
    }


    /**
     * Method twoIntegerArg
     *
     * @param a .
     * @param b .
     *
     * @return .
     */
    public int twoIntegerArg(int a, int b) {

        LOG.debug("public int twoIntegerArg(int a, int b)");
        nap();

        return a + b;
    }


    /**
     * Method twoLongArg
     *
     * @param a .
     * @param b .
     *
     * @return .
     */
    public long twoLongArg(long a, long b) {

        LOG.debug("public long twoLongArg(long a, long b)");
        nap();

        return a + b;
    }


    /**
     * Method twoObjectArg
     *
     * @param a .
     * @param b .
     *
     * @return .
     */
    public boolean twoObjectArg(Object a, Object b) {

        LOG.debug("public boolean twoObjectArg(Object a, Object b))");
        nap();

        return (a != null) && (a.equals(b));
    }


    /**
     * Method twoStringArrayArg
     *
     * @param a .
     * @param b .
     *
     * @return .
     */
    public boolean twoStringArrayArg(String[] a, String[] b) {

        LOG.debug("public boolean twoStringArrayArg(String[] a, String[] b)");
        nap();

        return (a.length > b.length);
    }


    /**
     * Method argsLongObjectLongLongArg
     *
     * @param a .
     * @param obj .
     * @param b .
     * @param c .
     *
     * @return .
     */
    public long argsLongObjectLongLongArg(long a, Object obj, long b, long c) {

        LOG.debug("public long argsLongObjectLongLongArg(long a, Object obj, long b, long c)");
        nap();

        return (a + b + c) + obj.hashCode();
    }


    /**
     * Method argsByteBooleanArrayObject
     *
     * @param b .
     * @param bool .
     * @param obj .
     *
     * @return .
     */
    public int argsByteBooleanArrayObject(byte b, boolean[] bool, Object obj) {

        LOG.debug("public int argsLongObjectLongLongArg(long a, Object obj, long b, long c)");
        nap();

        return b + obj.hashCode() + bool.length;
    }


    /**
     * Method add
     *
     * @param a .
     * @param b .
     *
     * @return .
     */
    private int add(int a, int b) {
        return a + b;
    }


    /**
     * Method callMethodAdd
     */
    public void callAdd() {

        int t = 1;

        while (t < 1000000)
        {
            t = add(t, (t / 5) + 1);
        }
    }


    /**
     * Method recurse
     *
     * @param x .
     *
     * @return .
     */
    private int recurse(int x) {

        if (x == 0)
        {
            return 0;
        }
        else
        {
            return x + (recurse(x - 1));
        }
    }


    /**
     * Method localVariableLength
     *
     * @param arg0 .
     *
     * @return .
     */
    public int localVariableLength(String arg0) {

        StringBuffer sBuffer = new StringBuffer("THis is arg0" + arg0);
        int          numbah  = 32;

        LOG.debug(sBuffer);

        numbah += add(68, 46);

        sBuffer.append("this is it");
        sBuffer.ensureCapacity(numbah);

        numbah          -= twoLongArg(6, 21);
        numbah          = 500 - add(60, 24);
        this.privateInt = 63;
        numbah          += this.privateInt;

        return add(numbah, arg0.length());
    }


    /**
     * Method callRecurse
     */
    public void callRecurse() {

        //      LOG.debug("recurse(2000) = " + recurse(2000));
        LOG.debug("recurse(250) = " + recurse(250));
    }


    /**
     * Method compute
     */
    public void compute() {
        Math.sqrt(9);
    }


    /**
     * Method callInLoop_1000
     */
    public void callInLoop_1000() {

        for (int i = 0; i < 1000; i++)
        {
            compute();
        }
    }


    /**
     * Method callInLoop_10000
     */
    public void callInLoop_10000() {

        for (int i = 0; i < 50000; i++)
        {
            compute();
        }
    }


    /**
     * Method callInLoop_50000
     */
    public void callInLoop_50000() {

        long start = System.currentTimeMillis();

        for (int i = 0; i < 50000; i++)
        {
            compute();
        }

        LOG.info("callInLoop_50000 took " + (System.currentTimeMillis() - start));
    }


    /**
     * Method callInLoop_100000
     */
    public void callInLoop_100000() {

        long start = System.currentTimeMillis();

        for (int i = 0; i < 100000; i++)
        {
            compute();
        }

        LOG.info("callInLoop_100000 took " + (System.currentTimeMillis() - start));
    }


    /**
     * Method callInLoop_1000000
     */
    public void callInLoop_1000000() {

        long start = System.currentTimeMillis();

        for (int i = 0; i < 1000000; i++)
        {
            compute();
        }

        LOG.info("callInLoop_1000000 took " + (System.currentTimeMillis() - start));
    }


    /**
     * Method throwIOException
     *
     * @throws IOException
     */
    public void throwIOException() throws IOException {

        LOG.debug("public void throwIOException() throws IOException");

        throw new IOException("test");
    }


    /**
     * Method throwRuntimeException
     *
     * @throws RuntimeException
     */
    public void throwRuntimeException() throws RuntimeException {

        LOG.debug("public void throwRuntimeException() throws RuntimeException");

        throw new RuntimeException("test");
    }
}
