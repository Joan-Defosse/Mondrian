package Main;

import Tree.*;
import Image.*;
import Struct.*;
import java.awt.Color;
import java.util.Random;
import java.util.Scanner;
import java.io.IOException;

/* Use these lines to compile and run :
* javac Main/Painter.java Main/Settings.java Image/Image.java Tree/Tree.java Tree/Zone.java Struct/BollIntPair.java
* java Main.Painter
* */
public class Painter {

    // PUBLIC STATIC MAIN ===================================== //

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        Random randomizer;
        Palette palette;
        Settings settings;
        Tree T;
        Image image;
        String answer, filename;
        int strategy, seed, height, width, lineWidth, minDimensionCut, nbLeaves;
        double sameColorProb, cutProportion;

        System.out.println("Do you want to use your own settings (yes/*) ?");
        answer = input.nextLine();

        if (answer.equals("yes")) {

            System.out.println("Strategy (0/*) : ");
            strategy = input.nextInt();

            System.out.println("Filename (do not write '.png') : ");
            filename = input.nextLine();

            System.out.println("Random Seed (> 0) : ");
            seed = input.nextInt();

            System.out.println("Height (> 0) : ");
            height = input.nextInt();

            System.out.println("Width (> 0) : ");
            width = input.nextInt();

            System.out.println("Lines' Width (> 0) : ");
            lineWidth = input.nextInt();

            System.out.println("Minimum Size to Cut a Dimension (> lineWidth) : ");
            minDimensionCut = input.nextInt();

            System.out.println("Number of Leaves / Rectangles (> 0) : ");
            nbLeaves = input.nextInt();

            System.out.println("Probabilty of same Color (0.0 <= x < 1.0) : ");
            sameColorProb = input.nextDouble();

            System.out.println("Forbidden proportion to cut (0.0 <= x < 0.5) : ");
            cutProportion = input.nextDouble();
        }
        else {

            filename = "test1006";
            strategy = 1;
            seed = 1006;
            height = 1200;
            width = 1800;
            lineWidth = 10;
            minDimensionCut = 20;
            nbLeaves = 40;
            sameColorProb = 0.4;
            cutProportion = 0.2;
        }

        randomizer = new Random(seed);

        if (strategy == 0) {

            palette = new Palette(Color.GRAY, Color.WHITE, Color.BLACK, Color.BLUE, Color.RED, Color.YELLOW);
            settings = new Settings(nbLeaves, minDimensionCut, sameColorProb, cutProportion, palette, randomizer);

            T = generateRandomTree(height, width, settings);
        }
        else {

            palette = new Palette(new Color(0xd6e6ff), new Color(0xd7f9f8), new Color(0xffffea),
                                  new Color(0xfff0d4), new Color(0xfbe0e0), new Color(0xe5d4ef));

            settings = new Settings(nbLeaves, minDimensionCut, sameColorProb, cutProportion, palette, randomizer);

            T = generateBetterRandomTree(height, width, settings);
        }


        image = toImage(T, lineWidth, palette.lineColor);

        try {

            image.save("output/" + filename + ".png");
        }
        catch (IOException e) {

            throw new RuntimeException(e);
        }

        System.out.println("No build error. You can find your picture in the output directory.");
    }

    // PUBLIC STATIC FUNCTIONS ===================================== //

    public static Tree generateRandomTree(int height, int width, Settings settings) {

        Tree T = new Tree(settings.getPalette().colorA, new Zone(0, width, 0,  height));

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

    public static Tree generateBetterRandomTree(int height, int width, Settings settings) {

        return generateRandomTree(height, width, settings);
    }

    public static Image toImage(Tree T, int lineWidth, Color lineColor) {

        Image image = new Image(T.getWidth(), T.getHeight());

        if (T.isLeaf()) {

            image.setRectangle(0, image.width(), 0 , image.height(), T.getColor());
        }
        else {

            fill(image, T);
            addLineCut(image, T, lineWidth, lineColor);
        }

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

    private static Color chooseColor(Color FColor, Settings settings) {

        double rand = settings.getRandomizer().nextDouble();

        if (rand > settings.getSameColorProb()) {

            return randomColor(settings);
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

    private static Color randomColor(Settings settings) {

        int rand = settings.getRandomizer().nextInt(5);
        Palette palette = settings.getPalette();
        Color result;

        switch (rand) {

            case 0:
                result = palette.colorA;
                break;
            case 1:
                result = palette.colorB;
                break;
            case 2:
                result = palette.colorC;
                break;
            case 3:
                result = palette.colorD;
                break;
            default:
                result = palette.colorE;
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

            zoneL = new Zone(T.getLeft(), T.getLineCut(), T.getDown(), T.getUp());
            zoneR = new Zone(T.getLineCut(), T.getRight(), T.getDown(), T.getUp());
        }
        else {

            T.setLineCut(T.getDown() + bip.cut);

            zoneL = new Zone(T.getLeft(), T.getRight(), T.getDown(), T.getLineCut());
            zoneR = new Zone(T.getLeft(), T.getRight(), T.getLineCut(), T.getUp());
        }

        colorL = chooseColor(T.getColor(), settings);
        colorR = chooseColor(T.getColor(), settings);

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

    private static void addLineCut(Image image, Tree T, int lineWidth, Color lineColor) {

        if (!T.isLeaf()) {

            if (T.getAxis() == Tree.AxisX) {

                image.setRectangle(T.getLineCut() - lineWidth, T.getLineCut(), T.getDown(), T.getUp(), lineColor);
            }
            else {

                image.setRectangle(T.getLeft(), T.getRight(), T.getLineCut() - lineWidth, T.getLineCut(), lineColor);
            }

            addLineCut(image, T.getL(), lineWidth, lineColor);
            addLineCut(image, T.getR(), lineWidth, lineColor);
        }
    }
}
