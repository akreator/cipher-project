package CipherClasses;


import java.util.ArrayList;
import javax.swing.JTable;

public class Text {

    public static final String[] alphabet = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r",
        "s", "t", "u", "v", "w", "x", "y", "z"};
    public static double[] standardRelativeFrequency = {0.64290624, 0.11745023999999998, 0.21899904, 0.33479616, 0.99990144, 0.17538816000000002,
        0.1586208, 0.47971968000000004, 0.54836352, 0.01204416, 0.06077184, 0.316848, 0.18940032, 0.53128128, 0.59095104, 0.15185088,
        0.0074784, 0.47129664, 0.49806143999999997, 0.71288832, 0.21710975999999998, 0.07698816, 0.18585792, 0.011807999999999999,
        0.15539328, 0.0058252799999999995};
    public static final int SPECIAL_FORMAT = 0;
    public static final int UPPERCASE = 1;
    public static final int LOWERCASE = 2;
    public static final int NO_SPACES = 3;
    public static final int NO_NUMBERS = 4;
    public static final int NO_PUNCTUATION = 5;
    public static final int ONLY_LETTERS = 6;
    public static final int NO_CHANGE = 7;
    public static final int NUMS_TO_LETTERS = 8;

    public static String shift(String text, int shiftAmt) {
        char[] plainTextArray = text.toCharArray();
        for (int i = 0; i < plainTextArray.length; i++) {
            if (Character.isLetter(plainTextArray[i])) {
                char temp = plainTextArray[i];
                plainTextArray[i] = (char) (shiftAmt + (int) plainTextArray[i]);

                if (isLowercase(temp) && plainTextArray[i] > 122 || isUppercase(temp) && plainTextArray[i] > 90) {
                    plainTextArray[i] = (char) ((int) plainTextArray[i] - 26);
                } else if (Text.isLowercase(temp) && plainTextArray[i] < 97 || Text.isUppercase(temp) && plainTextArray[i] < 65) {
                    plainTextArray[i] = (char) ((int) plainTextArray[i] + 26);
                } 

                if (Text.isLowercase(temp) && plainTextArray[i] < 97) {
                    plainTextArray[i] = (char) ((int) plainTextArray[i] + 26);
                } else if (Text.isUppercase(temp) && plainTextArray[i] < 65) {
                    plainTextArray[i] = (char) ((int) plainTextArray[i] + 26);
                }
            }
        }
        return new String(plainTextArray);
    }

    public static String unnecessarilyLongSubstitute(String text, JTable switchTable) {
        ArrayList<ArrayList<Integer>> indexes = new ArrayList<ArrayList<Integer>>();
        //indexes is an arraylist containing each character followed by the indicies that its found at
        // ----> characters are in same order as messagechars
        //replacements holds the values of messagechars that should be switched with the letters of the alphabet
        // ----> [new letter] [old letter]
        String[][] replacements = new String[26][2];
        for (int i = 0; i < 26; i++) {
            if (switchTable.getValueAt(0, i) != null) {
                replacements[i][0] = alphabet[i];
                replacements[i][1] = (switchTable.getValueAt(0, i).toString().toLowerCase());
            }
            //goal is to find replacement string in text, and then replace it with corresponding letter of alphabet
        }

        //go through every value of replacements[i] (the part to be replaced in the text)
        //then, in order, find all of the indexes and add them to a new arraylist
        //after that, add the new arraylist to indexes
        StringBuffer changedText = new StringBuffer(text.toLowerCase());
        for (int i = 0; i < replacements.length; i++) {
            indexes.add(new ArrayList<Integer>());
            if (!replacements[i][1].equals("")) {
                int currentIndex = 0;
                int previousIndex = 0;
                boolean lastInstance = false;
                while (!lastInstance) {
                    previousIndex = currentIndex;
                    currentIndex = changedText.indexOf(replacements[i][1], previousIndex);
                    if (currentIndex == -1) {
                        lastInstance = true;
                    } else {
                        indexes.get(i).add(currentIndex);
                        currentIndex += replacements[i][0].length();
                    }
                }
            }
        }
        //then, for i < replacements.length, for k < indexes.get(i).size(), delete and replace.
        for (int i = 0; i < replacements.length; i++) {
            for (int k = 0; k < indexes.get(i).size(); k++) {
                changedText.delete(indexes.get(i).get(k), indexes.get(i).get(k) + replacements[i][1].length());
                changedText.insert(indexes.get(i).get(k), replacements[i][0].toUpperCase());
            }
        }
        return new String(changedText);
    }

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

    public static boolean isLowercase(char c) {
        int cNum = (int) c;
        return (cNum >= 97 && cNum <= 122);
    }

    public static boolean isUppercase(char c) {
        int cNum = (int) c;
        return (cNum >= 65 && cNum <= 90);
    }

    public static String vigenereCrypt(String plainText, String keyword, boolean encrypt) {
        StringBuffer text = new StringBuffer();
        char[] keywordArray = keyword.toCharArray();
        int shiftAmt = 0;
        int k = 0;
        for (int i = 0; i < plainText.length(); i++) {
            shiftAmt = 0;
            if (Character.isLetter(plainText.charAt(i))) {
                if (isLowercase(keywordArray[k])) {
                    shiftAmt = (int) keywordArray[k] - 97;
                } else if (isUppercase(keywordArray[k])) {
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

    public static String shiftNthLetters(String text, int n, int patternLength, boolean right, boolean format) {
        char[] textArray = text.replaceAll("\\s", "").toCharArray();
        for (int i = n; i < textArray.length; i += patternLength) {
            if (right && Character.isLetter(textArray[i])) {
                textArray[i] = shift(String.valueOf(textArray[i]), 1).charAt(0);
            } else if (Character.isLetter(textArray[i])) {
                textArray[i] = shift(String.valueOf(textArray[i]), -1).charAt(0);
            }
        }
        if (format)
            return formatText(new String(textArray), SPECIAL_FORMAT);
        else
            return new String(textArray);
    }

    public static String getNthLetters(String text, int n, int patternLength) {
        char[] textArray = text.toCharArray();
        ArrayList<Character> newArray = new ArrayList();
        for (int i = n; i < textArray.length; i += patternLength) {
            if(Character.isLetter(textArray[i]))
                newArray.add(textArray[i]);
        }
        return "" + newArray;
    }
    
    public static String group(String text, int amount) {
        StringBuffer newText = new StringBuffer();
        text = text.replaceAll("\\s", "");
        for(int i = 0; i < text.length() - amount; i += amount) {
            newText.append(text.substring(i, i + amount));
            newText.append(" ");
            if (i + amount + amount >= text.length()) {
                newText.append(text.substring(i + amount));
            }
        }
        return new String(newText);
    }

    public static String formatText(String text, int type) {
        switch (type) {
            case SPECIAL_FORMAT:
                char[] original = text.toUpperCase().replaceAll("\\s", "").toCharArray();
                char[] formatted = new char[original.length * 2];
                formatted[0] = original[0];
                formatted[1] = ' ';
                for (int i = 2; i < formatted.length; i += 2) {
                    formatted[i] = original[i / 2];
                    formatted[i + 1] = ' ';
                }
                return new String(formatted);
            case UPPERCASE:
                return text.toUpperCase();
            case LOWERCASE:
                return text.toLowerCase();
            case NO_SPACES:
                return text.replaceAll("\\s", "");
            case NO_NUMBERS:
                return text.replaceAll("\\d", "");
            case NO_PUNCTUATION:
                return text.replaceAll("[^a-zA-Z\\s\\d]", "");
            case ONLY_LETTERS:
                return text.replaceAll("[^a-zA-Z]", "");
            case NUMS_TO_LETTERS:
                String nText = text;
                for (int i = 0; i < 10; i++) {
                    nText = nText.replaceAll(Integer.toString(i), alphabet[i].toUpperCase());
                }
                return nText;
                
        }
        System.out.println("what");
        return "ERROR";
    }
}
