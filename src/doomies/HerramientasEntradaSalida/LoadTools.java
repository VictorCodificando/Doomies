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
import java.util.ArrayList;
import javax.swing.JOptionPane;
import doomies.Doomies;
import doomies.Entes.Entidad;
import doomies.Entes.Seres.Enemigo;
import doomies.Visual.Sprite;
import doomies.Visual.SpriteSheet;
import doomies.mapa.Tile;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.net.URISyntaxException;

/**
 * Clase que cargara todos los recursos necesarios y nos dara diferentes
 * herramientas
 *
 * @author Víctor
 * @version 4
 * @since 1
 */
public class LoadTools {

    public static final String RUTA_ACTUAL = ((LoadTools.class.getProtectionDomain().getCodeSource().getLocation() + "").substring(6).replaceAll("%20", " "));

    /**
     * Carga una BufferedImage a partir de una ruta
     *
     * @param path Ruta de la imagen
     * @return Una BufferedImage
     */
    public static BufferedImage loadImage(final String path) {
        String ruta = "";
        try {
            ruta = LoadTools.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath().toString().substring(1).replaceAll("Doomies.jar|%20", "");
        } catch (URISyntaxException ex) {
            System.out.println(ex);
        }
        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();//Obtengo graficos compatibles
        Image img = null;//La imagen es nula por defecto para que no nos de error
        try {
            if (RUTA_ACTUAL.contains(".jar")) {
                img = ImageIO.read(LoadTools.class.getResourceAsStream(path));//Cargo la imagen
            } else {
                img = ImageIO.read(new File(ruta + path));//Cargo la imagen
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
        String ruta = "";
        try {
            ruta = LoadTools.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath().toString().substring(1).replaceAll("Doomies.jar|%20", "");
        } catch (URISyntaxException ex) {
            System.out.println(ex);
        }
        Font font = null;//null por defecto
        try {
            if (RUTA_ACTUAL.contains(".jar")) {
                ClassLoader loader = Doomies.class.getClassLoader();
                font = Font.createFont(Font.TRUETYPE_FONT, LoadTools.class.getResourceAsStream(path)).deriveFont(15f);//Cargo la fuente
            } else {
                font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File(ruta + path))).deriveFont(15f);//Cargo la fuente
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
     * @return Color mas claro
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
     * @return Color mas oscuro
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
        String ruta = "";
        try {
            ruta = LoadTools.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath().toString().substring(1).replaceAll("Doomies.jar|%20", "");//Ruta actual
        } catch (URISyntaxException ex) {
            System.out.println(ex);
        }
        File f = null;
        Tile tilesArray[][] = null;
        String mapa = "";
        try {
            /**
             * Abro el archivo y recorro linea a linea hasta llegar al ":" que
             * divide los enemigos de los tiles del mapa
             */
            Scanner lector;
            f = new File(ruta + path);
            lector = new Scanner(f);
            for (int i = 0; lector.hasNext(); i++) {
                mapa += lector.next();
                mapa += ";";
                if (mapa.contains(":")) {
                    mapa = mapa.replaceAll(":;", "");
                    break;
                }
            }
            lector.close();
            tilesArray = loadTile(mapa);//Cargo los tiles de las lineas leidas
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
     * Cargamos la informacion desde el String en bruto del archivo FUERA
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
        for (int i = 0; i < mapa.length; i++) {//Recorro todas las lineas, y si es xx el tile es null
            tiles[i] = new Tile[mapa[i].length];
            for (int j = 0; j < mapa[i].length; j++) {
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

    /**
     * Cargamos los entes desde el fichero de mapa
     *
     * @param path Ruta del fichero
     * @param entes ArrayList al que añadiremos los entes
     * @return entes en el mapa
     */
    public static ArrayList<Entidad> loadEntes(String path, ArrayList<Entidad> entes) {
        String ruta = "";
        try {
            ruta = LoadTools.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath().toString().substring(1).replaceAll("Doomies.jar|%20", "");
        } catch (URISyntaxException ex) {
            System.out.println(ex);
        }
        File f = null;
        String mapa = "";
        try {
            Scanner lector;
            f = new File(ruta + path);
            lector = new Scanner(f);
            //Leo el fichero
            for (int i = 0; lector.hasNext(); i++) {
                mapa += lector.next();
                if (mapa.contains(":")) {//Hacemos que llegue a la parte que nos interesa
                    break;
                }
            }
            for (int i = 0; lector.hasNext(); i++) {//Empezamos a cojer la informacion de los enemigos
                String line[] = lector.next().split(";");
                int enemyType = Integer.parseInt(line[0]);
                int x = Integer.parseInt(line[1]) * 64;
                int y = Integer.parseInt(line[2]) * 64;
                int anchuraAltura[] = Enemigo.establecerAnchuraYAlturaSegunTipo(enemyType);
                int width = anchuraAltura[0];
                int height = anchuraAltura[1];
                entes.add(new Enemigo(x, y, width, height, enemyType));

            }
            lector.close();
        } catch (IOException e) {//Si el mapa no se ha encontrado
            JOptionPane.showMessageDialog(new Frame(), "ERROR MAPA NO ENCONTRADO\n" + path, "ERROR", 2);
            System.exit(0);
        } catch (Exception e) {//Si hay otro error
            JOptionPane.showMessageDialog(new Frame(), "ERROR EN LA LECTURA DEL MAPA\n" + e, "ERROR", 2);
            System.exit(0);
        }
        return entes;
    }

    /**
     * Cuenta la cantidad de mapas que hay en la carpeta
     *
     * @param foldPath ruta de la carpeta
     * @return El numero de mapas que hay
     */
    public static int countMap(String foldPath) {
        String ruta = "";
        try {
            ruta = LoadTools.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath().toString().substring(1).replaceAll("Doomies.jar|%20", "");
        } catch (URISyntaxException ex) {
            System.out.println(ex);
        }
        File f = null;
        int count = 0;
        f = new File(ruta + foldPath);//Se abre el archivo
        if (!f.exists()) {//Si no existe llegado a este punto es error fatal
            JOptionPane.showMessageDialog(new Frame(), "ERROR EN LA LECTURA DEL DIRECTORIO:\n" + foldPath, "ERROR", 2);
            System.exit(0);
        }
        String[] archivos = f.list();//Cojemos la lista dentro de la carpeta y nos ponemos a buscar mapas
        for (int i = 0; i < archivos.length; i++) {
            if (archivos[i].contains("mapa")) {
                count++;
            }
        }
        return count;
    }

    /**
     * Cargamos partida desde el fichero, si no existe lo creamos
     *
     * @param path Ruta del archivo
     * @return ArrayList de partidas
     */
    public static ArrayList<String> cargarPartidas(String path) {
        String ruta = "";
        try {
            ruta = LoadTools.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath().toString().substring(1).replaceAll("Doomies.jar|%20", "");
        } catch (URISyntaxException ex) {
            System.out.println(ex);
        }
        File f = null;
        ArrayList<String> partidasExtensible = new ArrayList();
        try {
            Scanner lector;
            f = new File(ruta + path);
            if (!f.exists()) {//Si no existe lo creamos
                f.createNewFile();
            }
            lector = new Scanner(f);
            for (int i = 0; lector.hasNext(); i++) {//Recorremos el archivo buscando todas las partidas
                partidasExtensible.add(lector.next());
            }
            lector.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(new Frame(), "ERROR EN LA LECTURA DE LAS PARTIDAS:\n" + e, "ERROR", 2);//Error leyendo las partidas
            System.exit(0);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(new Frame(), "ERROR:\n" + e, "ERROR", 2);
            System.exit(0);
        }
        return partidasExtensible;
    }

    /**
     * Guardamos las partidas a un fichero
     *
     * @param path Ruta del archivo de guardado
     * @param partidas ArrayList de las partidas a guardar
     */
    public static void guardarPartidas(String path, ArrayList<String> partidas) {
        String ruta = "";
        try {
            ruta = LoadTools.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath().toString().substring(1).replaceAll("Doomies.jar|%20", "");
        } catch (URISyntaxException ex) {
            System.out.println(ex);
        }
        File f = null;
        FileWriter fw = null;
        try {
            f = new File(ruta + path);
            if (!f.exists()) {//Si no existe el archivo se crea
                f.createNewFile();
            }
            fw = new FileWriter(f);
            for (int i = 0; i < partidas.size(); i++) {
                fw.write(partidas.get(i) + "\n");
            }
            fw.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(new Frame(), "ERROR DE ESCRITURA:\n" + e, "ERROR", 2);//Error guardando las partidas
            System.exit(0);
            return;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(new Frame(), "ERROR:\n" + e, "ERROR", 2);//Error guardando las partidas
            System.exit(0);
            return;
        }
    }

    /**
     * Creamos el teclado según lo que haya en el fichero, si no hay nada se
     * cargan los controles por defecto
     *
     * @return Nuevo teclado con las opciones previstas
     */
    public static Teclado createTeclado() {
        String ruta = "";
        try {
            ruta = LoadTools.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath().toString().substring(1).replaceAll("Doomies.jar|%20", "");
        } catch (URISyntaxException ex) {
            System.out.println(ex);
        }
        Teclado keyboard = null;
        String path = "/save/config.save";
        File f = new File(ruta + path);
        if (!new File(ruta + "/save").exists()) {
            new File(ruta + "/save").mkdir();
        }
        if (!f.exists()) {
            try {
                f.createNewFile();
                FileWriter fw = new FileWriter(f);
                fw.write("37;39;16;38;67\n"
                        + "0");
                fw.close();
                return new Teclado();
            } catch (IOException ex) {
                System.err.println("ERROR NO SE PUEDE CREAR EL ARCHIVO DE CONFIGURACION ASEGURESE DE QUE SE TIENEN PERMISOS SUFICIENTES...");
            }
        }
        try {
            Scanner rd = new Scanner(f);
            String controles[] = rd.next().split(";");
            int controlesEnNumeros[] = new int[controles.length];
            for (int i = 0; i < controlesEnNumeros.length; i++) {
                controlesEnNumeros[i] = Integer.parseInt(controles[i]);
            }
            keyboard = new Teclado(controlesEnNumeros[0], controlesEnNumeros[1], controlesEnNumeros[2], controlesEnNumeros[3], controlesEnNumeros[4]);
            int opcionesFps = Integer.parseInt(rd.next());
            switch (opcionesFps) {
                case 0:
                    Doomies.fps60 = false;
                    Doomies.vSync = false;
                    break;
                case 1:
                    Doomies.fps60 = false;
                    Doomies.vSync = true;
                    break;
                case 2:
                    Doomies.fps60 = true;
                    Doomies.vSync = false;
                    break;
                default:
                    break;
            }

        } catch (FileNotFoundException ex) {
            return new Teclado();
        } catch (Exception e) {
            try {
                f.createNewFile();
                FileWriter fw = new FileWriter(f);
                fw.write("37;39;16;38;67\n"
                        + "0");
                fw.close();
                return new Teclado();
            } catch (IOException ex) {
            }
        }
        return keyboard;
    }

    public static void saveSettings(Teclado t) {
        String path = "resources/save/config.save";
        if (RUTA_ACTUAL.contains(".jar")) {
            path = "save/config.save";
        }
        File f = new File(path);

        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
        try {
            FileWriter fw = new FileWriter(f);
            String controles = t.leftKey + ";" + t.rightKey + ";" + t.runKey + ";" + t.jumpKey + ";" + t.shootKey;
            int opciones = (Doomies.fps60 || Doomies.vSync) ? (Doomies.fps60) ? 2 : 1 : 0;
            fw.write(controles + "\n" + opciones);
            fw.close();
        } catch (IOException ex) {
            System.err.println(ex);
        }

    }
}
