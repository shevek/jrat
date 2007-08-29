package org.shiftone.jrat.core.spi;

import javax.swing.*;
import java.io.ObjectInputStream;
import java.io.Serializable;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public interface ViewBuilder extends Serializable {

    public JComponent buildView(ObjectInputStream input) throws Exception;

}
