package Main;

import Tree.*;
import Image.*;
import Struct.PairBoolInt;
import Struct.PairAVL;
import java.awt.Color;
import java.util.Random;
import java.util.Scanner;
import java.io.IOException;

/**
 * Ce programme est un générateur d'image utilisant de l'aléatoire. Vous pouvez modifier
 * les paramètres dans la fonction principale, ou bien à l'exécution depuis le terminal.
 *
 * Réalisé par Hacala Maude et Défossé Joan.
 * Sujet de Vavrille Mathieu - Nantes Université.
 *
 * On utilisera ces commandes pour compiler et exécuter :
 * javac Main/*.java Image/*.java Struct/*.java Tree/*.java
 * java Main.Painter
 */
public class Painter {

    // PUBLIC STATIC MAIN ===================================== //

    public static void main(String[] args) {

        // VARIABLES =============================================== //

        Random randomizer;                  // Génère des nombres pseudo-aléatoires.
        Settings settings;                  // Regroupe les paramètres pour alléger les signatures de fonctions.
        AVL T;                              // Arbre contenant les zones et couleurs de l'image à générer.
        Image image;                        // Le résultat de la génération.
        Shape shape = Shape.Diamond;        // La forme géométrique générée sur l'image.
        Shades shades = Shades.PINK;        // Palette de couleurs, presets : (default, wood, pink, green, blue, rainbow, pastel).
        String filename = "Pink2002";       // Nom du fichier généré dans le dossier output.
        Integer strategy = 1;               // strategy != 0 permet de choisir le preset de couleurs et les formes générées.
        Integer seed = 2002;                // Graine aléatoire pour randomizer.
        Integer height = 2160;              // Hauteur de l'image.
        Integer width = 4096;               // Largeur de l'image.
        Integer lineWidth = 10;             // Largeur de la ligne séparant les zones.
        Integer minDimensionCut = 20;       // Taille minimum pour autoriser la découpe d'une zone.
        Integer nbLeaves = 2000;            // Nombre de zones maximum.
        Double sameColorProb = 0.4;         // Influe sur la proportion de zones voisines de la même couleur.
        Double cutProportion = 0.15;        // Proportion de zone interdite en découpe.

        // COMMAND LINE INPUT ===================================== //

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
                shades = Shades.toShades(answer);

                if (shades == null) {

                    System.out.println("Preset was not recognized, set as default.");
                    shades = Shades.DEFAULT;
                }

                System.out.print("Shape (rectangle, diamond) : ");
                answer = input.nextLine();
                shape = Shape.toShape(answer);

                if (shape == null) {

                    System.out.println("Shape was not recognized, set as Rectangle.");
                    shape = Shape.Rectangle;
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

        // TREE GENERATION ===================================== //

        randomizer = new Random(seed);

        if (strategy == 0) {

            shape = shape.Rectangle;
            shades = Shades.DEFAULT;
            settings = new Settings(lineWidth, nbLeaves, minDimensionCut, sameColorProb, cutProportion, shades, randomizer);

            T = generateRandomTree(height, width, settings);
        }
        else {

            settings = new Settings(lineWidth, nbLeaves, minDimensionCut, sameColorProb, cutProportion, shades, randomizer);

            T = generateBetterRandomTree(height, width, settings);
        }

        // IMAGE GENERATION ===================================== //

        image = toImage(T, width, height, shades.lineColor, shape);

        try {

            image.save("output/" + filename + ".png");
        }
        catch (IOException e) {

            throw new RuntimeException(e);
        }

        System.out.println("\nThanks for using our generator !\nYou can find your picture in the output directory.");
    }

    // PUBLIC STATIC FUNCTIONS ===================================== //

    /**
     * Génère un AVL aléatoire contenant dans chaque noeud une zone et une couleur.
     * @param height : la hauteur de l'arbre
     * @param width : la largeur de l'arbre
     * @param settings : les paramètres de l'arbre
     * @return AVL généré
     */
    public static AVL generateRandomTree(int height, int width, Settings settings) {

        // Initialisation de l'arbre et découpe de la première feuille.

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

        // Réitération de la découpe avec recherche préalable de la feuille à découper.

        for(int i = 2; i < settings.getNbLeaves(); i++) {

            AVL A = chooseLeaf(T, settings.getMinDimensionCut());

            // Lorqu'aucune feuille ne peut être découpée (minDimensionCut est limitant) : fin du programme
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

    /**
     * Génère un arbre aléatoire contenant dans chaque noeud une zone et une couleur.
     * @param height : la hauteur de l'arbre
     * @param width : la largeur de l'arbre
     * @param settings : les paramètres de l'arbre
     * @return AVL généré
     */
    public static AVL generateBetterRandomTree(int height, int width, Settings settings) {

        return generateRandomTree(height, width, settings);
    }

    /**
     * Crée une image à partir d'un arbre donné.
     * @param T : l'arbre selectionné.
     * @param width : la largeur de l'image.
     * @param height : la hauteur de l'image.
     * @param lineColor : la couleur de la ligne.
     * @param shape : forme du visuel des feuilles de l'arbre
     * @return image générée.
     */
    public static Image toImage(AVL T, int width, int height, Color lineColor, Shape shape) {

        Image image = new Image(width, height);

        // Cas où l'arbre ne contient qu'une feuille.
        if (T.getR() == null && T.getL() == null) {

            image.setRectangle(0, image.width(), 0 , image.height(), T.getColor());
        }
        else {

            // Création d'un fond pour faire apparaître les lignes entre les zones.
            image.setRectangle(0, image.width(), 0 , image.height(), lineColor);
            fill(image, T, shape);
        }

        return image;
    }

    // PRIVATE STATIC FUNCTIONS ===================================== //

    /**
     * Choisit la feuille de l'arbre qui sera divisée.
     * @param T : l'arbre à diviser.
     * @param minDimensionCut : la dimension de coupe minimale autorisée.
     * @return la feuille à diviser.
     */
    private static AVL chooseLeaf(AVL T, int minDimensionCut) {

        AVL M = T.Max();

        // Si les dimensions sont trop petites pour être découpées, on arrête la génération.
        if((M.getHeight() < minDimensionCut) || (M.getWidth() < minDimensionCut))
            return null;

        return M;
    }

    /**
     * Choisit les modalités de la division de la feuille.
     * @param height : la hauteur de la feuille
     * @param width : la largeur de la feuille
     * @param settings : les paramètres de l'arbre
     * @return l'axe de la division et les coordonnées de la coupe.
     */
    private static PairBoolInt chooseDivision(int height, int width, Settings settings) {

        int lineCut;
        Boolean axis = chooseAxis(height, width, settings.getRandomizer());

        // AxisX (true) et AxisY (false) sont des constantes booléenes statiques.
        if (axis == AVL.AxisX) {

            lineCut = chooseCoordinate(width, settings.getCutProportion(), settings.getRandomizer());
        }
        else {

            lineCut = chooseCoordinate(height, settings.getCutProportion(), settings.getRandomizer());
        }

        return new PairBoolInt(axis, lineCut);
    }

    /**
     * Choisit une couleur pour une feuille.
     * @param FColor : la couleur de son père.
     * @param settings : les paramètres de l'arbre.
     * @return la couleur choisie.
     */
    private static Color chooseColor(Color FColor, Settings settings) {

        double rand = settings.getRandomizer().nextDouble();

        if (rand > settings.getSameColorProb()) {

            return randomColor(settings);
        }

        return FColor;
    }

    /**
     * Choisit l'axe de la division, AxisX (true) et AxisY (false) facilitent la compréhension.
     * @param height : la hauteur de la feuille.
     * @param width : la largeur de la feuille.
     * @param randomizer : le générateur d'aléatoire.
     * @return l'axe choisi.
     */
    private static Boolean chooseAxis(int height, int width, Random randomizer) {

        double rand = randomizer.nextDouble();
        double ProbaX = (double)width / (double)((height + width));

        if (rand > ProbaX) {

            return AVL.AxisY;
        }

        return AVL.AxisX;
    }

    /**
     * Choisit les coordonnées de la division.
     * @param size : taille de la feuille à diviser.
     * @param proportionCut : zone interdite à diviser.
     * @param randomizer : générateur d'aléatoire.
     * @return les coordonnées choisies
     */
    private static int chooseCoordinate(int size, double proportionCut, Random randomizer) {

        double rand = randomizer.nextDouble();
        rand = proportionCut + rand * (1 - (2 * proportionCut)); // proportionCut < rand < (1 - propotionCut)

        return (int)(size * rand);
    }

    /**
     * Choisit une couleur aléatoirement.
     * @param settings : les paramètres du programme.
     * @return une couleur aléatoire.
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
            // case4
            default:
                result = shades.colorE;
                break;
        }

        return result;
    }

    /**
     * Divise une zone en deux.
     * @param T : l'arbre à diviser.
     * @param settings : les paramètres du programme.
     * @return les deux AVL fils créés.
     */
    private static PairAVL cutLeaf(AVL T, Settings settings) {

        AVL A, B;               // Les arbres à retourner.
        Zone zoneA, zoneB;      // Leurs zones.
        Color colorA, colorB;   // Leurs couleurs.

        PairBoolInt P = chooseDivision(T.getHeight(), T.getWidth(), settings);
        Boolean axis = P.bool;

        if (axis == AVL.AxisX) {

            int lineCut = T.getLeft() + P.value;            // Coupure verticale.
            int limit = lineCut - settings.getLineWidth();  // Espace pour la ligne séparatrice.

            zoneA = new Zone(T.getLeft(), limit, T.getDown(), T.getUp());
            zoneB = new Zone(lineCut, T.getRight(), T.getDown(), T.getUp());
        }
        else {

            int lineCut = T.getDown() + P.value;            // Coupure horizontale.
            int limit = lineCut - settings.getLineWidth();  // Espace pour la ligne séparatrice.

            zoneA = new Zone(T.getLeft(), T.getRight(), T.getDown(), limit);
            zoneB = new Zone(T.getLeft(), T.getRight(), lineCut, T.getUp());
        }

        colorA = chooseColor(T.getColor(), settings);
        colorB = chooseColor(T.getColor(), settings);

        A = new AVL(colorA, zoneA);
        B = new AVL(colorB, zoneB);

        return new PairAVL(A, B);
    }

    /**
     * Remplis l'image de formes géométriques.
     * @param image : l'image à remplir.
     * @param T : l'arbre la représentant.
     * @param shape : forme du visuel des feuilles de l'arbre
     */
    private static void fill(Image image, AVL T, Shape shape) {

        if (shape == Shape.Diamond) {

            image.setDiamond(T.getLeft(), T.getRight(), T.getDown(), T.getUp(), T.getColor());
        }
        else {

            image.setRectangle(T.getLeft(), T.getRight(), T.getDown(), T.getUp(), T.getColor());
        }

        // Remplissage via un parcours récursif.
        if (T.getL() != null)
            fill(image, T.getL(), shape);

        if(T.getR() != null)
            fill(image, T.getR(), shape);
    }
}
