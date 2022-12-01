package Main;

import Tree.*;
import Image.*;
import Struct.*;
import java.awt.Color;
import java.util.Random;
import java.util.Scanner;
import java.io.IOException;

/* Use these lines to compile and run :
* javac Main/Painter.java Main/Settings.java Image/Image.java AVL/AVL.java AVL/Zone.java Struct/PairBoolInt.java Struct/Shades.java
* java Main.Painter
* */
public class Painter {

    // PUBLIC STATIC MAIN ===================================== //

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        Random randomizer;
        Shades shades = null;
        Settings settings;
        AVL T;
        Image image;
        String answer, filename;
        int strategy, seed, height, width, lineWidth, minDimensionCut, nbLeaves;
        double sameColorProb, cutProportion;

        System.out.println("Do you want to use your own settings (yes/*) ?");
        answer = input.next();

        if (answer.equalsIgnoreCase("yes")) {

            System.out.println("Filename (do not write '.png') :");
            filename = input.next();

            System.out.println("Strategy (0 for default / * for something else) :");
            strategy = input.nextInt();

            if (strategy != 0) {

                System.out.println("Palette Preset (default, pastel, wood, green, blue, pink, rainbow) :");
                answer = input.next();
                shades = toShades(answer);

                if (shades == null) {

                    System.out.println("Preset was not recognized, set as default.");
                    shades = Shades.DEFAULT;
                }
            }

            System.out.println("Random Seed (> 0) :");
            seed = input.nextInt();
            while(seed <= 0) {
                System.err.println("--Error--");
                System.out.println("Random Seed (> 0) :");
                seed = input.nextInt();
            }

            System.out.println("Height (> 0) :");
            height = input.nextInt();
            while(height <= 0) {
                System.err.println("--Error--");
                System.out.println("Height (> 0) :");
                height = input.nextInt();
            }

            System.out.println("Width (> 0) :");
            width = input.nextInt();
            while(width <= 0) {
                System.err.println("--Error--");
                System.out.println("Width (> 0) :");
                width = input.nextInt();
            }

            System.out.println("Lines Width (> 0) :");
            lineWidth = input.nextInt();
            while(lineWidth <= 0) {
                System.err.println("--Error--");
                System.out.println("Lines Width (> 0) :");
                lineWidth = input.nextInt();
            }

            System.out.println("Minimum Size to Cut a Dimension (> lineWidth) :");
            minDimensionCut = input.nextInt();
            while(minDimensionCut <= lineWidth) {
                System.err.println("--Error--");
                System.out.println("Minimum Size to Cut a Dimension (> lineWidth) :");
                minDimensionCut = input.nextInt();
            }

            System.out.println("Number of Leaves / Rectangles (> 0) : ");
            nbLeaves = input.nextInt();
            while(nbLeaves <= 0) {
                System.err.println("--Error--");
                System.out.println("Number of Leaves / Rectangles (> 0) : ");
                nbLeaves = input.nextInt();
            }

            System.out.println("Probabilty of same Color (0.0 <= x < 1.0) :");
            sameColorProb = Double.parseDouble(input.next());
            while(sameColorProb <= 0 || sameColorProb >= 1.0) {
                System.err.println("--Error--");
                System.out.println("Probabilty of same Color (0.0 <= x < 1.0) :");
                sameColorProb = input.nextInt();
            }

            System.out.println("Forbidden proportion to cut (0.0 <= x < 0.5) :");
            cutProportion = Double.parseDouble(input.next());
            while(cutProportion <= 0 || cutProportion >= 0.5) {
                System.err.println("--Error--");
                System.out.println("Forbidden proportion to cut (0.0 <= x < 0.5) :");
                cutProportion = input.nextInt();
            }
        }
        else {

            filename = "AVLtest4";
            strategy = 1;
            seed = 1010;
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

            shades = Shades.DEFAULT;
            settings = new Settings(lineWidth, nbLeaves, minDimensionCut, sameColorProb, cutProportion, shades, randomizer);

            T = generateRandomTree(height, width, settings);
        }
        else {

            if (shades == null)
                shades = Shades.BLUE;

            settings = new Settings(lineWidth, nbLeaves, minDimensionCut, sameColorProb, cutProportion, shades, randomizer);

            T = generateBetterRandomTree(height, width, settings);
        }


        image = toImage(T, width, height, lineWidth, shades.lineColor);

        try {

            image.save("output/" + filename + ".png");
        }
        catch (IOException e) {

            throw new RuntimeException(e);
        }

        System.out.println("No build error. You can find your picture in the output directory.");
    }

    // PUBLIC STATIC FUNCTIONS ===================================== //

    public static AVL generateRandomTree(int height, int width, Settings settings) {

        AVL T = new AVL(settings.getShades().colorA, new Zone(0, width, 0,  height));
        T.setBalance(0);

        PairAVL P = cutLeaf(T, settings);

        T = T.delete(T).avl;

        if (T == null) {

            T = P.first;
        }
        else {

            T = T.add(P.first).avl;
        }

        T = T.add(P.second).avl;

        for(int i = 2; i < settings.getNbLeaves(); i++) {

            AVL A = chooseLeaf(T, settings.getMinDimensionCut());

            // si aucune feuille ne peut être découpée == fin du programme
            if(A == null)
                return T;

            P = cutLeaf(A, settings);

            T = T.delete(A).avl;

            if (T == null) {

                T = P.first;
            }
            else {

                T = T.add(P.first).avl;
            }

            T = T.add(P.second).avl;
        }

        return T;
    }

    public static AVL generateBetterRandomTree(int height, int width, Settings settings) {

        return generateRandomTree(height, width, settings);
    }

    public static Image toImage(AVL T, int width, int height, int lineWidth, Color lineColor) {

        Image image = new Image(width, height);

        if (T.getR() == null && T.getL() == null) {

            image.setRectangle(0, image.width(), 0 , image.height(), T.getColor());
        }
        else {

            image.setRectangle(0, image.width(), 0 , image.height(), lineColor);
            fill(image, T);
        }

        return image;
    }

    public static Shades toShades(String name) {

        if(name.equalsIgnoreCase("default"))
            return Shades.DEFAULT;

        if(name.equalsIgnoreCase("pastel"))
            return Shades.PASTEL;

        if(name.equalsIgnoreCase("wood"))
            return Shades.WOOD;

        if(name.equalsIgnoreCase("green"))
            return Shades.GREEN;

        if(name.equalsIgnoreCase("blue"))
            return Shades.BLUE;

        if(name.equalsIgnoreCase("pink"))
            return Shades.PINK;

        if(name.equalsIgnoreCase("rainbow"))
            return Shades.RAINBOW;

        return null;
    }

    // PRIVATE STATIC FUNCTIONS ===================================== //

    private static AVL chooseLeaf(AVL T, int minDimensionCut) {

        AVL M = T.Max();

        if((M.getHeight() < minDimensionCut) || (M.getWidth() < minDimensionCut))
            return null;

        return M;
    }

    private static PairBoolInt chooseDivision(int height, int width, Settings settings) {

        Boolean axis = chooseAxis(height, width, settings.getRandomizer());
        int result;

        if (axis == AVL.AxisX) {

            result = chooseCoordinate(width, settings.getCutProportion(), settings.getRandomizer());
        }
        else {

            result = chooseCoordinate(height, settings.getCutProportion(), settings.getRandomizer());
        }

        return new PairBoolInt(axis, result);
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

            return AVL.AxisY;
        }

        return AVL.AxisX;
    }

    private static int chooseCoordinate(int size, double proportionCut, Random randomizer) {

        double rand = randomizer.nextDouble();
        rand = proportionCut + rand * (1 - (2 * proportionCut));

        return (int)(size * rand);
    }

    private static Color randomColor(Settings settings) {

        int rand = settings.getRandomizer().nextInt(5);
        Shades shades = settings.getShades();
        Color result;

        switch (rand) {

            case 0:
                result = shades.colorA;
                break;
            case 1:
                result = shades.colorB;
                break;
            case 2:
                result = shades.colorC;
                break;
            case 3:
                result = shades.colorD;
                break;
            default:
                result = shades.colorE;
                break;
        }

        return result;
    }

    private static PairAVL cutLeaf(AVL T, Settings settings) {

        AVL A, B;
        Zone zoneA, zoneB;
        Color colorA, colorB;

        PairBoolInt bip = chooseDivision(T.getHeight(), T.getWidth(), settings);
        T.setAxis(bip.axis);

        if (T.getAxis() == AVL.AxisX) {

            T.setLineCut(T.getLeft() + bip.cut);

            zoneA = new Zone(T.getLeft(), T.getLineCut() - settings.getLineWidth(), T.getDown(), T.getUp());
            zoneB = new Zone(T.getLineCut(), T.getRight(), T.getDown(), T.getUp());
        }
        else {

            T.setLineCut(T.getDown() + bip.cut);

            zoneA = new Zone(T.getLeft(), T.getRight(), T.getDown(), T.getLineCut() - settings.getLineWidth());
            zoneB = new Zone(T.getLeft(), T.getRight(), T.getLineCut(), T.getUp());
        }

        colorA = chooseColor(T.getColor(), settings);
        colorB = chooseColor(T.getColor(), settings);

        A = new AVL(colorA, zoneA);
        A.setBalance(0);
        B = new AVL(colorB, zoneB);
        B.setBalance(0);

        return new PairAVL(A, B);
    }

    private static void fill(Image image, AVL T) {

        image.setRectangle(T.getLeft(), T.getRight(), T.getDown(), T.getUp(), T.getColor());

        if (T.getL() != null)
            fill(image, T.getL());

        if(T.getR() != null)
            fill(image, T.getR());
    }
}
