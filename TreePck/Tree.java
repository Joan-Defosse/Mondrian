package TreePck;

import java.awt.Color;

public class Tree {

    public final static Boolean X = true; // abscisses
    public final static Boolean Y = false; // ordonnées
    private final Boolean axis; // vrai = axe des abscisses, faux = axe des ordonnées
    private final Integer val;
    private final Color col;
    private Tree L, R;

    public Tree(Color col) {

        axis = null;
        val = null;
        this.col = col;
        L = null;
        R = null;
    }

    public Tree(Boolean axis, Integer val, Color col) {

        this.axis = axis;
        this.val = val;
        this.col = col;
        L = null;
        R = null;
    }

    public Tree(Boolean axis, Integer val, Color col, Tree L, Tree R) {

        this.axis = axis;
        this.val = val;
        this.col = col;

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

        axis = T.axis;
        val = T.val;
        col = T.col;

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

        return (col != null && L == null && R == null && val == null && axis == null);
    }
    public static boolean isLeaf(Tree T) {

        return T.isLeaf();
    }

    public void add(boolean axis, Integer val, Color col) {

    }

    public void add(boolean axis, Integer val) {

    }
}