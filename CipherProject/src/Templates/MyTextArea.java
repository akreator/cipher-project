 package Templates;


import java.awt.Font;
import javax.swing.JTextArea;

public class MyTextArea extends JTextArea {
    private static int fontSize = 14;
    
    public MyTextArea(int r, int c, boolean editable, boolean dontCarryOverText) {
        super(MyGUI.getCipherText(), r, c);
        if (dontCarryOverText)
            this.setText("");
        this.setFont(new Font(Font.MONOSPACED, Font.PLAIN, fontSize));
        this.setBackground(Properties.getTextAreaBG());
        this.setForeground(Properties.getTextAreaFG());
        this.setLineWrap(true);
        this.setWrapStyleWord(true);
        this.setEditable(editable);
    }

    public static void setFontSize(int fS) {
        fontSize = fS;
    }
    
    public static int getFontSize() {
        return fontSize;
    }
}
