package prototype.Entes.Objetos;
//IMPORTS

import java.awt.Color;
import java.awt.Graphics;
import prototype.Visual.Sprite;
import prototype.Entes.Entidad;

/**
 *
 * @author Víctor
 */
public class Bala extends Entidad {

    //Atributos de la clase
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
        this.right = right;
    }

    @Override
    public void dibujar(final Graphics g) {
        g.setColor(Color.green);//COLOR PROVISIONAL DE LA BALA, DEBERA SER UN SPRITE
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
