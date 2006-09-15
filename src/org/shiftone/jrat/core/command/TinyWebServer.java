package org.shiftone.jrat.core.command;

import org.shiftone.jrat.util.io.IOUtil;
import org.shiftone.jrat.util.VersionUtil;
import org.shiftone.jrat.util.log.Logger;
import org.shiftone.jrat.core.spi.Commandlet;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;

/**
 * @author Jeff Drost
 */
public class TinyWebServer extends Thread {

    private static final Logger   LOG          = Logger.getLogger(TinyWebServer.class);
    private static final int      LISTEN_PORT  = 2121;
    private ServerSocket          serverSocket = null;
    private int                   flushNumber;
    private final CommandletRegistry registry;

    public TinyWebServer(CommandletRegistry registry) {

        this.registry = registry;
        setDaemon(true);

    }


    private Commandlet readRequest(Socket socket) throws IOException {

        boolean          doRefresh   = false;
        InputStream      inputStream = null;
        LineNumberReader reader      = null;

        inputStream = socket.getInputStream();
        reader      = new LineNumberReader(new InputStreamReader(inputStream));

        String line;

        line      = reader.readLine();

        String commandletKey = "list";
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

        return (Commandlet)registry.getCommandlets().get(commandletKey);
    }


    public void run() {

        Socket           socket       = null;
        OutputStream     outputStream = null;
        Writer           writer       = null;
        LineNumberReader reader       = null;
        boolean          refresh;
        long             start;

        try
        {
            LOG.info("starting on port " + LISTEN_PORT + "...");

            serverSocket = new ServerSocket(LISTEN_PORT);

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
                    writer.write("\n\n");
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
            LOG.error("unable to listen on port : " + LISTEN_PORT);
        }
    }

}
