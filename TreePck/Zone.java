package TreePck;

public class Zone {
    private int left;
    private int right;
    private int down;
    private int up;

    public Zone(int left, int right, int down, int up) {

        this.left = left;
        this.right = right;
        this.down = down;
        this.up = up;
    }

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
    public double getWeight() {

        return (getWidth() * getHeight()) / Math.pow(getWidth() + getHeight(), 1.5);
    }

    public void setLeft(int left) {
        this.left = left;
    }
    public void setRight(int right) {
        this.right = right;
    }
    public void setDown(int down) {
        this.down = down;
    }
    public void setUp(int up) {
        this.up = up;
    }
}
