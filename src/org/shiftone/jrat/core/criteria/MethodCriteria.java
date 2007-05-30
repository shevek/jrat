package org.shiftone.jrat.core.criteria;



/**
 * @author Jeff Drost
 *
 */
public interface MethodCriteria {

    public static final MethodCriteria ALL     = ConstantMethodCriteria.ALL;
    public static final MethodCriteria NONE    = ConstantMethodCriteria.NONE;
    public static final MethodCriteria DEFAULT = ALL;    // new

    // DebugMethodCriteria(ALL);

    /**
     * should a class with these attributes be matched?
     */
    boolean isMatch(String className, long modifiers);


    /**
     * should a method with these attributes be matched?
     */
    boolean isMatch(String className, String methodName, String signature, long modifiers);
}
