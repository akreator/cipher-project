package CipherGui;

import Templates.*;
import TextTools.Crypter;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;


/**
 *
 * @author Audrey
 */
public class PatternFinderGui extends MyGUI {
    private JButton enter, knownLength;
    private ArrayList<History> patternHist = new ArrayList();
    private MyTextArea patternArea;
    private JComboBox possibleLengths;
    
    public PatternFinderGui() {
        super(700, 410, "Vignere Cipher: Deciphering Tool");
        init();
        frame.setVisible(true);
    }
    
    private void init() {
        //fix carry over errors
        newTextArea = new MyTextArea(5, 4, false, true);
        newTextArea.setText("");
        
        //left side
        JPanel leftPane = new JPanel();
        leftPane.setLayout(new BoxLayout(leftPane, BoxLayout.PAGE_AXIS));
        JLabel textLabel = new JLabel("Text:");
        textLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPane.add(textLabel);
        originalTextArea = new MyTextArea(12, 24, true, false);
        JScrollPane oPane = new JScrollPane(originalTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        leftPane.add(oPane);
        
        //pattern viewer
        JPanel patterns = new JPanel();
        patterns.setLayout(new BoxLayout(patterns, BoxLayout.PAGE_AXIS));
        patterns.add(new JLabel("Patterns:"));
        patternArea = new MyTextArea(4, 15, false, true);
        JScrollPane pPane = new JScrollPane(patternArea);
        pPane.setMaximumSize(new Dimension(400, 200));
        patterns.add(pPane);
        patterns.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        //pattern length
        JPanel middlePane = new JPanel();
        middlePane.setLayout(new BoxLayout(middlePane, BoxLayout.PAGE_AXIS));
        JPanel pLengthPane = new JPanel();
        pLengthPane.add(new JLabel("Pattern Length:"));
        Integer[] options = { 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20 };
        possibleLengths = new JComboBox(options);
        pLengthPane.add(possibleLengths);
        pLengthPane.setAlignmentY(Component.LEFT_ALIGNMENT);
        pLengthPane.setMaximumSize(new Dimension(250, 50));
        enter = new JButton("Search Text");
        enter.addActionListener(new PatternFinderListener());
        enter.setAlignmentX(Component.LEFT_ALIGNMENT);
        pLengthPane.add(enter);
        middlePane.add(pLengthPane);
        middlePane.add(Box.createRigidArea(new Dimension(0, 25)));
        knownLength = new JButton("Pattern Length Determined");
        knownLength.addActionListener(new PatternFinderListener());
        knownLength.setAlignmentX(Component.CENTER_ALIGNMENT);
        middlePane.add(knownLength);
        
        //panel managing right side of window
        JPanel rightPane = new JPanel();
        rightPane.setLayout(new BoxLayout(rightPane, BoxLayout.PAGE_AXIS));
        rightPane.add(patterns);
        rightPane.add(middlePane);
        rightPane.add(Box.createRigidArea(new Dimension(0, 25)));
              
        JPanel bigPane = new JPanel();
        bigPane.setLayout(new BoxLayout(bigPane, BoxLayout.LINE_AXIS));
        bigPane.add(leftPane);
        bigPane.add(Box.createRigidArea(new Dimension(15, 0)));
        bigPane.add(rightPane);
        
        frame.add(Box.createRigidArea(new Dimension(0, 15)), BorderLayout.SOUTH);
        frame.add(Box.createRigidArea(new Dimension(0, 5)), BorderLayout.NORTH);
        frame.add(Box.createRigidArea(new Dimension(15, 0)), BorderLayout.EAST);
        frame.add(Box.createRigidArea(new Dimension(15, 0)), BorderLayout.WEST);
        frame.add(bigPane);
        frame.setJMenuBar(new MenuBar(frame, originalTextArea));
    }

    public void checkHistory() {
        Arrays.sort(patternHist.toArray());
        //GUI
        StringBuffer histText = new StringBuffer();
        //Add everything to panel
        if (patternHist.size() == 0) { 
            histText = new StringBuffer("No patterns of that length found.");
        }
        for (History h : patternHist) {
            histText.append("*** TRIAL #" + (patternHist.indexOf(h) + 1) + " ***\n");
            histText.append(h.getStats());
        }
        patternArea.setText(histText.toString());
    }
    
    class PatternFinderListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == enter) {
                int n = possibleLengths.getSelectedIndex() + 2;
                ArrayList<String> patterns = Crypter.autoFindPatterns(n, originalTextArea.getText().replaceAll("\\s", ""));
                for (String p : patterns) {
                    patternHist.add(new History(p.substring(0, p.indexOf(",")), 
                            Integer.parseInt(p.substring(p.indexOf(",") + 1, p.lastIndexOf(","))), 
                            Integer.parseInt(p.substring(p.lastIndexOf(",") + 1))));
                }
                checkHistory();
                patternHist.clear();
            } else if (e.getSource() == knownLength) {
                int j = 0;
                boolean acceptNum = false;
                while (!acceptNum) {
                    String s = JOptionPane.showInputDialog(frame, "How long is the keyword? Enter a number.", "Enter keyword length", JOptionPane.PLAIN_MESSAGE);
                    if (s == null) 
                        break;
                    else if (!s.equals("")) 
                        j = Integer.parseInt("0" + s.replaceAll("\\D", ""));
                    acceptNum = j > 0;
                }
                if (acceptNum) {
                    new VigenereGraphGui(j);
                    frame.setVisible(false);
                    frame.dispose();
                }
            }
        }
    }
    
}
