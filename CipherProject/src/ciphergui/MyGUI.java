package ciphergui;

import other.*;
import texttools.TextFormatter;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.JFrame;
import java.util.Scanner;
import java.io.*;
import java.util.Arrays;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public abstract class MyGUI extends JFrame {
    private static String cipherText;
    protected static MyTextArea originalTextArea, newTextArea;
    protected JFrame frame;

    public MyGUI(int width, int height, String title) {
        super(title);
        if (newTextArea != null && !newTextArea.getText().equals("")) {
            cipherText = newTextArea.getText();
        } else if (originalTextArea != null && !originalTextArea.getText().equals("")){
            cipherText = originalTextArea.getText();
        } else {
            cipherText = "Enter text here.";
        }
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);
    }

    public static void updateTextAreas() {
        MyTextArea textAreas[] = {originalTextArea, newTextArea};
        for (MyTextArea textArea : textAreas) {
            if (textArea != null) {
                textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, MyTextArea.getFontSize()));
                textArea.setBackground(Properties.getTextAreaBG());
                textArea.setForeground(Properties.getTextAreaFG());
            }
        }
    }
    
    public static String getCipherText() {
        return cipherText;
    }
    
    public static MyTextArea getOriginalTextArea() {
        return originalTextArea;
    }
    
    public static void save() {
        JFileChooser fileChooser = new JFileChooser("C:\\Users\\" + System.getProperty("user.name") + "\\Documents");
        int result = fileChooser.showSaveDialog(null);
        File file = fileChooser.getSelectedFile();
        PrintWriter output = null;
        if (result == 0) {
            if (file.exists()) {
                result = JOptionPane.showConfirmDialog(null, "A file with this name already exists.  Do you wish to overwrite it?",
                        "", JOptionPane.ERROR_MESSAGE);
                if (result != 0)
                    return;
            }
            try {
                output = new PrintWriter(file);
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "There was an error loading the file.");
                return;
            }
            if (originalTextArea != null) {
                output.println(originalTextArea.getText());
            } else {
                output.println("Enter text here.");
            }
            for (int i = 0; i < SubstitutionCipherGui.getReplacements().length; i++) {
                output.println(Arrays.asList(SubstitutionCipherGui.getReplacements()[i]));
            }
            output.println(Arrays.toString(BoxCipherGui.getSettings()));
            output.println(Arrays.toString(BoxCipherGui.getOrientation()));
            output.println(Arrays.toString(BoxCipherGui.getBoxSize()));
            output.println(VigenereKnownGui.getKeyword());
            output.println(VigenereGraphGui.getKeyword());
            output.close();
        }
    }

    public static void load() {
        JFileChooser fileChooser = new JFileChooser("C:\\Users\\" + System.getProperty("user.name") + "\\Documents");
        int result = fileChooser.showOpenDialog(null);
        File file = fileChooser.getSelectedFile();
        Scanner scan = null;
        if (result == 0) {
            try {
                scan = new Scanner(file);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "An error has occured.");
            }
            try {
                originalTextArea.setText(scan.nextLine() + "");
                String[][] replacements = new String[26][2];
                for (int i = 0; i < 26; i++) {
                    String first = scan.next();
                    replacements[i][0] = first.substring(1, first.length() - 1);
                    String second = scan.next();
                    replacements[i][1] = second.substring(0, second.length() - 1);
                    scan.nextLine();
                }
                SubstitutionCipherGui.setReplacements(replacements);
                boolean bSettings[] = new boolean[4];
                for (int i = 0; i < 4; i++) {
                    String b = TextFormatter.formatText(scan.next(), TextFormatter.ONLY_LETTERS);
                    bSettings[i] = b.equals("true");
                }
                BoxCipherGui.setSettings(bSettings);
                scan.nextLine();
                int[] bOrientation = new int[2];
                bOrientation[0] = Integer.parseInt(scan.next().replaceAll("\\D", ""));
                bOrientation[1] = Integer.parseInt(scan.next().replaceAll("\\D", ""));
                BoxCipherGui.setOrientation(bOrientation);
                scan.nextLine();
                int[] bSize = new int[2];
                bSize[0] = Integer.parseInt(scan.next().replaceAll("\\D", ""));
                bSize[1] = Integer.parseInt(scan.next().replaceAll("\\D", ""));
                BoxCipherGui.setBoxSize(bSize);
                scan.nextLine();
                VigenereKnownGui.setKeyword(scan.nextLine());
                VigenereGraphGui.setKeyword(scan.nextLine());
                scan.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Sorry, this file does not have the correct format.\n"
                        + "Not all settings were loaded.");
            }
        } 
    }
    
    public static void quit(){
        System.exit(0);
    }
    
    public JFrame getFrame() {
        return frame;
    }
    
    public abstract void refresh();
}
