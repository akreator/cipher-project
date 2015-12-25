package CipherGui;

import Templates.GraphComp;
import Templates.MenuBar;
import Templates.MyGUI;
import Templates.MyTextArea;
import Templates.Properties;
import TextTools.*;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VigenereGraphGui extends MyGUI {

    private JFrame sFrame = new JFrame();

    private JButton rightShift, leftShift, back, checkStandard; 
    private JLabel shiftLabel = new JLabel("Shift: " + 0);
    
    //Vignere stuff
    private String originalText;
    private int patternLength;
    private int currentGraph;
    private JComboBox selectedGraph;
    private GraphComp[] graphs;
    private static JTextField patternField = new JTextField("");
    private static String loadedKeyword = "";
    
    private int gWidth, gHeight, perLine = 2;

    /***
     * Generates a window that ONLY ANALYZES LETTERS FROM PATTERN FINDER
     * Does not include numbers, punctuation, or white space
     * @param n 
     */
    public VigenereGraphGui(int n) {
        super(1000, 500, "Vignere Frequency Analysis");
        originalText = MyGUI.getCipherText();
        patternLength = n;
        graphs = new GraphComp[patternLength];
        char[] patternArray = new char[patternLength];
        for (int i = 0; i < patternLength; i++) {
            patternArray[i] = 'a';
        }
        patternField = new JTextField(new String(patternArray));
        patternField.setEditable(false);
        gWidth = getWidth() / 5;
        gHeight = 100;
        init();
        setVisible(true);
    }
    
    public VigenereGraphGui() {
        super(1000, 500, "Vignere Frequency Analysis");
        originalText = MyGUI.getCipherText();
        patternLength = loadedKeyword.length();
        graphs = new GraphComp[patternLength];
        patternField = new JTextField(loadedKeyword);
        patternField.setEditable(false);
        gWidth = getWidth() / 5;
        gHeight = 100;
        init();
        setVisible(true);
    }

    
    private void init() {
        //initialize EVERYTHING.  BECAUSE WHY NOT.
        rightShift = new JButton("Shift right");
        rightShift.addActionListener(new VignereGuiListener());
        leftShift = new JButton("Shift left");
        leftShift.addActionListener(new VignereGuiListener());
        checkStandard = new JButton("Check standard graph");
        checkStandard.addActionListener(new VignereGuiListener());
        back = new JButton("Back");
        back.addActionListener(new VignereGuiListener());
        originalTextArea = new MyTextArea(20, 50, false, false);
        originalTextArea.setText(TextFormatter.formatText(originalTextArea.getText(), TextFormatter.ONLY_LETTERS));
        if (!originalTextArea.getText().isEmpty()) {
            originalTextArea.setText(TextFormatter.formatText(originalTextArea.getText(), TextFormatter.SPECIAL_FORMAT));
        }
        
        //initialize the JComboBox
        String[] graphNums = new String[patternLength + 1];
        graphNums[0] = "Choose graph";
        for(int i = 1; i <= patternLength; i++) {
            graphNums[i] = "" + i;
        }
        selectedGraph = new JComboBox(graphNums);
        selectedGraph.addActionListener(new VignereGuiListener());
        
        //shifting and back buttons
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
        
        //keyword and button to show standard
        JPanel patternPane = new JPanel();
        patternPane.add(patternField);
        JPanel standardPane = new JPanel();
        standardPane.setLayout(new BoxLayout(standardPane, BoxLayout.PAGE_AXIS));
        standardPane.add(patternPane);
        standardPane.add(Box.createRigidArea(new Dimension(0, 10)));
        standardPane.add(checkStandard);
        
        //panel holding all buttons and keyword (top right)
        JPanel top = new JPanel();
        top.add(standardPane);
        top.add(buttonPane);
        
        //left side of the window: holds the text
        JPanel leftPane = new JPanel();
        JScrollPane scrollText = new JScrollPane(originalTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        leftPane.add(scrollText);

        //graph time
        JPanel[] graphPanes = new JPanel[patternLength / perLine + 1];
        for (int k = 0; k < graphPanes.length; k++) {
            JPanel g = new JPanel();
            g.setLayout(new BoxLayout(g, BoxLayout.LINE_AXIS));
            graphPanes[k] = g;
        }        
        int paneNum = 0;
        for (int i = 0; i < patternLength; i ++) {
            GraphComp g = new GraphComp(5, 20, gWidth, gHeight, "Graph #" + (i + 1), Crypter.getNthLetters(originalTextArea.getText(), i, patternLength));
            graphs[i] = g;
            if(i != 0 && i % perLine == 0) 
                paneNum++;
            graphPanes[paneNum].add(g);
            graphPanes[paneNum].add(Box.createRigidArea(new Dimension(5, 0)));
        }
        JPanel graphPane = new JPanel(); //pane for all the of the graphs
        graphPane.setLayout(new BoxLayout(graphPane, BoxLayout.PAGE_AXIS));
        for (JPanel g : graphPanes)
            graphPane.add(g);
        graphPane.setPreferredSize(new Dimension(getWidth() / 2, graphPanes.length * (gHeight + 50)));
        JScrollPane scrollingGraphs = new JScrollPane(graphPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollingGraphs.getVerticalScrollBar().setUnitIncrement(10);
        
        //overall panel for the right side
        JPanel rightPane = new JPanel();
        rightPane.setLayout(new BoxLayout(rightPane, BoxLayout.PAGE_AXIS));
        rightPane.add(top);
        rightPane.add(scrollingGraphs);
        
        //center panel: combines left and right
        JPanel centerPane = new JPanel();
        centerPane.setLayout(new BoxLayout(centerPane, BoxLayout.LINE_AXIS));
        centerPane.add(leftPane);
        centerPane.add(rightPane);
        
        JPanel fillerPaneY = new JPanel();
        fillerPaneY.setPreferredSize(new Dimension(0, 15));
        JPanel fillerPaneX = new JPanel();
        fillerPaneX.setPreferredSize(new Dimension(15, 0));

        //to make sure that shifts aren't saved (isn't added to frame)
        newTextArea = new MyTextArea(0, 0, false, false);
        newTextArea.setText(originalText);
        
        //Congrats, you survived.  A+.
        add(Box.createRigidArea(new Dimension(0, 5)), BorderLayout.NORTH);
        add(centerPane); 
        add(fillerPaneX, BorderLayout.EAST);
        add(fillerPaneX, BorderLayout.WEST);
        setJMenuBar(new MenuBar());
    }
    
    public void createStandardGraph() {
        sFrame.setTitle("Standard Fequency of " + Crypter.LANGUAGES[Properties.getLanguage()] + " Letters");
        sFrame.setSize(350, 200);
        sFrame.setDefaultCloseOperation(sFrame.DISPOSE_ON_CLOSE);

        GraphComp standardComp = new GraphComp(0, 10, 300, 100, 
                "Standard Fequency of " + Crypter.LANGUAGES[Properties.getLanguage()] + " Letters", 
                Crypter.getRelativeFrequency(Properties.getLanguage()));
        JPanel fillerPaneY = new JPanel();
        fillerPaneY.setPreferredSize(new Dimension(0, 15));
        JPanel fillerPaneX = new JPanel();
        fillerPaneX.setPreferredSize(new Dimension(15, 0));
        sFrame.add(fillerPaneY, BorderLayout.NORTH);
        sFrame.add(fillerPaneX, BorderLayout.WEST);
        sFrame.add(standardComp);
        
        sFrame.setVisible(true);
    }
    
    public static void setKeyword(String keyword) {
        loadedKeyword = keyword;
    }
    
    public static String getKeyword() {
        return patternField.getText();
    }
    
    @Override
    public void refresh() {
        newTextArea.setText(originalTextArea.getText());
        dispose();
        new VigenereGraphGui();
    }

    class VignereGuiListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == selectedGraph) {
                currentGraph = selectedGraph.getSelectedIndex() - 1;
            } else if (e.getSource() == leftShift && selectedGraph.getSelectedIndex() != 0) {
                graphs[currentGraph].shift(-1);
                originalTextArea.setText(TextFormatter.formatText(Crypter.shiftNthLetters(originalTextArea.getText(), currentGraph, patternLength, false), TextFormatter.SPECIAL_FORMAT));
                patternField.setText(Crypter.shiftNthLetters(patternField.getText(), currentGraph, patternLength, true));
                repaint();
            } else if (e.getSource() == rightShift && selectedGraph.getSelectedIndex() != 0) {
                graphs[currentGraph].shift(1);
                originalTextArea.setText(TextFormatter.formatText(Crypter.shiftNthLetters(originalTextArea.getText(), currentGraph, patternLength, true), TextFormatter.SPECIAL_FORMAT));
                patternField.setText(Crypter.shiftNthLetters(patternField.getText(), currentGraph, patternLength, false));
                repaint();
            } else if (e.getSource() == checkStandard) {
                createStandardGraph();
            } else if (e.getSource() == back) {
                new PatternFinderGui();
                sFrame.dispose();
                dispose();
            }
        }//end of method
    }//end of class
}
