package org.shiftone.jrat.core.web.http;

import org.shiftone.jrat.util.io.IOUtil;
import org.shiftone.jrat.util.log.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class Server extends Thread {

    private static final Logger LOG = Logger.getLogger(Server.class);
    private final int port;
    private ServerSocket serverSocket;
    private Handler handler;

    public Server(int port, Handler handler) {
        LOG.info("new");
        this.port = port;
        setHandler(handler);
        setDaemon(true);
        setName("HTTP Server");
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    private void initialize() {

        LOG.info("initialize");
        try {

            LOG.info("starting on port " + port + "...");
            serverSocket = new ServerSocket(port);

        } catch (Throwable e) {

            throw new RuntimeException(e);

        }
    }

    private void processRequest(Socket socket) throws Exception {

        InputStream inputStream = null;
        OutputStream outputStream = null;
         LOG.info("processRequest...");

        try {

            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            Request request = new Request(inputStream);
            Response response = new Response(outputStream);

            try {
                handler.handle(request, response);
            } catch (HttpException e) {
                handleException(response, e);
            }

            response.flush();

        } finally {

            IOUtil.close(inputStream);
            IOUtil.close(outputStream);

        }

    }

    private void handleException(Response response, HttpException e) throws IOException {
        response.setStatus(e.getStatus());
        Writer out = response.getWriter();
        out.write("<h1>HTTP ");
        out.write(String.valueOf(e.getStatus().getCode()));
        out.write(" - ");
        out.write(e.getStatus().getMessage());
        out.write("</h1>");
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        printWriter.flush();
        out.write("<pre>");
        out.write(stringWriter.toString());
        out.write("</pre>");
    }

    public void run() {

        LOG.info("run");
        initialize();

        while (true) {

            try {
                processRequest(serverSocket.accept());
            } catch (Throwable e) {
                LOG.error("failed to processRequest request", e);
            }

        } // while

    }

//    public static void main(String[] args) throws Exception {
//
//        WebActionHandler handler = new WebActionHandler();
//        Server server = new Server(8080, handler);
//        handler.add(new TestWebActionFactory("one"));
//        handler.add(new TestWebActionFactory("two"));
//        handler.add(new TestWebActionFactory("three"));
//
//        handler.add(new TreeWebActionFactory(new ArrayList()));
//        server.start();
//        Thread.sleep(1000 * 160);
//    }

}
