package org.shiftone.jrat.util;



import org.shiftone.jrat.core.JRatException;


/**
 * @author $Author: jeffdrost $
 * @version $Revision: 1.7 $
 */
public class Command {

    /**
     * implement this method if you DO NOT want to return something
     */
    protected void run() throws Exception {}


    /**
     * implement this method if you want to return something
     */
    protected Object call() throws Exception {

        run();

        return null;
    }


    public Object execute() {

        try
        {
            return call();
        }
        catch (RuntimeException e)
        {
            throw e;
        }
        catch (Throwable e)
        {
            throw new JRatException("command failed", e);
        }
    }
}
