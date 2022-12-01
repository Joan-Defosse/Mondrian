package Struct; 

public class BoolIntPair {

    // PUBLIC ATTRIBUTES ===================================== //

    public Boolean axis;
    public int cut;

    // PUBLIC CONSTRUCTORS ===================================== //

    /*
     * Constructeur de la classe BoolIntPair
     * axis : l'axe de division
     * cut : coordonnée de la division
     */
    public BoolIntPair(Boolean axis, int cut)  {

        this.axis = axis;
        this.cut = cut;
    }
}
