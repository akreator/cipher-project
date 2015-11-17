 package CipherGui;


import java.awt.Font;
import javax.swing.JTextArea;

public class MyTextArea extends JTextArea {
    private static int fontSize = 14;
    
    public MyTextArea(String str, int r, int c, boolean editable) {
        super(str, r, c);
        this.setFont(new Font(Font.MONOSPACED, Font.PLAIN, fontSize));
        this.setBackground(Properties.getTextBackgroundColor());
        this.setForeground(Properties.getTextColor());
        this.setLineWrap(true);
        this.setWrapStyleWord(true);
        this.setEditable(editable);
    }

    public static void setTextSize(int fS) {
        fontSize = fS;
    }
    
    public static int getTextSize() {
        return fontSize;
    }
    
    public void updateMyTextArea() {
        this.setFont(new Font(Font.MONOSPACED, Font.PLAIN, fontSize));
        this.setBackground(Properties.getTextBackgroundColor());
        this.setForeground(Properties.getTextColor());
    }
}
