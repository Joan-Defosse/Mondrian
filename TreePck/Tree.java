package TreePck;

import java.awt.Color;

public class Tree {

    public final static Boolean AxisX = true; // abscisses
    public final static Boolean AxisY = false; // ordonnées

    private final TreeSettings settings;
    private Color color;
    private Zone zone;
    private Boolean axis;
    private Integer lineCut;
    private Tree L, R;

    // Constructors ========================================================================//

    public Tree(TreeSettings settings, Color color, Zone zone) {

        this.settings = settings;
        this.color = color;
        this.zone = zone;

        axis = null;
        lineCut = null;
        L = null;
        R = null;
    }

    public Tree(TreeSettings settings, Color color, Zone zone, Boolean axis, Integer lineCut) {

        this.settings = settings;
        this.color = color;
        this.zone = zone;
        this.axis = axis;
        this.lineCut = lineCut;

        L = null;
        R = null;
    }

    public Tree(TreeSettings settings, Color color, Zone zone, Boolean axis, Integer lineCut, Tree L, Tree R) {

        this.settings = settings;
        this.color = color;
        this.zone = zone;
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
        zone = T.zone;
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
    public int getHeight() { return zone.getHeight(); }
    public int getWidth() { return zone.getWidth(); }
    public int getLeft() { return zone.getLeft(); }
    public int getRight() { return zone.getRight(); }
    public int getDown() { return zone.getDown(); }
    public int getUp() { return zone.getUp(); }
    public Boolean getAxis() { return axis; }
    public Integer getLineCut() { return lineCut; }
    public Tree getL() { return L; }
    public Tree getR() { return R; }

    // Setters ========================================================================//

    public void setAxis(Boolean axis) { this.axis = axis; }
    public void setLineCut(Integer lineCut) { this.lineCut =  lineCut; }

    // Methods ========================================================================================//

    public boolean isLeaf() {

        return (color != null && L == null && R == null && lineCut == null && axis == null);
    }
    public static boolean isLeaf(Tree T) {

        return T.isLeaf();
    }
}