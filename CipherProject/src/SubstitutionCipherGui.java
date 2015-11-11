package CipherClasses;


import javax.swing.*;
import java.awt.Toolkit;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.TextArea;
import java.util.ArrayList;

/**
 *
 * @author Audrey
 */
public class SubstitutionCipherGui {

    private JFrame frame;
    private JTextArea originalTextArea, newTextArea;
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
    private JComboBox analyzeOptions;

    public SubstitutionCipherGui() {
        frame = new JFrame("Substitution Cipher");
        frame.setSize(945, 595);

        init();

        Dimension dim = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setLocation(dim.width / 2 - frame.getWidth() / 2, dim.height / 2 - frame.getHeight() / 2);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public void init() {
        //create northpane
        originalTextArea = new MyTextArea("Enter text here.", 5, 70, true, 14);
        JScrollPane originalScroll = new JScrollPane(originalTextArea,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        // JLabel analyzeL = new JLabel("Analyze:");
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
        messageTable = new FrequencyTable(Text.getMessageFrequency(""), columnNames);
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
        String[] subAlphabet = new String[26];
        for (int i = 0; i < 26; i++) {
            subAlphabet[i] = "" + (char) (97 + i);
        }
        
        Object[][] fillerOptions = {
            subAlphabet,
            {"z", "y", "x", "w", "v", "u", "t", "s", "r", "q", "p", "o", "n",
                "m", "l", "k", "j", "i", "h", "g", "f", "e", "d", "c", "b", "a"}

        };
        switchTable = new JTable(fillerOptions, subAlphabet);
        switchTable.getTableHeader().setReorderingAllowed(false);
        switchTable.setPreferredScrollableViewportSize(new Dimension(switchTable.getWidth(),
                switchTable.getHeight() - switchTable.getRowHeight()));
        switchTable.setTableHeader(null);
        switchTable.setAutoCreateRowSorter(true);
        //switchTable.setFillsViewportHeight(true);
        JScrollPane switchPane = new JScrollPane(switchTable);
        JPanel centerPane = new JPanel();
        centerPane.setLayout(new BoxLayout(centerPane, BoxLayout.PAGE_AXIS));
        centerPane.add(tablePane);
        centerPane.add(Box.createRigidArea(new Dimension(0, 15)));
        centerPane.add(switchPane);
        centerPane.add(Box.createRigidArea(new Dimension(0, 5)));
        centerPane.add(enter);

        //southpane
        newTextArea = new MyTextArea("", 6, 70, false, 14);
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

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == analyzeOptions && !originalTextArea.getText().equals("") && originalTextArea.getText() != null) {
                int include = analyzeOptions.getSelectedIndex();
                switch (include) {
                    case 1:
                        messageTable.updateTable(Text.getMessageFrequency(
                                originalTextArea.getText().replaceAll("[^a-zA-Z]", "")));
                        break;
                    case 2:
                        messageTable.updateTable(Text.getMessageFrequency(
                                originalTextArea.getText().replaceAll("[^a-zA-Z0-9]", "")));
                        break;
                    case 3:
                        messageTable.updateTable(Text.getMessageFrequency(originalTextArea.getText()));
                        break;
                    case 4:
                        updateReplacements();
                        ArrayList<String> array = new ArrayList<String>();
                        for ( String[] r : replacements) 
                            array.add(r[1]);
                        messageTable.updateTable(Text.getSpecificMessageFrequency(originalTextArea.getText(), array));
                }
                frame.repaint();
            } else if (e.getSource() == enter && !originalTextArea.getText().equals("") && originalTextArea.getText() != null) {
                updateReplacements();
                newTextArea.setText(Text.substitute(originalTextArea.getText(), replacements));
                frame.repaint();
            }
        }
    }
}
