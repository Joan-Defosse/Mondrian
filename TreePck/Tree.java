package TreePck;

import org.jetbrains.annotations.NotNull;

import java.awt.Color;

public class Tree {

    public final static boolean X = true; // abscisses
    public final static boolean Y = false; // ordonnées
    private final boolean axis; // vrai = axe des abscisses, faux = axe des ordonnées
    private final Integer val;
    private final Color col;
    private Tree L, R;

    public Tree(boolean axis) {

        this.axis = axis;
        val = null;
        col = null;
        L = null;
        R = null;
    }

    public Tree(boolean axis, Integer val) {

        this.axis = axis;
        this.val = val;
        col = null;
        L = null;
        R = null;
    }

    public Tree(boolean axis, Color col) {

        this.axis = axis;
        val = null;
        this.col = col;
        L = null;
        R = null;
    }

    public Tree(boolean axis, Integer val, Color col) {

        this.axis = axis;
        this.val = val;
        this.col = col;
        L = null;
        R = null;
    }

    public Tree(boolean axis, Integer val, Color col, Tree L, Tree R) {

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

    public Tree(@NotNull Tree other) {

        axis = other.axis;
        val = other.val;
        col = other.col;

        if (other.L != null) {

            L = new Tree(other.L);
        }
        else  {

            L = null;
        }

        if (other.R != null) {

            R = new Tree(other.R);
        }
        else  {

            R = null;
        }
    }
}