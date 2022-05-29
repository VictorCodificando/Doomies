package doomies.mapa;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import doomies.Entes.Entidad;
import doomies.Entes.Seres.Enemigo;
import doomies.Entes.Seres.Jugador;
import doomies.Entes.Seres.SerVivo;
import doomies.HerramientasEntradaSalida.LoadTools;

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
    private int xa;
    private final int ID;
    private int ya;
    private boolean right;
    private boolean limit = true;
    private boolean limit_fin;
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
        this.ID = ID;
        this.tiles = LoadTools.loadMap("/mapas/mapa" + ID + ".txt");
        this.background = LoadTools.loadImage("/images/BG_" + ID + ".png");
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
//        System.out.println("antes");
        entesEnMapa=LoadTools.loadEntes("/mapas/mapa" + ID + ".txt", entesEnMapa);
        //Añadimos los seres
        /*for (int i = 0; i < seres.length; i++) {
            if (seres[i]==null) {
                continue;
            }
            entesEnMapa.add(seres[i]);
        }*/
    }

    public void dibujar(Graphics g) {
        g.drawImage(background, (int) bg_x, -Math.abs(SCREEN_HEIGHT - this.background.getHeight()), null);
    }

    /**
     * Actualizamos la posicion actual del mapa
     */
    public void actualizar() {
        //Ha llegado al final
        //System.out.println(x);
        if (0 <= x) {
//            System.out.println("limite izq");
            limit = true;
            limit_fin = false;
        } else if (SCREEN_WIDTH >= x + WIDTH) {
//            System.out.println("lmiite derecho");
            limit = false;
            limit_fin = true;
        } else {
            limit = false;
            limit_fin = false;
        }
        x += xa;
        bg_x = (int) ((this.background.getWidth() - SCREEN_WIDTH) * ((float) (x) / WIDTH));
        y += ya;
    }

    public BufferedImage getBackground() {
        return background;
    }

    public void setBackground(BufferedImage background) {
        this.background = background;
    }

    public int getBg_x() {
        return bg_x;
    }

    public void setBg_x(int bg_x) {
        this.bg_x = bg_x;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getXa() {
        return xa;
    }

    public void setXa(int xa) {
        this.xa = xa;
    }

    public int getYa() {
        return ya;
    }

    public void setYa(int ya) {
        this.ya = ya;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public ArrayList<Entidad> getEntesEnMapa() {
        return entesEnMapa;
    }

    public void setEntesEnMapa(ArrayList<Entidad> entesEnMapa) {
        this.entesEnMapa = entesEnMapa;
    }

    public boolean isLimit() {
        return limit;
    }

    public void setLimit(boolean limit) {
        this.limit = limit;
    }

    public boolean isLimit_fin() {
        return limit_fin;
    }

    public int getID() {
        return ID;
    }

}
