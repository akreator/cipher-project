package CipherClasses;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

public class MenuBar extends JMenuBar {

    private JMenuItem[] switchItems = new JMenuItem[4], fileItems = new JMenuItem[4], formatItems = new JMenuItem[10];
    private JFrame frame;
    private JTextArea textArea;
    private String beforeFormat = "Enter text here.";
    private boolean formatting = false;

    public MenuBar(JFrame jframe) {
        frame = jframe;
        init();
    }

    public MenuBar(JFrame jframe, JTextArea ta) {
        frame = jframe;
        textArea = ta;
        init();
        initFormat();
    }

    public void init() {
        //fileMenu
        JMenu fileMenu = new JMenu("File");
        String[] fileNames = {"Instructions", "About", "Save", "Load"};
        for (int i = 0; i < fileItems.length; i++) {
            fileItems[i] = new JMenuItem(fileNames[i]);
            fileItems[i].addActionListener(new FileListener(i));
            fileMenu.add(fileItems[i]);
        }
        this.add(fileMenu);

        //switch cipher menu
        JMenu switchCipherMenu = new JMenu("Switch Cipher");
        String[] switchNames = {
            "Caesar Cipher", "Substitution Cipher", "Known Keyword", "Unknown Keyword"
        };
        for (int i = 0; i < switchItems.length; i++) {
            switchItems[i] = new JMenuItem(switchNames[i]);
            switchItems[i].addActionListener(new SwitchListener(i));
        }
        switchCipherMenu.add(switchItems[0]);
        switchCipherMenu.add(switchItems[1]);
        JMenu vignereCipherMenu = new JMenu("Vignere Cipher");
        switchCipherMenu.add(vignereCipherMenu);
        vignereCipherMenu.add(switchItems[2]);
        vignereCipherMenu.add(switchItems[3]);
        switchCipherMenu.add(vignereCipherMenu);
        this.add(switchCipherMenu);
    }

    public void initFormat() {
        textArea.addCaretListener(new TextListener());
        //format menu
        JMenu formatMenu = new JMenu("Edit");
        String[] formatNames = {"Spaces", "Punctuation", "Numbers", "Upper", "Lower", 
            "Autoformat", "Undo Recent Formatting", "All non-text",
            "Numbers to Letters", "Group"
        };
        int[] formatNums = {
            Text.NO_SPACES, Text.NO_PUNCTUATION, Text.NO_NUMBERS, Text.UPPERCASE, Text.LOWERCASE, 
            Text.SPECIAL_FORMAT, 100, Text.ONLY_LETTERS, Text.NUMS_TO_LETTERS, 101
        };
        for (int i = 0; i < formatItems.length; i++) {
            formatItems[i] = new JMenuItem(formatNames[i]);
            formatItems[i].addActionListener(new FormatListener(formatNums[i]));
        }
        JMenu removeMenu = new JMenu("Remove...");
        removeMenu.add(formatItems[0]);
        removeMenu.add(formatItems[1]);
        removeMenu.add(formatItems[2]);
        removeMenu.add(formatItems[7]);
        JMenu caseMenu = new JMenu("Change Case");
        caseMenu.add(formatItems[3]);
        caseMenu.add(formatItems[4]);
        formatMenu.add(removeMenu);
        formatMenu.add(caseMenu);
        formatMenu.add(formatItems[5]);
        formatMenu.add(formatItems[8]);
        formatMenu.add(formatItems[9]);
        formatMenu.add(formatItems[6]);
        this.add(formatMenu);
    }

    class TextListener implements CaretListener {
        @Override
        public void caretUpdate(CaretEvent e) {
            if (!formatting)
                beforeFormat = textArea.getText();
        }   
    }
    
    class FormatListener implements ActionListener {
        int formatNum;
        public FormatListener(int n) {
            formatNum = n;
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if (formatNum <= formatItems.length) {
                System.out.println("a");
                formatting = true;
                if (textArea.getSelectedText() == null)
                    textArea.setText(Text.formatText(textArea.getText(), formatNum));
                else 
                    textArea.replaceRange(Text.formatText(textArea.getSelectedText(), formatNum)
                          , textArea.getSelectionStart(), textArea.getSelectionEnd());
                formatting = false;
            } else if (formatNum == 100) {
                textArea.setText(beforeFormat);
            } else if (formatNum == 101) {
                int groupNum = 0;
                String input = JOptionPane.showInputDialog(frame, "Enter the group length:", "Group Text", JOptionPane.PLAIN_MESSAGE);
                try {
                    groupNum = Integer.parseInt(input);
                    textArea.setText(Text.group(textArea.getText(), groupNum));
                } catch (NumberFormatException n){
                    JOptionPane.showMessageDialog(frame, "Sorry, that is not a valid number", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    class FileListener implements ActionListener {
        int fileNum;
        public FileListener(int n) {
            fileNum = n;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (fileNum) {
                case 0:
                    System.out.println("hey");
                    break;
                case 1:
                    JOptionPane.showMessageDialog(frame, "Designed and coded by Audrey Kintisch.\n"
                            + "\nV6.2 11/4/15: Added grouping to formatting, fixed alphabet error"
                            + "\nV6.1 10/30/15: Bug fixes"
                            + "\nV6.0 10/29/15: Added text formatting, updated menu bar"
                            + "\nV5.2 10/26/15: Updated substitution - 2 rows"
                            + "\nV5.1 10/18/15: Bug fixes"
                            + "\nV5.0 10/14/15: Added Substitution Cipher"
                            + "\nV4.0 10/12/15: Added Vignere Cipher - unknown keyword - graphs"
                            + "\nV3.0 10/11/15: Added Vignere Cipher - unknown keyword - pattern finder"
                            + "\nV2.0   10/1/15: Added Vignere Cipher - known keyword, menu bar"
                            + "\nV1.0   9/25/15: Caesar cipher",
                            "Credits", JOptionPane.PLAIN_MESSAGE);
                    break;
            }
        }
    }

    class SwitchListener implements ActionListener {
        int switchNum;
        public SwitchListener(int n) {
            switchNum = n;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (switchNum) {
                case 0:
                    new CaesarGui();
                    break;
                case 1:
                    new SubstitutionCipherGui();
                    break;
                case 2:
                    new VignereKnownGui();
                    break;
                case 3:
                    new PatternFinderGui();
                    break;
            }
            frame.setVisible(false);
            frame.dispose();
        }
    }
}
