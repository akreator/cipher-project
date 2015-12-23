package CipherGui;

import Templates.GraphComp;
import Templates.MenuBar;
import Templates.MyGUI;
import Templates.MyTextArea;
import Templates.Properties;
import TextTools.Crypter;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

public class CaesarGui extends MyGUI {

    private JButton rightShift, leftShift;
    private GraphComp messageComp, standardComp;

    public CaesarGui() {
        super(945, 500, "Casesar Cipher");
        init();
        setVisible(true);
    }

    private void init() {
        //WELCOME TO BUTTON HELL
        rightShift = new JButton("Shift right");
        rightShift.addActionListener(new GraphGuiListener());
        leftShift = new JButton("Shift left");
        leftShift.addActionListener(new GraphGuiListener());
        
        //prevent carrying over errors
        newTextArea = new MyTextArea(5, 4, false, true);
        newTextArea.setText("");

        JPanel bottom = new JPanel();
        bottom.add(leftShift);
        bottom.add(rightShift);

        //And now we jump to the north panel.  Because organization.
        originalTextArea = new MyTextArea(5, 75, true, false);
        originalTextArea.addCaretListener(new CaesarTextListener());
        JScrollPane scrollText = new JScrollPane(originalTextArea);
        JPanel top = new JPanel();
        top.add(scrollText);

        //YOU THOUGHT THAT WAS BAD??
        //WELCOME TO THE SECOND LEVEL OF THE INFERNO: BOX LAYOUTS
        standardComp = new GraphComp(40, 50, 395, 200, "Standard " + 
                Crypter.LANGUAGES[Properties.getLanguage()] + " Letter Frequency", 
                Crypter.getRelativeFrequency(Properties.getLanguage()));
        messageComp = new GraphComp(15, 50, 395, 200, "Letter Frequency of Text", "");
        JPanel graphPane = new JPanel();
        graphPane.setLayout(new BoxLayout(graphPane, BoxLayout.LINE_AXIS));
        graphPane.add(standardComp);
        graphPane.add(Box.createRigidArea(new Dimension(5, 0)));
        graphPane.add(messageComp);

        //Congrats, you survived.  A+.
        setJMenuBar(new MenuBar(originalTextArea));
        add(top, BorderLayout.NORTH);
        add(bottom, BorderLayout.SOUTH);
        add(graphPane);
    }
    
    @Override
    public void refresh() {
        standardComp.setTitle("Standard " + Crypter.LANGUAGES[Properties.getLanguage()] + " Letter Frequency");
        standardComp.setFrequency(Crypter.getRelativeFrequency(Properties.getLanguage()));
        repaint();
    }
    
    class CaesarTextListener implements CaretListener {
        @Override
        public void caretUpdate(CaretEvent e) {
            String text = originalTextArea.getText();
            messageComp.setMessage(text);
            repaint();
        }        
    }

    class GraphGuiListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == leftShift) {
                messageComp.shift(-1);
                originalTextArea.setText(Crypter.shift(originalTextArea.getText(), -1));
                repaint();
            }else if (e.getSource() == rightShift) {
                messageComp.shift(1);
                originalTextArea.setText(Crypter.shift(originalTextArea.getText(), 1));
                repaint();
            }
        }//end of method
    }//end of class
}
