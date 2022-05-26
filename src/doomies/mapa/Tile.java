<<<<<<< HEAD:src/doomies/mapa/Tile.java
package doomies.mapa;

import java.awt.Graphics;
import java.awt.Rectangle;
import doomies.Entes.Entidad;
import doomies.Visual.Sprite;
=======
package prototype.mapa;

import java.awt.Graphics;
import java.awt.Rectangle;
import prototype.Entes.Entidad;
import prototype.Visual.Sprite;
>>>>>>> origin/Nestor:src/prototype/mapa/Tile.java

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
<<<<<<< HEAD:src/doomies/mapa/Tile.java
        x+=xa;
=======
>>>>>>> origin/Nestor:src/prototype/mapa/Tile.java
        this.hitbox.x = x;
        this.hitbox.y = y;
    }

}
