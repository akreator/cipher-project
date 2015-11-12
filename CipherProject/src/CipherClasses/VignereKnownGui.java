package CipherClasses;

import javax.swing.*;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VignereKnownGui{
  
  private JFrame frame = new JFrame();
  private JTextField keywordField;
  private JTextArea originalTextArea, newTextArea;
  private JButton encipherButton, decryptButton;  
  
  public VignereKnownGui () {
    frame.setTitle("Vignere Cipher");
    frame.setSize(900, 300);
    frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
    
    //create items, add actionListeners
    originalTextArea = new MyTextArea("Enter text here.", 7, 25, true, 14);
    JScrollPane scrollText = new JScrollPane(originalTextArea);
    newTextArea = new MyTextArea("", 7, 25, false, 14);
    JScrollPane nscrollText = new JScrollPane(newTextArea);
    keywordField = new JTextField("keyword", 20);
    encipherButton = new JButton(" Encrypt text ");
    encipherButton.addActionListener(new VignereListener());
    decryptButton = new JButton (" Decrypt text ");
    decryptButton.addActionListener(new VignereListener());
    
    //Southpane: keyword, and encrypt/decrypt buttons
    JPanel southPane = new JPanel();
    JPanel buttonPane = new JPanel();
    buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.PAGE_AXIS));
    buttonPane.add(encipherButton);
    buttonPane.add(Box.createRigidArea(new Dimension(0, 10)));
    buttonPane.add(decryptButton);
    southPane.add(keywordField);
    southPane.add(buttonPane);
    
    //centerpane
    JPanel centerPane = new JPanel();
    centerPane.setLayout(new BoxLayout(centerPane, BoxLayout.LINE_AXIS));
    centerPane.add(Box.createRigidArea(new Dimension(20, 0)));
    centerPane.add(scrollText);
    centerPane.add(Box.createRigidArea(new Dimension(30, 0)));
    centerPane.add(nscrollText);
    centerPane.add(Box.createRigidArea(new Dimension(20, 0)));
    
    JPanel northPane = new JPanel();
    northPane.add(new JLabel("Original text:"));
    northPane.add(Box.createRigidArea(new Dimension(400, 0)));
    northPane.add(new JLabel("Changed text:"));
    
    //finally, add everything to the panel
    frame.add(northPane, BorderLayout.NORTH);
    frame.add(centerPane);
    frame.add(southPane, BorderLayout.SOUTH);
    
    
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
    frame.setJMenuBar(new MenuBar(frame, originalTextArea));
    frame.setVisible(true);
  }
  
  class VignereListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      if(e.getSource() == decryptButton)       
        newTextArea.setText(Text.vigenereCrypt(originalTextArea.getText(), keywordField.getText(), false));
      else if (e.getSource() == encipherButton)     
        newTextArea.setText(Text.vigenereCrypt(originalTextArea.getText(), keywordField.getText(), true));
    }
  }
}
