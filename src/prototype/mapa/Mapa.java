package prototype.mapa;

import java.awt.Graphics;
import java.awt.Image;
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
    private int bg_x;
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
        this.tiles = LoadTools.loadMap("./mapas/mapa" + ID + ".txt");
        this.background = LoadTools.loadImage("./images/BG_" + ID + ".png");
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
        g.drawImage(background, (int) bg_x, -Math.abs(SCREEN_HEIGHT-this.background.getHeight()), null);
        //Dibujamos todos los entes
        for (int i = 0; i < entesEnMapa.size(); i++) {
            entesEnMapa.get(i).dibujar(g);
        }
    }

    /**
     * Actualizamos la posicion actual del mapa
     */
    public void actualizar() {  
        //Ha llegado al final
        if (SCREEN_WIDTH > x + WIDTH) {
            right = true;
        } else if (0 < x) {
            right = false;
        }
        //Ha llegado al final
        if (SCREEN_HEIGHT > y + HEIGHT) {
            up = false;
        } else if (0 < y) {
            up = true;
        }
        //Cambio de la velocidad segun el eje
        //X
        if (right) {
            xa = Math.abs(xa);
        } else {
            xa = Math.abs(xa) * -1;
        }
        //Y
        if (up) {
            ya = Math.abs(ya) * -1;

        } else {
            ya = Math.abs(ya);
        }

        x += xa;
        bg_x = (int)((this.background.getWidth()-SCREEN_WIDTH) * ((float) (x) / WIDTH));
        // y += ya;
        for (int i = 0; i < entesEnMapa.size(); i++) {
            if (entesEnMapa.get(i) == null || entesEnMapa.get(i) instanceof Jugador) {
                continue;
            }
            entesEnMapa.get(i).actualizar();
            if (isOutSideScreen(entesEnMapa.get(i).getHitbox())) {
                entesEnMapa.get(i).setVisible(false);
            } else {
                entesEnMapa.get(i).setVisible(true);
            }
            entesEnMapa.get(i).setX(entesEnMapa.get(i).getX() + xa);
            //tiles[i][j].setY(tiles[i][j].getY() + ya);
        }

    }

    public boolean isOutSideScreen(Rectangle rect) {
        if (SCREEN_WIDTH > rect.x || SCREEN_HEIGHT > rect.y || rect.x + rect.width < 0 || rect.y + rect.height < 0) {
            return false;
        }
        return true;
    }

}
