package Main;

import Tree.AVL;
import Tree.Zone;
import Image.Image;
import Image.Shades;
import Struct.PairBoolInt;
import Struct.PairAVL;
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

        Random randomizer;
        Settings settings;
        AVL T;
        Image image;
        Shades shades = Shades.PINK;
        String filename = "failed_input";
        Integer strategy = 1;
        Integer seed = 2002;
        Integer height = 2160;
        Integer width = 4096;
        Integer lineWidth = 20;
        Integer minDimensionCut = 30;
        Integer nbLeaves = 2000;
        Double sameColorProb = 0.4;
        Double cutProportion = 0.15;

        Scanner input = new Scanner(System.in);
        String answer;

        System.out.print("Do you want to use your own settings (yes/*) ? ");
        answer = input.nextLine();

        if (answer.equalsIgnoreCase("yes")) {

            System.out.print("Filename (do not write '.png') : ");
            filename = input.nextLine();

            System.out.print("Strategy (0 for default / * for something else) : ");
            strategy = Integer.parseInt(input.nextLine());

            if (strategy != 0) {

                System.out.print("Palette Preset (default, pastel, wood, green, blue, pink, rainbow) : ");
                answer = input.nextLine();
                shades = toShades(answer);

                if (shades == null) {

                    System.out.println("Preset was not recognized, set as default.");
                    shades = Shades.DEFAULT;
                }
            }

            System.out.print("Random Seed (> 0) : ");
            seed = Integer.parseInt(input.nextLine());

            while(seed <= 0) {

                System.err.println("--> Error : please respect the preconditions !");
                System.out.print("Random Seed (> 0) : ");
                seed = Integer.parseInt(input.nextLine());
            }

            System.out.print("Height (> 0) : ");
            height = Integer.parseInt(input.nextLine());

            while(height <= 0) {

                System.err.println("--> Error : please respect the preconditions !");
                System.out.print("Height (> 0) : ");
                height = Integer.parseInt(input.nextLine());
            }

            System.out.print("Width (> 0) : ");
            width = Integer.parseInt(input.nextLine());

            while(width <= 0) {

                System.err.println("--> Error : please respect the preconditions !");
                System.out.print("Width (> 0) : ");
                width = Integer.parseInt(input.nextLine());
            }

            System.out.print("Lines Width (> 0) : ");
            lineWidth = Integer.parseInt(input.nextLine());

            while(lineWidth <= 0) {

                System.err.println("--> Error : please respect the preconditions !");
                System.out.print("Lines Width (> 0) : ");
                lineWidth = Integer.parseInt(input.nextLine());
            }

            System.out.print("Minimum Size to Cut a Dimension (> lineWidth) : ");
            minDimensionCut = Integer.parseInt(input.nextLine());

            while(minDimensionCut <= lineWidth) {

                System.err.println("--> Error : please respect the preconditions !");
                System.out.print("Minimum Size to Cut a Dimension (> lineWidth) : ");
                minDimensionCut = Integer.parseInt(input.nextLine());
            }

            System.out.print("Number of Leaves / Rectangles (> 0) : ");
            nbLeaves = Integer.parseInt(input.nextLine());

            while(nbLeaves <= 0) {

                System.err.println("--> Error : please respect the preconditions !");
                System.out.print("Number of Leaves / Rectangles (> 0) : ");
                nbLeaves = Integer.parseInt(input.nextLine());
            }

            System.out.print("Probabilty of same Color (0.0 <= x < 1.0) : ");
            sameColorProb = Double.parseDouble(input.nextLine());

            while(sameColorProb < 0.0 || sameColorProb >= 1.0) {

                System.err.println("--> Error : please respect the preconditions !");
                System.out.print("Probabilty of same Color (0.0 <= x < 1.0) : ");
                sameColorProb = Double.parseDouble(input.nextLine());
            }

            System.out.print("Forbidden proportion to cut (0.0 <= x < 0.5) : ");
            cutProportion = Double.parseDouble(input.nextLine());

            while(cutProportion < 0.0 || cutProportion >= 0.5) {

                System.err.println("--> Error : please respect the preconditions !");
                System.out.print("Forbidden proportion to cut (0.0 <= x < 0.5) : ");
                cutProportion = Double.parseDouble(input.nextLine());
            }
        }

        randomizer = new Random(seed);

        if (strategy == 0) {

            shades = Shades.DEFAULT;
            settings = new Settings(lineWidth, nbLeaves, minDimensionCut, sameColorProb, cutProportion, shades, randomizer);

            T = generateRandomTree(height, width, settings);
        }
        else {

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

        System.out.println("\nThanks for using our generator !\nYou can find your picture in the output directory.");
    }

    // PUBLIC STATIC FUNCTIONS ===================================== //

    /*
     * Génère un arbre aléatoire 
     * height : la hauteur de l'arbre
     * width : la largeur de l'arbre
     * settings : les paramètres de l'arbre
     * Retourne un arbre 
     */
    public static AVL generateRandomTree(int height, int width, Settings settings) {

        AVL T = new AVL(settings.getShades().colorA, new Zone(0, width, 0,  height));

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

    /*
     * Génère un arbre aléatoire avec une procédure différente
     * height : la hauteur de l'arbre
     * width : la largeur de l'arbre
     * settings : les paramètres de l'arbre
     * Retourne un arbre
     */
    public static AVL generateBetterRandomTree(int height, int width, Settings settings) {

        return generateRandomTree(height, width, settings);
    }

    /*
     * Créer une image à partir d'un arbre donné 
     * T : l'arbre selectionné
     * lineWidth : la largeur de la ligne
     * lineColor : la couleur de la ligne
     * Retourne l'image finale
     */
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

    /*
     * Définit la palette de couleur 
     * name : le nom de la palette
     * Retourne la palette de couleur
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
     * Retourne la feuille à diviser
     */
    private static AVL chooseLeaf(AVL T, int minDimensionCut) {

        AVL M = T.Max();

        if((M.getHeight() < minDimensionCut) || (M.getWidth() < minDimensionCut))
            return null;

        return M;
    }

    /*
     * Choisi les modalités de la division de la feuille 
     * height : la hauteur de la feuille
     * width : la largeur de la feuille
     * settings : les paramètres de l'arbre
     * Retourne l'axe de division et les coordonnées de la division
     */
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

    /*
     * Choisi la couleur de la feuille
     * FColor : la couleur de la feuille père
     * settings : les paramètres de l'arbre
     * Retourne la couleur de la feuille
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
     * Retourne AxisX ou AxisY 
     */
    private static Boolean chooseAxis(int height, int width, Random randomizer) {

        double rand = randomizer.nextDouble();
        double ProbaX = (double)width / (double)((height + width));

        if (rand > ProbaX) {

            return AVL.AxisY;
        }

        return AVL.AxisX;
    }

    /*
     * Choisi les coordonnées de la division
     * size : taille de la feuille à diviser
     * proportionCut : limite de la division
     * randomizer : le nombre aléatoire 
     * Retourne les coordonnées de la division
     */
    private static int chooseCoordinate(int size, double proportionCut, Random randomizer) {

        double rand = randomizer.nextDouble();
        rand = proportionCut + rand * (1 - (2 * proportionCut));

        return (int)(size * rand);
    }

    /*
     * Choisi une couleur aléatoirement
     * settings : les paramètres de l'arbre
     * Retourne une couleur aléatoire
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
    private static PairAVL cutLeaf(AVL T, Settings settings) {

        AVL A, B;
        Zone zoneA, zoneB;
        Color colorA, colorB;

        PairBoolInt P = chooseDivision(T.getHeight(), T.getWidth(), settings);

        if (P.bool == AVL.AxisX) {

            int lineCut = T.getLeft() + P.value;
            int limit = lineCut - settings.getLineWidth();

            zoneA = new Zone(T.getLeft(), limit, T.getDown(), T.getUp());
            zoneB = new Zone(lineCut, T.getRight(), T.getDown(), T.getUp());
        }
        else {

            int lineCut = T.getDown() + P.value;
            int limit = lineCut - settings.getLineWidth();

            zoneA = new Zone(T.getLeft(), T.getRight(), T.getDown(), limit);
            zoneB = new Zone(T.getLeft(), T.getRight(), lineCut, T.getUp());
        }

        colorA = chooseColor(T.getColor(), settings);
        colorB = chooseColor(T.getColor(), settings);

        A = new AVL(colorA, zoneA);
        B = new AVL(colorB, zoneB);

        return new PairAVL(A, B);
    }

    /*
     * Remplis le rectangle créer
     * image : l'image créée
     * T : l'arbre choisi
     */
    private static void fill(Image image, AVL T) {

        image.setRectangle(T.getLeft(), T.getRight(), T.getDown(), T.getUp(), T.getColor());

        if (T.getL() != null)
            fill(image, T.getL());

        if(T.getR() != null)
            fill(image, T.getR());
    }
}
