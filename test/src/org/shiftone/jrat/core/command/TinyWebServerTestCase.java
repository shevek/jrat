package org.shiftone.jrat.core.command;

import junit.framework.TestCase;

/**
 * @author Jeff Drost
 */
public class TinyWebServerTestCase extends TestCase {

    public void testOne() throws Exception {
        TinyWebServer webServer = new TinyWebServer(new CommandletRegistry());
        webServer.start();
        Thread.sleep(1000 * 60 * 60);
    }
}
