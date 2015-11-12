package CipherClasses;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

public class MenuBar extends JMenuBar {

    private JMenuItem[] switchItems = new JMenuItem[4], fileItems = new JMenuItem[5], formatItems = new JMenuItem[10];
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
        String[] fileNames = {"Instructions", "About", "Save", "Load", "Properties"};
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
                    break;
                case 1:
                    JOptionPane.showMessageDialog(frame, "Designed and coded by Asch."
                            + "\nPlease send any feedback or questions to akreator0@gmail.com",
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
