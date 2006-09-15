package org.shiftone.jrat.core.spi.ui;



import javax.swing.BoundedRangeModel;
import javax.swing.JComponent;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;


/**
 * @author $author$
 * @version $Revision: 1.4 $
 */
public interface ViewContext {

    BoundedRangeModel getBoundedRangeModel();


    File getInputFile();


    InputStream openInputStream() throws IOException;


    Reader openReader() throws IOException;


    View getView();


    /** shorthand for getView().setBody(component) */
    void setComponent(JComponent component);
}
