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

    private double[] standardFrequency = {0.64290624, 0.11745023999999998, 0.21899904, 0.33479616, 0.99990144, 0.17538816000000002,
        0.1586208, 0.47971968000000004, 0.54836352, 0.01204416, 0.06077184, 0.316848, 0.18940032, 0.53128128, 0.59095104, 0.15185088,
        0.0074784, 0.47129664, 0.49806143999999997, 0.71288832, 0.21710975999999998, 0.07698816, 0.18585792, 0.011807999999999999,
        0.15539328, 0.0058252799999999995};

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
        standardComp = new GraphComp(40, 50, 395, 200, "Standard English Letter Frequency", standardFrequency);
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
