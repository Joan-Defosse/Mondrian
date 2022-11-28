package Struct;

import java.awt.Color;

public class Palette {

    // PUBLIC PRESETS ===================================== //

    public static final Palette DEFAULT = new Palette(Color.GRAY, Color.WHITE, Color.BLACK, Color.BLUE, Color.RED, Color.YELLOW);

    public static final Palette PASTEL = new Palette(new Color(0xd6e6ff), new Color(0xd7f9f8), new Color(0xffffea),
            new Color(0xfff0d4), new Color(0xfbe0e0), new Color(0xe5d4ef));

    public static final Palette WOOD = new Palette(new Color(0xb36600), new Color(0xffaa00), new Color(0xb37700),
            new Color(0xffeabf), new Color(0xffd580), new Color(0x0078ab));

    // PUBLIC ATTRIBUTES ===================================== //

    public Color lineColor;
    public Color colorA;
    public Color colorB;
    public Color colorC;
    public Color colorD;
    public Color colorE;

    // PUBLIC CONSTRUCTORS ===================================== //

    public Palette(Color lineColor, Color colorA, Color colorB, Color colorC, Color colorD, Color colorE) {

        this.lineColor = lineColor;
        this.colorA = colorA;
        this.colorB = colorB;
        this.colorC = colorC;
        this.colorD = colorD;
        this.colorE = colorE;
    }
}
