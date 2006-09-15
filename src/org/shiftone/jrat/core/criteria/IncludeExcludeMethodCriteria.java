package org.shiftone.jrat.core.criteria;



/**
 * @author Jeff Drost
 */
public class IncludeExcludeMethodCriteria implements MethodCriteria {

    private final AndMethodCriteria root;
    private final OrMethodCriteria  positive;
    private final OrMethodCriteria  negative;

    public IncludeExcludeMethodCriteria() {

        root     = new AndMethodCriteria();
        positive = new OrMethodCriteria();
        negative = new OrMethodCriteria();

        root.addCriteria(positive);
        root.addCriteria(new NotMethodCriteria(negative));
    }


    public void addPositive(MethodCriteria criteria) {
        positive.addCriteria(criteria);
    }


    public void addNegative(MethodCriteria criteria) {
        negative.addCriteria(criteria);
    }


    public boolean isMatch(String className, long modifier) {
        return root.isMatch(className, modifier);
    }


    public boolean isMatch(String className, String methodName, String signature, long modifier) {
        return root.isMatch(className, methodName, signature, modifier);
    }
}
