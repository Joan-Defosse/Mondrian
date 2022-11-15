package TreePck;

import java.awt.Color;

public class Tree {

    public final static Boolean AxisX = true; // abscisses
    public final static Boolean AxisY = false; // ordonnées

    private final TreeSettings settings;
    private Color color;
    private Boolean axis;
    private Integer lineCut;
    private Tree L, R;
    private Position position;

    // Constructors ========================================================================//

    public Tree(TreeSettings settings, Color color, Position position) {

        this.settings = settings;
        this.color = color;
        this.position = position;

        axis = null;
        lineCut = null;
        L = null;
        R = null;
    }

    public Tree(TreeSettings settings, Color color, Position position, Boolean axis, Integer lineCut) {

        this.settings = settings;
        this.color = color;
        this.position = position;
        this.axis = axis;
        this.lineCut = lineCut;

        L = null;
        R = null;
    }

    public Tree(TreeSettings settings, Color color, Position position, Boolean axis, Integer lineCut, Tree L, Tree R) {

        this.settings = settings;
        this.color = color;
        this.position = position;
        this.axis = axis;
        this.lineCut = lineCut;

        if (L != null) {

            this.L = new Tree(L);
        }
        else  {

            this.L = null;
        }

        if (R != null) {

            this.R = new Tree(R);
        }
        else  {

            this.R = null;
        }
    }

    public Tree(Tree T) {

        settings = T.settings;
        color = T.color;
        this.position = T.position;
        axis = T.axis;
        lineCut = T.lineCut;

        if (T.L != null) {

            L = new Tree(T.L);
        }
        else  {

            L = null;
        }

        if (T.R != null) {

            R = new Tree(T.R);
        }
        else  {

            R = null;
        }
    }

    // Getters ========================================================================//

    public TreeSettings getSettings() { return settings; }
    public Color getColor(){
        return color;
    }
    public int getHeight() { return position.getHeight(); }
    public int getWidth() { return position.getWidth(); }
    public int getLeft() { return position.getLeft(); }
    public int getRight() { return position.getRight(); }
    public int getDown() { return position.getDown(); }
    public int getUp() { return position.getUp(); }
    public Boolean getAxis() { return axis; }
    public Integer getLineCut() { return lineCut; }
    public Tree getL() { return L; }
    public Tree getR() { return R; }

    // Methods ========================================================================================//

    public boolean isLeaf() {

        return (color != null && L == null && R == null && lineCut == null && axis == null);
    }
    public static boolean isLeaf(Tree T) {

        return T.isLeaf();
    }
}