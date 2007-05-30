package org.shiftone.jrat.inject;

import junit.framework.TestCase;
import org.shiftone.jrat.util.log.Logger;

/**
 * @author Jeff Drost
 */
public class InjectorCliTestCase extends TestCase {
    private static final Logger LOG = Logger.getLogger(InjectorCliTestCase.class);

    public void testOne() throws Exception {

        InjectorCli.main(new String[]{"target/classes/org/shiftone/jrat/ui"});

    }
}
