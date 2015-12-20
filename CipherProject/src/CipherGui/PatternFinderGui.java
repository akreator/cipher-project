package CipherGui;

import Templates.*;
import TextTools.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
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
    private JTextField lengthField;
    private ArrayList<History> patternHist = new ArrayList();
    
    public PatternFinderGui() {
        super(950, 300, "Vignere Cipher: Deciphering Tool");
        init();
        frame.setVisible(true);
    }
    
    private void init() {
        //create EVERY. SINGLE. LAST. THING.  JESUS.
        enter = new JButton("Analyze Text");
        knownLength = new JButton("Length determined");
        enter.addActionListener(new PatternFinderListener());
        knownLength.addActionListener(new PatternFinderListener());
        lengthField = new JTextField("", 20);
        JPanel fieldPane = new JPanel();
        fieldPane.setPreferredSize(new Dimension(5, 5));
        fieldPane.add(lengthField);
        originalTextArea = new MyTextArea(5, 30, true, false);
        //fix carry over errors
        newTextArea = new MyTextArea(5, 4, false, true);
        newTextArea.setText("");
        JLabel patternLabel = new JLabel("Length of pattern to look for:");
        
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
        patternPane.add(lengthField);
        patternPane.add(Box.createRigidArea(new Dimension(0, 5)));
        patternPane.add(enter);
        
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.PAGE_AXIS));
        buttonPane.add(Box.createRigidArea(new Dimension(0, 5)));
        buttonPane.add(knownLength);

        bottomPane.add(patternPane);
        bottomPane.add(Box.createRigidArea(new Dimension(25, 0)));
        bottomPane.add(buttonPane);

        JPanel northPane = new JPanel();
        northPane.setPreferredSize(new Dimension(0, 15));
        frame.add(northPane, BorderLayout.NORTH);
        frame.add(pane);
        frame.add(bottomPane, BorderLayout.SOUTH);
        frame.setJMenuBar(new MenuBar(frame, originalTextArea));
    }

    public void autoFindPatterns(int length) {
        ArrayList<String> patternsChecked = new ArrayList<String>();
        String cText = originalTextArea.getText().toLowerCase().replaceAll("[^a-zA-Z]", "");
        for (int startIndex = 0; startIndex < cText.length() - length; startIndex++) {
            int previousIndex = startIndex;
            int repetitions = 0, shortestDistance = Integer.MAX_VALUE;
            String pattern = cText.substring(startIndex, startIndex + length);
            if (!patternsChecked.contains(pattern) && cText.indexOf(pattern) != cText.lastIndexOf(pattern)) {
                patternsChecked.add(pattern);
                int i = cText.indexOf(pattern);
                while (i != -1) {
                    repetitions++;
                    if (i - (previousIndex + length) >= 0) {
                        shortestDistance = Math.min(shortestDistance, i - (previousIndex + length));
                    }
                    previousIndex = i;
                    i = cText.indexOf(pattern, i + 1);
                }
                patternHist.add(new History(pattern, repetitions, shortestDistance));
            }
        }
    }

    public void checkHistory() {
        Arrays.sort(patternHist.toArray());
        //GUI
        JFrame histWindow = new JFrame("Pattern history");
        MyTextArea histText = new MyTextArea(10, 5, false, true);
        JScrollPane histPane = new JScrollPane(histText, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        histText.setFont(new Font("", Font.PLAIN, 14));
        //Add everything to panel
        if (patternHist.size() == 0) { 
            histText.insert("No patterns of that length found.", 0);
        }
        for (History h : patternHist) {
            histText.insert("*** TRIAL #" + (patternHist.indexOf(h) + 1) + " ***\n", histText.getText().length());
            histText.insert(h.getStats(), histText.getText().length());
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
            if (e.getSource() == enter) {
                int n = 0;
                try {
                    n = Integer.parseInt("" + lengthField.getText());
                    autoFindPatterns(n);
                    checkHistory();
                    patternHist.clear();
                } catch (NumberFormatException m) {
                    //do nothing
                }
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
