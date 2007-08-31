package org.shiftone.jrat.api;


public class Callable implements Command {

    public void run() throws Throwable {
    }


    public Object call() throws Throwable {

        run();

        return null;
    }


    public Object execute() throws Throwable {
        return call();
    }
}
