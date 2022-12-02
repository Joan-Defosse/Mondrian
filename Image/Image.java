package Image;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 * Helper class to create, modify and save images.
 * @author Mathieu Vavrille
 * For example:
 * ```java
 * Image img = new Image (100, 200);
 * img.setRectangle(10, 20, 50, 30, Color.YELLOW); // Color should be imported with `import java.awt.Color`
 * img.save("test.png");
 * ```
 */
public class Image
{
  private final BufferedImage image; // The image

  /** Constructs an empty image (initially black) of width `width` and height `height` */
  public Image(int width, int height) {
    image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
  }

  /**
   * Sets one pixel of the image.
   * WARNING : NO CHECK IS DONE. IF YOU WRITE OUTSIDE THE IMAGE IT WILL RAISE AN ERROR
   */
  public void setPixel(int x, int y, Color col) {
    image.setRGB(x,y,col.getRGB());
  }
  /**
   * Sets all the pixels in the given region to the given color.
   * WARNING : END COORDINATES EXCLUDED.
   * WARNING : NO CHECK IS DONE. IF YOU WRITE OUTSIDE THE IMAGE IT WILL RAISE AN ERROR
   */
  public void setRectangle(int startX, int endX, int startY, int endY, Color color) {
    for(int x = startX; x < endX; x++) {
      for(int y = startY; y < endY; y++) {
        setPixel(x,y,color);
      }
    }
  }

  /**
   * Définit une zone en forme de diamant de la couleur demandée
   * @param startX : limite à gauche
   * @param endX : limite à droite
   * @param startY : limite haute
   * @param endY : limite basse
   * @param color : couleur choisi
   */
  public void setDiamond(int startX, int endX, int startY, int endY, Color color) {

    int width = endX-startX;
    int height = endY-startY;

    if(width < height) {
      // HAUT GAUCHE
      int nbPixel = 1;
      for(int y = startY; y < (startY+endY)/2; y++) {
        if (nbPixel < (endX - startX)/2);
        nbPixel++;
        for (int x = (startX+endX)/2; x > startX; x--) {
          if (x >= (startX+endX)/2 - nbPixel){
            setPixel(x, y, color);
          }
        }
      }
      //HAUT DROIT
      nbPixel = 1;
      for(int y = startY; y < (startY+endY)/2; y++) {
        if (nbPixel < (endX - startX)/2);
        nbPixel++;
        for (int x = (startX+endX)/2; x < endX; x++) {
          if (x <= (startX+endX)/2 + nbPixel){
            setPixel(x, y, color);
          }
        }
      }
      //BAS GAUCHE
      nbPixel = 1;
      for(int y = endY - 1; y >= (startY+endY)/2; y--) {
        if (nbPixel < (endX - startX)/2);
        nbPixel++;
        for (int x = (startX+endX)/2; x > startX; x--) {
          if (x >= (startX+endX)/2 - nbPixel){
            setPixel(x, y, color);
          }
        }
      }
      // BAS DROIT
      nbPixel = 1;
      for(int y = endY - 1; y >= (startY+endY)/2; y--) {
        if (nbPixel < (endX - startX)/2);
        nbPixel++;
        for (int x = (startX+endX)/2; x < endX; x++) {
          if (x <= (startX+endX)/2 + nbPixel){
            setPixel(x, y, color);
          }
        }
      }
    }

    else {
      // HAUT GAUCHE
      int nbPixel = 1;
      for(int x = startX; x < (startX+endX)/2; x++) {
        if (nbPixel < (endX - startX)/2);
        nbPixel++;
        for (int y = (startY+endY)/2; y > startY; y--) {
          if (y >= (startY+endY)/2 - nbPixel){
            setPixel(x, y, color);
          }
        }
      }
      //BAS GAUCHE
      nbPixel = 1;
      for(int x = startX+1; x < (startX+endX)/2; x++) {
        if (nbPixel < (endX - startX)/2);
        nbPixel++;
        for (int y = (startY+endY)/2; y < endY; y++) {
          if (y <= (startY+endY)/2 + nbPixel){
            System.out.println("bonjour");
            setPixel(x, y, color);
          }
        }
      }
      // HAUT DROIT
      nbPixel = 1;
      for(int x = endX-1; x >= (startX+endX)/2; x--) {
        if (nbPixel < (endX - startX)/2);
        nbPixel++;
        for (int y = (startY+endY)/2; y > startY; y--) {
          if (y >= (startY+endY)/2 - nbPixel){
            setPixel(x, y, color);
          }
        }
      }
      // BAS DROIT
      nbPixel = 1;
      for(int x = endX-1; x >= (startX+endX)/2; x--) {
        if (nbPixel < (endX - startX)/2);
        nbPixel++;
        for (int y = (startY+endY)/2; y < endY; y++) {
          if (y <= (startY+endY)/2 + nbPixel){
            setPixel(x, y, color);
          }
        }
      }
    }
  }

  /**
   * Saves the image to a file, in PNG format
   */
  public void save(String filename) throws IOException {
    File fic = new File(filename);
    fic = new File(fic.getAbsolutePath());
    ImageIO.write(image,"png",fic);
  }

  /**
   * Number of pixels in X dimension
   */
  public int width() {
    return image.getWidth();
  }

  /**
   * Number of pixels in Y dimension
   */
  public int height() {
    return image.getHeight();
  }
}

