package org.shiftone.jrat.provider.errors;



import org.shiftone.jrat.core.JRatException;
import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.core.spi.AbstractMethodHandlerFactory;
import org.shiftone.jrat.core.spi.MethodHandler;
import org.shiftone.jrat.util.log.Logger;

import java.io.PrintWriter;


/**
 * @author $Author: jeffdrost $
 * @version $Revision: 1.7 $
 */
public class ErrorsMethodHandlerFactory extends AbstractMethodHandlerFactory {

    private static final Logger LOG           = Logger.getLogger(ErrorsMethodHandlerFactory.class);
    private ErrorsMethodHandler methodHandler = null;

    public synchronized MethodHandler createMethodHandler(MethodKey methodKey) {

        try
        {
            if (methodHandler == null)
            {
                PrintWriter printWriter = getContext().createPrintWriter(getDefaultOutputFileName());

                methodHandler = new ErrorsMethodHandler(printWriter);
            }

            return methodHandler;
        }
        catch (Exception e)
        {
            throw new JRatException("ErrorsMethodHandlerFactory error", e);
        }
    }
}
