package ciphergui;

import other.*;
import texttools.Crypter;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 *
 * @author Audrey
 */
public class SubstitutionCipherGui extends MyGUI {

    private JButton enter;
    private FrequencyTable messageTable, standardTable;
    private String[] columnNames = {"Letters", "Frequency(%)"};
    private static String[][] replacements = {
        {"", ""}, {"", ""}, {"", ""}, {"", ""}, {"", ""}, {"", ""}, {"", ""},
        {"", ""}, {"", ""}, {"", ""}, {"", ""}, {"", ""}, {"", ""}, {"", ""}, 
        {"", ""}, {"", ""}, {"", ""}, {"", ""}, {"", ""}, {"", ""}, {"", ""},
        {"", ""}, {"", ""}, {"", ""}, {"", ""}, {"", ""}

    };
    private JTable switchTable;
    private JComboBox analyzeOptions, defaultOptions;
    private JLabel standardLabel;

    public SubstitutionCipherGui() {
        super(945, 595, "Substitution Cipher");
        init();
        setVisible(true);
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
        standardTable = new FrequencyTable(Crypter.getStandardFrequency(Properties.getLanguage()), columnNames);
        JScrollPane sPane = new JScrollPane(standardTable.createTable());
        standardLabel = new JLabel("Standard Frequency of " + 
                Crypter.LANGUAGES[Properties.getLanguage()] + " Letters");
        JPanel standardPane = new JPanel();
        standardPane.setLayout(new BoxLayout(standardPane, BoxLayout.PAGE_AXIS));
        standardPane.add(standardLabel);
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
        enter = new JButton("Remember Replacements & Enter");
        enter.setAlignmentX(Component.CENTER_ALIGNMENT);
        enter.addActionListener(new SubstitutionListener());
        String[] defaultStrings = {
            "Autofill Options...", "Alphabet Top Row", "Atbash", "A1Z26 (to numbers)", 
            "A1Z26 (to letters)", "Non-accented text", "Clear All", "Clear Bottom Row"
        };
        defaultOptions = new JComboBox(defaultStrings);
        defaultOptions.addActionListener(new SubstitutionListener());
        
        String[][] filler = new String[2][26];
        for (int i = 0; i < 26; i++) {
            filler[0][i] = replacements[i][0];
            filler[1][i] = replacements[i][1];
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
        add(newTextArea);
        JScrollPane newScroll = new JScrollPane(newTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JPanel southPane = new JPanel();
        southPane.add(newScroll);

        add(northPane, BorderLayout.NORTH);
        add(centerPane, BorderLayout.CENTER);
        add(southPane, BorderLayout.SOUTH);
        add(Box.createRigidArea(new Dimension(15, 0)), BorderLayout.WEST);
        add(Box.createRigidArea(new Dimension(15, 0)), BorderLayout.EAST);
        setJMenuBar(new MenuBar(originalTextArea));
    }

    public void updateReplacements() {
        for (int i = 0; i < 26; i++) {
            //the top row is what to look for in the text; the bottom row is the characters replacement characters for that text
            //top = replacements[][0]; bottom = replacements[][1]
            replacements[i][0] = (switchTable.getValueAt(0, i).toString().toLowerCase());
            replacements[i][1] = (switchTable.getValueAt(1, i).toString().toLowerCase());
        }
    }
    
    /**
     * Return replacements, synced from last update (!!)
     * IF YOU MAKE CHANGES AND DON'T TEST THEM, THE CHANGES WILL NOT BE SAVED
     * @return 
     */
    public static String[][] getReplacements() {
        return replacements;
    }
    
    public static void setReplacements(String[][] r) {
        replacements = r;
    }
    
    @Override
    public void refresh() {
        for (int i = 0; i < 26; i++) {
            switchTable.setValueAt(replacements[i][0], 0, i);
            switchTable.setValueAt(replacements[i][1], 1, i);
        }
        standardTable.updateTable(Crypter.getStandardFrequency(Properties.getLanguage())); 
        standardLabel.setText("Standard Frequency of " + 
                Crypter.LANGUAGES[Properties.getLanguage()] + " Letters");
    }

    class SubstitutionListener implements ActionListener {
        private final String[][] atbash = {
            {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r",
                "s", "t", "u", "v", "w", "x", "y", "z"},
            {"z", "y", "x", "w", "v", "u", "t", "s", "r", "q", "p", "o", "n",
                "m", "l", "k", "j", "i", "h", "g", "f", "e", "d", "c", "b", "a"}
        };
        
        private final String[][] accentLetters = {
            { "ù", "û", "ü", "ú", "ÿ", "á", "à", "â", "å", "ä", "é", "è", "ê", "ë", "ï", "î", "ì", "í",
                "ô", "ö", "ò", "ó", "ñ", "ç", "æ", "œ" },
            { "u", "u", "u", "u", "y", "a", "a", "a", "a", "a", "e", "e", "e", "e", "i", "i", "i", "i", 
                "o", "o", "o", "o", "n", "c", "ae", "oe" }
        };

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == defaultOptions) {
                //  "Autofill Options", "Alphabet Top Row", "Atbash", "A1Z26 (to Numbers)", 
                //"A1Z26 (from Numbers)", "Non-accented text", "Clear All", "Clear Bottom Row"
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
                            for (int c = 0; c < accentLetters[0].length; c++) {
                                switchTable.setValueAt(accentLetters[r][c], r, c);
                            }
                        }
                        break;
                    case 6:
                        for (int r = 0; r < 2; r++) {
                            for (int c = 0; c < 26; c++) {
                                switchTable.setValueAt("", r, c);
                            }
                        }
                        break;
                    case 7:
                        for (int c = 0; c < 26; c++) {
                            switchTable.setValueAt("", 1, c);
                        }
                        break;
                }

            } else if (e.getSource() == analyzeOptions && !originalTextArea.getText().equals("") && originalTextArea.getText() != null) {
                switch (analyzeOptions.getSelectedIndex()) {
                    case 1: //analyze letters only
                        messageTable.updateTable(Crypter.getMessageFrequency(
                                originalTextArea.getText().replaceAll("[^a-zA-Z]", "")));
                        break;
                    case 2: //analyze letters and numbers
                        messageTable.updateTable(Crypter.getMessageFrequency(
                                originalTextArea.getText().replaceAll("[^a-zA-Z0-9]", "")));
                        break;
                    case 3: //analyze all characters
                        messageTable.updateTable(Crypter.getMessageFrequency(originalTextArea.getText()));
                        break;
                    case 4: //analyze top row only
                        updateReplacements();
                        ArrayList<String> array = new ArrayList<>();
                        for ( String[] r : replacements) 
                            array.add(r[0]);
                        messageTable.updateTable(Crypter.getSpecificMessageFrequency(originalTextArea.getText(), array));
                }
                repaint();
            } else if (e.getSource() == enter && originalTextArea.getText() != null && !originalTextArea.getText().equals("")) {
                updateReplacements();
                newTextArea.setText(Crypter.substitute(originalTextArea.getText(), replacements));
                repaint();
            }
        }
    }
}
