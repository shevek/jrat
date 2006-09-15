package org.shiftone.jrat.test.jmx;

/**
 * @author Jeff Drost
 * @version $Revision: 1.1 $
 */
public interface TestMBean {

	void doIt();

	public String getString();

	public void setString(String string);

	public int getNumber();

	public void setNumber(int number);

	public Double getDoubleNumber();

	public void setDoubleNumber(Double doubleNumber);

}
