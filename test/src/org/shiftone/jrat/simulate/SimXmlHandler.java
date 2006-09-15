package org.shiftone.jrat.simulate;



import org.shiftone.jrat.core.ConfigurationException;
import org.shiftone.jrat.simulate.stmt.ListStatement;
import org.shiftone.jrat.simulate.stmt.LoopStatement;
import org.shiftone.jrat.simulate.stmt.MethodCallStatement;
import org.shiftone.jrat.simulate.stmt.PeriodicFailureStatement;
import org.shiftone.jrat.simulate.stmt.Statement;
import org.shiftone.jrat.util.BeanWrapper;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Stack;


/**
 * Class SimXmlHandler
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.1 $
 */
public class SimXmlHandler extends DefaultHandler {

    private Statement rootStatement  = null;
    private Statement stmtInProgress = null;
    private Stack     stack          = null;
    private int       threads        = 1;

    /**
     * method
     *
     * @return .
     */
    public Statement getRootStatement() {
        return rootStatement;
    }


    /**
     * method
     *
     * @return .
     */
    public int getThreads() {
        return threads;
    }


    /**
     * method
     *
     * @throws SAXException .
     */
    public void startDocument() throws SAXException {

        stmtInProgress = new ListStatement();
        stack          = new Stack();

        stack.push(stmtInProgress);
    }


    /**
     * method
     *
     * @throws SAXException .
     */
    public void endDocument() throws SAXException {
        rootStatement = stmtInProgress;
    }


    /**
     * method
     *
     * @param uri .
     * @param localName .
     * @param qName .
     *
     * @throws SAXException .
     */
    public void endElement(String uri, String localName, String qName) throws SAXException {
        stack.pop();
    }


    /**
     * method
     *
     * @param uri .
     * @param localName .
     * @param qName .
     * @param attributes .
     *
     * @throws SAXException .
     */
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        Statement stmt = (Statement) stack.peek();

        if (stmt instanceof ListStatement)
        {
            BeanWrapper   beanWrapper;
            Statement     newStmt;
            ListStatement listStatement = (ListStatement) stmt;

            if ("loop".equals(qName))
            {
                newStmt = new LoopStatement();
            }
            else if ("method".equals(qName))
            {
                newStmt = new MethodCallStatement();
            }
            else if ("failure".equals(qName))
            {
                newStmt = new PeriodicFailureStatement();
            }
            else if ("simulation".equals(qName))
            {

                // root node
                String threadString = attributes.getValue("threads");

                if (threadString != null)
                {
                    threads = Integer.parseInt(threadString);
                }

                return;
            }
            else
            {
                throw new SAXException("unknown tag : " + qName);
            }

            beanWrapper = new BeanWrapper(newStmt);

            try
            {
                for (int i = 0; i < attributes.getLength(); i++)
                {
                    beanWrapper.setProperty(attributes.getQName(i), attributes.getValue(i));
                }
            }
            catch (ConfigurationException e)
            {
                throw new SAXException("error setting attribute on statement object", e);
            }

            listStatement.addChild(newStmt);
            stack.push(newStmt);
        }
        else
        {
            throw new SAXException("statements " + stmt.getClass().getName() + " can not contain other statements");
        }
    }
}
