package TreePck;

public class Point {

    private Integer X, Y;

    public Point() {

        X = null;
        Y = null;
    }

    public Point(Integer X, Integer Y) {

        this.X = X;
        this.Y = Y;
    }

    public Point(Point P) {

        this.X = P.X;
        this.Y = P.Y;
    }

    public Integer getX() {

        return X;
    }

    public Integer getY() {

        return Y;
    }

    public void setX(Integer x) {

        X = x;
    }

    public void setY(Integer y) {

        Y = y;
    }

    public void move(Integer dx, Integer dy) {

        setX(X + dx);
        setY(Y + dy);
    }

    public Double radialDistance() {

        return Math.sqrt(X * X + Y * Y);
    }
}
