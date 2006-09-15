package org.shiftone.jrat.inject.bytecode;



/**
 * @author $Author: jeffdrost $
 * @version $Revision: 1.3 $
 */
public interface TransformerMBean {

    long getTransformedClassCount();


    double getAverageTransformTimeMs();


    double getAverageBloatPercent();


    String getInjectorStrategyText();
}
