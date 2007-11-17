package org.shiftone.jrat.core.web.http;
import org.shiftone.jrat.util.io.IOUtil;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class FsBrowseHandler implements Handler {

    private static final String ROOT = new File("").getAbsolutePath();
    private static Map mimeTypes = new HashMap();

    static {
        mimeTypes.put("txt", ContentType.TEXT_PLAIN);
        mimeTypes.put("log", ContentType.TEXT_PLAIN);
        mimeTypes.put("htm", ContentType.TEXT_HTML);
        mimeTypes.put("html", ContentType.TEXT_HTML);
        mimeTypes.put("xml", ContentType.TEXT_XML);
    }

    public void handle(Request request, Response response) throws Exception {

        response.setContentType(ContentType.TEXT_HTML);

        String uri = request.getRequestUri();
        File file = new File(ROOT + uri);


        if (file.isDirectory()) {

            Writer writer = response.getWriter();
            writer.write("<ul>");
            File[] children = file.listFiles();

            for (int i = 0; i < children.length; i++) {
                File child = children[i];
                if (child.isDirectory()) {
                    writer.write("<li><a href='" + child.getName() + "/'>" + child.getName() + "/</a>");
                } else {
                    writer.write("<li><a href='" + child.getName() + "'>" + child.getName() + "</a> ");
                    writer.write(" (" + child.length() + " bytes)");
                }
            }

            writer.write("</ul>");

        } else {

            String ext = IOUtil.getExtention(file.getName()).toLowerCase();
            String contentType = (String) mimeTypes.get(ext);
            OutputStream outputStream = response.getOutputStream();
            InputStream inputStream = null;
            try {
                if (contentType == null) {
                    contentType = ContentType.OCTET_STREAM;
                }
                response.setContentType(contentType);
                inputStream = IOUtil.openInputStream(file);
                IOUtil.copy(inputStream, outputStream);
            } finally {
                IOUtil.close(inputStream);
            }

        }


    }
}
