package org.shiftone.jrat.core.spi;

import javax.swing.JComponent;
import java.io.Serializable;
import java.io.File;

/**
 * @Author Jeff Drost
 */
public interface ViewBuilder extends Serializable {

     public JComponent buildView(File source) throws Exception;
    
}
