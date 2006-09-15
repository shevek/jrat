package org.shiftone.jrat.core.spi.ui;



/**
 * @author $Author: jeffdrost $
 * @version $Revision: 1.6 $
 */
public interface OutputViewBuilder {
    void buildView(ViewContext context) throws Exception;
}
