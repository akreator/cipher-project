package CipherGui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PropertiesGui extends GUI {

    private MyFrame openFrame;
    private String[] language = {"Select", "English", "Latin", "French", "Spanish"};
    private String[] componentNames = {"Window", "Labels", "Text", "Text background", "Buttons"};
    private JComboBox languageChoice;
    private final int WINDOW = 0;
    private final int TEXT = 2;
    private final int BACKGROUND = 3;
    private final int LABEL = 1;
    private final int BUTTONS = 4;
    private JColorChooser colorChooser;
    private int selectedComponent = 0;
    private Color selectedColor = Properties.getWindowColor();

    public PropertiesGui(MyFrame f) {
        super(300, 500, "Properties");
        init();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        openFrame = f;
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

        preferencesPane.add(new JLabel("Set color of..."));
        JPanel optionPane = new JPanel();
        optionPane.setLayout(new BoxLayout(optionPane, BoxLayout.LINE_AXIS));
        ButtonGroup componentSelection = new ButtonGroup();
        for (int i = 0; i < componentNames.length; i++) {
            JRadioButton b = new JRadioButton(componentNames[i]);
            if (selectedComponent == i) {
                b.setSelected(true);
            } else {
            }
            b.setActionCommand("" + i);
            componentSelection.add(b);
            optionPane.add(b);
        }
        preferencesPane.add(optionPane);

        colorChooser = new JColorChooser(selectedColor);
        colorChooser.getSelectionModel().addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                Properties.setButtonColor(colorChooser.getColor());
                Properties.updateFrame(frame);
            }
            
        });
        preferencesPane.add(colorChooser);

        frame.add(preferencesPane);
        frame.pack();
    }
    
    class PropertiesChangeListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            selectedColor = colorChooser.getSelectionModel().getSelectedColor();
            switch (selectedComponent) {
                case WINDOW:
                    Properties.setWindowColor(selectedColor);
                    break;
                case TEXT:
                    Properties.setTextColor(selectedColor);
                    break;
                case BACKGROUND:
                    Properties.setTextBackgroundColor(selectedColor);
                    break;
                case LABEL:
                    Properties.setLabelColor(selectedColor);
                    break;
                case BUTTONS:
                    Properties.setButtonColor(selectedColor);
                    break;
            }
            Properties.updateFrame(openFrame);
            Properties.updateFrame(frame);
        }
        
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
            } else {
                System.out.println(e.getActionCommand());
                selectedComponent = Integer.parseInt(e.getActionCommand().trim());
            }
        }
    } //end of inner class
}
