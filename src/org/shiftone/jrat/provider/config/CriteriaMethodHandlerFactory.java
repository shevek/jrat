package org.shiftone.jrat.provider.config;



import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.core.spi.MethodHandler;
import org.shiftone.jrat.core.spi.MethodHandlerFactory;
import org.shiftone.jrat.core.spi.RuntimeContext;
import org.shiftone.jrat.core.criteria.MethodCriteria;


public class CriteriaMethodHandlerFactory implements MethodHandlerFactory {

    private MethodCriteria       methodCriteria;
    private MethodHandlerFactory methodHandlerFactory;

    public CriteriaMethodHandlerFactory() {}


    public CriteriaMethodHandlerFactory(MethodHandlerFactory methodHandlerFactory, MethodCriteria methodCriteria) {
        this.methodHandlerFactory = methodHandlerFactory;
        this.methodCriteria       = methodCriteria;
    }


    public MethodHandler createMethodHandler(MethodKey methodKey) throws Exception {

        if (methodCriteria.isMatch(methodKey.getClassName(), 0))
        {
            if (methodCriteria.isMatch(methodKey.getClassName(), methodKey.getMethodName(), methodKey.getSignature(),
                                       0))
            {
                return methodHandlerFactory.createMethodHandler(methodKey);
            }
        }

        return null;
    }


    public void startup(RuntimeContext context) throws Exception {
        methodHandlerFactory.startup(context);
    }


    public void setMethodCriteria(MethodCriteria methodCriteria) {
        this.methodCriteria = methodCriteria;
    }


    public void setMethodHandlerFactory(MethodHandlerFactory methodHandlerFactory) {
        this.methodHandlerFactory = methodHandlerFactory;
    }
}
