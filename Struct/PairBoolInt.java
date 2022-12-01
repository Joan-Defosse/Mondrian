package Struct; 

public class PairBoolInt {

    // PUBLIC ATTRIBUTES ===================================== //

    public Boolean axis;
    public int cut;

    // PUBLIC CONSTRUCTORS ===================================== //

    /*
     * Constructeur de la classe BoolIntPair
     * axis : l'axe de division
     * cut : coordonnée de la division
     */
    public PairBoolInt(Boolean axis, int cut)  {

        this.axis = axis;
        this.cut = cut;
    }
}
