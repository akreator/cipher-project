package CipherClasses;


import java.util.ArrayList;


public class Calculations {
    
    public static int getGCF(int a, int b) {
        ArrayList<Integer> aFacts = getFactors(a);
        ArrayList<Integer> bFacts = getFactors(b);

        int commonFactor = 1;
        boolean aFactsSmaller = Math.min(aFacts.size(), bFacts.size()) == aFacts.size();
        if (aFactsSmaller) {
            for (int k = 0; k < aFacts.size(); k++) {
                if (bFacts.contains(aFacts.get(k))) {
                    commonFactor = aFacts.get(k);
                }
            }
        } else {
            for (int k = 0; k < bFacts.size(); k++) {
                if (aFacts.contains(bFacts.get(k))) {
                    commonFactor = bFacts.get(k);
                }
            }
        }
        return commonFactor;
    }
    
    public static int getLCM(int a, int b) {
        return 0;
    }
    
    public static ArrayList<Integer> getFactors(int n) {
        ArrayList<Integer> aFacts = new ArrayList();
        for (int i = 2; i <= n; i++) {
            if (n % i == 0) {
                aFacts.add(i);
            }
        }
        return aFacts;
    }
    
    public static ArrayList<Integer> getPrimeFactors(int n) {
        ArrayList<Integer> primeFactors = new ArrayList();
        int i = 2;
        while (i <= n) {
            if (n % i == 0) {
                primeFactors.add(i);
                n /= i;
                i = 2;
            } else if (i == n) {
                primeFactors.add(i);
                i++;
            } else {
                i++;
            }
        }
        return primeFactors;
    }
}
