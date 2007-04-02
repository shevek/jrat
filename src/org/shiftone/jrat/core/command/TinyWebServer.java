package org.shiftone.jrat.core.command;

import org.shiftone.jrat.core.spi.Commandlet;
import org.shiftone.jrat.core.Settings;
import org.shiftone.jrat.util.io.IOUtil;
import org.shiftone.jrat.util.log.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Jeff Drost
 */
public class TinyWebServer extends Thread {

    private static final Logger   LOG          = Logger.getLogger(TinyWebServer.class);

    private ServerSocket          serverSocket = null;
    private int                   flushNumber;
    private final CommandletRegistry registry;

    public TinyWebServer(CommandletRegistry registry) {

        this.registry = registry;
        setDaemon(true);
		setName("HTTP");

	}


    private Commandlet readRequest(Socket socket) throws IOException {

        boolean          doRefresh   = false;
        InputStream      inputStream = null;
        LineNumberReader reader      = null;

        inputStream = socket.getInputStream();
        reader      = new LineNumberReader(new InputStreamReader(inputStream));

        String line;

        line      = reader.readLine();

        String commandletKey = null;
        int a = line.indexOf(' ');
        int b = line.lastIndexOf(' ');
        String uri = line.substring(a+1,b);
        LOG.info("LINE = " + line + ">" + uri + "<");
        int commandletIndex = uri.indexOf("commandlet=");
        if (commandletIndex != -1) {
            commandletKey = uri.substring( commandletIndex + 11);
            LOG.info("commandletKey = " + commandletKey);
        }

        while ((line != null) && (line.length() > 0))
        {
            line = reader.readLine();
        }

		// the following code is kinda crapy
		
		Commandlet commandlet = null;

		if (commandletKey != null) {
			commandlet = (Commandlet)registry.getCommandlets().get(commandletKey);
		}

		if (commandlet == null) {
			LOG.warn("line(" + line + ") using default key");
			commandlet = registry.getDefaultCommandlet();
		}

		return commandlet;
	}


    public void run() {

		int              port         = Settings.isHttpPort();
		Socket           socket       = null;
        OutputStream     outputStream = null;
        Writer           writer       = null;
        //LineNumberReader reader       = null;
        //boolean          refresh;
        long             start;

        try
        {
            LOG.info("starting on port " + port + "...");

            serverSocket = new ServerSocket(port);

            while (true)
            {
                socket = serverSocket.accept();

                LOG.info("accept");

                try
                {
                    outputStream = socket.getOutputStream();
                    writer       = new OutputStreamWriter(outputStream);
                    start        = System.currentTimeMillis();

                    flushNumber++;

                    Commandlet commandlet =  readRequest(socket);

                    writer.write("HTTP/1.1 200 OK\n");
                    writer.write("Content-Type: " + commandlet.getContentType() + "\n");
                    writer.write("Cache-Control: no-store, no-cache, must-revalidate\n");    // normal
                    writer.write("Cache-Control: post-check=0, pre-check=0");                // IE
                    writer.write("Pragma: no-cache\n");                                      // good luck
                    writer.write("Expires: Sat, 6 May 1995 12:00:00 GMT\n");                 // more lock
                    writer.write("\n");
                    writer.flush();

                    commandlet.execute(outputStream);

                    outputStream.flush();
                }
                catch (Exception e)
                {
                    LOG.error("Flush error", e);
                }
                finally
                {
                    IOUtil.close(writer);
                    IOUtil.close(outputStream);
                    IOUtil.close(socket);
                }
            }
        }
        catch (Exception e)
        {
            LOG.error("unable to listen on port : " + port, e);
        }
    }

}
