package CipherGui;

import Templates.MenuBar;
import Templates.MyGUI;
import Templates.MyTextArea;
import TextTools.Calculations;
import TextTools.Crypter;
import TextTools.TextFormatter;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

public class BoxCipherGui extends MyGUI {

    private JButton generateSides, createBox, readText;
    private JTextField row, col;
    private MyTextArea sideCombos, boxArea;
    private static boolean topToBottom = true, leftToRight = true, readTopToBottom = true, readLeftToRight = true;
    private static int boxOrientation = Crypter.HORIZONTAL, readOrientation = Crypter.HORIZONTAL;
    private char[][] box = new char[0][0];
    
    public BoxCipherGui() {
        super(800, 580, "Transposition Cipher: Box");
        init();
        frame.setVisible(true);
    }

    public void init() {
        //OVERALL TOP:
        //INCLUDES ORIGINALTEXT, POSSIBLE SIDE COMBOS, AND BOX SETTINGS
        JPanel topPane = new JPanel();
        topPane.setLayout(new BoxLayout(topPane, BoxLayout.LINE_AXIS));
        topPane.add(new JLabel("Plaintext:"));
        topPane.add(Box.createRigidArea(new Dimension(5, 0)));
        originalTextArea = new MyTextArea(2, 15, true, false);
        JScrollPane oSPane = new JScrollPane(originalTextArea);
        topPane.add(oSPane);
        
        //side combos
        JPanel sidePane = new JPanel();
        sidePane.setLayout(new BoxLayout(sidePane, BoxLayout.PAGE_AXIS));
        sidePane.add(new JLabel("Possible side lengths:\n  (rows, columns)"));
        sideCombos = new MyTextArea(3, 3, false, true);
        JScrollPane sCPane = new JScrollPane(sideCombos);
        sidePane.add(sCPane);
        generateSides = new JButton("Generate possible side lengths");
        generateSides.addActionListener(new BoxActionListener());
        sidePane.add(generateSides);

        //box settings
        JPanel rcPane = new JPanel();
        rcPane.setLayout(new BoxLayout(rcPane, BoxLayout.PAGE_AXIS));
        rcPane.add(new JLabel("Make a box:"));
        //rows and columns
        JPanel rowPane = new JPanel();
        rowPane.add(new JLabel("Rows:"));
        row = new JTextField(3);
        rowPane.add(row);
        rcPane.add(rowPane);
        JPanel colPane = new JPanel();
        colPane.add(new JLabel("Columns:"));
        col = new JTextField(3);
        colPane.add(col);
        rcPane.add(colPane);        
        //directions
        String[][] names = {
            {"Horizontal", "Vertical", "Diagonal"},
            {"Left to Right", "Right to Left"},
            {"Top to Bottom", "Bottom to Top"}
        };
        String[][] actionCommands = {
            { "5", "6", "7" },
            { "1", "2" },
            { "3", "4" }
        };
        String[] labels = { "Orientation:", "Horizontal direction:", "Vertical direction:" };   
        int[] selections = { boxOrientation, truthToNum(leftToRight), truthToNum(topToBottom) };
        JPanel boxSettingsPane = new JPanel();
        boxSettingsPane.setLayout(new BoxLayout(boxSettingsPane, BoxLayout.LINE_AXIS));
        boxSettingsPane.add(sidePane);
        boxSettingsPane.add(rcPane);
        boxSettingsPane.add(createSettingsPane(labels, names, actionCommands, false, selections));
        
        //BoxPane
        JPanel bottomPane = new JPanel();
        bottomPane.setLayout(new BoxLayout(bottomPane, BoxLayout.LINE_AXIS));
        JPanel boxPane = new JPanel();
        boxPane.setLayout(new BoxLayout(boxPane, BoxLayout.PAGE_AXIS));
        boxPane.add(new JLabel("Box:"));
        boxArea = new MyTextArea(10, 15, false, true);
        boxArea.setLineWrap(false);
        JScrollPane bPane = new JScrollPane(boxArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        boxPane.add(bPane);
        bottomPane.add(boxPane);
        //readingsPane: contains settings for how the text is read, and the 
        //reading itself
        JPanel readingsPane = new JPanel();
        readingsPane.setLayout(new BoxLayout(readingsPane, BoxLayout.PAGE_AXIS));
        String[][] rActionCommands = {
            {"8", "9", "10"},
            {"11", "12"},
            {"13", "14"}
        };
        int[] rSelections = { readOrientation, truthToNum(readLeftToRight), truthToNum(readTopToBottom)};
        readingsPane.add(createSettingsPane(labels, names, rActionCommands, false, rSelections));
        readText = new JButton("Read text");
        readText.addActionListener(new BoxActionListener());
        readingsPane.add(readText);
        readingsPane.add(new JLabel("Ciphertext:"));
        newTextArea = new MyTextArea(2, 8, false, true);
        JScrollPane scrollPane = new JScrollPane(newTextArea);
        readingsPane.add(scrollPane);
        bottomPane.add(Box.createRigidArea(new Dimension(10, 0)));
        bottomPane.add(readingsPane);

        //MIDDLEPANE:
        //CONTAINS E V E R Y T H I N G
        //places topPane above boxPane
        JPanel middlePane = new JPanel();
        middlePane.setLayout(new BoxLayout(middlePane, BoxLayout.PAGE_AXIS));
        middlePane.add(topPane);
        middlePane.add(Box.createRigidArea(new Dimension(0, 10)));
        middlePane.add(boxSettingsPane);
        middlePane.add(Box.createRigidArea(new Dimension(0, 10)));
        middlePane.add(new JSeparator());
        createBox = new JButton("Create box");
        createBox.addActionListener(new BoxActionListener());
        middlePane.add(createBox);
        middlePane.add(new JSeparator());
        middlePane.add(Box.createRigidArea(new Dimension(0, 20)));
        middlePane.add(bottomPane);

        frame.add(middlePane);
        frame.add(Box.createRigidArea(new Dimension(0, 15)), BorderLayout.SOUTH);
        frame.add(Box.createRigidArea(new Dimension(0, 10)), BorderLayout.NORTH);
        frame.add(Box.createRigidArea(new Dimension(15, 0)), BorderLayout.WEST);
        frame.add(Box.createRigidArea(new Dimension(15, 0)), BorderLayout.EAST);
        frame.setJMenuBar(new MenuBar(frame, originalTextArea));
    }
    
    private JPanel createJRadioButtonGroup(String[] names, String[] actionCommands, int selected) {
        JPanel bP = new JPanel();
        bP.setLayout(new BoxLayout(bP, BoxLayout.PAGE_AXIS));
        ButtonGroup bg = new ButtonGroup();
        for (int i = 0; i < names.length; i++) {
            JRadioButton b = new JRadioButton(names[i]);
            if(i == selected) 
                b.setSelected(true);
            b.addActionListener(new BoxActionListener());
            b.setActionCommand(actionCommands[i]);
            bg.add(b);
            bP.add(b);
        }
        return bP;
    }
    
    private JPanel createSettingsPane(String[] labels, String[][] names, String[][] actionCommands, boolean vertical, int[] selections) {
        JPanel settingsPane = new JPanel();
        if (vertical)
            settingsPane.setLayout(new BoxLayout(settingsPane, BoxLayout.PAGE_AXIS));
        else 
            settingsPane.setLayout(new BoxLayout(settingsPane, BoxLayout.LINE_AXIS));
        for (int i = 0; i < names.length; i++) {
            JPanel pane = new JPanel();
            pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
            pane.add(new JLabel(labels[i]));
            pane.add(createJRadioButtonGroup(names[i], actionCommands[i], selections[i]));
            settingsPane.add(pane);
            settingsPane.add(Box.createRigidArea(new Dimension(10, 10)));
        }
        return settingsPane;
    }
    
    public static boolean[] getSettings() {
        boolean[] settings = { topToBottom, leftToRight, readTopToBottom, readLeftToRight};
        return settings;
    }
    
    public static int[] getOrientation() {
        int[] orientations = { boxOrientation, readOrientation };
        return orientations;
    }
    
    public static void setOrientation(int[] n) {
        boxOrientation = n[0];
        readOrientation = n[1];
    }
    
    public static void setSettings(boolean[] settings) {
        topToBottom = settings[0];
        leftToRight = settings[1];
        readTopToBottom = settings[2];
        readLeftToRight = settings[3];
    }
    
    public int truthToNum(boolean truth) {
        if (truth)
            return 0;
        else
            return 1;
    }

    class BoxActionListener implements ActionListener {   
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().getClass().equals(JRadioButton.class)) {
                int n = Integer.parseInt(e.getActionCommand());
                if (n < 3) {
                    leftToRight = n == 1;
                } else if (n < 5) {
                    topToBottom = n == 3;
                } else if (n < 8) {
                    int[] tations = {Crypter.HORIZONTAL, Crypter.VERTICAL, Crypter.DIAGONAL};
                    boxOrientation = tations[n - 5];
                } else if (n < 11) {
                    int[] tations = {Crypter.HORIZONTAL, Crypter.VERTICAL, Crypter.DIAGONAL};
                    readOrientation = tations[n - 8];
                } else if (n < 13) {
                    readLeftToRight = n == 11;
                } else if (n < 15) {
                    readTopToBottom = n == 13;
                }
            }else if (e.getSource().equals(generateSides)) {
                sideCombos.setText("");
                int length = originalTextArea.getText().replaceAll("\\s", "").length();
                ArrayList<Integer> factors = Calculations.getFactors(length);
                StringBuffer sides = new StringBuffer();
                if (factors.size() <= 1) {
                    sides.append("Length is prime.  Try adding in nulls.");
                } else {
                        factors.add(0, 1);
                        for (int i = 0; i < factors.size() / 2; i++) {
                            sides.append(factors.get(i) + ", " + factors.get(factors.size() - 1 - i) + "\n");
                        }
                        if (factors.size() % 2 != 0) {
                            sides.append(factors.get(factors.size() / 2) + ", " + factors.get(factors.size() / 2));
                        }
                }
                sideCombos.setText(sides.toString().trim());
            } else if (e.getSource().equals(createBox)) {
                int rows = 0;
                int cols = 0;
                try {
                    rows = Integer.parseInt(row.getText().trim());
                    cols = Integer.parseInt(col.getText().trim());
                } catch (NumberFormatException n) {
                    sideCombos.setText("Please enter a valid row and column number.");
                }
                if (rows != 0 && cols != 0) {
                    box = Crypter.createBox(originalTextArea.getText(), rows, cols, boxOrientation, leftToRight, topToBottom);
                    String extra = " ";
                    if (box.length * box[0].length < originalTextArea.getText().replaceAll("\\s", "").length()) {
                        extra = originalTextArea.getText().replaceAll("\\s", "").substring(box.length * box[0].length);
                        extra = TextFormatter.formatText(extra, TextFormatter.SPECIAL_FORMAT);
                    }
                    StringBuffer textInBox = new StringBuffer();
                    for (int r = 0; r < box.length; r++) {
                        for (int c = 0; c < box[r].length; c++) {
                            textInBox.append(box[r][c]).append(" ");
                        }
                        textInBox.append("\n");
                    }
                    textInBox.append("\n").append(extra);
                    boxArea.setText(textInBox.toString());
                } else {
                    sideCombos.setText("Please enter a valid row and column number.");
                }
            } else if (e.getSource().equals(readText)) {
                if (box.length > 0) 
                    newTextArea.setText(TextFormatter.formatText(Crypter.readBoxArray(box, readOrientation, readLeftToRight, readTopToBottom), 
                            TextFormatter.SPECIAL_FORMAT));
                else
                    newTextArea.setText("Create a box first.");
            } //end of if-statements
        } //end of method
    } //end of inner class
} //end of class
