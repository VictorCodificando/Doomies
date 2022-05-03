package prototype.Entes;

import java.awt.Graphics;
import prototype.Visual.Sprite;
import java.awt.Rectangle;

public abstract class Entidad implements CuerpoGravitatorio {//Si existe esta sometido a una gravedad

    protected int x;
    protected int y;
    protected int xa;
    protected int ya;
    //Variables de velocidad
    protected int Speed;
    //Colisiona por X o por Y
    protected boolean collidingY;
    protected boolean collidingX;
    //Dimensiones
    public final int WIDTH;
    public final int HEIGHT;
    protected Rectangle hitbox;
    protected boolean visible;
    protected int contGravedad;
    protected final Sprite[] sprites;

    public abstract void dibujar(final Graphics g);

    public abstract void actualizar();

    public Entidad(final Sprite[] sprite, final int WIDTH, final int HEIGHT) {
        this.Speed = 4;
        this.collidingY = false;
        this.collidingX = false;
        this.visible = true;
        this.sprites = sprite;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
    }

    public void caer() {
        if (this.contGravedad == 10) {//Si es 10 (numero establecido aleatoriamente porque quedaba mejor) entonces aumenta la velocidad
            // Esto hace a la variable G una aceleracion
            this.ya += 4;
            this.contGravedad = 0;
        } else {//Si no es 10 aumenta esta variable
            this.contGravedad++;
        }
    }

    public void ajustarHitbox(final Rectangle limit) {//Arregla glitchs visuales
        if (this.collidingY) {
            this.hitbox.y = limit.y - this.hitbox.height;
        }
    }

    public void setColliding(final boolean x, final boolean y) {//Sets de las colisiones
        this.collidingX = x;
        this.collidingY = y;
    }

    public int getX() {
        return this.x;
    }

    public void setX(final int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(final int y) {
        this.y = y;
    }

    public int getXa() {
        return this.xa;
    }

    public void setXa(final int xa) {
        this.xa = xa;
    }

    public int getYa() {
        return this.ya;
    }

    public void setYa(final int ya) {
        this.ya = ya;
    }

    public int getSpeed() {
        return this.Speed;
    }

    public void setSpeed(final int Speed) {
        this.Speed = Speed;
    }

    public boolean isCollidingY() {
        return this.collidingY;
    }

    public void setCollidingY(final boolean collidingY) {
        this.collidingY = collidingY;
    }

    public boolean isCollidingX() {
        return this.collidingX;
    }

    public void setCollidingX(final boolean collidingX) {
        this.collidingX = collidingX;
    }

    public Rectangle getHitbox() {
        return this.hitbox;
    }

    public void setHitbox(final Rectangle hitbox) {
        this.hitbox = hitbox;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void setVisible(final boolean visible) {
        this.visible = visible;
    }

    public int getContGravedad() {
        return this.contGravedad;
    }

    public void setContGravedad(final int contGravedad) {
        this.contGravedad = contGravedad;
    }
}
