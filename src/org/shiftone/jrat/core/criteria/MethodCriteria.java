package org.shiftone.jrat.core.criteria;



/**
 * @author Jeff Drost
 * @version $Revision: 1.2 $
 */
public interface MethodCriteria {

    public static final MethodCriteria ALL     = ConstantMethodCriteria.ALL;
    public static final MethodCriteria NONE    = ConstantMethodCriteria.NONE;
    public static final MethodCriteria DEFAULT = ALL;    // new

    // DebugMethodCriteria(ALL);

    /**
     * should a class with these attributes be matched?
     */
    boolean isMatch(String className, long modifier);


    /**
     * should a method with these attributes be matched?
     */
    boolean isMatch(String className, String methodName, String signature, long modifier);
}
