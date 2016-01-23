
package texttools;

/**
 * WIP
 * Class that will hopefully take the place of Object in Object[]s
 * @author Audrey
 */
public class FrequencyPair implements Comparable<FrequencyPair>{
    
    private String pattern;
    private double frequency;
    
    public FrequencyPair(String str, double amt) {
        pattern = str;
        frequency = amt;
    }
    
    public String getPattern() {
        return pattern;
    }
    
    public double getFrequency() {
        return frequency;
    }
    
    public void setFrequency(double f) {
        frequency = f;
    }

    @Override
    public int compareTo(FrequencyPair fp) {
        int diff = pattern.length() - fp.pattern.length();
        if (diff == 0)
            return pattern.compareTo(fp.pattern);
        return diff;
    }
}
