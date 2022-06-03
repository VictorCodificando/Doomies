package doomies.mapa;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import doomies.Entes.Entidad;
import doomies.HerramientasEntradaSalida.LoadTools;

/**
 * Mapa que contiene el fondo e importa los tiles necesarios
 *
 * @author Víctor
 * @version 4
 * @since 2
 */
public class Mapa {

    /**
     * Los tiles que contiene el mapa
     */
    private final Tile[][] tiles;
    /**
     * Anchura pantalla
     */
    private final int SCREEN_WIDTH;
    /**
     * Altura pantalla
     */
    private final int SCREEN_HEIGHT;
    /**
     * Anchura del mapa
     */
    private final int WIDTH;
    /**
     * Altura del mapa
     */
    private final int HEIGHT;
    /**
     * Imagen de fondo
     */
    private BufferedImage background;
    /**
     * Posicion x del fondo
     */
    private int bg_x;
    /**
     * posicion x del mapa
     */
    private int x;
    /**
     * Velocidad en x del mapa
     */
    private int xa;
    /**
     * ID del mapa
     */
    private final int ID;
    /**
     * Si la pantalla esta en el limite izquierdo del mapa
     */
    private boolean limit = true;
    /**
     * Si el mapa esta en el limite derecho del mapa
     */
    private boolean limit_fin;
    /**
     * Contiene los entes del mapa
     */
    public ArrayList<Entidad> entesEnMapa = new ArrayList();

    /**
     * Crea el mapa sabiendo la altura y achura de la pantalla, y la ID del mapa
     *
     * @param width Anchura de la pantalla
     * @param height Altura de la pantalla
     * @param ID ID del mapa
     */
    public Mapa(final int width, final int height, int ID) {
        this.SCREEN_HEIGHT = height;
        this.SCREEN_WIDTH = width;
        this.ID = ID;
        this.tiles = LoadTools.loadMap("/mapas/mapa" + ID + ".txt");//Importamos el mapa
        this.background = LoadTools.loadImage("/images/BG_" + ID + ".png");
        this.HEIGHT = this.tiles.length * this.tiles[this.tiles.length - 1][this.tiles[this.tiles.length - 1].length - 1].getHitbox().height;
        this.WIDTH = this.tiles[this.tiles.length - 1].length * this.tiles[this.tiles.length - 1][this.tiles[this.tiles.length - 1].length - 1].getHitbox().width;
        //Añadimos los tiles
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (tiles[i][j] == null) {
                    continue;
                }
                entesEnMapa.add(tiles[i][j]);
            }
        }
        entesEnMapa = LoadTools.loadEntes("/mapas/mapa" + ID + ".txt", entesEnMapa);//Impoartamos los enemigos
    }

    /**
     * Dibuja el fondo del mapa en pantalla
     *
     * @param g Graphics que dibujara en pantalla
     */
    public void dibujar(final Graphics g) {
        g.drawImage(background, (int) bg_x, -Math.abs(SCREEN_HEIGHT - this.background.getHeight()), null);
    }

    /**
     * Actualizamos la posicion actual del mapa
     */
    public void actualizar() {
        //Ha llegado al final
        if (0 <= x) {
            limit = true;
            limit_fin = false;
        } else if (SCREEN_WIDTH >= x + WIDTH) {
            limit = false;
            limit_fin = true;
        } else {
            limit = false;
            limit_fin = false;
        }
        x += xa;
        bg_x = (int) ((this.background.getWidth() - SCREEN_WIDTH) * ((float) (x) / WIDTH));
    }

    /**
     *
     * @param xa Establece la nueva velocidad en x
     */
    public void setXa(int xa) {
        this.xa = xa;
    }

    /**
     *
     * @return Si esta en el limite izquierdo del mapa
     */
    public boolean isLimit() {
        return limit;
    }

    /**
     *
     * @return Si esta en el limite derecho del mapa
     */
    public boolean isLimit_fin() {
        return limit_fin;
    }

    /**
     *
     * @return ID del mapa
     */
    public int getID() {
        return ID;
    }

}
