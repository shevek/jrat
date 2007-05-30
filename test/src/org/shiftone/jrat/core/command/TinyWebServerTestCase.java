package org.shiftone.jrat.core.command;

import org.shiftone.jrat.core.HandlerFactory;
import org.shiftone.jrat.core.Settings;
import org.shiftone.jrat.provider.tree.TreeMethodHandlerFactory;

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

        System.setProperty(Settings.HANDLER_CLASS, TreeMethodHandlerFactory.class.getName());

        HandlerFactory.initialize();

        Thread.sleep(1000 * 60 * 60);

    }
}
