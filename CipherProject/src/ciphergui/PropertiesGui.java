package ciphergui;

import other.Properties;
import texttools.Crypter;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class PropertiesGui {

    private JButton next;
    private JComboBox languageChoice;
    private static int selectedButton;
    private JFrame frame;

    public PropertiesGui() {
        frame = new JFrame("Properties");
        frame.setSize(300, 500);
        init();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private void init() {
        JPanel preferencesPane = new JPanel();
        preferencesPane.setLayout(new BoxLayout(preferencesPane, BoxLayout.PAGE_AXIS));

        languageChoice = new JComboBox(Crypter.LANGUAGES);
        languageChoice.addActionListener(new PropertiesActionListener());
        JPanel languagePane = new JPanel();
        languagePane.add(new JLabel("Select a language:"));
        languagePane.add(languageChoice);
        preferencesPane.add(languagePane);
        preferencesPane.add(new JSeparator());

        JLabel themeLabel = new JLabel("Change theme to:");
        themeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        preferencesPane.add(themeLabel);
        JPanel optionPane = new JPanel();
        optionPane.setLayout(new BoxLayout(optionPane, BoxLayout.LINE_AXIS));
        ButtonGroup componentSelection = new ButtonGroup();
        for (int i = 0; i < Properties.themeNames.length; i++) {
            JRadioButton b = new JRadioButton(Properties.themeNames[i]);
            if (selectedButton == i) {
                b.setSelected(true);
            }
            b.addActionListener(new PropertiesActionListener());
            b.setActionCommand("" + i);
            componentSelection.add(b);
            optionPane.add(b);
        }
        preferencesPane.add(optionPane);
        preferencesPane.add(Box.createRigidArea(new Dimension(0, 10)));

        next = new JButton("Continue");
        next.addActionListener(new PropertiesActionListener());

        frame.add(preferencesPane, BorderLayout.CENTER);
        frame.add(Box.createRigidArea(new Dimension(10, 0)), BorderLayout.WEST);
        frame.add(Box.createRigidArea(new Dimension(10, 0)), BorderLayout.EAST);
        frame.add(next, BorderLayout.SOUTH);
        frame.pack();
    }

    class PropertiesActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == languageChoice) {
                Properties.setLanguage(languageChoice.getSelectedIndex());
            } else if (e.getSource().equals(next)) {
                new CaesarGui();
                frame.setVisible(false);
                frame.dispose();
            } else {
                try {
                    selectedButton = Integer.parseInt(e.getActionCommand().trim());
                    Properties.setTheme(selectedButton);
                    SwingUtilities.updateComponentTreeUI(frame);
                } catch (NumberFormatException n) {
                    System.out.println("not a number");
                }
            }
        }
    } //end of inner class
}
