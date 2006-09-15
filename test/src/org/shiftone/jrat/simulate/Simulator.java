package org.shiftone.jrat.simulate;



import org.shiftone.jrat.core.InternalHandler;
import org.shiftone.jrat.simulate.stmt.Statement;
import org.shiftone.jrat.util.XMLUtil;
import org.shiftone.jrat.util.log.Logger;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;


/**
 * Class Simulator
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.2 $
 */
public class Simulator {

    private static final Logger LOG = Logger.getLogger(Simulator.class);

    /**
     * method
     *
     * @param file .
     *
     * @throws Exception .
     */
    public static void simulate(File file) throws Exception {

        InputStream     inputStream     = new FileInputStream(file);
        InputSource     inputSource     = new InputSource(inputStream);
        XMLReader       reader          = XMLUtil.getXMLReader();
        SimXmlHandler   xmlHandler      = new SimXmlHandler();
        InternalHandler internalHandler = new InternalHandler();
        Statement       statement       = null;
        Thread[]        threads         = null;
        long            start;

        LOG.info("configuring simulator...");
        reader.setContentHandler(xmlHandler);
        reader.parse(inputSource);
        LOG.info("configuration complete.");

        statement = xmlHandler.getRootStatement();
        threads   = new Thread[xmlHandler.getThreads()];

        LOG.info("running simulation...");

        start = System.currentTimeMillis();

        for (int i = 0; i < threads.length; i++)
        {
            threads[i] = new Thread(new SimulatorRunnable(internalHandler, statement));
        }

        for (int i = 0; i < threads.length; i++)
        {
            threads[i].start();
        }

        for (int i = 0; i < threads.length; i++)
        {
            threads[i].join();
        }

        //
        //statement.execute(internalHandler);
        LOG.info("simulation complated in " + (System.currentTimeMillis() - start) + " ms");
    }


    /**
     * method
     *
     * @param args .
     */
    public static void main(String[] args) {

        System.out.println(new File("").getAbsolutePath());

        if (args.length == 0)
        {
            LOG.info("usage: Simulator [filename]");
        }

        String fileName = args[0];
        File   file     = new File(fileName);

        try
        {
            Simulator.simulate(file);
        }
        catch (Exception e)
        {
            LOG.info("error running simulation", e);
        }
    }
}
