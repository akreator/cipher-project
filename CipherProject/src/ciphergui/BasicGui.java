package ciphergui;

import other.MenuBar;
import other.MyTextArea;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class BasicGui extends MyGUI {

    private JButton encipherButton, decryptButton;
    private String title;

    public BasicGui(int width, int height, String name) {
        super(width, height, name);
        title = name;
        init();
        setVisible(true);
    }

    private void init() {
        //create items, add actionListeners
        originalTextArea = new MyTextArea(5, 25, true, false);
        JScrollPane scrollText = new JScrollPane(originalTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        newTextArea = new MyTextArea(5, 25, false, true);
        add(newTextArea);
        JScrollPane nscrollText = new JScrollPane(newTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        encipherButton = new JButton(" To " + title + " ");
        encipherButton.addActionListener(new BasicListener());
        encipherButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        decryptButton = new JButton(" From " + title + " ");
        decryptButton.addActionListener(new BasicListener());
        decryptButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        //middlePane: encrypt/decrypt buttons
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.PAGE_AXIS));
        buttonPane.add(encipherButton);
        buttonPane.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPane.add(decryptButton);

        //centerpane
        JPanel centerPane = new JPanel();
        centerPane.setLayout(new BoxLayout(centerPane, BoxLayout.LINE_AXIS));
        centerPane.add(Box.createRigidArea(new Dimension(20, 0)));
        centerPane.add(scrollText);
        centerPane.add(Box.createRigidArea(new Dimension(5, 0)));
        centerPane.add(buttonPane);
        centerPane.add(Box.createRigidArea(new Dimension(5, 0)));
        centerPane.add(nscrollText);
        centerPane.add(Box.createRigidArea(new Dimension(20, 0)));

        JPanel northPane = new JPanel();
        northPane.add(new JLabel("Original text:"));
        northPane.add(Box.createRigidArea(new Dimension(350, 0)));
        northPane.add(new JLabel("Changed text:"));

        //finally, add everything to the panel
        add(northPane, BorderLayout.NORTH);
        add(centerPane);
        add(Box.createRigidArea(new Dimension(0, 15)), BorderLayout.SOUTH);
        setJMenuBar(new MenuBar(originalTextArea));
    }

    @Override
    public void refresh() {
        //do nothing
    }
    
    public abstract String encryptText();
    
    public abstract String decryptText();

    class BasicListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == decryptButton) {
                newTextArea.setText(decryptText());
            } else if (e.getSource() == encipherButton) {
                newTextArea.setText(encryptText());
            }
        }
    }
}
