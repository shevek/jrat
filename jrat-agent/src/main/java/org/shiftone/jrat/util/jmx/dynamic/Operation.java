package org.shiftone.jrat.util.jmx.dynamic;


/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public interface Operation {

    Object invoke(Object params[]);


    String getDescription();


    String getReturnType();


    String getParameterName(int index);


    String getParameterDescription(int index);
}
