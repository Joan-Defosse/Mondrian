package Main;

import Tree.*;
import Image.*;
import Struct.*;
import java.awt.Color;
import java.util.Random;
import java.util.Scanner;
import java.io.IOException;

/* Use these lines to compile and run :
* javac Main/Painter.java Main/Settings.java Image/Image.java Tree/Tree.java Tree/Zone.java Struct/BoolIntPair.java Struct/Shades.java
* java Main.Painter
* */
public class Painter {

    // PUBLIC STATIC MAIN ===================================== //

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        Random randomizer;
        Shades shades = null;
        Settings settings;
        Tree T;
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

            filename = "demo";
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

            shades = Shades.DEFAULT;
            settings = new Settings(nbLeaves, minDimensionCut, sameColorProb, cutProportion, shades, randomizer);

            T = generateRandomTree(height, width, settings);
        }
        else {

            if (shades == null)
                shades = Shades.GREEN;

            settings = new Settings(nbLeaves, minDimensionCut, sameColorProb, cutProportion, shades, randomizer);

            T = generateBetterRandomTree(height, width, settings);
        }


        image = toImage(T, lineWidth, shades.lineColor);

        try {

            image.save("output/" + filename + ".png");
        }
        catch (IOException e) {

            throw new RuntimeException(e);
        }

        System.out.println("No build error. You can find your picture in the output directory.");
    }

    // PUBLIC STATIC FUNCTIONS ===================================== //

    /*
     * Génère un arbre aléatoire 
     * height : la hauteur de l'arbre
     * width : la largeur de l'arbre
     * settings : les paramètres de l'arbre
     */
    public static Tree generateRandomTree(int height, int width, Settings settings) {

        Tree T = new Tree(settings.getShades().colorA, new Zone(0, width, 0,  height));

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

    /*
     * Génère un arbre aléatoire avec une procédure différente
     * height : la hauteur de l'arbre
     * width : la largeur de l'arbre
     * settings : les paramètres de l'arbre
     */
    public static Tree generateBetterRandomTree(int height, int width, Settings settings) {

        return generateRandomTree(height, width, settings);
    }

    /*
     * Créer une image à partir d'un arbre donné 
     * T : l'arbre selectionné
     * lineWidth : la largeur de la ligne
     * lineColor : la couleur de la ligne
     */
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

    /*
     * Définit la palette de couleur 
     * name : le nom de la palette
     */
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

    /*
     * Choisi la feuille de l'arbre qui sera divisée 
     * T : l'arbre choisi
     * minDimensionCut : la dimension de coupe minimale
     */
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

    /*
     * Choisi les modalités de la division de la feuille 
     * height : la hauteur de la feuille
     * width : la largeur de la feuille
     * settings : les paramètres de l'arbre
     */
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

    /*
     * Choisi la couleur de la feuille
     * FColor : la couleur de la feuille père
     * settings : les paramètres de l'arbre
     */
    private static Color chooseColor(Color FColor, Settings settings) {

        double rand = settings.getRandomizer().nextDouble();

        if (rand > settings.getSameColorProb()) {

            return randomColor(settings);
        }

        return FColor;
    }

    /*
     * Choisi l'axe de la division 
     * height : la hauteur de la feuille
     * width : la largeur de la feuille
     * randomizer : le nombre aléatoire 
     */
    private static Boolean chooseAxis(int height, int width, Random randomizer) {

        double rand = randomizer.nextDouble();
        double ProbaX = (double)width / (double)((height + width));

        if (rand > ProbaX) {

            return Tree.AxisY;
        }

        return Tree.AxisX;
    }

    /*
     * Choisi les coordonnées de la division
     * size : taille de la feuille à diviser
     * proportionCut : limite de la division
     * randomizer : le nombre aléatoire 
     */
    private static int chooseCoordinate(int size, double proportionCut, Random randomizer) {

        double rand = randomizer.nextDouble();
        rand = proportionCut + rand * (1 - (2 * proportionCut));

        return (int)(size * rand);
    }

    /*
     * Choisi une couleur aléatoirement
     * settings : les paramètres de l'arbre
     */
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

    /*
     * Définit la division d'une feuille
     * T : l'arbre choisi
     * settings : les paramètres de l'arbre
     */
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

    /*
     * Remplis le rectangle créer
     * image : l'image créée
     * T : l'arbre choisi
     */
    private static void fill(Image image, Tree T) {

        if (T.isLeaf()){

            image.setRectangle(T.getLeft(), T.getRight(), T.getDown(), T.getUp(), T.getColor());
        }
        else {

            fill(image, T.getL());
            fill(image, T.getR());
        }
    }

    /*
     * Ajoute des lignes entre les rectangles
     * image : l'arbre choisi
     * T : l'arbre choisi
     * lineWidth : la largeur de la ligne
     * lineColor : la couleur de la ligne
     */
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
