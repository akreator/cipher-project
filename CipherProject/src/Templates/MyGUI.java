package Templates;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.JFrame;

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
}
