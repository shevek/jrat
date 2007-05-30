package org.shiftone.jrat.inject.bytecode;


/**
 * @author Jeff Drost
 */
public interface TransformerMBean {

    long getTransformedClassCount();


    double getAverageTransformTimeMs();


    double getAverageBloatPercent();


    String getInjectorStrategyText();
}
