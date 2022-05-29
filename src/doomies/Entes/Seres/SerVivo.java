package doomies.Entes.Seres;

import java.awt.Graphics;
import doomies.Visual.Sprite;
import doomies.Entes.Entidad;
import java.awt.Color;
import java.awt.image.BufferedImage;

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
    protected Sprite[] spritesHovered = null;
    protected int animacion;
    protected int cooldownDaño = 0;
    protected int COOLDOWNDAÑOTOTAL = 100;

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

    /**
     * Dibuja los sprites con daño
     */
    protected void paintHoveredSprites() {
        this.spritesHovered = new Sprite[sprites.length];
        Sprite spActual;
        int incremento = 100;
        for (int i = 0; i < this.sprites.length; i++) {
            spActual = sprites[i];
            BufferedImage img = spActual.getImgCopy();
            Graphics g = img.getGraphics();
            g.drawImage(img, 0, 0, null);
            for (int k = 0; k < img.getHeight(); k++) {
                for (int j = 0; j < img.getWidth(); j++) {
                    if (((img.getRGB(j, k)) >>> 24) == 0x00) {
                        continue;
                    }
                    Color oldColor = new Color(img.getRGB(j, k));
                    int rojo = oldColor.getRed();
                    Color color = new Color(((rojo < (255 - incremento)) ? (rojo + incremento) : 255), oldColor.getGreen(), oldColor.getBlue());
                    g.setColor(color);
                    g.fillRect(j, k, 1, 1);
                }
            }

            g.dispose();
            spritesHovered[i] = new Sprite(img);

        }
    }

    @Override
    public void dibujar(final Graphics g) {//Dibujamos si es visible

        if (spritesHovered == null || spritesHovered[0] == null) {
            paintHoveredSprites();
        }
        if (!this.visible) {//Si no es visible se sale
            return;
        }
        if (cooldownDaño != 0) {
            if (this instanceof Jugador && cooldownDaño >= COOLDOWNDAÑOTOTAL - 5) {
                this.sprites[spriteActual].dibujar(g, this.hitbox.x, this.hitbox.y);
                return;
            }
            this.spritesHovered[spriteActual].dibujar(g, this.hitbox.x, this.hitbox.y);
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
        if (!collidingYDown) {
            return;
        }
        this.ya = -11;
    }

    protected void mover() {// mueve las variables de posicion
        hitbox.x += this.xa;//aumenta la x a la velocidad que debia
        hitbox.y += this.ya;//aumenta la y a la velocidad que debia
        if (this.collidingYDown) {// Si esta colisionando en Y, no puede estar cayendo
            this.falling = false;
            this.contGravedad = 0;//Reseteo de contador
        }
        if (ya != 0) {
            falling = true;
        }
        activarCooldownVida();
    }

    public void perderVida() {
        if (cooldownDaño == 0) {
            this.vida--;
            cooldownDaño++;
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
        this.spriteActual += (shooting) ? 2 : 0;
        this.spriteActual += (dir.equals("R") ? 0 : 1);
    }

    private void activarCooldownVida() {
        if (cooldownDaño == 0) {
        } else if (cooldownDaño < COOLDOWNDAÑOTOTAL && cooldownDaño > 0) {
            this.cooldownDaño++;
        } else {
            cooldownDaño = 0;
        }
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
