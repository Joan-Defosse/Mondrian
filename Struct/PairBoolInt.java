package Struct; 

public class PairBoolInt {

    // PUBLIC ATTRIBUTES ===================================== //

    public Boolean bool;
    public int value;

    // PUBLIC CONSTRUCTORS ===================================== //

    /*
     * Constructeur de la classe BoolIntPair
     * axis : l'axe de division
     * cut : coordonnée de la division
     */
    public PairBoolInt(Boolean bool, int value)  {

        this.bool = bool;
        this.value = value;
    }
}
