package Main;

import TreePck.*;
import java.awt.Color;
import java.io.IOException;

public class Painter {

    public static void main(String[] args) {

        TreeSettings settings = new TreeSettings(50, 0.7, 0.1, 10, 666);
        Tree T = generateRandomTree(1200, 1800, settings);

        Image image = toImage(T);

        try {

            image.save("test.png");
        }
        catch (IOException e) {

            throw new RuntimeException(e);
        }

        System.out.println("Aucune erreur de build.");
    }

    public static Tree generateRandomTree(int height, int width, TreeSettings settings) {

        Tree T = new Tree(Color.WHITE, new Zone(0, width, 0,  height));

        cutLeaf(T, settings);

        for(int i = 2; i < settings.getNbLeaves(); i++) {

            Tree A = chooseLeaf(T, settings.getMinDimensionCut());

            // si aucune feuille ne peut être découpée == fin du programme
            if(A == null)
                return T;

            cutLeaf(A, settings);
        }
        return T;
    }

    public static Image toImage(Tree T) {

        Image image = new Image(T.getWidth(), T.getHeight());
        fill(image, T);

        return image;
    }

    private static void fill(Image image, Tree T) {

        if (T.isLeaf()){

            image.setRectangle(T.getLeft(), T.getRight(), T.getDown(), T.getUp(), T.getColor());
        }
        else {

            fill(image, T.getL());
            fill(image, T.getR());
        }
    }

    private static void cutLeaf(Tree T, TreeSettings settings) {

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
    }

    private static BoolIntPair chooseDivision(int height, int width, double proportionCut) {

        Boolean axis = chooseAxis(height, width);
        int result;

        if (axis == Tree.AxisX) {

            result = chooseCoordinate(width, proportionCut);
        }
        else {

            result = chooseCoordinate(height, proportionCut);
        }

        return new BoolIntPair(axis, result);
    }
    private static Boolean chooseAxis(int height, int width) {

        double ProbaX = (double)width / (double)((height + width));
        double rand = Math.random();

        if (rand > ProbaX) {

            return Tree.AxisY;
        }

        return Tree.AxisX;
    }

    private static int chooseCoordinate(int size, double proportionCut) {

        double rand = proportionCut + Math.random() * (1 - (2 * proportionCut));

        return (int)(size * rand);
    }

    private static Color chooseColor(Color FColor, double sameColorProb) {

        if (Math.random() > sameColorProb) {

            return randomColor();
        }

        return FColor;
    }
    private static Color randomColor() {

        Color result;
        int rand = (int) (5 * Math.random());

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

            if((T.getHeight() < minDimensionCut) || (T.getWidth() < minDimensionCut))
                return null;

            return T;
        }

        Tree L = chooseLeaf(T.getL(), minDimensionCut);
        Tree R = chooseLeaf(T.getR(), minDimensionCut);

        if(R != null && L != null){

            if(L.getWeight() > R.getWeight())
                return L;

            return R;
        }

        if(R == null && L == null)
            return null;

        if(R == null)
            return L;

        return R;
    }
}
