package org.shiftone.jrat.core;

import java.io.Writer;
import org.shiftone.jrat.core.shutdown.ShutdownListener;
import org.shiftone.jrat.core.spi.RuntimeContext;
import org.shiftone.jrat.util.io.IOUtil;
import org.shiftone.jrat.util.log.Logger;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 *         TODO - add support for java.lang.management.MemoryMXBean
 */
public class MemoryMonitor implements ShutdownListener {

    private static final Logger LOG = Logger.getLogger(MemoryMonitor.class);
    private final RuntimeContext context;
    private final Writer writer;
    private final Thread thread;
    private static final Runtime RT = Runtime.getRuntime();
    private volatile boolean running = true;

    public MemoryMonitor(RuntimeContext context) {

        this.context = context;
        this.writer = context.createWriter("memory.csv");

        this.thread = new Thread(new Ticker());
        thread.setDaemon(true);
        thread.setName("Memory Ticker");
        thread.setPriority(Thread.NORM_PRIORITY - 1);
        thread.start();

        context.registerShutdownListener(this);

    }

    @Override
    public void shutdown() throws InterruptedException {
        running = false;
        thread.join(1000 * 10);
    }

    private void header() throws Exception {

        StringBuilder sb = new StringBuilder();
        sb.append("time ms");
        sb.append(",");
        sb.append("free");
        sb.append(",");
        sb.append("max");
        sb.append(",");
        sb.append("total");
        sb.append("\n");

        writer.write(sb.toString());
    }

    private void execute() throws Exception {

        StringBuilder sb = new StringBuilder();

        sb.append(System.currentTimeMillis() - context.getStartTimeMs());
        sb.append(",");
        sb.append(RT.freeMemory());
        sb.append(",");
        sb.append(RT.maxMemory());
        sb.append(",");
        sb.append(RT.totalMemory());
        sb.append("\n");

        writer.write(sb.toString());
        writer.flush();

    }

    @Override
    public String toString() {
        return "Memory Monitor";
    }

    private class Ticker implements Runnable {

        @Override
        public void run() {

            try {
                header();
                while (running) {
                    execute();
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                running = false;
            }

            IOUtil.close(writer);
        }

    }
}
