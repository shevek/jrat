package org.shiftone.jrat.ui.viewer;

import org.shiftone.jrat.core.spi.ui.ViewContext;
import org.shiftone.jrat.core.spi.ViewBuilder;
import org.shiftone.jrat.core.JRatException;
import org.shiftone.jrat.util.io.IOUtil;
import org.shiftone.jrat.util.log.Logger;

import javax.swing.JComponent;
import java.io.File;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.zip.GZIPInputStream;

/**
 * @Author Jeff Drost
 */
public class OpenOutputFileRunnable2 implements Runnable {

    private static final Logger LOG = Logger.getLogger(OpenOutputFileRunnable2.class);      
    private final ViewContext viewContext;


    public OpenOutputFileRunnable2(ViewContext viewContext) {
        this.viewContext = viewContext;
    }

    public void run() {

        File file = viewContext.getInputFile();

        try {

            InputStream inputStream = IOUtil.openInputStream(file);

            inputStream = new GZIPInputStream(inputStream);

            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

            ViewBuilder viewBuilder = (ViewBuilder) objectInputStream.readObject();

            JComponent component = viewBuilder.buildView();

            viewContext.setComponent(component);

        } catch (Exception e) {

            LOG.error("error creating view",e);

            throw new JRatException("unable to read file", e);

        }
    }
}
