package other;

import ciphergui.MyGUI;
import java.awt.Color;
import javax.swing.*;
import texttools.FrequencyCalculator;

public class Properties {
    private static int theme = 0;
    private static int selectedLang = 0;
    private static Color textColor = Color.BLACK, textBackgroundColor = Color.WHITE, radioBG = new Color(238, 238, 238),
        radioFG = new Color(51, 51, 51);
    public static final int DEFAULT = 0, HACKER = 1, BLUE = 2, GREEN = 3, ORANGE = 4, SIMPLE = 5;
    public static final String[] themeNames = {"Default", "Hacker", "Blue", "Green", "Orange", "Monochrome"};
    private static final String[] components = {
        "control", "nimbusBase", "nimbusFocus", 
        "text", "nimbusSelectedText", "nimbusSelectionBackground", 
        "Table.background", "TextField.background", "nimbusBlueGrey",
        "nimbusLightBackground", "Table[Enabled+Selected].textForeground", 
    };
    
    private static final Color[][] themeColors = {
        { //default colors
            new Color(214, 217, 223), new Color(51, 98, 140), new Color(115, 164, 209),
            Color.BLACK, Color.WHITE, new Color(57, 105, 138),
            Color.WHITE, Color.WHITE, new Color(169, 176, 190),
            Color.WHITE, Color.WHITE }, 
        { //hacker colors
            new Color(42, 46, 42), Color.BLACK, Color.GREEN, 
            Color.GREEN, Color.RED, Color.BLACK,
            Color.BLACK, Color.BLACK, Color.BLACK, 
            Color.BLACK, Color.RED }, 
        { //blue
            new Color(176, 202, 247), new Color(72, 182, 250), new Color(36, 116, 255),
            Color.BLACK, Color.WHITE, new Color(21, 66, 171), 
            Color.WHITE, Color.WHITE, new Color(69, 137, 255),
            Color.WHITE, Color.WHITE }, 
        { //green
            new Color(7, 77, 9), new Color(0, 5, 0), new Color(0, 104, 0),
            Color.WHITE, Color.WHITE, new Color(7, 77, 9), 
            new Color(7, 77, 9), new Color(0, 5, 0), new Color(0, 5, 0),
            new Color(0, 104, 0), Color.WHITE }, 
        { //orange
            new Color(255, 145, 0), new Color(255, 145, 0), new Color(255, 204, 0),
            Color.BLACK, Color.WHITE, new Color(184, 116, 20),
            new Color(255, 145, 0), Color.WHITE, new Color(252, 177, 91),
            new Color(255, 175, 46), Color.WHITE }, 
        { //simple
           Color.WHITE, Color.GRAY, Color.BLACK, 
           Color.BLACK, Color.WHITE, Color.BLACK, 
           Color.WHITE, Color.WHITE, Color.GRAY,
           Color.GRAY, Color.WHITE }, 
    };

    private Properties() {
    }
    
    public static void setTheme(int colorScheme) {
        theme = colorScheme;
        for (int i = 0; i < themeColors[theme].length; i++) {
            UIManager.put(components[i], themeColors[theme][i]);
        }
        radioBG = themeColors[theme][0];
        radioFG = themeColors[theme][2]; 
        switch (theme) {
            case DEFAULT:
                textBackgroundColor = Color.WHITE;
                textColor = Color.BLACK;
                break;
            case HACKER:
                textBackgroundColor = Color.BLACK;
                textColor = Color.GREEN;
                radioBG = Color.BLACK;
                radioFG = Color.GREEN; 
                break;
            case BLUE:
                textBackgroundColor = Color.WHITE;
                textColor = new Color(0, 53, 138);
                break;
            case GREEN:
                textBackgroundColor = Color.WHITE;
                textColor = new Color(7, 77, 9);
                break; 
            case ORANGE:
                textBackgroundColor = Color.WHITE;
                textColor = Color.BLACK;
                break;
            case SIMPLE:
                textBackgroundColor = Color.WHITE;
                textColor = Color.BLACK;
                break;
        }
        MyGUI.updateTextAreas();
    }

    public static int getLanguage() {
        return selectedLang;
    }

    public static void setLanguage(int l) {
        if (l >= FrequencyCalculator.LANGUAGES.length || l < 0) {
            selectedLang = 0;
        } else {
            selectedLang = l;
        }
    }

    public static Color getTextAreaBG() {
        return textBackgroundColor;
    }

    public static Color getTextAreaFG() {
        return textColor;
    }
    
    public static Color getRadioBG() {
        return radioBG;
    }
    
    public static Color getRadioFG() {
        return radioFG;
    }
    
}