package doomies.Entes.Seres;

import java.awt.Graphics;
import doomies.Visual.Sprite;
import doomies.Entes.Entidad;

public class SerVivo extends Entidad {

    protected int vida;
    protected String dir;
    protected boolean walking;
    protected boolean running;
    protected boolean falling;
    protected boolean shooting;
    protected boolean stay;
    protected static final int STAY_ID = 0;
    protected static final int WALK_ID = 2;
    protected static final int FALL_ID = 4;
    protected int spriteActual;
    protected int animacion;

    /*
    Distribucion de sprites PARA SERES:
        STAY DERECHA 0
        STAY IZQUIERDA 1
        STAY SHOOT D 2
        STAY SHOOT I 3
        WALK DERECHA 4
        WALK I 5
        WALK SHOOT D 6
        WALK SHOOT I 7
        FALL D 8
        FALL I 9
        FALL SHOOT D 10
        FALL SHOOT I 11

     */
    public SerVivo(final Sprite[] sprite, final int WIDTH, final int HEIGHT, final int vida) {
        super(sprite, WIDTH, HEIGHT);
        this.dir = "R";
        this.spriteActual = 0;
        this.vida = vida;
    }

    @Override
    public void dibujar(final Graphics g) {//Dibujamos si es visible
        if (!this.visible) {//Si no es visible se sale
            return;
        }
        this.sprites[spriteActual].dibujar(g, this.hitbox.x, this.hitbox.y);
    }

    @Override
    public void actualizar() {
        this.mover();
        this.actualizarSprite();
    }

    //Salto
    protected void jump() {
        this.ya = -11;
    }

    protected void mover() {// mueve las variables de posicion
        //this.caer();
        hitbox.x += this.xa;//aumenta la x a la velocidad que debia
        hitbox.y += this.ya;//aumenta la y a la velocidad que debia
        if (this.collidingYDown) {// Si esta colisionando en Y, no puede estar cayendo
            this.falling = false;
            this.contGravedad = 0;//Reseteo de contador
        }
        if (ya != 0) {
            falling = true;
        }
    }

    protected void contadorAnimacion() {
        if (this.animacion <= 30) {//La variable aumenta hasta 3'
            ++this.animacion;
        } else {//Si llega al tope se resetea
            this.animacion = 0;
        }
    }

    public void actualizarSprite() {
        //Actualizamos el sprite actual
        int max = 40;
        this.spriteActual = (xa != 0 && !(ya != 0 && !this.collidingYDown)) ? 4 : (ya != 0 && !this.collidingYDown) ? 8 : 0;
        this.spriteActual += (shooting) ? 2 : 0;
        if (walking && !((ya != 0) && !this.collidingYDown)) {
            if (animacion <= ((int) max / 2)) {
                spriteActual = 0;
            } else if (animacion > ((int) max / 2) && animacion <= max) {
                spriteActual = 4;
            } else {
                spriteActual = 4;
                animacion = 0;
            }
            animacion++;
        } else {
            animacion = 0;
        }
        this.spriteActual += (dir.equals("R") ? 0 : 1);
        System.out.println(spriteActual);
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public boolean isWalking() {
        return walking;
    }

    public void setWalking(boolean walking) {
        this.walking = walking;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean isFalling() {
        return falling;
    }

    public void setFalling(boolean falling) {
        this.falling = falling;
    }

    public boolean isShooting() {
        return shooting;
    }

    public void setShooting(boolean shooting) {
        this.shooting = shooting;
    }

    public boolean isStay() {
        return stay;
    }

    public void setStay(boolean stay) {
        this.stay = stay;
    }

    public int getSpriteActual() {
        return spriteActual;
    }

    public void setSpriteActual(int spriteActual) {
        this.spriteActual = spriteActual;
    }

    public int getAnimacion() {
        return animacion;
    }

    public void setAnimacion(int animacion) {
        this.animacion = animacion;
    }

    public boolean isDead() {
        return this.vida <= 0;
    }
}
