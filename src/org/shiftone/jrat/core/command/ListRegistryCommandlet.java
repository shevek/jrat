package org.shiftone.jrat.core.command;

import org.shiftone.jrat.core.spi.Commandlet;
import org.shiftone.jrat.util.log.Logger;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;

/**
 * This is a "special" commandlet that lists other commandlets.
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
		out.println("<font size='+2'>");
		out.println("<a href='http://wiki.shiftone.org/index.php/Java_Runtime_Analysis_Toolkit'>Java Runtime Analysis Toolkit</a><br>");
		out.println("<a href='http://wiki.shiftone.org/index.php/Tiny_Web_Server'>Tiny Web Server</a></font>");
		out.println("<br><br><table width='100%' border='1' cellpadding='3' cellspacing='0'>");
		int i = 0;
		String[] colors = {"eeeeee","ffffff"};
		while (keys.hasNext()) {
            out.print("<tr bgcolor='#");
			out.print(colors[i % colors.length]);
			out.println("'>");
			String key = (String)keys.next();
            Commandlet commandlet = (Commandlet)commandlets.get(key);
            out.println("<td>" + commandlet.getTitle() + "</td>");
            out.println("<td>" + commandlet.getContentType() + "</td>");
            out.println("<td><a href='?commandlet=" + key + "'>run</a></td>");
            out.println("</tr>");
			i++;
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
