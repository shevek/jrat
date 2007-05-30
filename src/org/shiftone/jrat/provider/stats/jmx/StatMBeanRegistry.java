package org.shiftone.jrat.provider.stats.jmx;


import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.core.MethodKeyAccumulator;
import org.shiftone.jrat.core.Signature;
import org.shiftone.jrat.core.spi.RuntimeContext;
import org.shiftone.jrat.provider.stats.jmx.attributes.AverageDurationAttributeValue;
import org.shiftone.jrat.provider.stats.jmx.attributes.ConcurrentThreadsAttributeValue;
import org.shiftone.jrat.provider.stats.jmx.attributes.MaxConcurrentThreadsAttributeValue;
import org.shiftone.jrat.provider.stats.jmx.attributes.MaxDurationAttributeValue;
import org.shiftone.jrat.provider.stats.jmx.attributes.MinDurationAttributeValue;
import org.shiftone.jrat.provider.stats.jmx.attributes.TotalEntersAttributeValue;
import org.shiftone.jrat.provider.stats.jmx.attributes.TotalErrorsAttributeValue;
import org.shiftone.jrat.provider.stats.jmx.attributes.TotalExitsAttributeValue;
import org.shiftone.jrat.util.jmx.dynamic.ConfigurableMBean;

import java.util.HashMap;
import java.util.Map;


/**
 * @author Jeff Drost
 */
public class StatMBeanRegistry {

    private Map classMbeans = new HashMap();    // maps methodKey to ConfigurableMBean
    private RuntimeContext context;

    public StatMBeanRegistry(RuntimeContext context) {
        this.context = context;
    }


    public synchronized ConfigurableMBean getMBean(String className) {

        ConfigurableMBean attributeMBean = (ConfigurableMBean) classMbeans.get(className);

        if (attributeMBean == null) {
            attributeMBean = new ConfigurableMBean("JRat Statistics for class " + className);

            classMbeans.put(className, attributeMBean);
            context.registerMBean(attributeMBean, "shiftone.jrat.stats:class=" + className);
        }

        return attributeMBean;
    }


    public void registerMethodKeyAccumulator(MethodKeyAccumulator accumulator) {

        MethodKey methodKey = accumulator.getMethodKey();
        ConfigurableMBean mBean = getMBean(methodKey.getClassName());
        Signature signature = new Signature(methodKey.getSignature());
        String name = methodKey.getMethodName() + "(" + signature.getShortText() + ")";

        mBean.add(name + ".AverageDuration", new AverageDurationAttributeValue(accumulator));
        mBean.add(name + ".ConcurrentThreads", new ConcurrentThreadsAttributeValue(accumulator));
        mBean.add(name + ".MaxConcurrent", new MaxConcurrentThreadsAttributeValue(accumulator));
        mBean.add(name + ".MaxDuration", new MaxDurationAttributeValue(accumulator));
        mBean.add(name + ".MinDuration", new MinDurationAttributeValue(accumulator));
        mBean.add(name + ".TotalEnters", new TotalEntersAttributeValue(accumulator));
        mBean.add(name + ".TotalExits", new TotalExitsAttributeValue(accumulator));
        mBean.add(name + ".TotalErrors", new TotalErrorsAttributeValue(accumulator));
        mBean.add(name + ".Reset", new ResetAccumulatorOperation(accumulator));
    }
}
