package prototype.HerramientasEntradaSalida;

import java.awt.Color;
import java.io.FileInputStream;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.GraphicsConfiguration;
import java.util.Scanner;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.awt.Font;
import java.awt.Transparency;
import java.io.IOException;
import java.util.ArrayList;
import prototype.Prototype;
import prototype.Visual.Sprite;
import prototype.Visual.SpriteSheet;
import prototype.mapa.Tile;

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
            System.out.println(RUTA_ACTUAL + path);
            String espera = new Scanner(System.in).nextLine();
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
                ClassLoader loader = Prototype.class.getClassLoader();
                font = Font.createFont(Font.TRUETYPE_FONT, LoadTools.class.getResourceAsStream(path)).deriveFont(15f);//Cargo la imagen
            } else {
                font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File(RUTA_ACTUAL + path))).deriveFont(15f);//Cargo la imagen
            }
        } catch (Exception e) {
            System.out.println("ERROR CARGANDO LA FUENTE EN LA RUTA " + path);
            System.out.println(RUTA_ACTUAL + path);
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

    /*public static Tile[] loadMap(String path) {
        File f = null;
        ArrayList<Tile> tiles = new ArrayList();
        Tile tilesArray[];
        SpriteSheet hoja = SpriteSheet.MAPA_ACTUAL;
        try {
            if (RUTA_ACTUAL.contains(".jar")) {
                f = new File(path);
            } else {
                f = new File(RUTA_ACTUAL + path);
            }
            Scanner lector = new Scanner(f);
            for (int i = 0; lector.hasNext(); i++) {
                String linea[] = lector.next().split("-");
                for (int j = 0; j < 10; j++) {
                    int[] id_Bloque = {Integer.parseInt(linea[j].substring(0, 1)), Integer.parseInt(linea[j].substring(1))};
                    Sprite spTemp[] = {hoja.getSprite(id_Bloque[0], id_Bloque[1])};
                    tiles.add(new Tile(1, 1, spTemp));
                }
            }
        } catch (IOException e) {
            System.out.println("ERROR MAPA NO ENCONTRADO");
        } catch (Exception e) {
            System.out.println("ERROR EN LA LECTURA DEL MAPA");
        }
        tilesArray = new Tile[tiles.size()];
        for (int i = 0; i < tilesArray.length; i++) {
            tilesArray[i] = (Tile) tiles.get(i);
        }
        return tilesArray;
    }*/
}
