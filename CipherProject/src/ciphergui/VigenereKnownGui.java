package ciphergui;

import other.*;
import texttools.Crypter;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VigenereKnownGui extends MyGUI {

    private static JTextField keywordField = new JTextField("keyword", 20);;
    private JButton encipherButton, decryptButton;

    public VigenereKnownGui() {
        super(750, 500, "Vignere Cipher");
        init();
        setVisible(true);
    }
    
    private void init() {
        //create items, add actionListeners
        originalTextArea = new MyTextArea(5, 50, true, false);
        JScrollPane scrollText = new JScrollPane(originalTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        newTextArea = new MyTextArea(5, 50, false, true);
        JScrollPane nscrollText = new JScrollPane(newTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        encipherButton = new JButton(" Encrypt text ");
        encipherButton.addActionListener(new VigenereListener());
        decryptButton = new JButton(" Decrypt text ");
        decryptButton.addActionListener(new VigenereListener());

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
        northPane.add(Box.createRigidArea(new Dimension(275, 0)));
        northPane.add(new JLabel("Changed text:"));

        //finally, add everything to the panel
        setJMenuBar(new MenuBar(originalTextArea));
        add(northPane, BorderLayout.NORTH);
        add(centerPane);
        add(southPane, BorderLayout.SOUTH);
    }
    
    public static String getKeyword() {
        return keywordField.getText();
    }
    
    public static void setKeyword(String kw) {
        keywordField.setText(kw);
    }
    
    @Override
    public void refresh() {
        repaint();
    }

    class VigenereListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == decryptButton) {
                newTextArea.setText(Crypter.vigenereCrypt(originalTextArea.getText(), keywordField.getText(), false));
            } else if (e.getSource() == encipherButton) {
                newTextArea.setText(Crypter.vigenereCrypt(originalTextArea.getText(), keywordField.getText(), true));
            }
        }
    }
}
