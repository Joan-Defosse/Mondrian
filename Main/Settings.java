package Main;

import java.util.Random;
import Image.Shades;

public class Settings {

    // PRIVATE ATTRIBUTES ===================================== //

    private Integer lineWidth;
    private Integer nbLeaves;
    private Integer minDimensionCut;
    private Double sameColorProb;
    private Double cutProportion;
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
    public Settings(Integer lineWidth, Integer nbLeaves, Integer minDimensionCut, Double sameColorProb, Double cutProportion, Shades shades, Random randomizer){

        this.lineWidth = lineWidth;
        this.nbLeaves = nbLeaves;
        this.minDimensionCut = minDimensionCut;
        this.sameColorProb = sameColorProb;
        this.cutProportion = cutProportion;
        this.shades = shades;
        this.randomizer = randomizer;
    }

    // PUBLIC GETTERS ===================================== //

    public int getLineWidth() { return lineWidth; }
    public int getNbLeaves() { return nbLeaves; }
    public int getMinDimensionCut() { return minDimensionCut; }
    public double getSameColorProb() { return sameColorProb; }
    public double getCutProportion() { return cutProportion; }
    public Shades getShades() { return shades; }
    public Random getRandomizer() { return randomizer; }
}