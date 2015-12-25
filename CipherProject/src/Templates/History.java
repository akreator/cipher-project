package Templates;


/**
 *
 * @author Audrey
 */
public class History implements Comparable<History> {
    private String pattern;
    private int repetitions;
    private int shortestDistance;
    
    public History(String p, int r, int d) {
        pattern = p;
        repetitions = r;
        shortestDistance = d;
    }
    
    public String getStats() {
        return "Pattern: " + pattern + "\nRepetitions: " + repetitions 
                + "\nShortest distance between repetitons: " + shortestDistance
                + "\n";
    }
    
    public String getPattern() {
        return pattern;
    }
    
    public int getRepetitions() {
        return repetitions;
    }
    
    public int getShortestDistance() {
        return shortestDistance;
    }
    @Override
    public String toString() {
        return pattern + "," + repetitions + "," + shortestDistance;
    }

    @Override
    /***
     * sorted most to least useful
     * (more useful given negative value)
     */
    public int compareTo(History h) {
        int diff = h.repetitions - repetitions; //most to least repetitons
        if (diff == 0) {
            diff = shortestDistance - h.shortestDistance; //shortest distance
            if (diff == 0) 
                diff = h.pattern.compareTo(pattern); //alphabetically
        }
        return diff;
    } 
    
}
