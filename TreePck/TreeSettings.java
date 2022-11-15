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
    public int getNbLeaves() { return nbLeaves; }
    public double getSameColorProb() {
        return sameColorProb;
    }
    public double getCutProportion() { return cutProportion; }
    public int getMinDimensionCut() { return minDimensionCut; }
    public int getSeed() { return seed; }
}