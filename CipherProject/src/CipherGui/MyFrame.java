
package CipherGui;

import java.util.ArrayList;
import javax.swing.JFrame;

public class MyFrame extends JFrame {
    private ArrayList<MyTextArea> textAreas = new ArrayList<MyTextArea>();
    
    public MyFrame(String title) {
        super(title);
    }
    
    public void addTextArea(MyTextArea textArea) {
        textAreas.add(textArea);
    }
    
    public void updateTextAreas() {
        for (int i = 0; i < textAreas.size(); i++) {
            textAreas.get(i).updateMyTextArea();
        }
    }
    
}
