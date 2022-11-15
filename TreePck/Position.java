package TreePck;

public class Position {
    private int left;
    private int right;
    private int down;
    private int up;

    public Position(int left, int right, int down, int up) {

        this.left = left;
        this.right = right;
        this.down = down;
        this.up = up;
    }

    public int getLeft() {
        return left;
    }
    public void setLeft(int left) {
        this.left = left;
    }

    public int getRight() {
        return right;
    }
    public void setRight(int right) {
        this.right = right;
    }

    public int getDown() {
        return down;
    }
    public void setDown(int down) {
        this.down = down;
    }

    public int getUp() {
        return up;
    }
    public void setUp(int up) {
        this.up = up;
    }

    public int getHeight() {
        return (up - down);
    }
    public int getWidth() {
        return (right - left);
    }
}
