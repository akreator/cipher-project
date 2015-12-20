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
                + "\n\n";
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
    public int compareTo(History h) {
        return repetitions - h.repetitions;
    } 
    
}
