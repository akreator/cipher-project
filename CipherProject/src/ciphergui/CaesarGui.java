package ciphergui;

import other.*;
import texttools.Crypter;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

public class CaesarGui extends MyGUI {

    private JButton rightShift, leftShift, reset;
    private GraphComp messageComp, standardComp;
    private JLabel shiftLabel;
    private int shiftAmt = 0;

    public CaesarGui() {
        super(945, 500, "Casesar Cipher");
        init();
        setVisible(true);
    }

    private void init() {
        //WELCOME TO BUTTON HELL 
        //what's funny is that when I first started this 2 buttons WAS button hell
        rightShift = new JButton("Shift right");
        rightShift.addActionListener(new GraphGuiListener());
        leftShift = new JButton("Shift left");
        leftShift.addActionListener(new GraphGuiListener());
        reset = new JButton("Reset Shift Count");
        reset.addActionListener(new GraphGuiListener());
        
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
        shiftLabel = new JLabel("Shift Amount: " + shiftAmt);
        JPanel shiftPane = new JPanel();
        shiftPane.setLayout(new BoxLayout(shiftPane, BoxLayout.PAGE_AXIS));
        shiftPane.add(shiftLabel);
        shiftPane.add(Box.createRigidArea(new Dimension(0, 5)));
        shiftPane.add(reset);
        JPanel top = new JPanel();
        top.add(scrollText);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(shiftPane);
        

        //YOU THOUGHT THAT WAS BAD??
        //WELCOME TO THE SECOND LEVEL OF THE INFERNO: BOX LAYOUTS
        standardComp = new GraphComp(40, 50, 395, 200, "Standard " + 
                Crypter.LANGUAGES[Properties.getLanguage()] + " Letter Frequency", 
                Crypter.getRelativeFrequency(Properties.getLanguage()));
        messageComp = new GraphComp(15, 50, 395, 200, "Letter Frequency of Text", originalTextArea.getText());
        JPanel graphPane = new JPanel();
        graphPane.setLayout(new BoxLayout(graphPane, BoxLayout.LINE_AXIS));
        graphPane.add(standardComp);
        graphPane.add(Box.createRigidArea(new Dimension(5, 0)));
        graphPane.add(messageComp);

        //Congrats, you survived.  A+. ((it gets so much worse later on i'm so sorry))
        setJMenuBar(new MenuBar(originalTextArea));
        add(top, BorderLayout.NORTH);
        add(bottom, BorderLayout.SOUTH);
        add(Box.createRigidArea(new Dimension(15, 0)), BorderLayout.WEST);
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
            messageComp.setMessage(originalTextArea.getText());
            repaint();
        }        
    }
    
    public void updateShiftLabel() {
        shiftLabel.setText("Shift Amount: " + shiftAmt);
    }

    class GraphGuiListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == leftShift) {
                messageComp.shift(-1);
                originalTextArea.setText(Crypter.shift(originalTextArea.getText(), -1));
                shiftAmt--;
                updateShiftLabel();
                repaint();
            } else if (e.getSource() == rightShift) {
                messageComp.shift(1);
                originalTextArea.setText(Crypter.shift(originalTextArea.getText(), 1));
                shiftAmt++;
                updateShiftLabel();
                repaint();
            } else if (e.getSource() == reset) {
                shiftAmt = 0;
                updateShiftLabel();
            }
        }//end of method
    }//end of class
}
