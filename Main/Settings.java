package Main;

import java.util.Random;

public class Settings {

    // PRIVATE ATTRIBUTES ===================================== //

    private int nbLeaves;
    private int minDimensionCut;
    private double sameColorProb;
    private double cutProportion;
    private Random randomizer;

    // PUBLIC CONSTRUCTORS ===================================== //
    public Settings(int nbLeaves, int minDimensionCut, double sameColorProb, double cutProportion, Random randomizer){

        this.nbLeaves = nbLeaves;
        this.minDimensionCut = minDimensionCut;
        this.sameColorProb = sameColorProb;
        this.cutProportion = cutProportion;
        this.randomizer = randomizer;
    }

    // PUBLIC GETTERS ===================================== //

    public int getNbLeaves() { return nbLeaves; }
    public int getMinDimensionCut() { return minDimensionCut; }
    public double getSameColorProb() {
        return sameColorProb;
    }
    public double getCutProportion() { return cutProportion; }
    public Random getRandomizer() { return randomizer; }
}