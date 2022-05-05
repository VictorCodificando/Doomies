package prototype.mapa;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import prototype.Entes.Seres.SerVivo;

/**
 *
 * @author Víctor Zero
 */
public class Mapa {

    private final Tile[] tiles;
    private SerVivo[] seres;
    private BufferedImage background;
    private int x;
    private int y;
    private int xa;
    private int ya;

    public Mapa(Tile[] tiles, SerVivo[] seres) {
        this.tiles = tiles;
        this.seres = seres;
    }

    public void dibujar(Graphics g) {
        //Dibujamos los tiles
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i].isVisible()) {
                tiles[i].dibujar(g);
            }
        }
        //Dibujamos los seres
        for (int i = 0; i < seres.length; i++) {
            if (seres[i].isVisible()) {
                seres[i].dibujar(g);
            }
        }
    }

    public void actualizar() {
        x+=xa;
        y+=ya;
    }

}
