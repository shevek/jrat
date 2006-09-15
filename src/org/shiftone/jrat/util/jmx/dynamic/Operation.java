package org.shiftone.jrat.util.jmx.dynamic;



/**
 * @author Jeff Drost
 * @version $Revision: 1.2 $
 */
public interface Operation {

    Object invoke(Object params[]);


    String getDescription();


    String getReturnType();


    String getParameterName(int index);


    String getParameterDescription(int index);
}
