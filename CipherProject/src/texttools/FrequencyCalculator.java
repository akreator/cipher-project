package texttools;

import java.util.ArrayList;

/**
 *
 * @author Audrey
 */
public class FrequencyCalculator {
    public static final int ENGLISH = 0;
    public static final int SPANISH = 3;
    public static final int FRENCH = 2;
    public static final String[] LANGUAGES = {"English", "Latin", "French", "Spanish"};
    public static final int LATIN = 1;

    /**
     * Method that calculates the frequency of all characters in the text.
     * Includes everything but whitespace.
     *
     * @param text: Text to be analyzed
     * @return : 2D array, each internal array containing the character and its
     * frequency
     */
    public static Object[][] getMessageFrequency(String text) {
        ArrayList<Integer> charAmts = new ArrayList();
        ArrayList<Character> messageChars = new ArrayList();
        char[] textArray = text.toLowerCase().replaceAll("\\s", "").toCharArray();
        for (int i = 0; i < textArray.length; i++) {
            char c = textArray[i];
            if (!messageChars.contains(c)) {
                messageChars.add(c);
                charAmts.add(1);
            } else {
                charAmts.set(messageChars.indexOf(c), charAmts.get(messageChars.indexOf(c)) + 1);
            }
        }
        Object[][] frequency = new Object[messageChars.size()][2];
        int totalChars = textArray.length;
        for (int i = 0; i < messageChars.size(); i++) {
            frequency[i][0] = messageChars.get(i);
            frequency[i][1] = 100.0 * (double) charAmts.get(i) / totalChars;
        }
        return frequency;
    }

    /***
     * Return relative frequencies of a specific language
     * @param language language
     * @return array of frequencies (double), with 1 = frequency of most frequent letter
     */
    public static double[] getRelativeLanguageFrequency(int language) {
        double[] standardFrequency = new double[26];
        switch (language) {
            case LATIN:
                double[] latinFrequency = {
                    0.777098, 0.138112, 0.348776, 0.242133, 0.994755, 0.081294, 
                    0.105769, 0.060315, 1.0, 0.0, 0.0, 0.27535, 0.47028, 0.548951, 
                    0.472028, 0.26486, 0.131993, 0.583042, 0.664336, 0.699301, 
                    0.73951, 0.083916, 0.0, 0.052448, 0.006119, 8.74E-4
                };
                standardFrequency = latinFrequency;
                break;
            case FRENCH:
                double[] frenchFrequency = {
                    0.538411, 0.061589, 0.208609, 0.235099, 1.0, 0.063576, 0.064238, 
                    0.071523, 0.459603, 0.04702, 0.010596, 0.376159, 0.213907, 
                    0.425166, 0.349007, 0.200662, 0.05894, 0.425828, 0.523841, 
                    0.470861, 0.400662, 0.121192, 0.002649, 0.027815, 0.012583, 
                    0.013907
                };
                standardFrequency = frenchFrequency;
                break;
            case SPANISH:
                double[] spanishFrequency = {
                    0.854227, 0.108601, 0.28207, 0.340379, 1.0, 0.050292, 0.072886, 
                    0.086006, 0.38484, 0.037901, 0.008017, 0.381924, 0.22449, 0.497813, 
                    0.61516, 0.210641, 0.080904, 0.467201, 0.524781, 0.335277, 
                    0.331633, 0.076531, 0.002915, 0.010204, 0.079446, 0.034257
                };
                standardFrequency = spanishFrequency;
                break;
            default:
                //default is english
                double[] englishFrequency = {
                    0.642906, 0.11745, 0.218999, 0.334796, 0.999901, 0.175388, 
                    0.158621, 0.47972, 0.548364, 0.012044, 0.060772, 0.316848, 
                    0.1894, 0.531281, 0.590951, 0.151851, 0.007478, 0.471297, 
                    0.498061, 0.712888, 0.21711, 0.076988, 0.185858, 0.011808, 
                    0.155393, 0.005825
                };
                standardFrequency = englishFrequency;
                break;
        }
        return standardFrequency;
    }

    /***
     * Get the relative frequency of specific characters in a text
     * @param text text to analyze
     * @param importantChars characters/letters/patterns to look for
     * @return
     */
    public static double[] getRelativeFrequency(String text, String[] importantChars) {
        double[] letFrequency = new double[importantChars.length];
        Object[][] frequencies = getSpecificMessageFrequency(text, importantChars);
        for (int i = 0; i < frequencies.length; i++) {
            letFrequency[i] = (double) frequencies[i][1];
        }
        //find the letter that repeats the most
        double max = 0;
        for (int i = 0; i < letFrequency.length; i++) {
            if (letFrequency[i] > max) {
                max = letFrequency[i];
            }
        }
        for (int i = 0; i < 26; i++) {
            letFrequency[i] = (double) letFrequency[i] / max;
        }
        return letFrequency;
    }

    /**
     * Return standard frequency of letters for a given language.
     * Values can be relative to one another or total to 100%.
     * @param language
     * @return
     */
    public static Object[][] getStandardFrequency(int language) {
        Object[][] standardFrequency = new Object[26][2];
        switch (language) {
            case LATIN:
                Object[][] latinFrequency = {
                    {"i", 11.44}, {"e", 11.38}, {"a", 8.89}, {"u", 8.46}, {"t", 8.0}, 
                    {"s", 7.6}, {"r", 6.67}, {"n", 6.28}, {"o", 5.4}, {"m", 4.025}, 
                    {"c", 3.99}, {"l", 3.15}, {"p", 3.03}, {"d", 2.77}, {"b", 1.58}, 
                    {"q", 1.51}, {"g", 1.21}, {"v", 0.96}, {"f", 0.93}, {"h", 0.69}, 
                    {"x", 0.6}, {"y", 0.07}, {"z", 0.01}
                };
                standardFrequency = latinFrequency;
                break;
            case FRENCH:
                Object[][] frenchFrequency = {
                    {"e", 15.1}, {"a", 8.13}, {"s", 7.91}, {"t", 7.11}, {"i", 6.94}, 
                    {"r", 6.43}, {"n", 6.42}, {"u", 6.05}, {"l", 5.68}, {"o", 5.27}, 
                    {"d", 3.55}, {"m", 3.23}, {"c", 3.15}, {"p", 3.03}, {"\u00e9", 2.13}, 
                    {"v", 1.83}, {"h", 1.08}, {"g", 0.97}, {"f", 0.96}, {"b", 0.93}, 
                    {"q", 0.89}, {"j", 0.71}, {"\u00e0", 0.54}, {"x", 0.42}, {"\u00e8", 0.35}, 
                    {"\u00ea", 0.24}, {"z", 0.21}, {"y", 0.19}, {"k", 0.16}, {"w", 0.04}
                };
                standardFrequency = frenchFrequency;
                break;
            case SPANISH:
                Object[][] spanishFrequency = {
                    {"e", 13.72}, {"a", 11.72}, {"o", 8.44}, {"s", 7.2}, {"n", 6.83}, 
                    {"r", 6.41}, {"i", 5.28}, {"l", 5.24}, {"d", 4.67}, {"t", 4.6}, 
                    {"u", 4.55}, {"c", 3.87}, {"m", 3.08}, {"p", 2.89}, {"b", 1.49}, 
                    {"h", 1.18}, {"q", 1.11}, {"y", 1.09}, {"v", 1.05}, {"g", 1.0}, 
                    {"\u00f3", 0.76}, {"\u00ed", 0.7}, {"f", 0.69}, {"j", 0.52}, {"z", 0.47}, 
                    {"\u00e1", 0.44}, {"\u00e9", 0.36}, {"\u00f1", 0.17}, {"x", 0.14}, 
                    {"\u00fa", 0.12}, {"k", 0.11}, {"w", 0.04}, {"\u00fc", 0.02}
                };
                standardFrequency = spanishFrequency;
                break;
            default:
                Object[][] englishFrequency = {
                    {"e", 12.702}, {"t", 9.056}, {"a", 8.167}, {"o", 7.507}, {"i", 6.966}, 
                    {"n", 6.749}, {"s", 6.327}, {"h", 6.094}, {"r", 5.987}, {"d", 4.253}, 
                    {"l", 4.025}, {"c", 2.782}, {"u", 2.758}, {"m", 2.406}, {"w", 2.361}, 
                    {"f", 2.228}, {"g", 2.015}, {"y", 1.974}, {"p", 1.929}, {"b", 1.492}, 
                    {"v", 0.978}, {"k", 0.772}, {"j", 0.153}, {"x", 0.15}, {"q", 0.095}, 
                    {"z", 0.74}
                };
                standardFrequency = englishFrequency;
                break;
        }
        return standardFrequency;
    }

    /**
     * Calculate the frequency of specific patterns in the text
     *
     * @param text: the message to parse through
     * @param importantChars: the patterns to look for
     * @return : A 2D array, each array containing a pattern and its frequency
     */
    public static Object[][] getSpecificMessageFrequency(String text, ArrayList<String> importantChars) {
        ArrayList<Integer> amts = new ArrayList<>();
        ArrayList<String> lookFor = new ArrayList<>(); //avoid repeats
        for (String str : importantChars)
            if (!lookFor.contains(str)) lookFor.add(str);
        text = text.toLowerCase().replaceAll("\\s", "");
        //count how many times each character appears
        for (String pattern : lookFor) {
            StringBuffer strb = new StringBuffer(text);
            int count = 0;
            if (!pattern.isEmpty() && !pattern.equals("")) {
                int index = strb.indexOf(pattern);
                while (index != -1) {
                    count++;
                    strb = strb.delete(0, index + pattern.length());
                    index = strb.indexOf(pattern);
                }
            }
            amts.add(count);
        }
        //calculate the frequency from each letter's count
        Object[][] frequency = new Object[amts.size()][2];
        for (int i = 0; i < lookFor.size(); i++) {
            frequency[i][0] = lookFor.get(i);
            frequency[i][1] = 100.0 * amts.get(i) * lookFor.get(i).length() / text.length();
        }
        return frequency;
    }

    /***
     * Get frequency of specific characters/patterns in the text using an array,
     * rather than an ArrayList
     * @param text text to search
     * @param lookFor patterns/chars to count
     * @return 2D Array; each array contains a pattern and its frequency
     */
    public static Object[][] getSpecificMessageFrequency(String text, String[] lookFor) {
        ArrayList<String> importantPatterns = new ArrayList<>();
        for (String str : lookFor) {
            importantPatterns.add(str);
        }
        return getSpecificMessageFrequency(text, importantPatterns);
    }    
}
