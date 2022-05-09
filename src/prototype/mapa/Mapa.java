package prototype.mapa;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
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
    private double xa = 30;
    private boolean right;
    private long ya;

    /**
     * Crea el mapa
     * 
     * @param seres Seres que estan en el mapa
     * @param width Anchura de la pantalla
     * @param height Altura de la pantalla
     */
    public Mapa(SerVivo[] seres, final int width, final int height) {
        this.SCREEN_HEIGHT = height;
        this.SCREEN_WIDTH = width;
        this.tiles = LoadTools.loadMap("/mapas/mapa.txt");
        this.HEIGHT = this.tiles.length * this.tiles[this.tiles.length - 1][this.tiles[this.tiles.length - 1].length - 1].getHitbox().height;
        this.WIDTH = this.tiles[this.tiles.length - 1].length * this.tiles[this.tiles.length - 1][this.tiles[this.tiles.length - 1].length - 1].getHitbox().width;
        this.seres = seres;
    }

    public void dibujar(Graphics g) {
        //Dibujamos los tiles
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (tiles[i][j] == null) {
                    continue;
                }
                tiles[i][j].dibujar(g);
            }

        }
        //Dibujamos los seres
        /*for (int i = 0; i < seres.length; i++) {
            if (seres[i].isVisible()) {
                seres[i].dibujar(g);
            }
        }*/
    }

    /**
     * Actualizamos la posicion actual del mapa
     */
    public void actualizar() {
        if (x <= 0) {
            right = true;
        } else if (x+SCREEN_WIDTH >= WIDTH) {
            right = false;
        }
        if (right) {
            x += xa;
        } else {
            x -= xa;
        }
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (tiles[i][j] == null) {
                    continue;
                }
                tiles[i][j].actualizar();
                if (!new Rectangle(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT).intersects(tiles[i][j].getHitbox())) {
                    tiles[i][j].setVisible(false);
                } else {
                    tiles[i][j].setVisible(true);
                }
                int variacionActual = (0 - x);
                tiles[i][j].setX(variacionActual + (j * tiles[i][j].WIDTH));
            }

        }
    }

}
