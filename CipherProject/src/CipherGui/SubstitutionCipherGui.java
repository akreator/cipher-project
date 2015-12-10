package CipherGui;


import Templates.MenuBar;
import Templates.FrequencyTable;
import Templates.MyGUI;
import Templates.MyTextArea;
import TextTools.Crypter;
import TextTools.TextFormatter;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 *
 * @author Audrey
 */
public class SubstitutionCipherGui extends MyGUI {

    private Object[][] standardFrequency = {
        {"e", 12.702}, {"t", 9.056}, {"a", 8.167}, {"o", 7.507}, {"i", 6.966},
        {"n", 6.749}, {"s", 6.327}, {"h", 6.094}, {"r", 5.987}, {"d", 4.253},
        {"l", 4.025}, {"c", 2.782}, {"u", 2.758}, {"m", 2.406}, {"w", 2.361},
        {"f", 2.228}, {"g", 2.015}, {"y", 1.974}, {"p", 1.929}, {"b", 1.492},
        {"v", 0.978}, {"k", 0.772}, {"j", 0.153}, {"x", 0.150}, {"q", 0.095},
        {"z", 0.74}
    };
    private JButton enter;
    private FrequencyTable messageTable, standardTable;
    private String[] columnNames = {"Letters", "Frequency(%)"};
    private Object[][] messageFrequency;
    private String[][] replacements;
    private JTable switchTable;
    private JComboBox analyzeOptions, defaultOptions;

    public SubstitutionCipherGui() {
        super(945, 595, "Substitution Cipher");
        init();
        frame.setVisible(true);
    }

    private void init() {
        //create northpane
        originalTextArea = new MyTextArea(5, 70, true, false);
        JScrollPane originalScroll = new JScrollPane(originalTextArea,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        String[] options = {"Analyze...", "Letters", "Letters and Numbers",
            "Letters, numbers, and punctuation", "Top row"};
        analyzeOptions = new JComboBox(options);
        analyzeOptions.addActionListener(new SubstitutionListener());
        JPanel northPane = new JPanel();
        northPane.add(originalScroll);
        northPane.add(analyzeOptions);

        // **** tablepane ****
        //standard table + label
        standardTable = new FrequencyTable(standardFrequency, columnNames);
        JScrollPane sPane = new JScrollPane(standardTable.createTable());
        JLabel sLabel = new JLabel("Standard Frequency of English Letters");
        JPanel standardPane = new JPanel();
        standardPane.setLayout(new BoxLayout(standardPane, BoxLayout.PAGE_AXIS));
        standardPane.add(sLabel);
        standardPane.add(sPane);
        //messageTable
        messageTable = new FrequencyTable(Crypter.getMessageFrequency(""), columnNames);
        JScrollPane mPane = new JScrollPane(messageTable.createTable());
        JLabel mLabel = new JLabel("Frequency of Input Characters");
        JPanel messagePane = new JPanel();
        messagePane.setLayout(new BoxLayout(messagePane, BoxLayout.PAGE_AXIS));
        messagePane.add(mLabel);
        messagePane.add(mPane);
        //tablepane
        JPanel tablePane = new JPanel();
        tablePane.setLayout(new BoxLayout(tablePane, BoxLayout.LINE_AXIS));
        tablePane.add(standardPane);
        tablePane.add(Box.createRigidArea(new Dimension(20, 0)));
        tablePane.add(messagePane);

        //input table
        enter = new JButton("Enter");
        enter.addActionListener(new SubstitutionListener());
        String[] defaultStrings = {
            "Autofill Options...", "Alphabet Top Row", "Atbash", "A1Z26 (to numbers)", "A1Z26 (to letters)", "Clear All", "Clear Bottom Row"
        };
        defaultOptions = new JComboBox(defaultStrings);
        defaultOptions.addActionListener(new SubstitutionListener());
        
        String[][] filler = new String[2][26];
        for (int i = 0; i < 26; i++) {
            filler[0][i] = "";
            filler[1][i] = "";
        }
        
        switchTable = new JTable(filler, filler[1]);
        switchTable.getTableHeader().setReorderingAllowed(false);
        switchTable.setPreferredScrollableViewportSize(new Dimension(switchTable.getWidth(),
                switchTable.getRowHeight() * 2));
        switchTable.setTableHeader(null);
        switchTable.setAutoCreateRowSorter(true);
        //switchTable.setFillsViewportHeight(true);
        JScrollPane switchPane = new JScrollPane(switchTable);

        //*** CENTERPANE ***
        JPanel centerPane = new JPanel();
        centerPane.setLayout(new BoxLayout(centerPane, BoxLayout.PAGE_AXIS));
        centerPane.add(tablePane);
        centerPane.add(Box.createRigidArea(new Dimension(0, 15)));
        centerPane.add(defaultOptions);
        centerPane.add(switchPane);
        centerPane.add(Box.createRigidArea(new Dimension(0, 5)));
        centerPane.add(enter);

        //southpane
        newTextArea = new MyTextArea(6, 70, false, true);
        frame.add(newTextArea);
        JScrollPane newScroll = new JScrollPane(newTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JPanel southPane = new JPanel();
        southPane.add(newScroll);

        frame.add(northPane, BorderLayout.NORTH);
        frame.add(centerPane, BorderLayout.CENTER);
        frame.add(southPane, BorderLayout.SOUTH);
        frame.add(Box.createRigidArea(new Dimension(15, 0)), BorderLayout.WEST);
        frame.add(Box.createRigidArea(new Dimension(15, 0)), BorderLayout.EAST);
        frame.setJMenuBar(new MenuBar(frame, originalTextArea));
    }

    public void updateReplacements() {
        replacements = new String[26][2];
        for (int i = 0; i < 26; i++) {
            if (switchTable.getValueAt(0, i) != null) {
                //the top row is what to look for in the text; the bottom row is the characters replacement characters for that text
                //top = replacements[][1]; bottom = replacements[][0]
                replacements[i][0] = (switchTable.getValueAt(1, i).toString().toLowerCase());
                replacements[i][1] = (switchTable.getValueAt(0, i).toString().toLowerCase());
            }
        }
    }

    class SubstitutionListener implements ActionListener {
        private Object[][] atbash = {
            {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r",
                "s", "t", "u", "v", "w", "x", "y", "z"},
            {"z", "y", "x", "w", "v", "u", "t", "s", "r", "q", "p", "o", "n",
                "m", "l", "k", "j", "i", "h", "g", "f", "e", "d", "c", "b", "a"}

        };

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == defaultOptions) {
                //  "Autofill Options", "Alphabet Top Row", "Atbash", "A1Z26 (to Numbers)", "A1Z26 (from Numbers)", "Clear All", "Clear Bottom Row"
                switch (defaultOptions.getSelectedIndex()) {
                     case 1:
                        for (int c = 0; c < 26; c++) {
                            switchTable.setValueAt(atbash[0][c], 0, c);
                        }
                        break;
                    case 2:
                        for (int r = 0; r < 2; r++) {
                            for (int c = 0; c < 26; c++) {
                                switchTable.setValueAt(atbash[r][c], r, c);
                            }
                        }
                        break;
                    case 3:
                        for (int c = 0; c < 26; c++) {
                            switchTable.setValueAt(atbash[1][c], 0, c);
                            switchTable.setValueAt("-" + (26 - c) + "-", 1, c);
                        }
                        break;
                    case 4:
                        for (int c = 0; c < 26; c++) {
                            switchTable.setValueAt(atbash[1][c], 1, c);
                            switchTable.setValueAt("" + (26 - c), 0, c);
                        }
                        break;
                    case 5:
                        for (int r = 0; r < 2; r++) {
                            for (int c = 0; c < 26; c++) {
                                switchTable.setValueAt("", r, c);
                            }
                        }
                        break;
                    case 6:
                        for (int c = 0; c < 26; c++) {
                            switchTable.setValueAt("", 1, c);
                        }
                        break;
                }

            } else if (e.getSource() == analyzeOptions && !originalTextArea.getText().equals("") && originalTextArea.getText() != null) {
                switch (analyzeOptions.getSelectedIndex()) {
                    case 1:
                        messageTable.updateTable(Crypter.getMessageFrequency(
                                originalTextArea.getText().replaceAll("[^a-zA-Z]", "")));
                        break;
                    case 2:
                        messageTable.updateTable(Crypter.getMessageFrequency(
                                originalTextArea.getText().replaceAll("[^a-zA-Z0-9]", "")));
                        break;
                    case 3:
                        messageTable.updateTable(Crypter.getMessageFrequency(originalTextArea.getText()));
                        break;
                    case 4:
                        updateReplacements();
                        ArrayList<String> array = new ArrayList<String>();
                        for ( String[] r : replacements) 
                            array.add(r[1]);
                        messageTable.updateTable(Crypter.getSpecificMessageFrequency(originalTextArea.getText(), array));
                }
                frame.repaint();
            } else if (e.getSource() == enter && !originalTextArea.getText().equals("") && originalTextArea.getText() != null) {
                updateReplacements();
                newTextArea.setText(Crypter.substitute(originalTextArea.getText(), replacements));
                frame.repaint();
            }
        }
    }
}
