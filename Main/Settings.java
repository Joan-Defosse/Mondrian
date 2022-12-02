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
     * Constructeur de la classe Settings.
     * lineWidth : la largeur des lignes entre les zones.
     * nbLeaves : le nombre maximum de zones (= noeuds dans notre AVL) de l'arbre.
     * mindimensionCut : dimension minimum d'une zone.
     * sameColorProb : la probabilité pour une zone d'avoir la même couleur que son père.
     * cutProportion : limite de coupe sur les bords de zones.
     * shades : la palette de couleurs.
     * randomizer : un générateur d'aléatoire.
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