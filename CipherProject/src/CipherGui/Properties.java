package CipherGui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.*;

public class Properties {

    private static String language = "English";
    private static Color windowColor = Color.WHITE;
    private static Color labelColor = Color.BLACK;
    private static Color textColor = Color.GREEN;
    private static Color textBackgroundColor = Color.BLACK;
    private static Color buttonColor = Color.GREEN;
    
    private Properties() {
    }

    public static Color getWindowColor() {
        return windowColor;
    }

    public static Color getLabelColor() {
        return labelColor;
    }

    public static Color getTextColor() {
        return textColor;
    }

    public static Color getTextBackgroundColor() {
        return textBackgroundColor;
    }

    public static void setWindowColor(Color c) {
        windowColor = c;
    }

    public static void setTextColor(Color tC) {
        textColor = tC;
    }

    public static void setTextBackgroundColor(Color tBC) {
        textBackgroundColor = tBC;
    }

    public static void setLabelColor(Color lC) {
        labelColor = lC;
    }
    
    public static Color getButtonColor() {
        return buttonColor;
    }

    public static void setButtonColor(Color buttonColor) {
        Properties.buttonColor = buttonColor;
    }

    public static String getLanguage() {
        return language;
    }

    public static void setLanguage(String language) {
        Properties.language = language;
    }

    public static void updateFrame(MyFrame frame) {
        UIManager.put("control", windowColor);
        UIManager.put("text", labelColor);
        UIManager.put("Button.background", buttonColor);
        frame.updateTextAreas();
        SwingUtilities.updateComponentTreeUI(frame);
        frame.repaint();
    }

}
