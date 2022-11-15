package TreePck;

public class TreeSettings {
    private int nbLeaves;
    private double sameColorProb;
    private double cutProportion;
    private int minDimensionCut;
    private double seed;

    // Constructor
    public TreeSettings(int nbLeaves, double sameColorProb, double cutProportion, int minDimensionCut, double seed){
        this.nbLeaves = nbLeaves;
        this.sameColorProb = sameColorProb;
        this.cutProportion = cutProportion;
        this.minDimensionCut = minDimensionCut;
        this.seed = seed;
    }

    // Getter
    int getNbLeaves() {
        return nbLeaves;
    }
    double getSameColorProb() {
        return sameColorProb;
    }
    double getCutProportion() {
        return cutProportion;

    }
    int getMinDimensionCut() {
        return minDimensionCut;

    }
}
