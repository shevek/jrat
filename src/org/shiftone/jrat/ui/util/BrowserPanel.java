package org.shiftone.jrat.ui.util;


import org.shiftone.jrat.util.log.Logger;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;


/**
 * Class BrowserPanel
 *
 * @author Jeff Drost
 */
public class BrowserPanel extends JPanel implements HyperlinkListener, ActionListener {

    private static final Logger LOG = Logger.getLogger(BrowserPanel.class);
    private URL homePage = null;
    private JToolBar toolBar = new JToolBar();
    private JEditorPane editorPane = new JEditorPane();
    private JScrollPane scrollPane = new JScrollPane(editorPane);
    private JLabel location = new JLabel();
    private JButton home = new JButton("Home");
    private JButton back = new JButton("Back");
    private JButton forword = new JButton("Forward");
    private URL[] urls = new URL[1024];
    private int current;
    private int maxCurrent;

    public BrowserPanel(URL homePage) {

        this.homePage = homePage;

        //
        Container pane = this;    // getContentPane();
        HTMLEditorKit kit = new HTMLEditorKit();

        kit.setLinkCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        //
        editorPane.setEditorKit(kit);
        editorPane.setEditable(false);
        editorPane.addHyperlinkListener(this);
        editorPane.setBorder(new EmptyBorder(20, 10, 10, 10));

        // editorPane.setBackground(new Color(0x006666));
        editorPane.setBackground(Color.white);

        //
        pane.setLayout(new BorderLayout());
        pane.add(toolBar, BorderLayout.NORTH);
        pane.add(scrollPane, BorderLayout.CENTER);
        pane.add(location, BorderLayout.SOUTH);

        //
        toolBar.add(home);
        toolBar.add(back);
        toolBar.add(forword);
        home.addActionListener(this);
        back.addActionListener(this);
        forword.addActionListener(this);
        setHyperLink(homePage, true);
    }


    /**
     * Method hyperlinkUpdate
     */
    public void hyperlinkUpdate(HyperlinkEvent e) {

        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            URL newURL = e.getURL();

            // if (!newURL.equals(current))
            // {
            setHyperLink(e.getURL(), true);

            // }
        }
    }


    /**
     * Method setHyperLink
     */
    public void setHyperLink(URL url, boolean push) {

        try {
            editorPane.setPage(url);

            if (push) {
                current++;

                maxCurrent = current;
            } else {
                maxCurrent = Math.max(current, maxCurrent);
            }

            urls[current] = url;

            location.setText(current + " : " + url.toString());
            back.setEnabled(current > 1);
            forword.setEnabled(current < maxCurrent);
        }
        catch (Exception e) {
            error(e);
        }
    }


    /**
     * Method error
     */
    private void error(Exception e) {

        StringWriter stringWriter = new StringWriter();
        PrintWriter out = new PrintWriter(stringWriter);

        out.println("<b>Unable to open Documentation</b><p>");
        e.printStackTrace(out);
        out.flush();
        editorPane.setText(stringWriter.toString());
    }


    /**
     * Method actionPerformed
     */
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == home) {
            setHyperLink(homePage, true);
        } else if (e.getSource() == back) {
            if (current > 1) {
                current--;

                setHyperLink(urls[current], false);
            }
        } else if (e.getSource() == forword) {
            if (current < maxCurrent) {
                current++;

                setHyperLink(urls[current], false);
            }
        }
    }
}
