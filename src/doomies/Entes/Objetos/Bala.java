package doomies.Entes.Objetos;
//IMPORTS

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import doomies.Visual.Sprite;
import doomies.Entes.Entidad;

/**
 * Clase bala que hereda de entidad
 *
 * @author Víctor
 * @version 4
 * @since 1
 */
public class Bala extends Entidad {

    /**
     * Boolean que indica la direccion de la bala
     */
    private final boolean right;

    /**
     * Crea una bala en una posicion y en una direccion en la cual saldrá
     * disparada
     *
     * @param x es la posicion en la x al inicio
     * @param y es la posicion en las y al incio
     * @param right es la direccion
     */
    public Bala(final int x, final int y, final boolean right) {
        super(new Sprite[0], 10, 6);
        this.x = x;
        this.y = y;
        this.hitbox = new Rectangle(x, y, 10, 6);
        this.right = right;
        speed = 40;
    }

    /**
     * Dibuja la bala
     *
     * @param g Graphics donde se dibujará la bala
     */
    @Override
    public void dibujar(final Graphics g) {
        g.setColor(Color.CYAN);
        g.fillRect(this.x, this.y, this.WIDTH, this.HEIGHT);
    }

    /**
     * Actualiza la bala y su posicion
     */
    @Override
    public void actualizar() {
        /**
         * Si su direccion es derecha su velocidad es positiva
         */
        this.x += (this.right) ? speed : -speed;
        this.y += this.ya;
        this.hitbox.x = x;
        this.hitbox.y = y;
        /**
         * Si se sale de la pantalla lo convertimos a invisible
         */
        if (x > 1280 || x + this.hitbox.width < 0) {
            visible = false;
        }
    }
}
