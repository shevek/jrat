package org.shiftone.jrat.util.io;



import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * @author $Author: jeffdrost $
 * @version $Revision: 1.8 $
 * @deprecated
 */
public class CountingInputStream extends BufferedInputStream {

    private long bytesReadMark = 0;
    private long bytesRead     = 0;

    public CountingInputStream(InputStream in, int size) {
        super(in, size);
    }


    public CountingInputStream(InputStream in) {
        super(in);
    }


    public long getBytesRead() {
        return bytesRead;
    }


    public synchronized int read() throws IOException {

        int read = super.read();

        if (read >= 0)
        {
            bytesRead++;
        }

        return read;
    }


    public synchronized int read(byte[] b, int off, int len) throws IOException {

        int read = super.read(b, off, len);

        if (read >= 0)
        {
            bytesRead += read;
        }

        return read;
    }


    public synchronized long skip(long n) throws IOException {

        long skipped = super.skip(n);

        if (skipped >= 0)
        {
            bytesRead += skipped;
        }

        return skipped;
    }


    public synchronized void mark(int readlimit) {

        super.mark(readlimit);

        bytesReadMark = bytesRead;
    }


    public synchronized void reset() throws IOException {

        super.reset();

        bytesRead = bytesReadMark;
    }
}
