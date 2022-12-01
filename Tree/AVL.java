package Tree;

import Struct.AVLIntPair;

import java.awt.Color;

public class AVL {

    // PUBLIC STATIC CONST ===================================== //

    public final static Boolean AxisX = true; // abscisses
    public final static Boolean AxisY = false; // ordonnées

    // PRIVATE ATTRIBUTES ===================================== //

    private Color color;
    private Zone zone;
    private Boolean axis;
    private Integer lineCut, balance;
    private AVL L, R;

    // PUBLIC CONSTRUCTORS ===================================== //

    public AVL(Color color, Zone zone) {

        this.color = color;
        this.zone = zone;

        axis = null;
        lineCut = null;
        L = null;
        R = null;
    }

    public AVL(Color color, Zone zone, Boolean axis, Integer lineCut) {

        this.color = color;
        this.zone = zone;
        this.axis = axis;
        this.lineCut = lineCut;

        L = null;
        R = null;
    }

    public AVL(Color color, Zone zone, Boolean axis, Integer lineCut, AVL L, AVL R) {

        this.color = color;
        this.zone = zone;
        this.axis = axis;
        this.lineCut = lineCut;

        if (L != null) {

            this.L = new AVL(L);
        }
        else  {

            this.L = null;
        }

        if (R != null) {

            this.R = new AVL(R);
        }
        else  {

            this.R = null;
        }
    }

    public AVL(AVL T) {

        color = T.color;
        zone = T.zone;
        axis = T.axis;
        lineCut = T.lineCut;

        if (T.L != null) {

            L = new AVL(T.L);
        }
        else  {

            L = null;
        }

        if (T.R != null) {

            R = new AVL(T.R);
        }
        else  {

            R = null;
        }
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
    public Boolean getAxis() { return axis; }
    public Integer getLineCut() { return lineCut; }
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

    // PUBLIC SETTERS ===================================== //

    public void setAxis(Boolean axis) { this.axis = axis; }
    public void setLineCut(Integer lineCut) { this.lineCut =  lineCut; }
    public void setBalance(Integer balance) { this.balance = balance; }

    // PUBLIC METHODS ===================================== //

    public boolean isLeaf() {

        return (color != null && zone != null && L == null && R == null && lineCut == null && axis == null);
    }

    public boolean equals(AVL Tree) {

        return (zone == Tree.zone) && (color == Tree.color);
    }

    public AVL LeftRotate() {

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

    public AVL RightRotate() {

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

    public AVL balanceAVL() {

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
    public AVLIntPair add(AVL Tree) {

        int h;

        if (zone == null) {

            Tree.setBalance(0);
            return new AVLIntPair(Tree, 1);
        }

        if (this == Tree) {

            return new AVLIntPair(this, 0);
        }

        if (Tree.getWeight() >= getWeight()) {

            if (R == null) {

                R = Tree;
                h = 1;
            }
            else {

                AVLIntPair P = R.add(Tree);
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

                AVLIntPair P = L.add(Tree);
                L = P.avl;
                h = - P.value;
            }
        }

        if (h == 0) {

            return new AVLIntPair(this, 0);
        }

        balance += h;
        balanceAVL();

        if (balance == 0) {

            return new AVLIntPair(this, 0);
        }

        return new AVLIntPair(this, 1);
    }

    public AVLIntPair delete(AVL Tree) {

        int h;

        if (zone == null) {

            return new AVLIntPair(this, 0);
        }
        if (Tree.getWeight() > getWeight()) {

            if (R == null) {

                return new AVLIntPair(this, 0);
            }

            AVLIntPair P = R.delete(Tree);
            R = P.avl;
            h = P.value;
        }
        else if(Tree.getWeight() < getWeight()) {

            if (L == null) {

                return new AVLIntPair(this, 0);
            }

            AVLIntPair P = L.delete(Tree);
            L = P.avl;
            h = - P.value;
        }
        else if (this != Tree) {

            if (R == null) {

                return new AVLIntPair(this, 0);
            }

            AVLIntPair P = R.delete(Tree);
            R = P.avl;
            h = P.value;
        }
        else {

            if (L == null) {

                return new AVLIntPair(R, -1);
            }

            if (R == null) {

                return new AVLIntPair(L, -1);
            }

            AVL T = R.Min();

            this.color = T.color;
            this.zone = T.zone;
            this.axis = T.axis;
            this.lineCut = T.lineCut;

            AVLIntPair P = R.delete_minimum();
            R = P.avl;
            h = P.value;
        }

        if (h == 0) {

            return new AVLIntPair(this, 0);
        }

        balance += h;
        balanceAVL();

        if (balance == 0) {

            return new AVLIntPair(this, -1);
        }

        return new AVLIntPair(this, 0);
    }

    public AVLIntPair delete_minimum() {

        int h;

        if (L == null) {

            return new AVLIntPair(R, -1);
        }

        AVLIntPair P = L.delete_minimum();
        L = P.avl;
        h = - P.value;

        if (h == 0) {

            return new AVLIntPair(this, 0);
        }

        balance += h;
        balanceAVL();

        if (balance == 0) {

            return new AVLIntPair(this, -1);
        }

        return new AVLIntPair(this, 0);
    }
}