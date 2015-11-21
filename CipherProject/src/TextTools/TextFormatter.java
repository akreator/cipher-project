package TextTools;

import javax.swing.JOptionPane;

public class TextFormatter {

    public static final int SPECIAL_FORMAT = 0,
            UPPERCASE = 1, LOWERCASE = 2, NO_SPACES = 3,
            NO_NUMBERS = 4, NO_PUNCTUATION = 5, ONLY_LETTERS = 6,
            NO_CHANGE = 7, NUMS_TO_LETTERS = 8, GROUP = 9,
            CHANGE_TEXT_SIZE = 101;

    public static final String[] alphabet = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r",
        "s", "t", "u", "v", "w", "x", "y", "z"};

    private TextFormatter() {
    }

    /**
     * Group the text by a given amount
     *
     * @param text: the text to be grouped
     * @param amount: the length of a group
     * @return : the grouped text
     */
    public static String group(String text, int amount) {
        StringBuffer newText = new StringBuffer();
        text = text.replaceAll("\\s", "");
        for (int i = 0; i < text.length() - amount; i += amount) {
            newText.append(text.substring(i, i + amount));
            newText.append(" ");
            if (i + amount + amount >= text.length()) {
                newText.append(text.substring(i + amount));
            }
        }
        return new String(newText);
    }

    public static String numsToLetters(String text) {
        StringBuffer newText = new StringBuffer(text);
        int n = 0;
        boolean isTwoDNum = false;
        while (n < newText.length()) {
            if (Character.isDigit(newText.charAt(n))) {
                if (n < newText.length() - 1 && Character.isDigit(newText.charAt(n + 1)) && Integer.parseInt(newText.substring(n, n + 2)) < 26) {
                    newText.replace(n, n + 2, alphabet[Integer.parseInt(newText.substring(n, n + 2))]);
                } else {
                    newText.replace(n, n + 1, alphabet[Integer.parseInt(newText.substring(n, n + 1))]);
                }
            }
            n++;
        }
        return newText.toString();
    }

    /**
     * Format the given text
     *
     * @param text: text to be formatted
     * @param type: How the text is to be formatted
     * @return : the formatted text
     */
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
                return numsToLetters(text);
            case GROUP:
                int groupNum = 0;
                String input = JOptionPane.showInputDialog(null, "Enter the group length:", "Group Text", JOptionPane.PLAIN_MESSAGE);
                try {
                    groupNum = Integer.parseInt(input);
                    return (TextFormatter.group(text, groupNum));
                } catch (NumberFormatException n) {
                    JOptionPane.showMessageDialog(null, "Sorry, that is not a valid number", "Error", JOptionPane.ERROR_MESSAGE);
                }
                break;
        }
        return "ERROR";
    }
}
