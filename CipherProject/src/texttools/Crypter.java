package texttools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Crypter {
    private static final String[] vowels = {"a", "e", "i", "o", "u"};
    private static final String[] binaryArray = {" ", "!", "\"", "#", "$", "%","&", "'", 
        "(", ")", "*", "+", ",", "-", ".", "/", "0", "1", "2", "3", "4", "5", "6",
        "7", "8", "9", ":", ";", "<", "=", ">", "?", "@", "A", "B", "C", "D",
        "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", 
        "T", "U", "V", "W", "X", "Y", "Z", "[", "\\", "]", "^", "_", "'", "a", 
        "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", 
        "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "{", "|", "}", "~", ""};

    public static final int HORIZONTAL = 0, VERTICAL = 1, DIAGONAL = 2;

    private Crypter() {
    }    

    /**
     * Method for shifting characters in a given text by a certain amount.
     * Only applies to characters that  are letters.
     * @param text: text to be converted
     * @param shiftAmt: the amount that the letters should be shifted
     * @return : changed text
     */
    public static String shift(String text, int shiftAmt) {
        char[] plainTextArray = text.toCharArray();
        for (int i = 0; i < plainTextArray.length; i++) {
            if (Character.isLetter(plainTextArray[i]) && (int) plainTextArray[i] < 123) {
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
        StringBuffer text = new StringBuffer("");
        str = str.toLowerCase();
        Map<String, String> map = new HashMap<>();
        int max = 0;
        for (String[] replacement : replacements) {
            map.put(replacement[0], replacement[1]);
            max = Math.max(replacement[0].length(), max);
        }
        int i = 0;
        while (i < str.length()) {
            boolean found = false;
            for (int n = max; n > 0; n--) {
                if (i + n <= str.length() && map.containsKey(str.substring(i, i + n)) && !map.get(str.substring(i, i + n)).equals("")) {
                    text.append(map.get(str.substring(i, i + n)).toUpperCase());
                    i += n;
                    found = true;
                    break;
                }
            }
            if (!found) {
                text.append(str.substring(i, i + 1));
                i++;
            }
        }
        return new String(text);
    }
    
    
    /**
     * Encrypt or decrypt text using a Vigenere cipher.
     * Only works with letters; removes all non-letters from text before analyzing
     *
     * @param plainText: text to be encrypted/decrypted
     * @param keyword: keyword to use in encryption
     * @param encrypt: true = encrypt; false = decrypt
     * @return : encrypted/decrypted text
     */
    public static String vigenereCrypt(String plainText, String keyword, boolean encrypt) {
        StringBuffer text = new StringBuffer();
        char[] keywordArray = TextFormatter.formatText(keyword, TextFormatter.ONLY_LETTERS).toCharArray();
        if (keywordArray.length == 0)
            return plainText;
        int k = 0;
        for (int i = 0; i < plainText.length(); i++) {
            int shiftAmt = 0;
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
     * Searches text for patterns of a given length.  
     * **INCLUDES SPACES!!!
     * @param length
     * @param text
     * @return ArrayList of strings in format: pattern, repetitions, shortest distance
     */
    public static ArrayList<String> autoFindPatterns(int length, String text) {
        ArrayList<String> stats = new ArrayList<>();
        ArrayList<String> patternsChecked = new ArrayList<>();
        String cText = text.toLowerCase().replaceAll("\\s", "");
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
                if (shortestDistance == Integer.MAX_VALUE)
                    shortestDistance = 0;
                stats.add(pattern + "๘" + repetitions + "๘" + shortestDistance);
            }
        }
        return stats;
    }

    /**
     * Translate one word to pig latin.  Word must have no punctuation or white
     * space for it to make sense.
     * @param word
     * @param sp
     * @param vwls
     * @return 
     */
    private static String wordToPigLatin(String word, ArrayList<String> vwls) {
        int prefixEnd = 0;
        if (word.length() >= 2) {
            if (word.substring(0, 2).equals("qu")) {
                prefixEnd = 2;
            } else {
                for (int i = 0; i < word.length(); i++) {
                    if (vwls.contains(word.substring(i, i + 1).toLowerCase())) {
                        prefixEnd = i;
                        break;
                    }
                } //end of for-loop
            } 
        } //end of first if-statement
        return word.substring(prefixEnd) + "-" + word.substring(0, prefixEnd) + "ay";
    }

    /**
     * Translate a word from pig latin to english
     * @param word
     * @param sp
     * @param vwls
     * @return 
     */
    private static String wordFromPigLatin(String word, ArrayList<String> vwls) {
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
            if (vwls.contains(word.substring(i, i + 1).toLowerCase())) {
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
        ArrayList<String> vwls = new ArrayList<>();
        for (String v : vowels) {
            vwls.add(v);
        }
        text = text.trim();
        StringBuffer pigText = new StringBuffer(text);
        int startIndex = 0, n = 0;
        boolean moreWords = n < pigText.length() - 1,
                inAWord = false;
        while (moreWords) {
            while (inAWord) {
                if (Character.isLetter(pigText.charAt(n)) || pigText.charAt(n) == '\'' || pigText.charAt(n) == '-') {
                    n++;
                    if (n > pigText.length() - 1) {
                        if (encrypt) {
                            pigText.replace(startIndex, n, wordToPigLatin(pigText.substring(startIndex, n), vwls));
                        } else {
                            pigText.replace(startIndex, n, wordFromPigLatin(pigText.substring(startIndex, n), vwls));
                        }
                        moreWords = false;
                        inAWord = false;
                    }
                } else {
                    String newWord;
                    if (encrypt) {
                        newWord = wordToPigLatin(pigText.substring(startIndex, n), vwls);
                    } else {
                        newWord = wordFromPigLatin(pigText.substring(startIndex, n), vwls);
                    }
                    pigText.replace(startIndex, n, newWord);
                    n = startIndex + newWord.length();
                    inAWord = false;
                    moreWords = n < pigText.length() - 1;
                }
            }
            while (!inAWord) {
                if (n >= pigText.length() - 1) {
                    inAWord = true;
                    moreWords = false;
                } else if (Character.isLetter(pigText.charAt(n))) {
                    startIndex = n;
                    inAWord = true;
                }
                n++;
            }
        }
        return pigText.toString();
    }

    /**
     * Shift the nth letters of the text by an 1 in either direction. Does not
     * count spaces, but includes punctuation and numbers.  
     * 
     * (Only removes spaces)
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
     * Returns the nth letters of the text.  Does not count spaces, but includes
     * punctuation and numbers.
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
    
    /***
     * Translate normal text to binary code
     * @param text normal text
     * @return binary code
     */
    public static String toBinary(String text) {
        StringBuffer newText = new StringBuffer();
        StringBuffer binaryNum = new StringBuffer(8);
        int value = 0;
        for (int i = 0; i < text.length(); i++) {
            binaryNum.replace(0, 8, "");
            value = text.charAt(i);
            for (int j = 8; j > 0; j--) {
                binaryNum.append(value % 2);
                value /= 2;
            }
            newText.append(binaryNum.reverse()).append(" ");
        }
        return newText.toString();
    }
    
    /***
     * Translate binary code to standard text/digits/punctuation
     * @param text binary code
     * @return normal text
     */
    public static String fromBinary(String text) {
        text = text.replaceAll("[^01]", "");
        StringBuffer newText = new StringBuffer();
        String binaryNum;
        int index;
        for (int i = 0; i <= text.length() - 8; i += 8) {
            binaryNum = text.substring(i, i + 8);
            index = 0;
            for (int j = 7; j > 0; j--) {
                if (binaryNum.charAt(j) == '1') index += (int) Math.pow(2, 7 - j);
            }
            if (index >= 32)
                newText.append(binaryArray[index - 32]);
            else if (index == 9)
                newText.append("\t");
            else if (index == 10 || index == 13)
                newText.append("\n");
            else
                newText.append("?");
            
        }
        return newText.toString();
    }       
}
