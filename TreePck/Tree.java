package TreePck;

import java.awt.Color;

public class Tree {

    public final static Boolean AxisX = true; // abscisses
    public final static Boolean AxisY = false; // ordonnées
    private Color color;
    private Boolean axis; // vrai = axe des abscisses, faux = axe des ordonnées
    private Point P;
    private Tree L, R;
    private TreeSettings settings;

    private int height;
    private int width;


    // Constructor ========================================================================//
    public Tree(Color color, TreeSettings settings, int height, int width) {

        this.color = color;
        this.settings = settings;
        this.height = height;
        this.width = width;
        axis = null;
        P = null;
        L = null;
        R = null;
    }

    public Tree(Color color, Boolean axis, Point P) {

        this.color = color;
        this.axis = axis;

        if (P != null) {

            this.P = new Point(P);
        }
        else {

            this.P = null;
        }

        L = null;
        R = null;
    }

    public Tree(Color color, Boolean axis, Integer X, Integer Y) {

        this.color = color;
        this.axis = axis;
        this.P = new Point(X, Y);
        L = null;
        R = null;
    }

    public Tree(Color color, Boolean axis, Point P, Tree L, Tree R) {

        this.color = color;
        this.axis = axis;

        if (P != null) {

            this.P = new Point(P);
        }
        else {

            this.P = null;
        }

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

    public Tree(Color color, Boolean axis, Integer X, Integer Y, Tree L, Tree R) {

        this.color = color;
        this.axis = axis;
        this.P = new Point(X, Y);

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

        color = T.color;
        axis = T.axis;

        if (T.P != null) {

            P = new Point(T.P);
        }
        else {

            P = null;
        }

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
    //========================================================================================//

    public boolean isLeaf() {

        return (color != null && L == null && R == null && P == null && axis == null);
    }
    public static boolean isLeaf(Tree T) {

        return T.isLeaf();
    }

    public void add(Point P, Color col) {

    }

    public void add(Point P) {

    }


    public Color getColor(){
        return color;
    }


}