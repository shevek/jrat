package org.shiftone.jrat.core.web.http;

import java.io.PrintWriter;
import org.shiftone.jrat.core.spi.WebAction;
import org.shiftone.jrat.core.spi.WebActionFactory;

/**
 * @author (jeff@shiftone.org) Jeff Drost
 */
public class TestWebActionFactory implements WebActionFactory {

    private final String title;
    private final WebAction webAction = new TestWebAction();

    public TestWebActionFactory(String title) {
        this.title = title;
    }

    @Override
    public WebAction createAction() throws Exception {
        return webAction;
    }

    @Override
    public String getTitle() {
        return title;
    }

    private class TestWebAction implements WebAction {

        private String a;
        private int b;

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }

        public int getB() {
            return b;
        }

        public void setB(int b) {
            this.b = b;
        }

        @Override
        public void execute(Response response) throws Exception {

            PrintWriter out = new PrintWriter(response.getWriter());
            out.write("<html><head><title>");
            out.write(title);
            out.write("</title></head><body><h1>");
            out.write(title);
            out.write("</h1><pre>");
            out.write("a = " + getA() + "\n");
            out.write("b = " + getB());
            out.write("</pre></body></html>");
        }
    }
}
