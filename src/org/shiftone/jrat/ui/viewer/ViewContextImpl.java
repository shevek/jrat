package org.shiftone.jrat.ui.viewer;


import org.shiftone.jrat.core.spi.ui.View;
import org.shiftone.jrat.core.spi.ui.ViewContext;
import org.shiftone.jrat.util.io.IOUtil;
import org.shiftone.jrat.util.log.Logger;

import javax.swing.*;
import java.io.*;
import java.util.zip.GZIPInputStream;


/**
 * @author $author$
 */
public class ViewContextImpl implements ViewContext {

    private static final Logger LOG = Logger.getLogger(ViewContextImpl.class);
    private final File file;
    private final View view;
    private final BoundedRangeModel boundedRangeModel;

    ViewContextImpl(View view, File file) throws IOException {

        this.view = view;
        this.file = file;
        this.boundedRangeModel = view.getRangeModel();

        if (file.exists() == false) {
            throw new IOException("file does not exist : " + file);
        }
    }


    public BoundedRangeModel getBoundedRangeModel() {
        return boundedRangeModel;
    }


    public File getInputFile() {
        return file;
    }


    public InputStream openInputStream() throws IOException {

        ProgressInputStream progressInputStream = null;
        InputStream intputStream = null;

        LOG.info("openInputStream");

        progressInputStream = new ProgressInputStream(file, boundedRangeModel);
        intputStream = isZipped()
                ? (InputStream) new GZIPInputStream(progressInputStream)
                : progressInputStream;

        return intputStream;
    }


    public Reader openReader() throws IOException {
        return new InputStreamReader(openInputStream());
    }


    private boolean isZipped() {
        return "gz".equalsIgnoreCase(IOUtil.getExtention(file));
    }


    public View getView() {
        return view;
    }


    public void setComponent(JComponent component) {
        getView().setBody(component);
    }
}
