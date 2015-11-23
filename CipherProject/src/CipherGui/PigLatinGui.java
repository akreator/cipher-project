package CipherGui;

import Templates.MenuBar;
import Templates.MyGUI;
import Templates.MyTextArea;
import TextTools.Crypter;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PigLatinGui extends MyGUI{
  
  private JButton encipherButton, decryptButton;  
  
  public PigLatinGui () {
      super(900, 300, "Pig Latin");
      init();
      frame.setVisible(true);
  }
  
  private void init() {
    //create items, add actionListeners
    originalTextArea = new MyTextArea(7, 25, true, false);
    JScrollPane scrollText = new JScrollPane(originalTextArea);
    newTextArea = new MyTextArea(7, 25, false, true);
    frame.add(newTextArea);
    JScrollPane nscrollText = new JScrollPane(newTextArea);
    encipherButton = new JButton(" To Pig Latin ");
    encipherButton.addActionListener(new PigLatinListener());
    decryptButton = new JButton (" From Pig Latin ");
    decryptButton.addActionListener(new PigLatinListener());
    
    //Southpane: keyword, and encrypt/decrypt buttons
    JPanel southPane = new JPanel();
    JPanel buttonPane = new JPanel();
    buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.PAGE_AXIS));
    buttonPane.add(encipherButton);
    buttonPane.add(Box.createRigidArea(new Dimension(0, 10)));
    buttonPane.add(decryptButton);
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
    frame.setJMenuBar(new MenuBar(frame, originalTextArea));
  }
  
  class PigLatinListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      if(e.getSource() == decryptButton)       
        newTextArea.setText(Crypter.toPigLatin(originalTextArea.getText(), false));
      else if (e.getSource() == encipherButton)     
        newTextArea.setText(Crypter.toPigLatin(originalTextArea.getText(), true));
    }
  }
}
