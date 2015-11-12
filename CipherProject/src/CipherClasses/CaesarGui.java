package CipherClasses;

import javax.swing.*;
import java.awt.Toolkit;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CaesarGui {

    private JFrame frame = new JFrame();

    private JButton rightShift, leftShift, enter; 
    private GraphComp messageComp, standardComp;
    private JTextArea textArea;
    private JLabel shiftLabel = new JLabel("Shift: " + 0);


    /**
     * Constructor that sets up the window
     *
     */
    public CaesarGui() {
        frame.setTitle("Caesar Cipher");
        frame.setSize(945, 500);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        
        init();

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
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

        JPanel bottom = new JPanel();
        bottom.add(leftShift);
        bottom.add(rightShift);

        //And now we jump to the north panel.  Because organization.
        textArea = new MyTextArea("Enter text here.", 5, 75, true, 14);
        JScrollPane scrollText = new JScrollPane(textArea);
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
        standardComp = new GraphComp(40, 50, 395, 200, "Standard English Letter Frequency", Text.standardRelativeFrequency);
        messageComp = new GraphComp(15, 50, 395, 200, "Letter Frequency of Text", "");
        JPanel graphPane = new JPanel();
        graphPane.setLayout(new BoxLayout(graphPane, BoxLayout.LINE_AXIS));
        graphPane.add(standardComp);
        graphPane.add(Box.createRigidArea(new Dimension(5, 0)));
        graphPane.add(messageComp);

        //Congrats, you survived.  A+.
        frame.setJMenuBar(new MenuBar(frame, textArea));
        frame.add(top, BorderLayout.NORTH);
        frame.add(bottom, BorderLayout.SOUTH);
        frame.add(graphPane);
    }
   
    class GraphGuiListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == leftShift) {
                messageComp.shift(-1);
                textArea.setText(Text.shift(textArea.getText(), -1));
                shiftLabel.setText("Shift: " + messageComp.getShift());
                frame.repaint();
            }
            if (e.getSource() == rightShift) {
                messageComp.shift(1);
                textArea.setText(Text.shift(textArea.getText(), 1));
                shiftLabel.setText("Shift: " + messageComp.getShift());
                frame.repaint();
            }
            if (e.getSource() == enter) {
                String text = textArea.getText();
                messageComp.setMessage(text);
                shiftLabel.setText("Shift: " + messageComp.getShift());
                frame.repaint();
            }
        }//end of method
    }//end of class
}
