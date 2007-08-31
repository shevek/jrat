package org.shiftone.jrat.test.time;

import java.util.regex.Pattern;


/**
 * @author Jeff Drost
 */
public class PatternMatchesRunnable implements Runnable {

    public void run() {
        Pattern.matches("a*b", "aaaaab");
    }

    public String toString() {
        return "Pattern.matches(\"a*b\", \"aaaaab\")";
    }
}
