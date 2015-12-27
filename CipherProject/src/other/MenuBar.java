package other;

import texttools.*;
import ciphergui.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

public class MenuBar extends JMenuBar {

    private JMenuItem[] switchItems = new JMenuItem[7], fileItems = new JMenuItem[5], formatItems = new JMenuItem[9],
            languageItems = new JMenuItem[4];
    private JTextArea textArea;
    private String beforeFormat = "Enter text here.";
    private boolean formatting = false;

    public MenuBar() {
        init();
    }

    public MenuBar(MyTextArea ta) {
        textArea = ta;
        init();
        initFormat();
    }

    private void init() {
        //fileMenu
        JMenu fileMenu = new JMenu("File");
        String[] fileNames = {"Instructions", "About", "Save", "Load", "Quit"};
        for (int i = 0; i < fileItems.length; i++) {
            fileItems[i] = new JMenuItem(fileNames[i]);
            fileItems[i].addActionListener(new FileListener(i));
            fileMenu.add(fileItems[i]);
            if ( (i + 1) % 2 == 0) {
                fileMenu.add(new JSeparator());
            }
        }
        this.add(fileMenu);

        //switch cipher menu
        JMenu switchCipherMenu = new JMenu("Switch Cipher");
        String[] switchNames = {
            "Caesar Cipher", "Substitution Cipher", "Known Keyword", "Unknown Keyword", 
            "Pig Latin", "Box Cipher", "Binary Code"
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
        switchCipherMenu.add(switchItems[4]);
        switchCipherMenu.add(switchItems[5]);
        switchCipherMenu.add(switchItems[6]);
        this.add(switchCipherMenu);
    }

    private void initFormat() {
        textArea.addCaretListener(new TextListener());
        
        //language menu
        JMenu languageMenu = new JMenu("Language");
        String[] languageNames = Crypter.LANGUAGES;
        for (int i = 0; i < languageItems.length; i++) {
            languageItems[i] = new JMenuItem(languageNames[i]);
            languageItems[i].setActionCommand("" + i);
            languageItems[i].addActionListener(new LanguageListener());
            languageMenu.add(languageItems[i]);
        }
        this.add(languageMenu);
        
        
        
        //format menu
        JMenu formatMenu = new JMenu("Edit");
        String[] formatNames = {"Spaces", "Punctuation", "Numbers", "Upper", "Lower", 
            "Autoformat", "Undo Formatting", "All non-text",
            "Group"
        };
        int[] formatNums = {
            TextFormatter.NO_SPACES, TextFormatter.NO_PUNCTUATION, TextFormatter.NO_NUMBERS, TextFormatter.UPPERCASE, 
            TextFormatter.LOWERCASE, TextFormatter.SPECIAL_FORMAT, 100, TextFormatter.ONLY_LETTERS, 
            TextFormatter.GROUP
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
        formatMenu.add(formatItems[6]);
        this.add(formatMenu);
    }
    
    public void manageFrame(boolean reload) {
        Window w = SwingUtilities.getWindowAncestor(this);
        if (w instanceof MyGUI) {
            MyGUI frame = (MyGUI) w;
            if (reload)
                frame.refresh();
            else
                frame.dispose();
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(201, 201, 201));
        g2d.fillRect(0, 0, getWidth(), getHeight());
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
                    textArea.setText(TextFormatter.formatText(textArea.getText(), formatNum));
                else 
                    textArea.replaceRange(TextFormatter.formatText(textArea.getSelectedText(), formatNum)
                          , textArea.getSelectionStart(), textArea.getSelectionEnd());
                formatting = false;
            } else if (formatNum == 100) {
                textArea.setText(beforeFormat);
            } 
        }
    }
    
    class LanguageListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int n = Integer.parseInt(e.getActionCommand());
            switch (n) {
                case 1:
                    Properties.setLanguage(Crypter.LATIN);
                    break;
                case 2:
                    Properties.setLanguage(Crypter.FRENCH);
                    break;
                case 3:
                    Properties.setLanguage(Crypter.SPANISH);
                    break;
                default:
                    Properties.setLanguage(Crypter.ENGLISH);
                    break;
            }  
            manageFrame(true);
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
                    JOptionPane.showMessageDialog(null, "Designed and coded by Asch."
                            + "\nPlease send any feedback or questions to akreator0@gmail.com",
                            "Credits", JOptionPane.PLAIN_MESSAGE);
                    break;
                case 2:
                    MyGUI.save();
                    break;
                case 3:
                    if (MyGUI.load())
                        manageFrame(true);
                    break;
                case 4:
                    MyGUI.quit();
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
                    CaesarGui cg = new CaesarGui();
                    break;
                case 1:
                    SubstitutionCipherGui scg = new SubstitutionCipherGui();
                    break;
                case 2:
                    VigenereKnownGui vkg = new VigenereKnownGui();
                    break;
                case 3:
                    PatternFinderGui pfg = new PatternFinderGui();
                    break;
                case 4:
                    PigLatinGui plg = new PigLatinGui();
                    break;
                case 5:
                    BoxCipherGui bcg = new BoxCipherGui();
                    break;
                case 6:
                    BinaryGui bg = new BinaryGui();
                    break;
            }
            manageFrame(false);
        } //end of method
    }
}
