package org.shiftone.jrat.test.jmx;

/**
 * @author Jeff Drost
 */
public class Test implements TestMBean {

    private String string;
    private int number;
    private Double doubleNumber;

    public void doIt() {

    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public int getNumber() {
        number += 1;
        if (number > 100) {
            number = 0;
        }
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Double getDoubleNumber() {
        return doubleNumber;
    }

    public void setDoubleNumber(Double doubleNumber) {
        this.doubleNumber = doubleNumber;
    }

}
