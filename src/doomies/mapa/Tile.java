package doomies.mapa;

import java.awt.Graphics;
import java.awt.Rectangle;
import doomies.Entes.Entidad;
import doomies.Visual.Sprite;

/**
 * Es una clase que se compone de una hitbox y de un sprite de ahi que herede de
 * entidad
 *
 * @see doomies.Entes.Entidad
 * @author Víctor
 * @version 4
 * @since 2
 */
public class Tile extends Entidad {

    /**
     * Crea un tile con una posicion y un Sprite
     *
     * @param x Posicion en x
     * @param y Posicion en y
     * @param sprite Sprite que representa al tile
     */
    public Tile(final int x, final int y, Sprite sprite[]) {
        super(sprite, sprite[0].getWIDTH(), sprite[0].getHEIGHT());
        this.x = x;
        this.y = y;
        this.hitbox = new Rectangle(x, y, sprite[0].getWIDTH(), sprite[0].getHEIGHT());
    }

    /**
     * Dibuja el tile en su posicion correspondiente
     *
     * @param g Objeto Graphics que dibujara en pantalla
     */
    @Override
    public void dibujar(Graphics g) {
        if (this.visible) {
            this.sprites[0].dibujar(g, x, y);
        }
    }

    /**
     * Actualiza la posicion del Tile, actualizando su hitbox
     */
    @Override
    public void actualizar() {
        x += xa;
        this.hitbox.x = x;
        this.hitbox.y = y;
    }

}
