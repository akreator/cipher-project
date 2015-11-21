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

    JButton generateSides, createBox;
    JTextField row, col;
    MyTextArea sideCombos;

    public BoxCipherGui() {
        super(500, 500, "Transposition Cipher: Box");
        init();
        frame.setVisible(true);
    }

    public void init() {
        JPanel middlePane = new JPanel();
        middlePane.setLayout(new BoxLayout(middlePane, BoxLayout.PAGE_AXIS));
        originalTextArea = new MyTextArea(15, 15, true, false);
        JScrollPane oSPane = new JScrollPane(originalTextArea);
        middlePane.add(oSPane);

        JPanel sidePane = new JPanel();
        sidePane.setLayout(new BoxLayout(sidePane, BoxLayout.PAGE_AXIS));
        sidePane.add(new JLabel("Possible side lengths:\n  (rows, columns)"));
        sideCombos = new MyTextArea(4, 7, false, true);
        JScrollPane sCPane = new JScrollPane(sideCombos);
        sidePane.add(sCPane);
        generateSides = new JButton("Generate possible side lengths");
        generateSides.addActionListener(new BoxActionListener());
        sidePane.add(generateSides);

        JPanel boxPane = new JPanel();
        boxPane.setLayout(new BoxLayout(boxPane, BoxLayout.PAGE_AXIS));
        boxPane.add(new JLabel("Make a box:"));
        JPanel rowPane = new JPanel();
        rowPane.add(new JLabel("Rows:"));
        row = new JTextField(3);
        rowPane.add(row);
        boxPane.add(rowPane);
        JPanel colPane = new JPanel();
        colPane.add(new JLabel("Columns:"));
        col = new JTextField(3);
        colPane.add(col);
        boxPane.add(colPane);
        createBox = new JButton("Create box");
        createBox.addActionListener(new BoxActionListener());
        boxPane.add(createBox);

        JPanel bottomPane = new JPanel();
        bottomPane.setLayout(new BoxLayout(bottomPane, BoxLayout.LINE_AXIS));
        bottomPane.add(sidePane);
        bottomPane.add(boxPane);
        middlePane.add(bottomPane);

        frame.add(middlePane);
        frame.add(Box.createRigidArea(new Dimension(0, 15)), BorderLayout.SOUTH);
        frame.add(Box.createRigidArea(new Dimension(0, 10)), BorderLayout.NORTH);
        frame.add(Box.createRigidArea(new Dimension(15, 0)), BorderLayout.WEST);
        frame.add(Box.createRigidArea(new Dimension(15, 0)), BorderLayout.EAST);
        frame.setJMenuBar(new MenuBar(frame, originalTextArea));
    }

    class BoxActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(generateSides)) {
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
                }
                sideCombos.setText(sides.toString());
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
                    originalTextArea.setText(Crypter.createBox(rows, cols, originalTextArea.getText()));
                } else {
                    sideCombos.setText("Please enter a valid row and column number.");
                }
            }
        }
    }
}
