package org.shiftone.jrat.provider.rate.ui;



import org.shiftone.jrat.core.spi.ui.OutputViewBuilder;
import org.shiftone.jrat.core.spi.ui.ViewContext;
import org.shiftone.jrat.util.log.Logger;


/**
 * @author Jeff Drost
 *
 */
public class RateOutputViewBuilder implements OutputViewBuilder {

    private static final Logger LOG = Logger.getLogger(RateOutputViewBuilder.class);

    public void buildView(ViewContext context) throws Exception {

        RateModel model = new RateModel();

        model.load(context.openInputStream());
        context.setComponent(new RateViewerPanel(model));
    }
}
