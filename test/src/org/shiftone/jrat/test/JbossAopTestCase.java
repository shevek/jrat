package org.shiftone.jrat.test;



import org.jboss.aop.standalone.SystemClassLoader;

import org.shiftone.jrat.util.log.Logger;


/**
 * Class JbossAopTestCase
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.2 $
 */
public class JbossAopTestCase extends InjectorTestCase {

    private static final Logger         LOG            = Logger.getLogger(JbossAopTestCase.class);
    private static final ClassLoader DEFAULT_LOADER = JbossAopTestCase.class.getClassLoader();
    private static final ClassLoader AOP_LOADER     = new SystemClassLoader(DEFAULT_LOADER);

    /**
         * method
         *
         * @throws Exception .
         */
    protected void setUp() throws Exception {
        clazz = AOP_LOADER.loadClass(CLASS_NAME);
    }
}
