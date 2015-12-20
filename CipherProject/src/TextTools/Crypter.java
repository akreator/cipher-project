package TextTools;

import java.util.ArrayList;
import java.util.Scanner;

public class Crypter {

    public static final double[] standardRelativeFrequency = {0.64290624, 0.11745023999999998, 0.21899904, 0.33479616, 0.99990144, 0.17538816000000002,
        0.1586208, 0.47971968000000004, 0.54836352, 0.01204416, 0.06077184, 0.316848, 0.18940032, 0.53128128, 0.59095104, 0.15185088,
        0.0074784, 0.47129664, 0.49806143999999997, 0.71288832, 0.21710975999999998, 0.07698816, 0.18585792, 0.011807999999999999,
        0.15539328, 0.0058252799999999995};

    private static char[] vowels = {'a', 'e', 'i', 'o', 'u'};
    private static String[] specialCases = {"qu", "wr", "ph", "kn", "th", "sh", "fr", "sw", "gl", "sm", "wy"};

    public static final int HORIZONTAL = 0, VERTICAL = 1, DIAGONAL = 2;

    private Crypter() {
    }

    /**
     * Method for shifting characters in a given text by a certain amount.
     *
     * @param text: text to be converted
     * @param shiftAmt: the amount that the letters should be shifted
     * @return : changed text
     */
    public static String shift(String text, int shiftAmt) {
        char[] plainTextArray = text.toCharArray();
        for (int i = 0; i < plainTextArray.length; i++) {
            if (Character.isLetter(plainTextArray[i])) {
                char temp = plainTextArray[i];
                plainTextArray[i] = (char) (shiftAmt + (int) plainTextArray[i]);

                if (Character.isLowerCase(temp) && plainTextArray[i] > 122 || Character.isUpperCase(temp) && plainTextArray[i] > 90) {
                    plainTextArray[i] = (char) ((int) plainTextArray[i] - 26);
                } else if (Character.isLowerCase(temp) && plainTextArray[i] < 97 || Character.isUpperCase(temp) && plainTextArray[i] < 65) {
                    plainTextArray[i] = (char) ((int) plainTextArray[i] + 26);
                }

                if (Character.isLowerCase(temp) && plainTextArray[i] < 97) {
                    plainTextArray[i] = (char) ((int) plainTextArray[i] + 26);
                } else if (Character.isUpperCase(temp) && plainTextArray[i] < 65) {
                    plainTextArray[i] = (char) ((int) plainTextArray[i] + 26);
                }
            }
        }
        return new String(plainTextArray);
    }

    /**
     * Substitute characters in the text for other characters. Characters that
     * are not replaced are converted to lowercase; replaced characters are
     * uppercase
     *
     * @param str: text to be encrypted/decrypted
     * @param replacements: { {original character, replacement character}, ...}
     * @return : text with characters replaced according to replacements
     */
    public static String substitute(String str, String[][] replacements) {
        if (str.equals("") || str.isEmpty()) {
            return "";
        }
        StringBuffer text = new StringBuffer("");
        str = str.toLowerCase();
        for (int i = 0; i < str.length(); i++) {
            boolean found = false;
            int n = 0;
            while (!found) {
                if (!replacements[n][1].isEmpty() && !replacements[n][1].equals("")
                        && !replacements[n][0].isEmpty() && !replacements[n][0].equals("")
                        && i + replacements[n][1].length() <= str.length()) {
                    if (str.substring(i, i + replacements[n][1].length()).equals(replacements[n][1])) {
                        text.append(replacements[n][0].toUpperCase());
                        i += replacements[n][1].length() - 1;
                        found = true;
                    } else if (n < replacements.length - 1) {
                        n++;
                    } else {
                        text.append(str.substring(i, i + 1));
                        found = true;
                    }
                } else if (n < replacements.length - 1) {
                    n++;
                } else {
                    text.append(str.substring(i, i + 1));
                    found = true;
                }
            }
        }
        return new String(text);
    }

    /**
     * Encrypt or decrypt text using a Vigenere cipher.
     *
     * @param plainText: text to be encrypted/decrypted
     * @param keyword: keyword to use in encryption
     * @param encrypt: true = encrypt; false = decrypt
     * @return : encrypted/decrypted text
     */
    public static String vigenereCrypt(String plainText, String keyword, boolean encrypt) {
        StringBuffer text = new StringBuffer();
        char[] keywordArray = TextFormatter.formatText(keyword, TextFormatter.ONLY_LETTERS).toCharArray();
        int shiftAmt = 0;
        int k = 0;
        for (int i = 0; i < plainText.length(); i++) {
            shiftAmt = 0;
            if (Character.isLetter(plainText.charAt(i))) {
                if (Character.isLowerCase(keywordArray[k])) {
                    shiftAmt = (int) keywordArray[k] - 97;
                } else if (Character.isUpperCase(keywordArray[k])) {
                    shiftAmt = (int) keywordArray[k] - 65;
                } else if (Character.isDigit(keywordArray[k])) {
                    shiftAmt = Character.getNumericValue(keywordArray[k]);
                }
                k++;
                if (k >= keywordArray.length) {
                    k = 0;
                }
            }
            if (encrypt) {
                text.append(shift(plainText.substring(i, i + 1), shiftAmt));
            } else {
                text.append(shift(plainText.substring(i, i + 1), -shiftAmt));
            }
        }
        String newText = new String(text);
        return newText;
    }

    /**
     * Translate one word to pig latin.  Word must have no punctuation or white
     * space for it to make sense.
     * @param word
     * @param sp
     * @param vwls
     * @return 
     */
    private static String wordToPigLatin(String word, ArrayList<String> sp, ArrayList<Character> vwls) {
        int prefixEnd = 0;
        if (word.equals("the")) {
            return word + "-ay";
        } else {
            for (int i = 0; i < word.length(); i++) {
                if (vwls.contains(word.charAt(i))) {
                    prefixEnd = i;
                    break;
                }
            }
            return word.substring(prefixEnd) + "-" + word.substring(0, prefixEnd) + "ay";
        }
    }

    /**
     * Translate a word from pig latin to english
     * @param word
     * @param sp
     * @param vwls
     * @return 
     */
    private static String wordFromPigLatin(String word, ArrayList<String> sp, ArrayList<Character> vwls) {
        int dashIndex = 0;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == '-') {
                dashIndex = i;
                break;
            }
        }
        if (dashIndex == 0) {
            return word;
        }
        int prefixEnd = 0;
        for (int i = dashIndex + 1; i < word.length(); i++) {
            if (vwls.contains(word.charAt(i))) {
                prefixEnd = i;
                break;
            }
        }
        return word.substring(dashIndex + 1, prefixEnd) + word.substring(0, dashIndex);
    }

    /**
     * PIG LATIN ITCHES-BAY
     * @param text text to mess with
     * @param encrypt true: to pig latin, false: from pig latin
     * @return 
     */
    public static String pigLatin(String text, boolean encrypt) {
         ArrayList<Character> vwls = new ArrayList<Character>();
        ArrayList<String> sp = new ArrayList<String>();
        for (char v : vowels) {
            vwls.add(v);
        }
        for (String str : specialCases) {
            sp.add(str);
        }
        text = text.trim().toLowerCase();
        StringBuffer pigText = new StringBuffer(text);
        Scanner scan = new Scanner(text);
        boolean moreWords = true;
        boolean inAWord = true;
        int startIndex = 0;
        int n = 0;
        while (moreWords) {
            while (inAWord) {
                if (Character.isLetter(pigText.charAt(n)) || pigText.charAt(n) == '\'' || pigText.charAt(n) == '-') {
                    n++;
                    if (n > pigText.length() - 1) {
                        if (encrypt) {
                            pigText.replace(startIndex, n, wordToPigLatin(pigText.substring(startIndex, n), sp, vwls));
                        } else {
                            pigText.replace(startIndex, n, wordFromPigLatin(pigText.substring(startIndex, n), sp, vwls));
                        }
                        moreWords = false;
                        inAWord = false;
                    }
                } else {
                    String newWord = "";
                    if (encrypt) {
                        newWord = wordToPigLatin(pigText.substring(startIndex, n), sp, vwls);
                    } else {
                        newWord = wordFromPigLatin(pigText.substring(startIndex, n), sp, vwls);
                    }
                    pigText.replace(startIndex, n, newWord);
                    n = startIndex + newWord.length();
                    inAWord = false;
                    moreWords = n < pigText.length() - 1;
                }
            }
            while (!inAWord) {
                n++;
                if (n >= pigText.length() - 1) {
                    inAWord = true;
                    moreWords = false;
                } else if (Character.isLetter(pigText.charAt(n))) {
                    startIndex = n;
                    inAWord = true;
                }
            }
        }
        return pigText.toString();
    }

    /**
     * Shift the nth letters of the text by an 1 in either direction. Does not
     * count spaces. Counts all but spaces.
     *
     * @param text: text to be analyzed
     * @param n: index to start at
     * @param patternLength: the amount of letter to jump (the "nth" letters)
     * @param right: whether or not the text will be shifted right
     * @return : the text with the nth letters shifted by one
     */
    public static String shiftNthLetters(String text, int n, int patternLength, boolean right) {
        char[] textArray = text.replaceAll("\\s", "").toCharArray();
        for (int i = n; i < textArray.length; i += patternLength) {
            if (right && Character.isLetter(textArray[i])) {
                textArray[i] = shift(String.valueOf(textArray[i]), 1).charAt(0);
            } else if (Character.isLetter(textArray[i])) {
                textArray[i] = shift(String.valueOf(textArray[i]), -1).charAt(0);
            }
        }
        return new String(textArray);
    }

    /**
     * Returns the nth letters of the text.  Does not count spaces.
     *
     * @param text: text to analyze
     * @param n: starting index
     * @param patternLength: amount of letters to jump (the "nth" letters"
     * @return : The nth letters of the text
     */
    public static String getNthLetters(String text, int n, int patternLength) {
        text = text.replaceAll("\\s", "");
        char[] textArray = text.toCharArray();
        ArrayList<Character> newArray = new ArrayList();
        for (int i = n; i < textArray.length; i += patternLength) {
            if (Character.isLetter(textArray[i])) {
                newArray.add(textArray[i]);
            }
        }
        return "" + newArray;
    }

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
            frequency[i][1] = 100. * (double) charAmts.get(i) / totalChars;
        }
        return frequency;
    }

    /**
     * Calculate the frequency of specific patterns in the text
     *
     * @param text: the message to parse through
     * @param lookFor: the patterns to look for
     * @return : A 2D array, each array containing a pattern and its frequency
     */
    public static Object[][] getSpecificMessageFrequency(String text, ArrayList<String> lookFor) {
        text = text.toLowerCase().replaceAll("\\s", "");
        ArrayList<Integer> amts = new ArrayList<Integer>();
        for (int i = 0; i < lookFor.size(); i++) {
            StringBuffer strb = new StringBuffer(text);
            int count = 0;
            if (!lookFor.get(i).isEmpty() && !lookFor.get(i).equals("")) {
                int index = strb.indexOf(lookFor.get(i));
                while (index != -1) {
                    count++;
                    strb = strb.delete(0, index + lookFor.get(i).length());
                    index = strb.indexOf(lookFor.get(i));
                }
            }
            amts.add(count);
        }

        Object[][] frequency = new Object[amts.size()][2];
        for (int i = 0; i < lookFor.size(); i++) {
            frequency[i][0] = lookFor.get(i);
            frequency[i][1] = 100. * (double) amts.get(i) / text.length();
        }
        return frequency;
    }

    //**OLD WAY TO ANALYZE
    /**
     * Create a box from the given string.  It accounts for if the given size needs more letters than
     * the text can provide.  If the box size does not account for all the letters, the extra text is
     * not included in the  box.
     * @param str: text to analyze
     * @param rows: rows the box should have
     * @param cols: amount of columns that the box should have.
     * @param orientation: how the text should be added to the box (horizontal, vertical, or diagonal)
     * @param lToR: if the text should be added from left to right or right to left
     * @param tToB: if the text should be added from the bottom to the top or the top to the bottom.
     * @return 
     */
    public static char[][] createBox(String str, int rows, int cols, int orientation, boolean lToR, boolean tToB) {
        StringBuffer text = new StringBuffer(str.toUpperCase().replaceAll("\\s", ""));
        if (cols > text.length()) {
            cols = text.length();
            rows = 1;
        } else if (rows > text.length()) {
            rows = text.length();
            cols = 1;
        } else if (rows * cols > text.length()) {
            rows = text.length() / cols;
        }   
        if (text.length() > rows * cols) {
            text.replace(0, text.length(), text.substring(0, rows * cols));
        }         
        char[][] box = getBoxArray(rows, cols, orientation, text.toString(), lToR, tToB);
        return box;
    }
    
    /**
     * SPACES DO NOT NEED TO BE REMOVED BEFOREHAND
     * prereq: text length == boxlength
     */
    private static char[][] getBoxArray(int rows, int cols, int orientation, String text, boolean lToR, boolean tToB) {
        text = text.replaceAll("\\s", "").toUpperCase();
        char[][] box = new char[rows][cols];
        int startCol = 0, cChangeAmt = 1, startRow = 0, rChangeAmt = 1, n = 0;
        if (!lToR) {
            startCol = cols - 1;
            cChangeAmt = -1;
        }
        if (!tToB) {
            startRow = rows - 1;
            rChangeAmt = -1;
        }
        switch (orientation) {
            case DIAGONAL:
                int currentCol = startCol, currentRow = startRow;
                while (n < cols * rows) {
                    int r = currentRow;
                    int c = currentCol;
                    while (r < rows && r >= 0 && c < cols && c >= 0) {
                        box[r][c] = text.charAt(n);
                        r += rChangeAmt;
                        c -= cChangeAmt;
                        n++;
                    }
                    if ((lToR && currentCol == cols - 1) || (!lToR && currentCol == 0)) {
                        currentRow += rChangeAmt;
                    } else {
                        currentCol += cChangeAmt;
                        currentRow = startRow;
                    }
                }
                break;
           case HORIZONTAL:
                for (int r = startRow; r < rows && r >= 0; r += rChangeAmt) {
                    for (int c = startCol; c < cols && c >= 0; c += cChangeAmt) {
                        box[r][c] = text.charAt(n);
                        n++;
                    }
                }
                break;
            case VERTICAL:
                for (int c = startCol; c < cols && c >= 0; c += cChangeAmt) {
                    for (int r = startRow; r < rows && r >= 0; r += rChangeAmt) {
                        box[r][c] = text.charAt(n);
                        n++;
                    }
                }
                break;
        }
        return box;
    }
    
    /**
     * Traverse a 2D array with the specified modifications, and return a String
     * of the array's contents.
     * @param box the 2D array to be traversed
     * @param orientation diagonal, vertical, horizontal
     * @param lToR left to right
     * @param tToB top to bottom
     * @return String of array's contents in specified order
     */
    public static String readBoxArray(char[][] box, int orientation, boolean lToR, boolean tToB) {
        int rows = box.length;
        int cols = box[0].length;
        StringBuffer boxText = new StringBuffer(rows * cols);
        int startCol = 0, cChangeAmt = 1, startRow = 0, rChangeAmt = 1, n = 0;
        if (!lToR) {
            startCol = cols - 1;
            cChangeAmt = -1;
        }
        if (!tToB) {
            startRow = rows - 1;
            rChangeAmt = -1;
        }
        switch (orientation) {
            case DIAGONAL:
                int currentCol = startCol, currentRow = startRow;
                while (n < cols * rows) {
                    int r = currentRow;
                    int c = currentCol;
                    while (r < rows && r >= 0 && c < cols && c >= 0) {
                        boxText.append(box[r][c]);
                        r += rChangeAmt;
                        c -= cChangeAmt;
                        n++;
                    }
                    if ((lToR && currentCol == cols - 1) || (!lToR && currentCol == 0)) {
                        currentRow += rChangeAmt;
                    } else {
                        currentCol += cChangeAmt;
                        currentRow = startRow;
                    }
                }
                break;
           case HORIZONTAL:
                for (int r = startRow; r < rows && r >= 0; r += rChangeAmt) {
                    for (int c = startCol; c < cols && c >= 0; c += cChangeAmt) {
                        boxText.append(box[r][c]);
                        n++;
                    }
                }
                break;
            case VERTICAL:
                for (int c = startCol; c < cols && c >= 0; c += cChangeAmt) {
                    for (int r = startRow; r < rows && r >= 0; r += rChangeAmt) {
                        boxText.append(box[r][c]);
                        n++;
                    }
                }
                break;
        }
        return boxText.toString();
    }
       
}
