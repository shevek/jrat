package org.shiftone.jrat.util;

import java.util.zip.ZipException;
import junit.framework.TestCase;
import org.shiftone.jrat.inject.InjectionException;
import org.shiftone.jrat.util.log.Logger;

/**
 * @author Jeff Drost
 */
public class NestedRuntimeExceptionTestCase extends TestCase {

    private static final Logger LOG = Logger.getLogger(NestedRuntimeExceptionTestCase.class);

    public void testOne() throws Exception {
        ZipException zip = new ZipException("this is a real error");
        InjectionException A = new InjectionException("A", zip);
        InjectionException B = new InjectionException("B", A);
        InjectionException C = new InjectionException("C", B);

        LOG.error("ERROR", C);

    }

}
