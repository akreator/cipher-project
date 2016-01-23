package ciphergui;

import other.*;
import texttools.TextFormatter;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import javax.swing.JFrame;
import java.util.Scanner;
import java.io.*;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
        PrintWriter output;
        if (result == 0) {
            file = new File(file.toString() + ".txt");
            if (file.exists()) {
                result = JOptionPane.showConfirmDialog(null, "A file with this name already exists.  Do you wish to overwrite it?",
                        "", JOptionPane.ERROR_MESSAGE);
                if (result != 0)
                    return;
            }
            try {
                output = new PrintWriter(file);
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "There was an error saving the file.");
                return;
            }
            for (String[] replacement : SubstitutionCipherGui.getReplacements()) { //print replacements
                output.println(Arrays.asList(replacement));
            }
            output.println(Arrays.toString(BoxCipherGui.getSettings())); //print box settings
            output.println(Arrays.toString(BoxCipherGui.getOrientation())); //box orientation
            output.println(Arrays.toString(BoxCipherGui.getBoxSize())); //box size
            output.println(VigenereKnownGui.getKeyword()); //known keyword
            output.println(Properties.getLanguage()); //language
            if (originalTextArea != null) { //text working with
                output.println(originalTextArea.getText());
            } else {
                output.println("Enter text here.");
            }
            output.close();
            file.setReadOnly();
        }
    }

    /***
     * Load a file.  Returns true if successful load (ANY settings were retrieved)
     * Returns false if load failed completely or canceled.
     * @return 
     */
    public static boolean load() {
        //open the file
        JFileChooser fileChooser = new JFileChooser("C:\\Users\\" + System.getProperty("user.name") + "\\Documents");
        int result = fileChooser.showOpenDialog(null);
        File file = fileChooser.getSelectedFile();
        Scanner scan;
        if (result == 0) { //if a file was selected
            try { //try to open the file
                scan = new Scanner(file);
                try { //if the file was able to be loaded...
                    String[][] replacements = new String[26][2]; //1. load replacements
                    for (int i = 0; i < 26; i++) {
                        String first = scan.next();
                        replacements[i][0] = first.substring(1, first.length() - 1);
                        String second = scan.next();
                        replacements[i][1] = second.substring(0, second.length() - 1);
                        scan.nextLine();
                    }
                    SubstitutionCipherGui.setReplacements(replacements);
                    boolean bSettings[] = new boolean[4]; //2. load box settings
                    for (int i = 0; i < 4; i++) {
                        String b = TextFormatter.formatText(scan.next(), TextFormatter.ONLY_LETTERS);
                        bSettings[i] = b.equals("true");
                    }
                    BoxCipherGui.setSettings(bSettings);
                    scan.nextLine();
                    int[] bOrientation = new int[2]; //3. load box orientation
                    bOrientation[0] = Integer.parseInt(scan.next().replaceAll("\\D", ""));
                    bOrientation[1] = Integer.parseInt(scan.next().replaceAll("\\D", ""));
                    BoxCipherGui.setOrientation(bOrientation);
                    scan.nextLine();
                    int[] bSize = new int[2]; //4.load box size
                    bSize[0] = Integer.parseInt(scan.next().replaceAll("\\D", ""));
                    bSize[1] = Integer.parseInt(scan.next().replaceAll("\\D", ""));
                    BoxCipherGui.setBoxSize(bSize);
                    scan.nextLine();
                    VigenereKnownGui.setKeyword(scan.nextLine()); //5. load vigenere keyword
                    Properties.setLanguage(Integer.parseInt("0" + scan.nextLine()));
                    originalTextArea.setText(""); // 6. read in the original text
                    while (scan.hasNext()) {
                        originalTextArea.append(scan.nextLine());
                        originalTextArea.append("\n");
                    }
                    scan.close();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Sorry, this file does not have the correct format.\n"
                            + "Not all settings were loaded.");
                    return true;
                }
            } catch (FileNotFoundException | HeadlessException e) {
                JOptionPane.showMessageDialog(null, "The file was not able to be loaded.");
                return false;
            }
            return true;
        }
        return false;
    }
    
    public static void quit(){
        System.exit(0);
    }
    
    public JFrame getFrame() {
        return frame;
    }
    
    public abstract void refresh();
}
