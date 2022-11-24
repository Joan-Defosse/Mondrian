package Main;

import Tree.*;
import Image.*;
import Struct.*;
import java.awt.Color;
import java.util.Random;
import java.io.IOException;

public class Painter {

    // PUBLIC STATIC MAIN ===================================== //

    public static void main(String[] args) {

        Random randomizer = new Random(1001);
        Settings settings = new Settings(15, 70, 20, 0.3, 0.1, randomizer);
        Tree T = generateRandomTree(1200, 1800, settings);

        Image image = toImage(T);

        try {

            image.save("test1001.png");
        }
        catch (IOException e) {

            throw new RuntimeException(e);
        }

        System.out.println("Aucune erreur de build.");
    }

    // PUBLIC STATIC FUNCTIONS ===================================== //

    public static Tree generateRandomTree(int height, int width, Settings settings) {

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
        image.setRectangle(0, image.width(), 0 , image.height(), Color.GRAY);

        fill(image, T);

        return image;
    }

    // PRIVATE STATIC FUNCTIONS ===================================== //

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

    private static BoolIntPair chooseDivision(int height, int width, Settings settings) {

        Boolean axis = chooseAxis(height, width, settings.getRandomizer());
        int result;

        if (axis == Tree.AxisX) {

            result = chooseCoordinate(width, settings.getCutProportion(), settings.getRandomizer());
        }
        else {

            result = chooseCoordinate(height, settings.getCutProportion(), settings.getRandomizer());
        }

        return new BoolIntPair(axis, result);
    }

    private static Color chooseColor(Color FColor, double sameColorProb, Random randomizer) {

        double rand = randomizer.nextDouble();

        if (rand > sameColorProb) {

            return randomColor(randomizer);
        }

        return FColor;
    }

    private static Boolean chooseAxis(int height, int width, Random randomizer) {

        double rand = randomizer.nextDouble();
        double ProbaX = (double)width / (double)((height + width));

        if (rand > ProbaX) {

            return Tree.AxisY;
        }

        return Tree.AxisX;
    }

    private static int chooseCoordinate(int size, double proportionCut, Random randomizer) {

        double rand = randomizer.nextDouble();
        rand = proportionCut + rand * (1 - (2 * proportionCut));

        return (int)(size * rand);
    }

    private static Color randomColor(Random randomizer) {

        int rand = randomizer.nextInt(5);
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

    private static void cutLeaf(Tree T, Settings settings) {

        Tree L, R;
        Zone zoneL, zoneR;
        Color colorL, colorR;

        BoolIntPair bip = chooseDivision(T.getHeight(), T.getWidth(), settings);
        T.setAxis(bip.axis);

        if (T.getAxis() == Tree.AxisX) {

            T.setLineCut(T.getLeft() + bip.cut);

            zoneL = new Zone(T.getLeft(), T.getLineCut() - settings.getLineWidth(), T.getDown(), T.getUp());
            zoneR = new Zone(T.getLineCut(), T.getRight(), T.getDown(), T.getUp());
        }
        else {

            T.setLineCut(T.getDown() + bip.cut);

            zoneL = new Zone(T.getLeft(), T.getRight(), T.getDown(), T.getLineCut() - settings.getLineWidth());
            zoneR = new Zone(T.getLeft(), T.getRight(), T.getLineCut(), T.getUp());
        }

        colorL = chooseColor(T.getColor(), settings.getSameColorProb(), settings.getRandomizer());
        colorR = chooseColor(T.getColor(), settings.getSameColorProb(), settings.getRandomizer());

        L = new Tree(colorL, zoneL);
        R = new Tree(colorR, zoneR);

        T.setL(L);
        T.setR(R);
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
}
