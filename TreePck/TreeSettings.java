package TreePck;

public class TreeSettings {
    private int nbLeaves;
    private double sameColorProb;
    private double cutProportion;
    private int minDimensionCut;
    private int seed;

    // Constructor
    public TreeSettings(int nbLeaves, double sameColorProb, double cutProportion, int minDimensionCut, int seed){

        this.nbLeaves = nbLeaves;
        this.sameColorProb = sameColorProb;
        this.cutProportion = cutProportion;
        this.minDimensionCut = minDimensionCut;
        this.seed = seed;
    }

    // Getters
    int getNbLeaves() { return nbLeaves; }
    double getSameColorProb() {
        return sameColorProb;
    }
    double getCutProportion() { return cutProportion; }
    int getMinDimensionCut() { return minDimensionCut; }
    int getSeed() { return seed; }
}