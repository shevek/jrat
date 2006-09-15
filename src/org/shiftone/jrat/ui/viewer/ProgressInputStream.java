package org.shiftone.jrat.ui.viewer;



import org.shiftone.jrat.util.log.Logger;

import javax.swing.BoundedRangeModel;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * Class ProgressInputStream
 *
 * @author $Author: jeffdrost $
 * @version $Revision: 1.17 $
 */
public class ProgressInputStream extends InputStream {

    private static final Logger LOG         = Logger.getLogger(ProgressInputStream.class);
    private InputStream         inputStream = null;
    private BoundedRangeModel   rangeModel  = null;
    private long                maxBytes    = 0;
    private long                bytesRead   = 0;

    public ProgressInputStream(InputStream inputStream, long maxBytes, BoundedRangeModel rangeModel) {

        this.inputStream = new BufferedInputStream(inputStream, 24 * 1024);
        this.maxBytes    = maxBytes;
        this.rangeModel  = rangeModel;

        rangeModel.setMinimum(0);
        rangeModel.setMaximum((int) maxBytes);
        rangeModel.setValue(0);
    }


    public ProgressInputStream(File file, BoundedRangeModel rangeModel) throws IOException {
        this(new FileInputStream(file), file.length(), rangeModel);
    }


    public void close() throws IOException {

        LOG.debug("bytesRead = " + bytesRead);
        LOG.debug("maxBytes  = " + maxBytes);
        LOG.info("close");
        rangeModel.setValue(0);
        super.close();
    }


    public BoundedRangeModel getBoundedRangeModel() {
        return rangeModel;
    }


    private void updateModel() {
        rangeModel.setValue((int) bytesRead);
        Thread.yield();
    }


    public int read() throws IOException {

        int i = inputStream.read();

        if ((i != -1) && (bytesRead < maxBytes))
        {
            bytesRead++;

            updateModel();
        }

        return i;
    }


    public boolean markSupported() {
        return false;
    }


    public int read(byte b[]) throws IOException {

        int i = inputStream.read(b);

        if ((i != -1) && (bytesRead < maxBytes))
        {
            bytesRead += i;

            updateModel();
        }

        return i;
    }


    public int read(byte b[], int off, int len) throws IOException {

        int i = inputStream.read(b, off, len);

        if ((i != -1) && (bytesRead < maxBytes))
        {
            bytesRead += i;

            updateModel();
        }

        return i;
    }


    public long skip(long n) throws IOException {

        long i = inputStream.skip(n);

        if ((i != -1) && (bytesRead < maxBytes))
        {
            bytesRead += i;

            updateModel();
        }

        return i;
    }
}
