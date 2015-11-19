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
                    SwingUtilities.updateComponentTreeUI(null);
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }
        
        new PropertiesGui();
    }
}
