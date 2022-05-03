package prototype.HerramientasEntradaSalida;

import java.awt.Color;
import java.io.InputStream;
import java.io.FileInputStream;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.GraphicsConfiguration;
import java.awt.image.ImageObserver;
import java.util.Scanner;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.awt.Font;
import java.awt.Transparency;

public class LoadTools {

    public static final String RUTA_ACTUAL = ((LoadTools.class.getProtectionDomain().getCodeSource().getLocation() + "").substring(6).replaceAll("%20", " "));

    public static BufferedImage loadImage(final String path) {
        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();//Obtengo graficos compatibles
        Image img = null;//La imagen es nula por defecto para que no nos de error
        try {
            if (RUTA_ACTUAL.contains(".jar")) {
                img = ImageIO.read(LoadTools.class.getResourceAsStream(path));//Cargo la imagen
            } else {
                img = ImageIO.read(new File(RUTA_ACTUAL + path));//Cargo la imagen
            }

        } catch (Exception ex) {
            System.out.println("ERROR CARGANDO LA IMAGEN EN LA RUTA " + path);
            String espera = new Scanner(System.in).nextLine();
            System.exit(0);
        }
        BufferedImage imgCompatible = gc.createCompatibleImage(img.getWidth(null), img.getHeight(null), Transparency.TRANSLUCENT);//Cojemos la imagen compatible de los graficos compatibles
        Graphics g = imgCompatible.getGraphics();//Creamos unos graficos en torno a la buffered image
        g.drawImage(img, 0, 0, null);//Dibujamos la imagen leida en el bufferedImage, desde el punto 0,0
        g.dispose();// Desechamos la memoria de los graphics creados
        return imgCompatible;//devolvemos la imagen comatible ya que ahora contiene la imagen que necesitabamos
    }

    public static Font loadFont(final String path) {//Cargamos una fuente en una ruta indicada
        Font font = null;
        try {
            if (RUTA_ACTUAL.contains(".jar")) {
                font = Font.createFont(Font.TRUETYPE_FONT, LoadTools.class.getResourceAsStream(path)).deriveFont(15f);//Cargo la imagen
            } else {
                font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File(RUTA_ACTUAL + path))).deriveFont(15f);//Cargo la imagen
            }
        } catch (Exception e) {
            System.out.println("ERROR CARGANDO LA FUENTE EN LA RUTA" + path);
        }
        return font;
    }

    public static Color brighter(Color c, int n) {
        int red = (c.getRed() >= (255 - n)) ? 255 : c.getRed() + n;
        int green = (c.getGreen() >= (255 - n)) ? 255 : c.getGreen() + n;
        int blue = (c.getBlue() >= (255 - n)) ? 255 : c.getBlue() + n;
        Color c2 = new Color(red, green, blue);
        return c2;
    }

    public static Color darker(Color c, int n) {
        int red = (c.getRed() <= (0 + n)) ? 0 : c.getRed() - n;
        int green = (c.getGreen() <= (0 + n)) ? 0 : c.getGreen() - n;
        int blue = (c.getBlue() <= (0 + n)) ? 0 : c.getBlue() - n;
        Color c2 = new Color(red, green, blue);
        return c2;
    }
}
