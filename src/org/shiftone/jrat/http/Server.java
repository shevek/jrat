package org.shiftone.jrat.http;

import org.shiftone.jrat.util.io.IOUtil;
import org.shiftone.jrat.util.log.Logger;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class Server extends Thread {

    private static final Logger LOG = Logger.getLogger(Server.class);
    private final int port;
    private ServerSocket serverSocket;
    private Handler handler;

    public Server(int port) {
        this.port = port;
        setDaemon(true);
        setName("HTTP Server");
    }


    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    private void initialize() {

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
        PrintWriter printWriter = new  PrintWriter(stringWriter);
        e.printStackTrace( printWriter );
        printWriter.flush();
        out.write("<pre>");
        out.write(stringWriter.toString());
        out.write("</pre>");
    }

    public void run() {

        initialize();

        while (true) {

            try {
                processRequest(serverSocket.accept());
            } catch (Throwable e) {
                LOG.error("failed to processRequest request", e);
            }

        } // while

    }

    public static void main(String[] args) {
        Dispatcher dispatcher = new Dispatcher("Root Dispatcher");
        Server server = new Server(8008);
        server.setHandler(dispatcher);
        server.setDaemon(false);
        server.start();

        Dispatcher dispatcher2 = new Dispatcher("dispatcher2");
        dispatcher.addRoute("1", dispatcher2);
        dispatcher.addRoute("2", dispatcher2);
        dispatcher.addRoute("3", dispatcher2);
        dispatcher.addRoute("4", dispatcher2);
        dispatcher.addRoute("5", dispatcher2);

        dispatcher.addRoute("fs", new FsBrowseHandler());

        Dispatcher dispatcher3 = new Dispatcher("dispatcher3");
        dispatcher2.addRoute("a", dispatcher3);
        dispatcher2.addRoute("b", dispatcher3);
        dispatcher2.addRoute("c", dispatcher3);
        dispatcher2.addRoute("d", dispatcher3);

    }


}
