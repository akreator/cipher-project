package CipherGui;

import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;

public class Run {

    public static void main(String[] args) {
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    UIManager.put("TextField.font", new Font(Font.MONOSPACED, Font.PLAIN, 12));
                    UIManager.put("Table.showGrid", true);
                    break;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Sorry, but you do not have the Nimbus package.\n"
                    + "Email akreator0@gmail.com with your operating system to let them know.", 
                    "Package not available", JOptionPane.ERROR_MESSAGE);
        }
        new PatternFinderGui();
    }
}
