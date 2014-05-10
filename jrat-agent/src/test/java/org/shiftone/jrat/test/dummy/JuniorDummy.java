package org.shiftone.jrat.test.dummy;


import java.io.Serializable;
import org.shiftone.jrat.util.log.Logger;


/**
 * Class JuniorDummy
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 */
public class JuniorDummy implements Serializable, Cloneable {

    // private static int    privateInt = 0;
    // static final int      x          = 1;
    //  private static String stringA    = "test";

    private final long serialVersionUID = 0;

    private final Logger LOG = Logger.getLogger(CrashTestDummy.class);
    public String string1 = "";
    private final String stringC = "";
    private final transient String stringD = "";
    public int a;
    public int b;
    public int c;

    /**
     * Constructor JuniorDummy
     */
    public JuniorDummy() {
    }


    /**
     * Method x
     *
     * @return 1 + CrashTestDummy.class.getName().length()
     */
    public int x() {
        return 1 + CrashTestDummy.class.getName().length();
    }


    /**
     * Method y
     *
     * @return 1
     */
    public int y() {
        return 1;
    }

    public void ttt(int i) {
        System.out.println("ttt");
    }

    public int uuu(int i) {
        return i;
    }

    public static void main(String[] args) {
        System.out.println("JuniorDummy main");
        JuniorDummy dummy = new JuniorDummy();
        //dummy.y();
        //dummy.x();
    }
}
