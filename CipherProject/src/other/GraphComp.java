package other;

import texttools.TextFormatter;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Dimension;
import javax.swing.*;
import java.util.Arrays;
import texttools.FrequencyCalculator;

/**
 * Component that analyzes the input and creates a graph representing the
 * frequency of each character in the message.
 *
 */
public class GraphComp extends JComponent {

    private String text;

    private double[] letFrequency = new double[26];
    private String[] importantChars;

    private Graphics2D g2;

    private String graphTitle;
    private int xGraph, yGraph, graphWidth, graphHeight,shiftNum;

    private GraphComp(int x, int y, int width, int height, String title) {
        xGraph = x;
        yGraph = y;
        graphWidth = width;
        graphHeight = height;
        graphTitle = title;
    }
    
   /**
   * Create a graph showing the frequency of letters in a message
   * 
   * @param x: top left x-coord
   * @param y: top left y-coord
   * @param width: width of the graph
   * @param height: height of the graph
   * @param title: graph title
   * @param message:  message to be analyzed
   * */
    public GraphComp(int x, int y, int width, int height, String title, String message) {
        this(x, y, width, height, title);
        text = message.toLowerCase().replaceAll("\\W", "").replaceAll("\\d", "");
        importantChars = TextFormatter.ALPHABET;
        getRelativeFrequency();
        this.setPreferredSize(new Dimension(graphWidth, graphHeight));
    }
    
    /***
     * Create a graph showing the frequency of specified patterns/letters as specified by
     * lookFor
     *
     * @param x: top left x-coord
     * @param y: top left y-coord
     * @param width: width of the graph
     * @param height: height of the graph
     * @param title: graph title
     * @param lookFor: patterns to look for
     */
    public GraphComp(int x, int y, int width, int height, String title, String message, String[] lookFor) {
        this(x, y, width, height, title);
        text = message.toLowerCase().replaceAll("\\W", "");
        importantChars = lookFor;
        getRelativeFrequency();
        this.setPreferredSize(new Dimension(graphWidth, graphHeight));
    }

    /**
     * Create a graph showing the frequency of letters of the English alphabet as specified by
     * messageArray
     *
     * @param x: top left x-coord
     * @param y: top left y-coord
     * @param width: width of the graph
     * @param height: height of the graph
     * @param title: graph title
     * @param messageArray: frequency of letters
     */
    public GraphComp(int x, int y, int width, int height, String title, double[] messageArray) {
        this(x, y, width, height, title);
        letFrequency = messageArray;
        this.setPreferredSize(new Dimension(graphWidth, graphHeight));
    }

    /**
     * Method that paints the graph on the screen
     *
     * @param g: Graphics component that the graph is drawn on.
   *
     */
    @Override
    public void paintComponent(Graphics g) {
        g2 = (Graphics2D) g;
        g2.drawString(graphTitle, 5, graphHeight / 10);
        drawGraph();
    }

    /**
     * Method that draws the graph, with height graphHeight and width
     * graphWidth. Each bar in the graph depends on the frequency of the letter.
     *
     * @param x: x-coordinate of the top left corner of the graph
     * @param yGraph: y-coordinate of the top left corner of the graph
     * @param graphHeight: height of the graph
     * @param graphWidth: width of the graph
     * @param frequency: array of frequencies (for letters in alphabetical order)
     *
     */
    private void drawGraph() {
        int rWidth = graphWidth / 39;
        int rSpacing = graphWidth / 26;

        g2.drawLine(xGraph, yGraph, xGraph, yGraph + graphHeight);
        g2.drawLine(xGraph, yGraph + graphHeight, xGraph + (rSpacing - rWidth) + graphWidth, yGraph + graphHeight);

        for (int i = 0; i < 26; i++) {
            int rHeight = (int) (graphHeight * letFrequency[i]);
            Rectangle rect = new Rectangle(xGraph + (rSpacing - rWidth) + rSpacing * i, yGraph + (graphHeight - rHeight), rWidth, rHeight);
            g2.draw(rect);
            g2.drawString("" + TextFormatter.ALPHABET[i], xGraph + (rSpacing - rWidth) + rSpacing * i + rWidth / 4, yGraph + graphHeight + 10);
        }

    }

    /**
     * Calculate the frequency of characters in the message (Rather than the
     * amount of times they appear)
     *
     */
    private void getRelativeFrequency() {
        Object[][] frequencies = FrequencyCalculator.getSpecificMessageFrequency(text, importantChars);
        for (int i = 0; i < frequencies.length; i++)
            letFrequency[i] = (double) frequencies[i][1];
        //find the letter that repeats the most
        double max = 0;
        for (int i = 0; i < letFrequency.length; i++) {
            if (letFrequency[i] > max) {
                max = letFrequency[i];
            }
        }

        //take the frequency relative to the max value (to scale graph)
        for (int i = 0; i < 26; i++) {
            letFrequency[i] = (double) letFrequency[i] / max;
        }
    }

    public void shift(int shiftAmt) {
        if (shiftAmt == 1) {
            double temp = letFrequency[25];
            for (int i = (letFrequency.length - 1); i > 0; i--) {
                letFrequency[i] = letFrequency[i - 1];
            }
            letFrequency[0] = temp;
        }
        if (shiftAmt == -1) {
            double temp = letFrequency[0];
            for (int i = 0; i < (letFrequency.length - 1); i++) {
                letFrequency[i] = letFrequency[i + 1];
            }
            letFrequency[25] = temp;
        }
        shiftNum += shiftAmt;
        repaint();
    }

    public void setMessage(String message) {
        Arrays.fill(letFrequency, 0);
        text = message.toLowerCase().replaceAll("\\W", "");
        shiftNum = 0;
        getRelativeFrequency();
    }

    public int getShift() {
        return shiftNum;
    }
    
    public void setTitle(String title) {
        graphTitle = title;
        repaint();
    }
    
    public void setFrequency(double[] frequency) {
        letFrequency = frequency;
        repaint();
    }
}
