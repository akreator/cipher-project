package other;

import texttools.TextFormatter;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Dimension;
import javax.swing.*;
import java.util.Arrays;

/**
 * Component that analyzes the input and creates a graph representing the
 * frequency of each character in the message.
 *
 */
public class GraphComp extends JComponent {

    private String text;

    private int[] letAmt = new int[26];
    private double[] letFrequency = new double[26];

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
        getCharAmt();
        getFrequency();
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
     * Count how many times characters appear in the message.
   *
     */
    private void getCharAmt() {
        char[] textArray = text.toCharArray();
        int textLength = textArray.length;
        for (int i = 0; i < textArray.length; i++) {
            switch (textArray[i]) {
                case 'a':
                    letAmt[0] = letAmt[0] + 1;
                    break;
                case 'b':
                    letAmt[1] = letAmt[1] + 1;
                    break;
                case 'c':
                    letAmt[2] = letAmt[2] + 1;
                    break;
                case 'd':
                    letAmt[3] = letAmt[3] + 1;
                    break;
                case 'e':
                    letAmt[4] = letAmt[4] + 1;
                    break;
                case 'f':
                    letAmt[5] = letAmt[5] + 1;
                    break;
                case 'g':
                    letAmt[6] = letAmt[6] + 1;
                    break;
                case 'h':
                    letAmt[7] = letAmt[7] + 1;
                    break;
                case 'i':
                    letAmt[8] = letAmt[8] + 1;
                    break;
                case 'j':
                    letAmt[9] = letAmt[9] + 1;
                    break;
                case 'k':
                    letAmt[10] = letAmt[10] + 1;
                    break;
                case 'l':
                    letAmt[11] = letAmt[11] + 1;
                    break;
                case 'm':
                    letAmt[12] = letAmt[12] + 1;
                    break;
                case 'n':
                    letAmt[13] = letAmt[13] + 1;
                    break;
                case 'o':
                    letAmt[14] = letAmt[14] + 1;
                    break;
                case 'p':
                    letAmt[15] = letAmt[15] + 1;
                    break;
                case 'q':
                    letAmt[16] = letAmt[16] + 1;
                    break;
                case 'r':
                    letAmt[17] = letAmt[17] + 1;
                    break;
                case 's':
                    letAmt[18] = letAmt[18] + 1;
                    break;
                case 't':
                    letAmt[19] = letAmt[19] + 1;
                    break;
                case 'u':
                    letAmt[20] = letAmt[20] + 1;
                    break;
                case 'v':
                    letAmt[21] = letAmt[21] + 1;
                    break;
                case 'w':
                    letAmt[22] = letAmt[22] + 1;
                    break;
                case 'x':
                    letAmt[23] = letAmt[23] + 1;
                    break;
                case 'y':
                    letAmt[24] = letAmt[24] + 1;
                    break;
                case 'z':
                    letAmt[25] = letAmt[25] + 1;
                    break;
                default:
                    System.out.println("ERROR.");
                    break;
            }
        }
    }

    /**
     * Calculate the frequency of characters in the message (Rather than the
     * amount of times they appear)
   *
     */
    private void getFrequency() {
        //find the letter that repeats the most
        double max = 0;
        for (int i = 0; i < 26; i++) {
            if (letAmt[i] > max) {
                max = letAmt[i];
            }
        }

        //take the frequency relative to the max value (to scale graph)
        for (int i = 0; i < 26; i++) {
            letFrequency[i] = (double) letAmt[i] / max;
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
        Arrays.fill(letAmt, 0);
        Arrays.fill(letFrequency, 0);
        text = message.toLowerCase().replaceAll("\\W", "").replaceAll("\\d", "");
        shiftNum = 0;
        getCharAmt();
        getFrequency();
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
