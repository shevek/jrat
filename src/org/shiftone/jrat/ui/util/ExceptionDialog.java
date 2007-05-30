package org.shiftone.jrat.ui.util;


import org.shiftone.jrat.util.log.Logger;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.StringWriter;


/**
 * Class ExceptionDialog
 *
 * @author Jeff Drost
 *
 */
public class ExceptionDialog extends JDialog implements ActionListener {

    // http://www.sutic.nu/misc/plaf/
    private static final Logger LOG          = Logger.getLogger(ExceptionDialog.class);
    private JPanel              main         = new JPanel();
    private JPanel              buttons      = new JPanel();
    private JTabbedPane         tabPane      = new JTabbedPane();
    private JTextArea           textArea     = new JTextArea();
    private JScrollPane         scrollPane   = new JScrollPane(textArea);
    private UIDefaults          uiDefaults   = UIManager.getLookAndFeelDefaults();
    private Icon                icon         = uiDefaults.getIcon("OptionPane.errorIcon");
    private JLabel              messageLabel = null;
    private JLabel              iconLabel    = new JLabel(icon);
    private JButton             close        = new JButton("Close");

    /**
     * Constructor ExceptionDialog
     *
     * @param parent
     * @param t
     */
    public ExceptionDialog(Frame parent, Throwable t) {

        super(parent, true);

        initialize(t);
    }


    /**
     * Constructor ExceptionDialog
     *
     * @param parent
     * @param t
     */
    public ExceptionDialog(Dialog parent, Throwable t) {

        super(parent, true);

        initialize(t);
    }


    /**
     * Constructor ExceptionDialog
     *
     * @param parent
     * @param title
     * @param message
     * @param throwable
     */
    public ExceptionDialog(Dialog parent, String title, String message, Throwable throwable) {

        super(parent, true);

        initialize(title, message, throwable);
    }


    /**
     * Constructor ExceptionDialog
     *
     * @param parent
     * @param title
     * @param message
     * @param throwable
     */
    public ExceptionDialog(Frame parent, String title, String message, Throwable throwable) {

        super(parent, true);

        initialize(title, message, throwable);
    }


    /**
     * Method initialize
     *
     * @param throwable .
     */
    private void initialize(Throwable throwable) {

        Throwable t = throwable;

        try
        {

            // TODO : better java 1.3 support
            while (t.getCause() != null)
            {
                t = t.getCause();
            }
        }
        catch (NoSuchMethodError e)
        {

            // oh well, somebody is using java 1.3
        }

        initialize(t.getClass().getName(), throwable.getMessage(), throwable);
    }


    /**
     * Method initialize
     *
     * @param title .
     * @param message .
     * @param throwable .
     */
    private void initialize(String title, String message, Throwable throwable) {

        Container pane = getContentPane();

        setTitle(title);

        messageLabel = new JLabel(message, SwingConstants.CENTER);

        iconLabel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        close.addActionListener(this);
        buttons.add(close);

        //
        textArea.setText(stackTrace(throwable));
        textArea.setEditable(false);
        textArea.setFont(textArea.getFont().deriveFont(9.0f));
        textArea.setCaretPosition(0);

        //
        main.setLayout(new BorderLayout());
        main.add(iconLabel, BorderLayout.WEST);
        main.add(buttons, BorderLayout.SOUTH);
        main.add(messageLabel, BorderLayout.CENTER);

        //
        tabPane.add(main, "Error");
        tabPane.add(scrollPane, "Details");

        //
        pane.setLayout(new BorderLayout());
        pane.add(tabPane, BorderLayout.CENTER);
        pane.add(buttons, BorderLayout.SOUTH);

        //
        // setLocationRelativeTo(parent);
        setLocation(400, 300);
        setSize(650, 250);
    }


    /**
     * Method stackTrace
     *
     * @param throwable .
     *
     * @return .
     */
    private String stackTrace(Throwable throwable) {

        String       value        = null;
        StringWriter stringWriter = new StringWriter();
        PrintWriter  printWriter  = new PrintWriter(stringWriter);

        throwable.printStackTrace(printWriter);
        printWriter.flush();

        value = stringWriter.toString();

        return value;
    }


    /**
     * Method actionPerformed
     *
     * @param e .
     */
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == close)
        {
            this.dispose();
        }
    }
}
