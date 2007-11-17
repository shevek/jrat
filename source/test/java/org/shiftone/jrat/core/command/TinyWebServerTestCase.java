package org.shiftone.jrat.core.command;

import org.shiftone.jrat.core.HandlerFactory;

/**
 * @author Jeff Drost
 */
public class TinyWebServerTestCase {

//    public void testOne() throws Exception {
//        TinyWebServer webServer = new TinyWebServer(new CommandletRegistry());
//        webServer.start();
//        Thread.sleep(1000 * 60 * 60);
//    }

    public static void main(String[] args) throws Exception {

         
        HandlerFactory.initialize();

        Thread.sleep(1000 * 60 * 60);

    }
}
