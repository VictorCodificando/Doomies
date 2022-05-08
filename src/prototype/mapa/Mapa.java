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

    private final Tile[] tiles;
    private final int SCREEN_WIDTH;
    private final int SCREEN_HEIGHT;
    private SerVivo[] seres;
    private BufferedImage background;
    private double x;
    private int y;
    private double xa = 0.009;
    private long ya;

    /**
     * Crea el mapa teniendo los tiles y los seres que componen el mapa
     *
     * @param tiles Los tiles que representan los mapas
     * @param seres Los seres, enemigos... que estan representados en el mapa
     */
    public Mapa(SerVivo[] seres, final int width, final int height) {
        this.SCREEN_HEIGHT = height;
        this.SCREEN_WIDTH = width;
        this.tiles = LoadTools.loadMap("/mapas/mapa.txt");
        this.seres = seres;
    }

    public void dibujar(Graphics g) {
        //Dibujamos los tiles
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i] == null) {
                continue;
            }
            if (tiles[i].isVisible()) {
                tiles[i].dibujar(g);
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
        x += xa;
        y += ya;
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i] == null) {
                continue;
            }
            tiles[i].actualizar();
            if (!new Rectangle(0,0,SCREEN_WIDTH,SCREEN_HEIGHT).intersects(tiles[i].getHitbox())) {
                tiles[i].setVisible(false);
            }else{
                tiles[i].setVisible(true);
                
            }
            tiles[i].setX(tiles[i].getX() + (0 - (int) x));
        }
    }

}
