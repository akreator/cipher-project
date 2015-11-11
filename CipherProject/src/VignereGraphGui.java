package CipherClasses;


import javax.swing.*;
import java.awt.Toolkit;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.TextArea;
import java.util.ArrayList;

public class VignereGraphGui {

    private JFrame frame = new JFrame(), sFrame = new JFrame();

    private JButton rightShift, leftShift, back, checkStandard; 
    private JTextArea textArea;
    private JLabel shiftLabel = new JLabel("Shift: " + 0);
    
    //Vignere stuff
    private String cipherText, originalText;
    private int patternLength, currentGraph;
    private JComboBox selectedGraph;
    private GraphComp[] graphs;
    private JTextField patternField;
    
    private int gWidth, gHeight;


    /**
     * Constructor to be called by subclass
     * @param text
     * @param n
     */
    public VignereGraphGui(String text, int n) {
        originalText = text;
        cipherText = text.toUpperCase().replaceAll("\\s", "");
        patternLength = n;
        graphs = new GraphComp[patternLength];
        char[] patternArray = new char[patternLength];
        for (int i = 0; i < n; i++) {
            patternArray[i] = 'a';
        }
        patternField = new JTextField(new String(patternArray));
        patternField.setEditable(false);
        if (n > 5) 
            gWidth = 1300 / 6;
        else
            gWidth = 1200 / (n + 1);
        gHeight = 100;
        
        frame.setTitle("Vigenere Frequency Analysis");
        frame.setSize(1200, 500);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);

        init();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
        frame.setVisible(true);
    }

    
    public void init() {
        //initialize EVERYTHING.  BECAUSE WHY NOT.
        rightShift = new JButton("Shift right");
        rightShift.addActionListener(new VignereGuiListener());
        leftShift = new JButton("Shift left");
        leftShift.addActionListener(new VignereGuiListener());
        checkStandard = new JButton("Check standard graph");
        checkStandard.addActionListener(new VignereGuiListener());
        back = new JButton("Back");
        back.addActionListener(new VignereGuiListener());
        textArea = new MyTextArea(Text.formatText(cipherText, Text.SPECIAL_FORMAT), 7, 60, false, 12);
        
        //initialize the JComboBox
        String[] graphNums = new String[patternLength + 1];
        graphNums[0] = "Choose graph";
        for(int i = 1; i <= patternLength; i++) {
            graphNums[i] = "" + i;
        }
        selectedGraph = new JComboBox(graphNums);
        selectedGraph.addActionListener(new VignereGuiListener());
        
        //Jpanels
        JPanel buttonPane = new JPanel();
        buttonPane.add(selectedGraph);
        JPanel buttonOrganizer = new JPanel();
        buttonOrganizer.setLayout(new BoxLayout(buttonOrganizer, BoxLayout.PAGE_AXIS));
        buttonOrganizer.add(selectedGraph);
        JPanel bOrg2 = new JPanel();
        bOrg2.add(leftShift);
        bOrg2.add(rightShift);
        buttonOrganizer.add(bOrg2);
        buttonPane.add(buttonOrganizer);
        buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPane.add(back);
        
        JPanel top = new JPanel();
        JScrollPane scrollText = new JScrollPane(textArea);
        top.add(scrollText);
        JPanel patternPane = new JPanel();
        patternPane.add(patternField);
        JPanel standardPane = new JPanel();
        standardPane.setLayout(new BoxLayout(standardPane, BoxLayout.PAGE_AXIS));
        standardPane.add(patternPane);
        standardPane.add(Box.createRigidArea(new Dimension(0, 10)));
        standardPane.add(checkStandard);
        top.add(standardPane);
        top.add(buttonPane);

        JPanel[] graphPanes = new JPanel[patternLength / 5 + 1];
        for (int k = 0; k < graphPanes.length; k++) {
            JPanel g = new JPanel();
            g.setLayout(new BoxLayout(g, BoxLayout.LINE_AXIS));
            graphPanes[k] = g;
        }
        
        int paneNum = 0;
        for (int i = 0; i < patternLength; i ++) {
            GraphComp g = new GraphComp(5, 20, gWidth, gHeight, "Graph #" + (i + 1), Text.getNthLetters(cipherText, i, patternLength));
            graphs[i] = g;
            if(i != 0 && i % 5 == 0) 
                paneNum++;
            graphPanes[paneNum].add(g);
            graphPanes[paneNum].add(Box.createRigidArea(new Dimension(5, 0)));
        }
        JPanel graphPane = new JPanel();
        graphPane.setLayout(new BoxLayout(graphPane, BoxLayout.PAGE_AXIS));
        for (JPanel g : graphPanes)
            graphPane.add(g);
        graphPane.setPreferredSize(new Dimension(frame.getWidth() - 50, graphPanes.length * (gHeight + 50)));
        JScrollPane scrollingGraphs = new JScrollPane(graphPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollingGraphs.getVerticalScrollBar().setUnitIncrement(10);
        
        JPanel fillerPaneY = new JPanel();
        fillerPaneY.setPreferredSize(new Dimension(0, 15));
        JPanel fillerPaneX = new JPanel();
        fillerPaneX.setPreferredSize(new Dimension(15, 0));

        
        //Congrats, you survived.  A+.
        frame.add(top, BorderLayout.NORTH);
        frame.add(scrollingGraphs); 
        frame.add(fillerPaneX, BorderLayout.EAST);
        frame.add(fillerPaneX, BorderLayout.WEST);
        frame.setJMenuBar(new MenuBar(frame));
    }
    
    public void createStandardGraph() {
        sFrame.setTitle("Standard Fequency of English Letters");
        sFrame.setSize(350, 200);
        sFrame.setDefaultCloseOperation(sFrame.DISPOSE_ON_CLOSE);

        GraphComp standardComp = new GraphComp(0, 10, 300, 100, "Standard English Letter Frequency", Text.standardRelativeFrequency);
        JPanel fillerPaneY = new JPanel();
        fillerPaneY.setPreferredSize(new Dimension(0, 15));
        JPanel fillerPaneX = new JPanel();
        fillerPaneX.setPreferredSize(new Dimension(15, 0));
        sFrame.add(fillerPaneY, BorderLayout.NORTH);
        sFrame.add(fillerPaneX, BorderLayout.WEST);
        sFrame.add(standardComp);
        
        sFrame.setVisible(true);
    }

    class VignereGuiListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == selectedGraph) {
                currentGraph = selectedGraph.getSelectedIndex() - 1;
            } else if (e.getSource() == leftShift && selectedGraph.getSelectedIndex() != 0) {
                graphs[currentGraph].shift(-1);
                textArea.setText(Text.shiftNthLetters(textArea.getText(), currentGraph, patternLength, false, true));
                patternField.setText(Text.shiftNthLetters(patternField.getText(), currentGraph, patternLength, true, false));
                frame.repaint();
            } else if (e.getSource() == rightShift && selectedGraph.getSelectedIndex() != 0) {
                graphs[currentGraph].shift(1);
                textArea.setText(Text.shiftNthLetters(textArea.getText(), currentGraph, patternLength, true, true));
                patternField.setText(Text.shiftNthLetters(patternField.getText(), currentGraph, patternLength, false, false));
                frame.repaint();
            } else if (e.getSource() == checkStandard) {
                createStandardGraph();
            } else if (e.getSource() == back) {
                new PatternFinderGui(originalText);
                sFrame.dispose();
                frame.dispose();
            }
        }//end of method
    }//end of class
}
