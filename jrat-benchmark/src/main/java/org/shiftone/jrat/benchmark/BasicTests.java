package org.shiftone.jrat.benchmark;

/**
 * @author (jeff@shiftone.org) Jeff Drost
 */
public class BasicTests {


    /*
    DOB 9/17/07
    Time 1708
    Wt 3465
    Apgar 6, 8
    Gest Age 38
    Delivery Doctor Crane
    (V) C/S
     */

    public Object aMethod(Object x) {
        return x;
    }

    @Benchmark(title= "Do Nothing")
    public void nothing() {
       ;
    }

    @Benchmark(title = "Call A Method")
    public void callMethod() {
        aMethod(this);
    }

     
}
