package Main;

import TreePck.*;
import java.awt.Color;

public class Painter {

    public static void main(String[] args) {

        Tree tree = generateRandomTree(800, 1000, 10, 0.7, 0.1, 10, 666);

        System.out.println("Aucune erreur de build.");
    }

    public static Tree generateRandomTree(int height, int width, int nbLeaves, double sameColorProb, double cutProportion, int minDimensionCut, int seed) {

        TreeSettings settings = new TreeSettings(nbLeaves, sameColorProb, cutProportion, minDimensionCut, seed);
        Tree T = initTree(height, width, settings);

        for(int i = 2; i <= nbLeaves; i++) {
            Tree A = chooseLeaf(T, minDimensionCut);
            Tree L, R;
            Zone zoneL, zoneR;
            Color colorL, colorR;

            // si aucune feuille ne peut être découpée == fin du programme
            if(A == null) {
                return T;
            }

            BoolIntPair bip = chooseDivision(A.getHeight(), A.getWidth(), settings.getCutProportion());

            A.setAxis(bip.axis);

            if (A.getAxis() == Tree.AxisX) {
                A.setLineCut(A.getLeft() + bip.cut);
                zoneL = new Zone(A.getLeft(), A.getLineCut() - 1, A.getDown(), A.getUp());
                zoneR = new Zone(A.getLineCut(), A.getRight(), A.getDown(), A.getUp());
            }
            else {

                A.setLineCut(A.getDown() + bip.cut);
                zoneL = new Zone(A.getLeft(), A.getRight(), A.getDown(), A.getLineCut() - 1);
                zoneR = new Zone(A.getLeft(), A.getRight(), A.getLineCut(), A.getUp());
            }

            colorL = chooseColor(A.getColor(), settings.getSameColorProb());
            colorR = chooseColor(A.getColor(), settings.getSameColorProb());

            L = new Tree(colorL, zoneL);
            R = new Tree(colorR, zoneR);

            A.setL(L);
            A.setR(R);
        }
        return T;
    }
    public static Tree initTree(int height, int width, TreeSettings settings) {

        Tree T = new Tree(Color.WHITE, new Zone(0, width, 0,  height));
        Tree L, R;
        Zone zoneL, zoneR;
        Color colorL, colorR;
        BoolIntPair bip = chooseDivision(T.getHeight(), T.getWidth(), settings.getCutProportion());

        T.setAxis(bip.axis);

        if (T.getAxis() == Tree.AxisX) {

            T.setLineCut(T.getLeft() + bip.cut);
            zoneL = new Zone(T.getLeft(), T.getLineCut() - 1, T.getDown(), T.getUp());
            zoneR = new Zone(T.getLineCut(), T.getRight(), T.getDown(), T.getUp());
        }
        else {

            T.setLineCut(T.getDown() + bip.cut);
            zoneL = new Zone(T.getLeft(), T.getRight(), T.getDown(), T.getLineCut() - 1);
            zoneR = new Zone(T.getLeft(), T.getRight(), T.getLineCut(), T.getUp());
        }

        colorL = chooseColor(T.getColor(), settings.getSameColorProb());
        colorR = chooseColor(T.getColor(), settings.getSameColorProb());

        L = new Tree(colorL, zoneL);
        R = new Tree(colorR, zoneR);

        T.setL(L);
        T.setR(R);

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

        int ProbaX = width / (width + height);

        if (Math.random() > ProbaX) {

            return Tree.AxisY;
        }

        return Tree.AxisX;
    }

    private static int chooseCoordinate(int size, double proportionCut) {

        double rand = proportionCut + Math.random() * (1 - (2 * proportionCut));

        return (int)(size * rand);
    }

    public static Color chooseColor(Color FColor, double sameColorProb) {

        if(Math.random() > sameColorProb) {
            return randomColor();
        }
        return FColor;

    }
    private static Color randomColor() {
        int rand = (int) (5 * Math.random());
        Color result;
        switch (rand) {
            case 0:
                result = Color.WHITE;
                break;
            case 1:
                result = Color.BLACK;
                break;
            case 2:
                result = Color.BLUE;
                break;
            case 3:
                result = Color.RED;
                break;
            default:
                result = Color.YELLOW;
                break;
        }
        return result;
    }

    private static Tree chooseLeaf(Tree T, int minDimensionCut) {

        if(T.isLeaf()) {
            if(T.getHeight() < minDimensionCut || T.getWidth() < minDimensionCut) {
                return null;
            }
            return T;
        }

        Tree L = chooseLeaf(T.getL(), minDimensionCut);
        Tree R = chooseLeaf(T.getR(), minDimensionCut);

        if(R != null && L != null){
            if(L.getWeight() > R.getWeight()){
                return L;
            }
            return R;
        }
        if(R == null && L == null) {
            return null;
        }
        if(R == null) {
            return L;
        }
        return R;
    }
}
