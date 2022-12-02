package Image;

public enum Shape {

    Rectangle,
    Diamond;

    public static Shape toShape(String s) {

        if (s.equalsIgnoreCase("Rectangle"))
            return Rectangle;

        if(s.equalsIgnoreCase(("Diamond")))
            return Diamond;

        return null;
    }
}