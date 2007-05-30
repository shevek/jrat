package org.shiftone.jrat.core.spi.ui;



/**
 * @author Jeff Drost
 *
 */
public interface OutputViewBuilder {
    void buildView(ViewContext context) throws Exception;
}
