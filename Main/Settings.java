package Main;

import java.util.Random;
import Struct.Shades;

public class Settings {

    // PRIVATE ATTRIBUTES ===================================== //

    private int nbLeaves;
    private int minDimensionCut;
    private double sameColorProb;
    private double cutProportion;
    private Shades shades;
    private Random randomizer;

    // PUBLIC CONSTRUCTORS ===================================== //

    /*
     * Constructeur de la classe Settings
     * nbLeaves : le nombre maximum de feuille dans l'arbre
     * mindimensionCut : dimension minimum d'une feuille 
     * sameColorProb : la probabilité d'avoir la même couleur que la feuille père
     * cutProportion : limite de coupe sur les bords
     * shades : la palette de couleur
     * randomizer : un nombre aléatoire
     */
    public Settings(int nbLeaves, int minDimensionCut, double sameColorProb, double cutProportion, Shades shades, Random randomizer){

        this.nbLeaves = nbLeaves;
        this.minDimensionCut = minDimensionCut;
        this.sameColorProb = sameColorProb;
        this.cutProportion = cutProportion;
        this.shades = shades;
        this.randomizer = randomizer;
    }

    // PUBLIC GETTERS ===================================== //

    public int getNbLeaves() { return nbLeaves; }
    public int getMinDimensionCut() { return minDimensionCut; }
    public double getSameColorProb() {
        return sameColorProb;
    }
    public double getCutProportion() { return cutProportion; }
    public Shades getShades() { return shades; }
    public Random getRandomizer() { return randomizer; }
}