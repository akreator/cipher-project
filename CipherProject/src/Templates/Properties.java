package Templates;

import java.awt.Color;
import javax.swing.*;

public class Properties {

    private static String language = "English";
    private static Color textColor = Color.BLACK;
    private static Color textBackgroundColor = Color.WHITE;
    public static final int DEFAULT = 0, HACKER = 1, BLUE = 2, GREEN = 3, ORANGE = 4, SIMPLE = 5;
    public static final String[] themeNames = {"Default", "Hacker", "Blue", "Green", "Orange", "Monochrome"};
    private static String[] components = {
        "control", "nimbusBase", "nimbusFocus", 
        "text", "nimbusSelectedText", "nimbusSelectionBackground", 
        "Table.background", "TextField.background", "nimbusBlueGrey",
        "nimbusLightBackground"
    };
    
    private static Color[][] themeColors = {
        { //default colors
            new Color(214, 217, 223), new Color(51, 98, 140), new Color(115, 164, 209),
            Color.BLACK, Color.WHITE, new Color(57, 105, 138),
            Color.WHITE, Color.WHITE, new Color (169, 176, 190),
            Color.WHITE
        }, 
        { //hacker colors
            new Color(42, 46, 42), Color.BLACK, Color.GREEN, 
            Color.GREEN, Color.RED, Color.BLACK,
            Color.BLACK, Color.BLACK, Color.BLACK, 
            Color.BLACK }, 
        { //blue
            new Color(176, 202, 247), new Color(72, 182, 250), new Color(36, 116, 255),
            Color.BLACK, Color.WHITE, new Color(21, 66, 171), 
            Color.WHITE, Color.WHITE, new Color(69, 137, 255),
            Color.WHITE
        },
        {}, //green
        {}, //orange
        {}, //simple
    };

    private Properties() {
    }

    public static void setTheme(int theme) {
        for (int i = 0; i < themeColors[theme].length; i++) {
            UIManager.put(components[i], themeColors[theme][i]);
        }
        switch (theme) {
            case DEFAULT:
                textBackgroundColor = Color.WHITE;
                textColor = Color.BLACK;
                break;
            case HACKER:
                textBackgroundColor = Color.BLACK;
                textColor = Color.GREEN;
                break;
            case BLUE:
                textBackgroundColor = Color.WHITE;
                textColor = new Color(0, 53, 138);
                break;
        }
        MyGUI.updateTextAreas();
    }

    public static String getLanguage() {
        return language;
    }

    public static void setLanguage(String language) {
        Properties.language = language;
    }

    public static Color getTextAreaBG() {
        return textBackgroundColor;
    }

    public static Color getTextAreaFG() {
        return textColor;
    }
}
