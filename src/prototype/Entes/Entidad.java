package prototype.Entes;

import java.awt.Graphics;
import prototype.Visual.Sprite;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import javax.sound.sampled.Line;
import prototype.Gestores.GestorJuego;

public abstract class Entidad implements CuerpoGravitatorio {//Si existe esta sometido a una gravedad

    protected int x;
    protected int y;
    protected int xa;
    protected int ya;
    //Variables de velocidad
    protected int Speed;
    //Colisiona por X o por Y
    protected boolean collidingYUp;
    protected boolean collidingXRight;
    protected boolean collidingYDown;
    protected boolean collidingXLeft;
    //Dimensiones
    public final int WIDTH;
    public final int HEIGHT;
    protected Rectangle hitbox;
    protected boolean visible;
    protected int contGravedad;
    protected Sprite[] sprites;

    public abstract void dibujar(final Graphics g);

    public abstract void actualizar();

    public Entidad(final Sprite[] sprite, final int WIDTH, final int HEIGHT) {
        this.Speed = 4;
        collidingYUp = false;
        collidingXRight = false;
        collidingYDown = false;
        collidingXLeft = false;
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

    public void ajustarHitboxinX(final Rectangle limit, final Rectangle next) {//Arregla glitchs visuales
        if (limit.x > next.x && limit.x < next.x + next.width && xa != 0) {
            collidingXLeft = true;
            hitbox.x = limit.x - hitbox.width - ((Math.abs(xa) == 4) ? 4 : 8);
        } else if (limit.x < next.x && limit.x + limit.width > next.x && xa != 0) {
            collidingXRight = true;
            hitbox.x = limit.x + limit.width + ((Math.abs(xa) == 4) ? 4 : 8);
        }
    }

    public void ajustarHitboxinY(final Rectangle limit, final Rectangle next) {
        if (limit.y > next.y && limit.y < next.y + next.height) {
            collidingYDown = true;
            ya = 0;
            hitbox.y = limit.y - hitbox.height;
        } else if (limit.y < next.y && limit.y + limit.height > next.y) {
            collidingYUp = true;
            ya = 0;
            hitbox.y = limit.y + limit.height;
        }
    }

    public void isGoingToCollide(Rectangle estatico) {
        Rectangle nextHitboxinX = new Rectangle(hitbox.x + xa, hitbox.y, hitbox.width, hitbox.height);
        Rectangle nextHitboxinY = new Rectangle(hitbox.x, hitbox.y + ya, hitbox.width, hitbox.height);
        if (estatico.intersects(nextHitboxinY)) {
            this.ajustarHitboxinY(estatico, nextHitboxinY);
        }
        if (estatico.intersects(nextHitboxinX)) {
            this.ajustarHitboxinX(estatico, nextHitboxinX);
        }
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
//funcion para cambiar de sentido al enemigo

    public void enemyDir() {
    }

    public boolean isCollidingYUp() {
        return collidingYUp;
    }

    public void setCollidingYUp(boolean collidingYUp) {
        this.collidingYUp = collidingYUp;
    }

    public boolean isCollidingXRight() {
        return collidingXRight;
    }

    public void setCollidingXRight(boolean collidingXRight) {
        this.collidingXRight = collidingXRight;
    }

    public boolean isCollidingYDown() {
        return collidingYDown;
    }

    public void setCollidingYDown(boolean collidingYDown) {
        this.collidingYDown = collidingYDown;
    }

    public boolean isCollidingXLeft() {
        return collidingXLeft;
    }

    public void setCollidingXLeft(boolean collidingXLeft) {
        this.collidingXLeft = collidingXLeft;
    }

}
