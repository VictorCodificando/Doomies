package doomies.Entes.Objetos;
//IMPORTS

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import doomies.Visual.Sprite;
import doomies.Entes.Entidad;

/**
 *
 * @author Víctor
 */
public class Bala extends Entidad {

    /**
     * Boolean que indica si esta llendo hacia la derecha
     */
    private final boolean right;//Indica direccion
    private final int speed = 40;//La velocidad por defecto sera 40

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

    @Override
    public void actualizar() {//Acualizo la posicion de la bala
        //caer(); por ahora no cae
        if (!this.right) {
            this.x -= this.speed;
        } else {
            this.x += this.speed;
        }
        this.y += this.ya;//es 0 por ahora
        this.hitbox.x = x;
        this.hitbox.y = y;
        if (x > 1280 || x + this.hitbox.width < 0) {
            visible = false;
        }
    }

    /*
    getters y setters
     */
    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    public int getWIDTH() {
        return this.WIDTH;
    }

    public int getHEIGHT() {
        return this.HEIGHT;
    }
}
