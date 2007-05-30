package org.shiftone.jrat.provider.rate;



import org.shiftone.jrat.core.Accumulator;
import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.core.spi.RuntimeContext;
import org.shiftone.jrat.provider.rate.ui.RateOutputViewBuilder;
import org.shiftone.jrat.util.log.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;


/**
 * Class RateOutput
 *
 * @author Jeff Drost
 *
 */
public class RateOutput {

    private static final Logger  LOG             = Logger.getLogger(RateOutput.class);
    private static final Runtime RT              = Runtime.getRuntime();
    private static final String  TAB             = "\t";
    public static final String   PREFIX_METHOD   = "METHOD";
    public static final String   PREFIX_SAMPLE   = "SAMPLE";
    public static final String   PREFIX_SHUTDOWN = "SHUTDOWN";
    public static final String   POSTFIX_END     = "END";
    private int                  methodCount     = 0;
    private int                  maxMethods      = 0;
    private RuntimeContext       context         = null;
    private OutputStream         outputStream    = null;
    private Writer               writer          = null;

    /**
     * Constructor for RateOutput
     *
     * @param outputStream .
     * @param maxMethods .
     * @param context .
     */
    public RateOutput(OutputStream outputStream, int maxMethods, RuntimeContext context) {

        // super(outputStream);
        this.outputStream = outputStream;
        this.writer       = new PrintWriter(outputStream);
        this.maxMethods   = maxMethods;
        this.context      = context;
    }


    /**
     * method
     *
     * @return .
     */
    public int getMethodCount() {
        return methodCount;
    }


    private void print(char c) throws IOException {
        print(String.valueOf(c));
    }


    private void print(long number) throws IOException {
        print(String.valueOf(number));
    }


    private void println(long number) throws IOException {
        println(String.valueOf(number));
    }


    private void println(String text) throws IOException {
        print(text);
        print("\n");
    }


    private void print(String text) throws IOException {
        writer.write(text);
    }


    /**
     * method
     *
     * @param methodKey .
     *
     * @return .
     */
    public synchronized int printMethodDef(MethodKey methodKey) throws IOException {

        int methodId = methodCount++;

        LOG.info("writeMethodDef : " + methodKey);
        print(PREFIX_METHOD + TAB);
        print(methodId);
        print(TAB);
        print(methodKey.getClassName());
        print(TAB);
        print(methodKey.getMethodName());
        print(TAB);
        print(methodKey.getSignature());
        println(TAB + POSTFIX_END);

        return methodId;
    }


    /**
     * method
     *
     * @param period .
     */
    public synchronized void printHeader(long period) throws IOException {

        LOG.info("writeHeader " + period);
        print("viewer=\"");
        print(RateOutputViewBuilder.class.getName());
        println("\"");
        println(maxMethods);
        println(context.getStartTimeMs());
        println(System.currentTimeMillis());
        println(period);
    }


    /**
     * Flushes the current set of accumulators to file and resets them. This
     * method has side effect on handlers.
     *
     * @param handlers .
     */
    public synchronized void printSample(RateMethodHandler[] handlers) throws IOException {

        RateMethodHandler handler     = null;
        Accumulator       accumulator = null;
        long              time        = System.currentTimeMillis() - context.getStartTimeMs();
        long              totalMemory = RT.totalMemory();    // was maxMemory
        long              freeMemory  = RT.freeMemory();

        print(PREFIX_SAMPLE + TAB);
        print(time);
        print(',');
        print(freeMemory);
        print(',');
        print(totalMemory);

        for (int i = 0; i < methodCount; i++)
        {
            handler     = handlers[i];
            accumulator = handler.getAndReplaceAccumulator();

            print(TAB);
            print(Accumulator.toCSV(accumulator));
        }

        println(TAB + POSTFIX_END);
    }


    /**
     * method
     */
    public synchronized void close() throws IOException {

        LOG.info("close");
        print(PREFIX_SHUTDOWN + TAB);
        print(System.currentTimeMillis());
        print(TAB);
        print(System.currentTimeMillis() - context.getStartTimeMs());
        println(TAB + POSTFIX_END);
        writer.flush();
        writer.close();
        outputStream.flush();
        outputStream.close();
    }
}
