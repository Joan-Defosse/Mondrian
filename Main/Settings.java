package Main;

public class Settings {

    // PRIVATE ATTRIBUTES ===================================== //

    private int lineWidth;
    private int nbLeaves;
    private int minDimensionCut;
    private double sameColorProb;
    private double cutProportion;
    private long seed;

    // PUBLIC CONSTRUCTORS ===================================== //
    public Settings(int lineWidth, int nbLeaves, int minDimensionCut, double sameColorProb, double cutProportion, long seed){

        this.lineWidth = lineWidth;
        this.nbLeaves = nbLeaves;
        this.minDimensionCut = minDimensionCut;
        this.sameColorProb = sameColorProb;
        this.cutProportion = cutProportion;
        this.seed = seed;
    }

    // PUBLIC GETTERS ===================================== //
    public int getLineWidth() { return lineWidth; }
    public int getNbLeaves() { return nbLeaves; }
    public int getMinDimensionCut() { return minDimensionCut; }
    public double getSameColorProb() {
        return sameColorProb;
    }
    public double getCutProportion() { return cutProportion; }
    public long getSeed() { return seed; }
}