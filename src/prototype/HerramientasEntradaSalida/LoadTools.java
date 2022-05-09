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
import java.io.InputStream;
import java.util.ArrayList;
import prototype.Prototype;
import prototype.Visual.Sprite;
import prototype.Visual.SpriteSheet;
import prototype.mapa.Mapa;
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

    public static Tile[][] loadMap(String path) {
        File f = null;
        ArrayList<Tile> tiles = new ArrayList();
        Tile tilesArray[][]=null;
        String mapa = "";
        SpriteSheet hoja = SpriteSheet.MAPA;
        try {
            Scanner lector;
            if (RUTA_ACTUAL.contains(".jar")) {
                InputStream inputStream = LoadTools.class.getResourceAsStream(path);
                lector = new Scanner(inputStream);
            } else {
                f = new File(RUTA_ACTUAL + path);
                lector = new Scanner(f);
            }
            for (int i = 0; lector.hasNext(); i++) {
                mapa += lector.next() + ";";
            }
            tilesArray=loadTile(mapa);
        } catch (IOException e) {
            System.out.println(RUTA_ACTUAL.replaceAll("Prototype.jar", "") + path);
            System.out.println("ERROR MAPA NO ENCONTRADO" + e);
        } catch (Exception e) {
            System.err.println("ERROR EN LA LECTURA DEL MAPA" + e);
        }
        return tilesArray;
    }

    private static Tile[][] loadTile(String in) {
        String [] inSplitted=in.split(";");
        String mapa[][]=new String[inSplitted.length][];
        for (int i = 0; i < inSplitted.length; i++) {
            mapa[i]=inSplitted[i].split("-");
        }
        Tile[][] tiles = new Tile[mapa.length][];
        for (int i = 0; i < mapa.length; i++) {
            tiles[i] = new Tile[mapa[i].length];
            for (int j = 0; j < mapa[i].length; j++) {
                if (mapa[i][j].contains("x")) {
                    tiles[i][j]=null;
                    continue;
                }
                Sprite actual[]= {SpriteSheet.MAPA.getSprite(Integer.parseInt(mapa[i][j].substring(0, 1)),Integer.parseInt(mapa[i][j].substring(1)))};
                tiles[i][j] = new Tile(actual[0].getWIDTH()*j,actual[0].getHEIGHT()*i,actual);
            }
        }
        return tiles;
    }
}
