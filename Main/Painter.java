package Main;

import TreePck.*;
import java.awt.Color;
import java.util.Random;

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

    public BoolIntPair chooseDivision(int height, int width, double proportionCut) {

        Boolean axis = chooseAxis(height, width);
        int result;

        if (axis = Tree.AxisX) {

            result = chooseCoordinate(width, proportionCut);
        }
        else {

            result = chooseCoordinate(height, proportionCut);
        }

        return new BoolIntPair(axis, result);
    }
    private static Boolean chooseAxis(int height, int width) {

        int ProbaX = width / width + height;

        if (Math.random() > ProbaX) {

            return Tree.AxisY;
        }

        return Tree.AxisX;
    }

    private static int chooseCoordinate(int size, double proportionCut) {

        double rand = Math.random();

        while(rand < proportionCut || rand > 1 - proportionCut)  {

            rand = Math.random();
        }

        return (int)(size * rand);
    }


}
