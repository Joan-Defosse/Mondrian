package Image;
 
import java.awt.Color;

public class Shades {

    // PUBLIC PRESETS ===================================== //

    public static final Shades DEFAULT = new Shades(Color.GRAY, Color.WHITE, Color.BLACK, Color.BLUE, Color.RED, Color.YELLOW);

    public static final Shades PASTEL = new Shades(new Color(0xd6e6ff), new Color(0xd7f9f8), new Color(0xffffea),
            new Color(0xfff0d4), new Color(0xfbe0e0), new Color(0xe5d4ef));

    public static final Shades WOOD = new Shades(new Color(0x8c4820), new Color(0xffaa00), new Color(0xb37700),
            new Color(0xffeabf), new Color(0xffd580), new Color(0x0078ab));

    public static final Shades GREEN = new Shades(new Color(0x11150d), new Color(0x314026), new Color(0x526a40),
            new Color(0x739559), new Color(0x94bf74), new Color(0xb5ea8c));

    public static final Shades BLUE = new Shades(Color.WHITE, new Color(0xedf2fa), new Color(0xd7e3fc),
            new Color(0xccdbfd), new Color(0xc1d3fe), new Color(0xabc4ff));

    public static final Shades PINK = new Shades(Color.WHITE, new Color(0xffe5ec), new Color(0xffc2d1),
            new Color(0xffb3c6), new Color(0xff8fab), new Color(0xfb6f92));

    public static final Shades RAINBOW = new Shades(new Color(0xffca3a), new Color(0xf27b50), new Color(0xff595e),
            new Color(0x8ac926), new Color(0x1982c4), new Color(0x6a4c93));

    // PUBLIC ATTRIBUTES ===================================== //

    public Color lineColor;
    public Color colorA;
    public Color colorB;
    public Color colorC;
    public Color colorD;
    public Color colorE;

    // PUBLIC CONSTRUCTORS ===================================== //

    /*
     * Constructeur de la classe Shades 
     * lineColor : la couleur des bordures
     * colorA, colorB, colorC,  colorB, colorE: une couleur de la palette
     */
    public Shades(Color lineColor, Color colorA, Color colorB, Color colorC, Color colorD, Color colorE) {

        this.lineColor = lineColor;
        this.colorA = colorA;
        this.colorB = colorB;
        this.colorC = colorC;
        this.colorD = colorD;
        this.colorE = colorE;
    }

    // PUBLIC FUNCTIONS ===================================== //

    /*
     * Convertit une chaîne de caractères en palette de couleurs.
     * name : le nom du preset selectionné.
     * Retourne la palette de couleur.
     */
    public static Shades toShades(String name) {

        if(name.equalsIgnoreCase("default"))
            return Shades.DEFAULT;

        if(name.equalsIgnoreCase("pastel"))
            return Shades.PASTEL;

        if(name.equalsIgnoreCase("wood"))
            return Shades.WOOD;

        if(name.equalsIgnoreCase("green"))
            return Shades.GREEN;

        if(name.equalsIgnoreCase("blue"))
            return Shades.BLUE;

        if(name.equalsIgnoreCase("pink"))
            return Shades.PINK;

        if(name.equalsIgnoreCase("rainbow"))
            return Shades.RAINBOW;

        return null;
    }
}
