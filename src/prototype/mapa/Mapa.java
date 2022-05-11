package prototype.mapa;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import prototype.Entes.Entidad;
import prototype.Entes.Seres.Jugador;
import prototype.Entes.Seres.SerVivo;
import prototype.HerramientasEntradaSalida.LoadTools;

/**
 *
 * @author Víctor Zero
 */
public class Mapa {

    private final Tile[][] tiles;
    private final int SCREEN_WIDTH;
    private final int SCREEN_HEIGHT;
    private final int WIDTH;
    private final int HEIGHT;
    private final SerVivo[] seres;
    private BufferedImage background;
    private int x;
    private int y;
    private int xa = 15;
    private int ya = 15;
    private boolean right;
    private boolean up;
    public ArrayList<Entidad> entesEnMapa = new ArrayList<Entidad>();

    /**
     * Crea el mapa
     *
     * @param seres Seres que estan en el mapa
     * @param width Anchura de la pantalla
     * @param height Altura de la pantalla
     */
    public Mapa(SerVivo[] seres, final int width, final int height, int ID) {
        this.SCREEN_HEIGHT = height;
        this.SCREEN_WIDTH = width;
        this.tiles = LoadTools.loadMap("/mapas/mapa" + ID + ".txt");
        this.HEIGHT = this.tiles.length * this.tiles[this.tiles.length - 1][this.tiles[this.tiles.length - 1].length - 1].getHitbox().height;
        this.WIDTH = this.tiles[this.tiles.length - 1].length * this.tiles[this.tiles.length - 1][this.tiles[this.tiles.length - 1].length - 1].getHitbox().width;
        this.seres = seres;
        //Añadimos los tiles
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (tiles[i][j] == null) {
                    continue;
                }
                entesEnMapa.add(tiles[i][j]);
            }
        }
        //Añadimos los seres
        /*for (int i = 0; i < seres.length; i++) {
            if (seres[i]==null) {
                continue;
            }
            entesEnMapa.add(seres[i]);
        }*/
    }

    public void dibujar(Graphics g) {
        //Dibujamos todos los entes
        for (int i = 0; i < entesEnMapa.size(); i++) {
            entesEnMapa.get(i).dibujar(g);
        }
    }

    /**
     * Actualizamos la posicion actual del mapa
     */
    public void actualizar() {
        if (SCREEN_WIDTH > x + WIDTH) {
            right = true;
        } else if (0 < x) {
            right = false;
        }
        if (SCREEN_HEIGHT > y + HEIGHT) {
            up = false;
        } else if (0 < y) {
            up = true;
        }
        if (right) {
            xa = Math.abs(xa);
        } else {
            xa = Math.abs(xa) * -1;
        }
        if (up) {
            ya = Math.abs(ya) * -1;

        } else {
            ya = Math.abs(ya);
        }
        x += xa;
        // y += ya;
        for (int i = 0; i < entesEnMapa.size(); i++) {
            if (entesEnMapa.get(i) == null || entesEnMapa.get(i) instanceof Jugador) {
                continue;
            }
            entesEnMapa.get(i).actualizar();
            if (!new Rectangle(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT).intersects(entesEnMapa.get(i).getHitbox())) {
                entesEnMapa.get(i).setVisible(false);
            } else {
                entesEnMapa.get(i).setVisible(true);
            }
            entesEnMapa.get(i).setX(entesEnMapa.get(i).getX() + xa);
            //tiles[i][j].setY(tiles[i][j].getY() + ya);
        }

    }

}
