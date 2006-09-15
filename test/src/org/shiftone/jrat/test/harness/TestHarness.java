package org.shiftone.jrat.test.harness;



import org.shiftone.jrat.util.log.Logger;


/**
 * Class TestHarness
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.4 $
 */
public class TestHarness {

    private static final Logger               LOG    = Logger.getLogger(TestHarness.class);
    private static final VirtualMethodCall CALL_A = new VirtualMethodCall("a.b.c", "This", "()");
    private static final VirtualMethodCall CALL_B = new VirtualMethodCall("a.b.c", "Is", "()");
    private static final VirtualMethodCall CALL_C = new VirtualMethodCall("a.b.c", "Another", "()");
    private static final VirtualMethodCall CALL_D = new VirtualMethodCall("a.b.c", "Test", "()");
    private static final VirtualMethodCall CALL_E = new VirtualMethodCall("a.b.c", "Harness", "()");
    private static final VirtualMethodCall CALL_F = new VirtualMethodCall("a.b.c", "Method", "()");
    private static final VirtualMethodCall CALL_G = new VirtualMethodCall("a.b.c", "Runner", "()");

    static
    {
        CALL_A.addChildMethodCall(CALL_B);
        CALL_A.addChildMethodCall(CALL_C);
        CALL_A.addChildMethodCall(CALL_D);
        CALL_A.addChildMethodCall(CALL_G);

        //
        CALL_B.addChildMethodCall(CALL_C);
        CALL_B.addChildMethodCall(CALL_D);
        CALL_B.addChildMethodCall(CALL_E);
        CALL_B.addChildMethodCall(CALL_F);
        CALL_B.addChildMethodCall(CALL_G);

        //
        CALL_C.addChildMethodCall(CALL_D);
        CALL_C.addChildMethodCall(CALL_E);
        CALL_C.addChildMethodCall(CALL_F);
        CALL_C.addChildMethodCall(CALL_G);

        //
        CALL_D.addChildMethodCall(CALL_E);
        CALL_D.addChildMethodCall(CALL_F);
        CALL_D.addChildMethodCall(CALL_G);

        //
        CALL_E.addChildMethodCall(CALL_F);
        CALL_E.addChildMethodCall(CALL_G);

        //
        CALL_F.addChildMethodCall(CALL_G);
    }

    /**
     * Method main
     *
     * @param args .
     */
    public static void main(String[] args) throws Exception {

     //   System.setProperty("jrat.factory", Log4jMethodHandlerFactory.class.getName());

        Runnable[] runnables = { new HarnessRunnable(10, CALL_A),     //
                                 new HarnessRunnable(200, CALL_B),    //
                                 new HarnessRunnable(300, CALL_C) };
        Thread[]   threads   = new Thread[runnables.length];

        System.out.println("testing " + System.getProperty("jrat.factory"));

        for (int i = 0; i < runnables.length; i++)
        {
            threads[i] = new Thread(runnables[i]);

            threads[i].setDaemon(true);
            threads[i].start();
        }

        for (int i = 0; i < runnables.length; i++)
        {
            threads[i].join();
        }

        System.out.println("done.");
    }
}
