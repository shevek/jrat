package org.shiftone.jrat.test.jmx;

/**
 * @author Jeff Drost
 */
public class TestObject implements TestObjectMBean {

    private String string;
    private int number;
    private Double doubleNumber;

    @Override
    public void doIt() {

    }

    @Override
    public String getString() {
        return string;
    }

    @Override
    public void setString(String string) {
        this.string = string;
    }

    @Override
    public int getNumber() {
        number += 1;
        if (number > 100) {
            number = 0;
        }
        return number;
    }

    @Override
    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public Double getDoubleNumber() {
        return doubleNumber;
    }

    @Override
    public void setDoubleNumber(Double doubleNumber) {
        this.doubleNumber = doubleNumber;
    }

}
