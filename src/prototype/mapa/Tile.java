package prototype.mapa;

import java.awt.Graphics;
import java.awt.Rectangle;
import prototype.Entes.Entidad;
import prototype.Visual.Sprite;

/**
 *
 * @author Víctor Zero
 */
public class Tile extends Entidad {//Nada todavia

    /**
     *
     * @param x
     * @param y
     * @param sprite
     */
    public Tile(final int x, final int y, Sprite sprite[]) {
        super(sprite, sprite[0].getWIDTH(), sprite[0].getHEIGHT());
        this.x = x;
        this.y = y;
        this.hitbox = new Rectangle(x, y, sprite[0].getWIDTH(), sprite[0].getHEIGHT());
    }

    @Override
    public void dibujar(Graphics g) {
        if (this.visible) {
            this.sprites[0].dibujar(g, x, y);
        }
    }

    @Override
    public void actualizar() {
        this.hitbox.x = x;
        this.hitbox.y = y;
    }

}
