package org.shiftone.jrat.inject;

import java.io.PrintStream;
import org.shiftone.jrat.util.VersionUtil;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class InjectorCli {

    private static final PrintStream OUT = System.out;

    public static void main(String[] args) throws Exception {

        OUT.println("JRat " + VersionUtil.getVersion() + " Injector Command Line Tool");

        if (args.length == 0) {
            OUT.println("usage : ");
            OUT.println("\tinject [target]");
            OUT.println("\tinject [source] [target]");
        } else {
            Injector injector = new Injector();

            if (args.length == 1) {

                // injector.setForceOverwrite(true);
                injector.inject(args[0]);
            } else if (args.length == 2) {

                // injector.setForceOverwrite(true);
                injector.inject(args[0], args[1]);
            }
        }
    }
}
