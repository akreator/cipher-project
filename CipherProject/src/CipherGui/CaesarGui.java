package CipherGui;

import Templates.MenuBar;
import Templates.MyGUI;
import Templates.MyTextArea;
import TextTools.Crypter;
import javax.swing.*;
import java.awt.Toolkit;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CaesarGui extends MyGUI {

    private JButton rightShift, leftShift, enter;
    private GraphComp messageComp, standardComp;
    private JLabel shiftLabel = new JLabel("Shift: " + 0);

    public CaesarGui() {
        super(945, 500, "Casesar Cipher");
        init();
        frame.setVisible(true);
    }

    private void init() {
        //WELCOME TO BUTTON HELL
        rightShift = new JButton("Shift right");
        rightShift.addActionListener(new GraphGuiListener());
        leftShift = new JButton("Shift left");
        leftShift.addActionListener(new GraphGuiListener());
        enter = new JButton("Enter");
        enter.addActionListener(new GraphGuiListener());
        
        //prevent carrying over errors
        newTextArea = new MyTextArea(5, 4, false, true);
        newTextArea.setText("");

        JPanel bottom = new JPanel();
        bottom.add(leftShift);
        bottom.add(rightShift);

        //And now we jump to the north panel.  Because organization.
        originalTextArea = new MyTextArea(5, 75, true, false);
        JScrollPane scrollText = new JScrollPane(originalTextArea);
        JPanel top = new JPanel();
        top.add(scrollText);
        JPanel organizeTop = new JPanel();
        organizeTop.setLayout(new BoxLayout(organizeTop, BoxLayout.PAGE_AXIS));
        organizeTop.add(enter);
        organizeTop.add(Box.createRigidArea(new Dimension(0, 15)));
        organizeTop.add(shiftLabel);
        top.add(organizeTop);

        //YOU THOUGHT THAT WAS BAD??
        //WELCOME TO THE SECOND LEVEL OF THE INFERNO: BOX LAYOUTS
        standardComp = new GraphComp(40, 50, 395, 200, "Standard English Letter Frequency", Crypter.standardRelativeFrequency);
        messageComp = new GraphComp(15, 50, 395, 200, "Letter Frequency of Text", "");
        JPanel graphPane = new JPanel();
        graphPane.setLayout(new BoxLayout(graphPane, BoxLayout.LINE_AXIS));
        graphPane.add(standardComp);
        graphPane.add(Box.createRigidArea(new Dimension(5, 0)));
        graphPane.add(messageComp);

        //Congrats, you survived.  A+.
        frame.setJMenuBar(new MenuBar(frame, originalTextArea));
        frame.add(top, BorderLayout.NORTH);
        frame.add(bottom, BorderLayout.SOUTH);
        frame.add(graphPane);
    }

    class GraphGuiListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == leftShift) {
                messageComp.shift(-1);
                originalTextArea.setText(Crypter.shift(originalTextArea.getText(), -1));
                shiftLabel.setText("Shift: " + messageComp.getShift());
                frame.repaint();
            }
            if (e.getSource() == rightShift) {
                messageComp.shift(1);
                originalTextArea.setText(Crypter.shift(originalTextArea.getText(), 1));
                shiftLabel.setText("Shift: " + messageComp.getShift());
                frame.repaint();
            }
            if (e.getSource() == enter) {
                String text = originalTextArea.getText();
                messageComp.setMessage(text);
                shiftLabel.setText("Shift: " + messageComp.getShift());
                frame.repaint();
            }
        }//end of method
    }//end of class
}
