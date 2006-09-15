package org.shiftone.jrat.provider.trace.ui;



import org.shiftone.jrat.core.spi.ui.OutputViewBuilder;
import org.shiftone.jrat.core.spi.ui.ViewContext;


/**
 * @author Jeff Drost
 * @version $Revision: 1.5 $
 */
public class TraceOutputViewBuilder implements OutputViewBuilder {

    public void buildView(ViewContext context) throws Exception {

        TraceInput input      = new TraceInput(context.openInputStream());
        TraceModel traceModel = input.read();

        context.setComponent(new TraceViewPanel(traceModel));
    }
}
