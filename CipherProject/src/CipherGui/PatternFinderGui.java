package CipherGui;

import Templates.*;
import TextTools.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;


/**
 *
 * @author Audrey
 */
public class PatternFinderGui extends MyGUI {
    private JButton enter, history, knownLength;
    private JTextField patternField;
    private JLabel repLabel, distanceLabel;
    private int shortestDistance, repetitions;
    private String pattern;
    private ArrayList<History> patternHist = new ArrayList();
    
    public PatternFinderGui() {
        super(950, 300, "Vignere Cipher: Deciphering Tool");
        init();
        frame.setVisible(true);
    }
    
    private void init() {
        //create EVERY. SINGLE. LAST. THING.  JESUS.
        enter = new JButton("Analyze Text");
        history = new JButton("Check pattern history");
        knownLength = new JButton("Length determined");
        enter.addActionListener(new PatternFinderListener());
        history.addActionListener(new PatternFinderListener());
        knownLength.addActionListener(new PatternFinderListener());
        patternField = new JTextField("pattern", 20);
        JPanel fieldPane = new JPanel();
        fieldPane.setPreferredSize(new Dimension(5, 5));
        fieldPane.add(patternField);
        originalTextArea = new MyTextArea(7, 30, true, false);
        repLabel = new JLabel("Pattern found x times.");
        distanceLabel = new JLabel("Shortest distance between repetitions: x");
        JLabel patternLabel = new JLabel("Pattern to look for:");
        
        //pane holds the originalTextArea
        JPanel pane = new JPanel();
        pane.setLayout(new BoxLayout(pane, BoxLayout.LINE_AXIS));
        pane.add(Box.createRigidArea(new Dimension(25, 0)));
        JScrollPane scrollText = new JScrollPane(originalTextArea);
        pane.add(scrollText);
        pane.add(Box.createRigidArea(new Dimension(15, 0)));

        JPanel bottomPane = new JPanel();

        JPanel patternPane = new JPanel();
        patternPane.setLayout(new BoxLayout(patternPane, BoxLayout.PAGE_AXIS));
        patternPane.add(patternLabel);
        patternPane.add(Box.createRigidArea(new Dimension(0, 5)));
        patternPane.add(patternField);
        patternPane.add(Box.createRigidArea(new Dimension(0, 5)));
        patternPane.add(enter);

        JPanel historyPane = new JPanel();
        historyPane.setLayout(new BoxLayout(historyPane, BoxLayout.PAGE_AXIS));
        historyPane.add(repLabel);
        historyPane.add(Box.createRigidArea(new Dimension(0, 5)));
        historyPane.add(distanceLabel);
        historyPane.add(Box.createRigidArea(new Dimension(0, 5)));
        historyPane.add(history);
        historyPane.add(Box.createRigidArea(new Dimension(0, 15)));
        
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.PAGE_AXIS));
        buttonPane.add(Box.createRigidArea(new Dimension(0, 5)));
        buttonPane.add(knownLength);

        bottomPane.add(patternPane);
        bottomPane.add(Box.createRigidArea(new Dimension(25, 0)));
        bottomPane.add(historyPane);
        bottomPane.add(Box.createRigidArea(new Dimension(25, 0)));
        bottomPane.add(buttonPane);

        JPanel northPane = new JPanel();
        northPane.setPreferredSize(new Dimension(0, 15));
        frame.add(northPane, BorderLayout.NORTH);
        frame.add(pane);
        frame.add(bottomPane, BorderLayout.SOUTH);
        frame.setJMenuBar(new MenuBar(frame, originalTextArea));
    }

    public void findPattern() {
        int currentIndex = 0;
        int previousIndex = 0;
        repetitions = 0;
        shortestDistance = Integer.MAX_VALUE;
        String cText = originalTextArea.getText().toLowerCase().replaceAll("[^a-zA-Z]", "");
        pattern = patternField.getText().toLowerCase();

        if (cText.contains(pattern)) {
            currentIndex = cText.indexOf(pattern);
            repetitions++;
                
            boolean lastInstance = false;
            while (!lastInstance) {
                previousIndex = currentIndex;
                currentIndex = cText.indexOf(pattern, previousIndex + pattern.length());

                if (currentIndex == -1 && repetitions == 1) {
                    repLabel.setText("Pattern found " + repetitions + " times.");
                    distanceLabel.setText("Shortest distance between repetitions: x");
                    frame.repaint();
                    lastInstance = true;
                } else if (currentIndex == -1) {
                    patternHist.add(new History(pattern, repetitions, shortestDistance));
                    repLabel.setText("Pattern found " + repetitions + " times.");
                    distanceLabel.setText("Shortest distance between repetitions: " + shortestDistance);
                    frame.repaint();
                    lastInstance = true;
                } else {
                    shortestDistance = Math.min(shortestDistance,
                            currentIndex - (previousIndex + pattern.length()));
                    repetitions++;
                }
            }
        } else {
            repLabel.setText("Pattern found 0 times.");
            distanceLabel.setText("Shortest distance between repetitions: x");
            frame.repaint();
        }
    }

    public void checkHistory() {
        //GUI
        JFrame histWindow = new JFrame("Pattern history");
        MyTextArea histText = new MyTextArea(10, 5, false, true);
        JScrollPane histPane = new JScrollPane(histText, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        histText.setFont(new Font("", Font.PLAIN, 14));
        
        //Add everything to panel
        int shortest = 1;
        for (History h : patternHist) {
            histText.insert("*** TRIAL #" + (patternHist.indexOf(h) + 1) + " ***\n", histText.getText().length());
            histText.insert(h.getStats(), histText.getText().length());
            shortest = Calculations.getGCF(shortest, h.getShortestDistance());
        }
        histText.setEditable(false);
        
        //GUI
        JPanel northPane = new JPanel();
        northPane.setPreferredSize(new Dimension(0, 15));
        JPanel southPane = new JPanel();
        southPane.setPreferredSize(new Dimension(0, 15));
        JPanel eastPane = new JPanel();
        eastPane.setPreferredSize(new Dimension(15, 0));
        JPanel westPane = new JPanel();
        westPane.setPreferredSize(new Dimension(15, 0));
        
        histWindow.add(histPane, BorderLayout.CENTER);
        histWindow.add(northPane, BorderLayout.NORTH);
        histWindow.add(southPane, BorderLayout.SOUTH);
        histWindow.add(westPane, BorderLayout.WEST);
        histWindow.add(eastPane, BorderLayout.EAST);
        histWindow.setSize(450, 300);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        histWindow.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
        histWindow.setVisible(true);
    }
    
    class PatternFinderListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == enter) 
                findPattern();
            else if (e.getSource() == history) 
                checkHistory();
            else if (e.getSource() == knownLength) {
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
