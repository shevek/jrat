package org.shiftone.jrat.core.spi;

import javax.swing.JComponent;
import java.io.Serializable;

/**
 * @Author Jeff Drost
 */
public interface ViewBuilder extends Serializable {

     public JComponent buildView() throws Exception;
    
}
