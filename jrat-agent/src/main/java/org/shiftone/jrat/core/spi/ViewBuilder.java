package org.shiftone.jrat.core.spi;

import java.io.ObjectInputStream;
import java.io.Serializable;
import javax.swing.JComponent;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public interface ViewBuilder extends Serializable {

    public JComponent buildView(ObjectInputStream input) throws Exception;

}
