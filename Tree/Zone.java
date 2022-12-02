package Tree;

public class Zone {

    // PRIVATE ATTRIBUTES ===================================== //

    private int left; // startX
    private int right; // endX 
    private int down; // startY
    private int up; // endY

    // PUBLIC CONSTRUCTORS ===================================== //

    /**
     * Constructeur de la classe Zone
     * @param left : limite de la zone à gauche
     * @param right : limite de la zone à droite
     * @param down : limite basse de la zone
     * @param up : limite haute de la zone
     */
    public Zone(int left, int right, int down, int up) {

        this.left = left;
        this.right = right;
        this.down = down;
        this.up = up;
    }

    // PUBLIC GETTERS ===================================== //

    public int getLeft() {
        return left;
    }
    public int getRight() {
        return right;
    }
    public int getDown() {
        return down;
    }
    public int getUp() {
        return up;
    }
    public int getHeight() {
        return (up - down);
    }
    public int getWidth() {
        return (right - left);
    }
    public double getWeight() { return (getWidth() * getHeight()) / Math.pow(getWidth() + getHeight(), 1.5); }
}
