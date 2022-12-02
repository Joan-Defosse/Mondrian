package Tree;

import Struct.PairAVLInt;
import java.awt.Color;


/*
* Les AVL sont organisés en fonction de leur poids.
* Le poids n'est pas un attribut mais est accessible avec la méthode getWeight().
* Il est calculé à partir de la zone de l'AVL.
* */
public class AVL {

    // PUBLIC STATIC CONST ===================================== //

    public final static Boolean AxisX = true; // abscisses
    public final static Boolean AxisY = false; // ordonnées

    // PRIVATE ATTRIBUTES ===================================== //

    private Color color;
    private Zone zone;
    private Integer balance;
    private AVL L, R;

    // PUBLIC CONSTRUCTORS ===================================== //

    public AVL(Color color, Zone zone) {

        this.color = color;
        this.zone = zone;
        this.balance = 0;

        L = null;
        R = null;
    }

    // PUBLIC GETTERS ===================================== //

    public Color getColor(){ return color; }
    public double getWeight() { return zone.getWeight(); }
    public int getHeight() { return zone.getHeight(); }
    public int getWidth() { return zone.getWidth(); }
    public int getLeft() { return zone.getLeft(); }
    public int getRight() { return zone.getRight(); }
    public int getDown() { return zone.getDown(); }
    public int getUp() { return zone.getUp(); }
    public AVL getL() { return L; }
    public AVL getR() { return R; }
    public AVL Min() {

        if (L == null) {

            return this;
        }

        return L.Min();
    }

    public AVL Max() {

        if (R == null) {

            return this;
        }

        return R.Max();
    }

    // PUBLIC METHODS ===================================== //

    public PairAVLInt add(AVL Tree) {

        int h;

        if (zone == null) {

            return new PairAVLInt(new AVL(Tree.color, Tree.zone), 1);
        }

        if (this == Tree) {

            return new PairAVLInt(this, 0);
        }

        if (Tree.getWeight() >= getWeight()) {

            if (R == null) {

                R = Tree;
                h = 1;
            }
            else {

                PairAVLInt P = R.add(Tree);
                R = P.avl;
                h = P.value;
            }
        }
        else {

            if (L == null) {

                L = Tree;
                h = -1;
            }
            else {

                PairAVLInt P = L.add(Tree);
                L = P.avl;
                h = - P.value;
            }
        }

        if (h == 0) {

            return new PairAVLInt(this, 0);
        }

        balance += h;
        AVL balancedAVL = balanceAVL();

        if (balancedAVL.balance == 0) {

            return new PairAVLInt(balancedAVL, 0);
        }

        return new PairAVLInt(balancedAVL, 1);
    }

    public PairAVLInt delete(AVL Tree) {

        int h;

        if (zone == null) {

            return new PairAVLInt(this, 0);
        }
        if (Tree.getWeight() > getWeight()) {

            if (R == null) {

                return new PairAVLInt(this, 0);
            }

            PairAVLInt P = R.delete(Tree);
            R = P.avl;
            h = P.value;
        }
        else if(Tree.getWeight() < getWeight()) {

            if (L == null) {

                return new PairAVLInt(this, 0);
            }

            PairAVLInt P = L.delete(Tree);
            L = P.avl;
            h = - P.value;
        }
        else if (this != Tree) {

            if (R == null) {

                return new PairAVLInt(this, 0);
            }

            PairAVLInt P = R.delete(Tree);
            R = P.avl;
            h = P.value;
        }
        else {

            if (L == null) {

                return new PairAVLInt(R, -1);
            }

            if (R == null) {

                return new PairAVLInt(L, -1);
            }

            AVL T = R.Min();

            this.color = T.color;
            this.zone = T.zone;

            PairAVLInt P = R.delete_minimum();
            R = P.avl;
            h = P.value;
        }

        if (h == 0) {

            return new PairAVLInt(this, 0);
        }

        balance += h;
        AVL balancedAVL = balanceAVL();

        if (balancedAVL.balance == 0) {

            return new PairAVLInt(balancedAVL, -1);
        }

        return new PairAVLInt(balancedAVL, 0);
    }

    // PRIVATE METHODS ===================================== //
    private AVL LeftRotate() {

        AVL B;
        int a, b;

        B = this.R;
        a = this.balance;
        b = B.balance;

        this.R = B.L;
        B.L = this;

        this.balance = a - Math.max(b, 0) - 1;
        B.balance = Math.min(Math.min(a - 2, a + b - 2), b - 1);

        return B;
    }

    private AVL RightRotate() {

        AVL B;
        int a, b;

        B = this.L;
        a = this.balance;
        b = B.balance;

        this.L = B.R;
        B.R = this;

        this.balance = a - Math.min(b, 0) + 1;
        B.balance = Math.max(Math.max(a + 2, a + b + 2), b + 1);

        return B;
    }

    private AVL balanceAVL() {

        if (balance == 2) {

            if (R.balance >= 0) {

                return LeftRotate();
            }

            R = R.RightRotate();
            return LeftRotate();
        }

        if (balance == -2) {

            if (L.balance <= 0) {

                return RightRotate();
            }

            L = L.LeftRotate();
            return RightRotate();
        }

        return this;
    }

    private PairAVLInt delete_minimum() {

        int h;

        if (L == null) {

            return new PairAVLInt(R, -1);
        }

        PairAVLInt P = L.delete_minimum();
        L = P.avl;
        h = - P.value;

        if (h == 0) {

            return new PairAVLInt(this, 0);
        }

        balance += h;
        AVL balancedAVL = balanceAVL();

        if (balancedAVL.balance == 0) {

            return new PairAVLInt(balancedAVL, -1);
        }

        return new PairAVLInt(balancedAVL, 0);
    }
}