package org.shiftone.jrat.core.command;

import org.shiftone.jrat.core.spi.Commandlet;
import org.shiftone.jrat.util.log.Logger;
import org.shiftone.jrat.util.VersionUtil;


import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Jeff Drost
 */
public class ListRegistryCommandlet implements Commandlet {
    private static final Logger LOG = Logger.getLogger(ListRegistryCommandlet.class);

    private CommandletRegistry registry;

    public ListRegistryCommandlet(CommandletRegistry registry) {
        this.registry = registry;
    }

    public void execute(OutputStream output) {
        LOG.info("execute");
        PrintWriter out = new PrintWriter(output);
        Map commandlets = registry.getCommandlets();
        Iterator keys = commandlets.keySet().iterator();

        out.println("<html>");
        out.println("<h2>JRat "+ VersionUtil.getVersion() + "</h2>");
        out.println("<table width='100%' border='1' cellpadding='5' cellspacing='0'>");

        while (keys.hasNext()) {
            out.println("<tr>");
            String key = (String)keys.next();
            Commandlet commandlet = (Commandlet)commandlets.get(key);
            out.println("<td>" + commandlet.getTitle() + "</td>");
            out.println("<td>" + commandlet.getContentType() + "</td>");
            out.println("<td><a href='?commandlet=" + key + "'>run</a></td>");
            out.println("</tr>");
        }

        out.println("</table>");
        out.println("</html>");
        out.flush();
    }

    public String getContentType() {
        return ContentType.HTML;
    }

    public String getTitle() {
        return "List Commandlets Commandlet";
    }
}
