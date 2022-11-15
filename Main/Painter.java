package Main;

import TreePck.*;
import java.awt.Color;
import java.util.Random;

public class Painter {

    public static void main(String[] args) {

        Tree tree = generateRandomTree(800, 1000, 50, 0.7, 0.1, 10, 666);

        System.out.println("Aucune erreur de build.");
    }

    public static Tree generateRandomTree(int height, int width, int nbLeaves, double sameColorProb, double cutProportion, int minDimensionCut, int seed) {

        Tree T = initTree(height, width, nbLeaves, sameColorProb, cutProportion, minDimensionCut, seed);

        return T;
    }
    public static Tree initTree(int height, int width, int nbLeaves, double sameColorProb, double cutProportion, int minDimensionCut, int seed) {

        TreeSettings settings = new TreeSettings(nbLeaves, sameColorProb, cutProportion, minDimensionCut, seed);
        Tree T = new Tree(settings, Color.WHITE, new Position(0, height, 0,  width));
        BoolIntPair bip = chooseDivision(T.getHeight(), T.getWidth(), T.getSettings().getCutProportion());
        
        return T;
    }

    public static BoolIntPair chooseDivision(int height, int width, double proportionCut) {

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
