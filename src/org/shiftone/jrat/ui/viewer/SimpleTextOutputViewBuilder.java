package org.shiftone.jrat.ui.viewer;


import org.shiftone.jrat.core.spi.ui.OutputViewBuilder;
import org.shiftone.jrat.core.spi.ui.ViewContext;
import org.shiftone.jrat.util.io.IOUtil;
import org.shiftone.jrat.util.log.Logger;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;


/**
 * @author Jeff Drost
 */
public class SimpleTextOutputViewBuilder implements OutputViewBuilder {

    private static final Logger LOG = Logger.getLogger(SimpleTextOutputViewBuilder.class);

    public void buildView(ViewContext context) throws Exception {

        InputStream inputStream = null;
        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        LOG.debug("createViewerComponent");

        try {
            inputStream = context.openInputStream();

            IOUtil.copy(inputStream, outputStream);
            outputStream.flush();

            //
            textArea.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            textArea.setEditable(false);
            textArea.setBackground(Color.white);
            textArea.setText(outputStream.toString());
        }
        finally {
            IOUtil.close(inputStream);
            IOUtil.close(outputStream);
        }

        context.setComponent(scrollPane);
    }
}
