package doomies.HerramientasEntradaSalida;

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
import java.awt.Frame;
import java.awt.Transparency;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import doomies.Doomies;
import doomies.Visual.Sprite;
import doomies.Visual.SpriteSheet;
import doomies.mapa.Tile;

public class LoadTools {

    public static final String RUTA_ACTUAL = ((LoadTools.class.getProtectionDomain().getCodeSource().getLocation() + "").substring(6).replaceAll("%20", " "));

    /**
     * Carga una BufferedImage a partir de una ruta
     *
     * @param path Ruta de la imagen
     * @return Una BufferedImage
     */
    public static BufferedImage loadImage(final String path) {
        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();//Obtengo graficos compatibles
        Image img = null;//La imagen es nula por defecto para que no nos de error
        try {
            if (RUTA_ACTUAL.contains(".jar")) {
                System.out.println("error aqui");
                img = ImageIO.read(LoadTools.class.getResourceAsStream(path));//Cargo la imagen
            } else {
                img = ImageIO.read(new File(RUTA_ACTUAL + path));//Cargo la imagen
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(new Frame(), "ERROR CARGANDO LA IMAGEN EN LA RUTA\n" + path);
            System.exit(0);
        }
        BufferedImage imgCompatible = gc.createCompatibleImage(img.getWidth(null), img.getHeight(null), Transparency.TRANSLUCENT);//Cojemos la imagen compatible de los graficos compatibles
        Graphics g = imgCompatible.getGraphics();//Creamos unos graficos en torno a la buffered image
        g.drawImage(img, 0, 0, null);//Dibujamos la imagen leida en el bufferedImage, desde el punto 0,0
        g.dispose();// Desechamos la memoria de los graphics creados
        return imgCompatible;//devolvemos la imagen comatible ya que ahora contiene la imagen que necesitabamos
    }

    /**
     * Carga desde un archivo una fuente
     *
     * @param path La ruta donde se encuentra la fuente
     *
     * @return La fuente de la ruta, no llega a retornar null si hay error, ya
     * que se termina el programa
     */
    public static Font loadFont(final String path) {//Cargamos una fuente en una ruta indicada
        Font font = null;
        try {
            if (RUTA_ACTUAL.contains(".jar")) {
                ClassLoader loader = Doomies.class.getClassLoader();
                font = Font.createFont(Font.TRUETYPE_FONT, LoadTools.class.getResourceAsStream(path)).deriveFont(15f);//Cargo la imagen
            } else {
                font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File(RUTA_ACTUAL + path))).deriveFont(15f);//Cargo la imagen
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(new Frame(), "ERROR CARGANDO LA FUENTE EN LA RUTA\n" + path, "ERROR", 2);
            System.exit(0);
        }
        return font;
    }

    /**
     * Aclara un color n veces, o lo que es igual, acerca 20 veces el color al
     * blanco
     *
     * @param c Es el color a aclarecer
     * @param n Es la cantidad claridad se quiere aplicar
     * @return
     */
    public static Color brighter(Color c, int n) {
        int red = (c.getRed() >= (255 - n)) ? 255 : c.getRed() + n;
        int green = (c.getGreen() >= (255 - n)) ? 255 : c.getGreen() + n;
        int blue = (c.getBlue() >= (255 - n)) ? 255 : c.getBlue() + n;
        Color c2 = new Color(red, green, blue);
        return c2;
    }

    /**
     * Oscurece un color n veces, o lo que es igual, acerca 20 veces el color al
     * negro
     *
     * @param c Es el color a oscurecer
     * @param n Es la cantidad de oscureza se quiere aplicar
     * @return
     */
    public static Color darker(Color c, int n) {
        int red = (c.getRed() <= (0 + n)) ? 0 : c.getRed() - n;
        int green = (c.getGreen() <= (0 + n)) ? 0 : c.getGreen() - n;
        int blue = (c.getBlue() <= (0 + n)) ? 0 : c.getBlue() - n;
        Color c2 = new Color(red, green, blue);
        return c2;
    }

    /**
     * Carga los tiles del mapa desde un archivo
     *
     * @param path Ruta donde se encuentra el archivo que compone el mapa
     * @return Los tiles que componen el mapa del archivo
     */
    public static Tile[][] loadMap(String path) {
        File f = null;
        ArrayList<Tile> tiles = new ArrayList();
        Tile tilesArray[][] = null;
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
            lector.close();
            tilesArray = loadTile(mapa);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(new Frame(), "ERROR MAPA NO ENCONTRADO\n" + path, "ERROR", 2);
            System.exit(0);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(new Frame(), "ERROR EN LA LECTURA DEL MAPA\n" + e, "ERROR", 2);
            System.exit(0);
        }

        return tilesArray;
    }

    /**
     * Cargamos la informacion desde el String en bruto del archivo
     *
     * @param in El String que compone los tiles del mapa
     * @return Un array Bidimensional de Tiles que representa en que posicion
     * esta cada uno
     */
    private static Tile[][] loadTile(String in) {
        String[] inSplitted = in.split(";");
        String mapa[][] = new String[inSplitted.length][];
        for (int i = 0; i < inSplitted.length; i++) {
            mapa[i] = inSplitted[i].split("-");
        }
        Tile[][] tiles = new Tile[mapa.length][];
        for (int i = 0; i < mapa.length; i++) {
            tiles[i] = new Tile[mapa[i].length];
            for (int j = 0; j < mapa[i].length; j++) {
                //System.out.println(mapa[i][j]);
                if (mapa[i][j].contains("x")) {
                    tiles[i][j] = null;
                    continue;
                }
                Sprite actual[] = {SpriteSheet.MAPA.getSprite(Integer.parseInt(mapa[i][j].substring(0, 1)), Integer.parseInt(mapa[i][j].substring(1, 2)))};
                tiles[i][j] = new Tile(actual[0].getWIDTH() * j, actual[0].getHEIGHT() * i, actual);
            }
        }
        return tiles;
    }

    public static int countMap(String foldPath) {
        File f = null;
        int count = 0;
        if (RUTA_ACTUAL.contains(".jar")) {
            final ClassLoader loader = Thread.currentThread().getContextClassLoader();
            try {
                int hasMoreFiles = 0;
                //Se hara referencia a mapa
                for (int i = 1; hasMoreFiles < 1; i++) {
                    try {
                        InputStream inputStream = LoadTools.class.getResourceAsStream(foldPath + "/mapa" + i + ".txt");
                        if (inputStream != null) {
                            count++;
                        } else {
                            hasMoreFiles = 1;
                        }
                    } catch (Exception e) {
                        hasMoreFiles = 1;
                        break;
                    }
                }
                return count;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(new Frame(), "ERROR EN LA LECTURA DEL DIRECTORIO DENTRO DEL JAR:\n" + foldPath, "ERROR", 2);
                System.exit(0);
            }
        } else {
            f = new File(RUTA_ACTUAL + foldPath);
        }
        if (!f.exists()) {
            JOptionPane.showMessageDialog(new Frame(), "ERROR EN LA LECTURA DEL DIRECTORIO:\n" + foldPath, "ERROR", 2);
            System.exit(0);
        }
        String[] archivos = f.list();
        for (int i = 0; i < archivos.length; i++) {
            count++;
        }
        return count;
    }

    public static String reemplazarANull(String a) {
        if ((a.length() + 1) % 3 != 0) {//tiene que ser divisible entre 3 para que este el formato bien porque coje xx- y es +1 porque los ultimos 2 no acaban en -
            System.err.println("DA ERROR");
            System.exit(0);
        }
        for (int i = 0; i < a.length(); i += 3) {//Se hace un for para comprobar que lo que se meten son parejas de xx o de numeros y no otra cosa
            if (a.substring(i, i + 2).equals("xx")) {//Se comprueba que lo que se mete es una pareja de xx y no otras letras.
            } else {
                try {
                    int block = Integer.parseInt(a.substring(i, i + 2));//Se comprueba que lo que se mete es una pareja de numeros y no otras cosas
                } catch (Exception e) {
                    System.err.println("DA ERROR");
                    System.exit(0);
                }
            }
            if (i == a.length() - 2) {//Si son los 2 ultimos se los salta y continua
                continue;
            }
            if (!a.substring(i + 2, i + 3).equals("-")) {//Se comprueba que en cada 3 posicion haya un - , sino da un error
                System.err.println("DA ERROR");
                System.exit(0);
            }
        }
        a = a.replace("xx", "");//Se remplazan las xx por espacios vacios para optimizar espacio
        return a;

    }

    public static String reemplazarAX(String c) {
        int i = 0;
        if (!c.substring(i, i + 1).equals("-")) {//Se comprueba que lo que esta en primera posicion sea distinto de "-"

            try {
                int block = Integer.parseInt(c.substring(0, 2));//Se comprueba si lo que se mete al principio es una pareja de numeros y no otras cosas
                i += 2;//Se salta 2 para que no se vuelva a comprobar 
            } catch (Exception e) {//si lo primero no son numeros se pone i = 0 para que se empieze a comprobar desde el principio
                i = 0;
            }
        }
        while (i < c.length()) {
            if (!c.substring(i, i + 1).equals("-")) {//Se comprueba que la primera posicion es distinta a "-", si es distinta da un error porque ya se habia comprobado antes si era un numero
                System.err.println("DA ERROR");//
                System.exit(0);
            } else if ((c.length() - 2) > (i) && c.substring(i + 1, i + 2).equals("-")) {//Se comprueba que si no es el final del String y hay un "-" seguido de otro "-" sigue con el bucle
                i++;
                continue;
            } else if (c.length() - 2 > (i)) {//Se comprueba otra vez si no es el final del String

                try {
                    int block = Integer.parseInt(c.substring(i + 1, i + 3));//Se comprueba que lo que se mete es una pareja de numeros y no otras cosas, sino da error
                } catch (Exception e) {
                    System.err.println("DA ERROR");
                    System.exit(0);
                }
                i += 3;//Se le suma 3 a la i para que no se vuelva a comprobar la pareja de numeros
            } else {
                break;
            }
        }

        if (c.substring(0, 1).equals("-")) {//Se comprueba si el String empieza por "-" y si lo hace pone "xx" al principio
            c = "xx" + c;
        }
        while (c.contains("--")) {//Se comprueba que hay "--" y si las hay mete "xx" entre ellas

            c = c.replaceAll("--", "-xx-");
        }

        if (c.substring((c.length() - 1), c.length()).equals("-")) {//Se comprueba si acaba en "-" y si acaba asi añade "xx" al final
            c = c + "xx";
        }
        return c;
    }

}
