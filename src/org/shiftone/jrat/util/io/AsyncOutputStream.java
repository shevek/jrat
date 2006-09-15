package org.shiftone.jrat.util.io;



import org.shiftone.jrat.util.collection.Queue;
import org.shiftone.jrat.util.log.Logger;

import java.io.IOException;
import java.io.OutputStream;


/**
 * @author $author$
 * @version $Revision: 1.8 $
 */
public class AsyncOutputStream extends OutputStream implements Runnable {

    private static final Logger LOG             = Logger.getLogger(AsyncOutputStream.class);
    private static final int    FLUSH_FREQUENCY = 500;
    private OutputStream        target;
    private Queue               queue  = new Queue();
    private boolean             closed = false;

    public AsyncOutputStream(OutputStream target) {
        this.target = target;
    }


    public boolean work() {

        boolean keepWorking = true;

        while (keepWorking)
        {
            Runnable work = null;

            synchronized (queue)
            {
                work = (Runnable) queue.dequeue();
            }

            if (work == null)
            {
                keepWorking = false;
            }
            else
            {
                work.run();
            }
        }

        return closed;
    }


    public void write(int b) throws IOException {

        synchronized (queue)
        {
            queue.enqueue(new WriteBytesRunnable(b));
        }
    }


    public void write(byte b[]) throws IOException {

        synchronized (queue)
        {
            queue.enqueue(new WriteBytesRunnable(b));
        }
    }


    public void write(byte b[], int off, int len) throws IOException {

        synchronized (queue)
        {
            queue.enqueue(new WriteBytesRunnable(b, off, len));
        }
    }


    public void flush() throws IOException {

        synchronized (queue)
        {
            queue.enqueue(new FlushRunnable());
        }
    }


    public void close() throws IOException {

        synchronized (queue)
        {
            queue.enqueue(new CloseRunnable());
        }
    }


    public void run() {}


    private class WriteBytesRunnable implements Runnable {

        byte[] bytes;

        public WriteBytesRunnable(int b) {
            this.bytes = new byte[]{ (byte) b };
        }


        public WriteBytesRunnable(byte[] in) {

            this.bytes = new byte[in.length];

            System.arraycopy(in, 0, bytes, 0, in.length);
        }


        public WriteBytesRunnable(byte in[], int off, int len) {

            this.bytes = new byte[len];

            System.arraycopy(in, off, bytes, 0, len);
        }


        public void run() {

            try
            {
                target.write(bytes);
            }
            catch (Exception e)
            {
                throw new InputOutputException("write failed", e);
            }
        }
    }

    private class FlushRunnable implements Runnable {

        public void run() {

            try
            {
                target.flush();
            }
            catch (Exception e)
            {
                throw new InputOutputException("flush failed", e);
            }
        }
    }

    private class CloseRunnable implements Runnable {

        public void run() {

            try
            {
                closed = true;

                target.close();
            }
            catch (Exception e)
            {
                throw new InputOutputException("close failed", e);
            }
        }
    }
}
