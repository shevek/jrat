package org.shiftone.jrat.provider.stats.ui;



import org.shiftone.jrat.util.log.Logger;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * @author Jeff Drost
 * @version $Revision: 1.4 $
 */
public class StatsMatcherEditorPanel extends JPanel implements DocumentListener, ActionListener {

    private static final Logger   LOG         = Logger.getLogger(StatsMatcherEditorPanel.class);
    public static final String    MATCHES_YES = "matches";
    public static final String    MATCHES_NO  = "does not match";
    private static final Object[] MATCH_TYPES = { MATCHES_YES, MATCHES_NO };
    private StatsMatcherEditor    statsMatcherEditor;
    private JComboBox             comboBox;
    private JTextField            textField;

    public StatsMatcherEditorPanel(StatsMatcherEditor statsMatcherEditor, String title) {

        super(new GridLayout(1, 0, 5, 0));

        this.statsMatcherEditor = statsMatcherEditor;

        JPanel left  = new JPanel(new GridLayout(1, 0, 5, 0));
        JLabel label = new JLabel(title);

        comboBox  = new JComboBox(MATCH_TYPES);
        textField = new JTextField("*");

        comboBox.addActionListener(this);
        textField.getDocument().addDocumentListener(this);
        label.setHorizontalAlignment(JLabel.RIGHT);
        left.add(label);
        left.add(comboBox);
        add(left);
        add(textField);
    }


    public void insertUpdate(DocumentEvent event) {
        somethingChanges();
    }


    public void removeUpdate(DocumentEvent event) {
        somethingChanges();
    }


    public void changedUpdate(DocumentEvent event) {
        somethingChanges();
    }


    public void actionPerformed(ActionEvent e) {
        somethingChanges();
    }


    private void somethingChanges() {
        statsMatcherEditor.setPattern(textField.getText(), comboBox.getSelectedItem() == MATCHES_NO);
    }
}
