package CipherClasses;


import java.awt.Font;
import javax.swing.JTextArea;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Audrey
 */
public class MyTextArea extends JTextArea {
    
    public MyTextArea(String str, int r, int c, boolean editable, int fontSize) {
        super(str, r, c);
        this.setFont(new Font(Font.MONOSPACED, Font.PLAIN, fontSize));
        this.setLineWrap(true);
        this.setWrapStyleWord(true);
        this.setEditable(editable);
    }
    
    public static JTextArea createTextArea(int r, int c, String str, boolean editable) {
        JTextArea textArea = new JTextArea(str, r, c);
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(editable);
        return textArea;
    }
}
