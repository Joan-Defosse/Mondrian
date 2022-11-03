package TreePck;

import java.awt.Color;

public class Tree {

    public final static Boolean AxisX = true; // abscisses
    public final static Boolean AxisY = false; // ordonnées
    private Color col;
    private Boolean axis; // vrai = axe des abscisses, faux = axe des ordonnées
    private Point P;
    private Tree L, R;

    public Tree(Color col) {

        this.col = col;
        axis = null;
        P = null;
        L = null;
        R = null;
    }

    public Tree(Color col, Boolean axis, Point P) {

        this.col = col;
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

    public Tree(Color col, Boolean axis, Integer X, Integer Y) {

        this.col = col;
        this.axis = axis;
        this.P = new Point(X, Y);
        L = null;
        R = null;
    }

    public Tree(Color col, Boolean axis, Point P, Tree L, Tree R) {

        this.col = col;
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

    public Tree(Color col, Boolean axis, Integer X, Integer Y, Tree L, Tree R) {

        this.col = col;
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

        col = T.col;
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

    public boolean isLeaf() {

        return (col != null && L == null && R == null && P == null && axis == null);
    }
    public static boolean isLeaf(Tree T) {

        return T.isLeaf();
    }

    public void add(Point P, Color col) {

    }

    public void add(Point P) {

    }
}