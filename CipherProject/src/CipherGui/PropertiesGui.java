package CipherGui;

import Templates.Properties;
import Templates.MyGUI;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class PropertiesGui extends MyGUI {

    private String[] language = {"Select", "English", "Latin", "French", "Spanish"};
    private JButton next;
    private JComboBox languageChoice;
    private static int selectedButton;


    public PropertiesGui() {
        super(300, 500, "Properties");
        init();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    private void init() {
        JPanel preferencesPane = new JPanel();
        preferencesPane.setLayout(new BoxLayout(preferencesPane, BoxLayout.PAGE_AXIS));

        languageChoice = new JComboBox(language);
        languageChoice.addActionListener(new PropertiesActionListener());
        JPanel languagePane = new JPanel();
        languagePane.add(new JLabel("Select a language:"));
        languagePane.add(languageChoice);
        preferencesPane.add(languagePane);
        preferencesPane.add(new JSeparator());

        preferencesPane.add(new JLabel("Change theme to:"));
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
                switch (languageChoice.getSelectedIndex()) {
                    case 1:
                        Properties.setLanguage(language[1]);
                        break;
                    case 2:
                        Properties.setLanguage(language[2]);
                        break;
                    case 3:
                        Properties.setLanguage(language[3]);
                        break;
                    case 4:
                        Properties.setLanguage(language[4]);
                        break;
                }
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