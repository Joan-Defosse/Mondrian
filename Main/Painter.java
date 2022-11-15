package Main;

import TreePck.*;
import java.awt.*;

public class Painter {

    public static void main(String[] args) {

        Tree tree = generateRandomTree(800, 1000, 50, 0.7, 0.1, 10, 666);

        System.out.println("Aucune erreur de build.");
    }

    public static Tree generateRandomTree(int height, int width, int nbLeaves, double sameColorProb, double proportionCut, int minDimensionCut, int seed) {

        Tree tree = initTree(height, width, nbLeaves, sameColorProb, proportionCut, minDimensionCut, seed);

        return tree;
    }
    public static Tree initTree(int height, int width, int nbLeaves, double sameColorProb, double proportionCut, int minDimensionCut, int seed) {

        TreeSettings settings = new TreeSettings(nbLeaves, sameColorProb, proportionCut, minDimensionCut, seed);
        Tree tree = new Tree(settings, Color.WHITE, 0, height, 0,  width);
        
        return tree;
    }
}
