package org.shiftone.jrat.ui.viewer;


import org.shiftone.jrat.core.spi.ui.OutputViewBuilder;
import org.shiftone.jrat.core.spi.ui.View;
import org.shiftone.jrat.core.spi.ui.ViewContainer;
import org.shiftone.jrat.core.spi.ui.ViewContext;
import org.shiftone.jrat.ui.UIConstants;
import org.shiftone.jrat.ui.util.ExceptionDialog;
import org.shiftone.jrat.util.io.IOUtil;
import org.shiftone.jrat.util.io.ResourceUtil;
import org.shiftone.jrat.util.log.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;


/**
 * Class OpenOutputFileAction
 *
 * @author Jeff Drost
 */
public class OpenOutputFileAction implements ActionListener, UIConstants {

    private static final Logger LOG = Logger.getLogger(OpenOutputFileAction.class);
    public static final String VIEWER_STRING = "viewer=\"";
    private Frame parent = null;
    private ViewContainer viewContainer = null;
    private ActionEvent actionEvent = null;

    public OpenOutputFileAction(Frame parent, ViewContainer viewContainer) {
        this.parent = parent;
        this.viewContainer = viewContainer;
    }


    public void actionPerformed(ActionEvent e) {

        JFileChooser chooser = new JFileChooser();
        File lastFile = SETTINGS.getLastOpenedOutputFile();

        try {
            chooser.addChoosableFileFilter(OUTPUT_FILE_FILTER);

            if (lastFile != null) {
                chooser.setCurrentDirectory(IOUtil.getNearestExistingParent(lastFile));
                chooser.setSelectedFile(lastFile);
            }

            if (JFileChooser.APPROVE_OPTION == chooser.showOpenDialog(parent)) {
                openFile(chooser.getSelectedFile());
            }
        }
        catch (Exception ex) {
            new ExceptionDialog(parent, ex).setVisible(true);
        }
    }


    public void openFile(File inputFile) throws IOException {

        LOG.info("openFile(" + inputFile + ")");
        SETTINGS.setLastOpenedOutputFile(inputFile);

        String title = inputFile.getName();
        ViewContextImpl runtimeOutput = null;
        OutputViewBuilder viewBuilder = null;
        View view = viewContainer.createView(title);

        runtimeOutput = new ViewContextImpl(view, inputFile);
        viewBuilder = getOutputViewerFactory(runtimeOutput);

        Runnable runnable = new OpenOutputFileRunnable(runtimeOutput, viewBuilder);

        new Thread(runnable).start();
    }


    private OutputViewBuilder getOutputViewerFactory(ViewContext viewContext) throws IOException {

        OutputViewBuilder factory = null;
        String klassName = null;

        klassName = getOutputViewerFactoryClassName(viewContext);
        factory = (OutputViewBuilder) ResourceUtil.newInstance(klassName);

        return factory;
    }


    private String getOutputViewerFactoryClassName(ViewContext viewContext) throws IOException {

        Reader reader = null;
        LineNumberReader lineReader = null;
        String line = null;
        String klass = null;

        try {
            reader = viewContext.openReader();
            lineReader = new LineNumberReader(reader);

            while ((line = lineReader.readLine()) != null) {
                int a = line.indexOf(VIEWER_STRING);

                if (a >= 0) {
                    int b = a + VIEWER_STRING.length();
                    int c = line.indexOf("\"", b);

                    klass = line.substring(b, c);

                    LOG.info("getOutputViewerFactory = " + klass);

                    break;
                }
            }

            if (klass == null) {
                throw new IOException("unable to find viewer definition in file");
            }
        }
        catch (IOException e) {
            throw e;
        }
        finally {
            IOUtil.close(reader);
        }

        return klass;
    }
}
