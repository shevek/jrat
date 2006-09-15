package org.shiftone.jrat.provider.config.spring;



import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.core.Settings;
import org.shiftone.jrat.core.spi.MethodHandler;
import org.shiftone.jrat.core.spi.MethodHandlerFactory;
import org.shiftone.jrat.core.spi.RuntimeContext;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;


public class SpringMethodHandlerFactory implements MethodHandlerFactory {

    private ApplicationContext   springContext;
    private MethodHandlerFactory rootHandlerFactory;

    public MethodHandler createMethodHandler(MethodKey methodKey) throws Exception {

        MethodHandler methodHandler = rootHandlerFactory.createMethodHandler(methodKey);

        return methodHandler;
    }


    public void startup(RuntimeContext context) throws Exception {

        springContext      = new FileSystemXmlApplicationContext(Settings.getSpringConfigFile());
        rootHandlerFactory = (MethodHandlerFactory) springContext.getBean(Settings.getSpringBeanName());

        rootHandlerFactory.startup(context);
    }
}
