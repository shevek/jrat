package org.shiftone.jrat.core.spi;

import javax.swing.*;
import java.io.File;
import java.io.Serializable;
import java.io.ObjectInputStream;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public interface ViewBuilder extends Serializable {

    public JComponent buildView(ObjectInputStream input) throws Exception;

}
