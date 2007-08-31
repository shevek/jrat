package org.shiftone.jrat.core.spi.ui;


/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public interface OutputViewBuilder {
    void buildView(ViewContext context) throws Exception;
}
