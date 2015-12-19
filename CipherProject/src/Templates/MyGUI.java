package Templates;

import CipherGui.*;
import TextTools.TextFormatter;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.JFrame;
import java.util.Scanner;
import java.io.*;
import java.util.Arrays;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public abstract class MyGUI {
    private static String cipherText;
    protected static MyTextArea originalTextArea, newTextArea;
    protected JFrame frame;

    public MyGUI(int width, int height, String title) {
        if (newTextArea != null && !newTextArea.getText().equals("")) {
            cipherText = newTextArea.getText();
        } else if (originalTextArea != null && !originalTextArea.getText().equals("")){
            cipherText = originalTextArea.getText();
        } else {
            cipherText = "Enter text here.";
        }
        frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
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
        String fileName = JOptionPane.showInputDialog(null, "Save as:", "Save", JOptionPane.PLAIN_MESSAGE);
        File dir = new File("userData");
        File file = new File(dir, fileName + ".txt");
        System.out.println(file.getAbsolutePath());
        int result = 0;
        PrintWriter output = null;
        if (file.exists()) {
            result = JOptionPane.showConfirmDialog(null, "A file with this name already exists.  Do you wish to overwrite it?", 
                    "", JOptionPane.ERROR_MESSAGE);
        } 
        
        if (result == 0) {
            try {
                output = new PrintWriter(fileName + ".txt");
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "SOMETHING WENT WRONG ERROR 666");
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
            output.println(VigenereKnownGui.getKeyword());
            output.close();
        }
    }

    public static void load() {
        JFileChooser fileChooser = new JFileChooser("..\\CipherProject\\userData");
        fileChooser.showOpenDialog(null);
        File file = fileChooser.getSelectedFile();
        Scanner scan = null;
        try {
            scan = new Scanner(file);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "File not found.");
        }
       // try {
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
            scan.nextLine();
            BoxCipherGui.setSettings(bSettings);
            int[] bOrientation = new int[2];
            bOrientation[0] = Integer.parseInt(scan.next().replaceAll("\\D", ""));
            bOrientation[1] = Integer.parseInt(scan.next().replaceAll("\\D", ""));
            BoxCipherGui.setOrientation(bOrientation);
            scan.nextLine();
            VigenereKnownGui.setKeyword(scan.nextLine());
        /*} catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Sorry, this file does not have the correct format.");
        } */
    }
}
