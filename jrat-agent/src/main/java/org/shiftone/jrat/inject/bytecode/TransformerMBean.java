package org.shiftone.jrat.inject.bytecode;


/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public interface TransformerMBean {

    long getTransformedClassCount();


    double getAverageTransformTimeMs();


    double getAverageBloatPercent();


    String getInjectorStrategyText();
}
