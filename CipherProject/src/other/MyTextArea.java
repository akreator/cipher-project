 package other;


import ciphergui.MyGUI;
import java.awt.Font;
import javax.swing.JTextArea;

public class MyTextArea extends JTextArea {
    private static int fontSize = 14;
    
    public MyTextArea(int r, int c, boolean editable, boolean dontCarryOverText) {
        super(MyGUI.getCipherText(), r, c);
        if (dontCarryOverText)
            setText("");
        setFont(new Font(Font.MONOSPACED, Font.PLAIN, fontSize));
        setBackground(Properties.getTextAreaBG());
        setForeground(Properties.getTextAreaFG());
        setLineWrap(true);
        setWrapStyleWord(true);
        setEditable(editable);
    }

    public static void setFontSize(int fS) {
        fontSize = fS;
    }
    
    public static int getFontSize() {
        return fontSize;
    }
    
    @Override
    public final void setText(String str) {
        super.setText(str);
        setCaretPosition(0);
    }
}
