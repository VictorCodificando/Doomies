/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package doomies.Entes.Seres;

import java.awt.Graphics;
import java.awt.Rectangle;
import doomies.Visual.Sprite;
import doomies.Visual.SpriteSheet;
import java.awt.Color;
import java.awt.Frame;
import javax.swing.JOptionPane;

/**
 *
 * @author Víctor Zero
 */
public class Enemigo extends SerVivo {

    private final int VIDA_TOTAL;
    private int enemyType;
    public static int xPlayer;
    public static int yPlayer;
    private int cooldownMuestra = 0;
    private int cooldownMuestraY = 0;
    protected static final int SPRINT_ID = 6;
    private final int COOLDONWMUESTRALIMIT = (int) Math.random() * 15 + 10;
    private int SpeedY;

    public Enemigo(final int x, final int y, final int WIDTH, final int HEIGHT, int type) {
        super(new Sprite[8], WIDTH, HEIGHT, 5);
        this.COOLDOWNDAÑOTOTAL = 10;
        switch (type) {
            //IMP (2-5)
            case 0:
                this.Speed = (int) (Math.floor(Math.random() * 4 + 2));
                this.vida = 10;
                this.sprites = new Sprite[]{SpriteSheet.IMP.getSprite(0, 0), SpriteSheet.IMP.getSprite(0, 1), SpriteSheet.IMP.getSprite(1, 0), SpriteSheet.IMP.getSprite(1, 1), SpriteSheet.IMP.getSprite(2, 0), SpriteSheet.IMP.getSprite(2, 1)};
                this.enemyType = 0;
                break;
            case 1://(3-6)
                this.Speed = (int) (Math.floor(Math.random() * 4 + 3));
                this.vida = 12;
                this.sprites = new Sprite[]{SpriteSheet.PINKIE.getSprite(0, 0), SpriteSheet.PINKIE.getSprite(0, 1), SpriteSheet.PINKIE.getSprite(1, 0), SpriteSheet.PINKIE.getSprite(1, 1), SpriteSheet.PINKIE.getSprite(2, 0), SpriteSheet.PINKIE.getSprite(2, 1), SpriteSheet.PINKIE.getSprite(3, 0), SpriteSheet.PINKIE.getSprite(3, 1)};
                this.enemyType = 1;
                break;
            case 2://(1-4)
                this.Speed = (int) (Math.floor(Math.random() * 4 + 1));
                this.vida = 8;
                this.sprites = new Sprite[]{SpriteSheet.SOUL.getSprite(0, 0), SpriteSheet.SOUL.getSprite(0, 1),
                    SpriteSheet.SOUL.getSprite(0, 0), SpriteSheet.SOUL.getSprite(0, 1),
                    SpriteSheet.SOUL.getSprite(0, 0), SpriteSheet.SOUL.getSprite(0, 1),
                    SpriteSheet.SOUL.getSprite(0, 0), SpriteSheet.SOUL.getSprite(0, 1)};
                this.enemyType = 2;
                break;
            case 3://(2-5)
                this.Speed = (int) (Math.floor(Math.random() * 4 + 2));
                this.vida = 18;
                this.sprites = new Sprite[]{SpriteSheet.CACODEMON.getSprite(0, 0), SpriteSheet.CACODEMON.getSprite(0, 1), SpriteSheet.CACODEMON.getSprite(1, 0), SpriteSheet.CACODEMON.getSprite(1, 1), SpriteSheet.CACODEMON.getSprite(2, 0), SpriteSheet.CACODEMON.getSprite(2, 1), SpriteSheet.CACODEMON.getSprite(3, 0), SpriteSheet.CACODEMON.getSprite(3, 1)};
                this.enemyType = 3;
                break;
            case 4://(2-4)
                this.Speed = (int) (Math.floor(Math.random() * 3 + 2));
                this.vida = 30;
                this.sprites = new Sprite[]{SpriteSheet.BARON.getSprite(0, 0), SpriteSheet.BARON.getSprite(0, 1), SpriteSheet.BARON.getSprite(1, 0), SpriteSheet.BARON.getSprite(1, 1), SpriteSheet.BARON.getSprite(2, 0), SpriteSheet.BARON.getSprite(2, 1), SpriteSheet.BARON.getSprite(3, 0), SpriteSheet.BARON.getSprite(3, 1)};
                this.enemyType = 4;
                break;
            default:
                JOptionPane.showMessageDialog(new Frame(), "ERROR FATAL", "ERROR", 2);
                System.exit(0);
                sprites = null;
        }
        this.VIDA_TOTAL = vida;
        this.SpeedY = Speed;
        this.hitbox = new Rectangle(x, y, WIDTH, HEIGHT);
    }

    public void dibujar(final Graphics g) {
        super.dibujar(g);
        dibujarBarraVida(g);
    }

    public void actualizar() {
        this.moverEnemigo();
        super.actualizar();
    }

    private void dibujarBarraVida(Graphics g) {
        int anchuraBarra = 50;
        int alturaBarra = 4;
        int posX = this.hitbox.x + (this.hitbox.width / 2) - (anchuraBarra / 2);
        int posY = this.hitbox.y - 20;
        float porcentaje = ((vida * 1f) / VIDA_TOTAL);
        int format = 1;
//        System.out.println(VIDA_TOTAL + " " + vida);
        g.setColor(Color.BLACK);
        g.fillRect(posX, posY + format, format, alturaBarra - format * 2);
        g.fillRect(posX + anchuraBarra - format, posY + format, format, alturaBarra - format * 2);
        //Lineas X
        g.fillRect(posX + format, posY + alturaBarra - format, anchuraBarra - (format * 2), format);
        g.fillRect(posX + format, posY, anchuraBarra - (format * 2), format);
        g.setColor(Color.red);
        g.fillRect(posX + 1, posY + 1, (int) (anchuraBarra * porcentaje) - 2, alturaBarra - 2);
    }

    @Override
    public void actualizarSprite() {
        int max = 20;

        if (this.spriteActual == WALK_ID) {
            if (animacion <= ((int) max / 2)) {
                spriteActual = WALK_ID;
            } else if (animacion > ((int) max / 2) && animacion <= max) {
                spriteActual = STAY_ID;
            } else {
                spriteActual = FALL_ID;
                animacion = 0;
            }
            animacion++;
        } else {
            animacion = 0;
        }
        if (Speed < 0) {
            spriteActual += 1;
        }

    }

    protected void moverEnemigo() {
        if (!visible) {
            ya = 0;
            return;
        }
        spriteActual = 0;
        switch (this.enemyType) {
            //IMP
            case 0:
                /**
                 * Mover en x
                 *
                 *
                 *
                 */
                moverEnX();
                break;
            //PINKIE
            case 1:
                /**
                 * Mover en x Sprint(try) Saltar
                 */
                perseguirX();
                jump();
                sprint();
                break;
            //SOUL
            case 2:
                /**
                 * mover en x volar(intento) direccion
                 */
                perseguirX();
                volar();
                break;
            //CACODEMON
            case 3:
                /**
                 * mover en x intento volar
                 */
                perseguirX();
                volar();
                sprint();
                break;
            //BARON
            case 4:
                /**
                 * mover en x
                 */
                perseguirX();
                break;
            default:
                JOptionPane.showMessageDialog(new Frame(), "ERROR FATAL", "ERROR", 2);
                System.exit(0);
                break;
        }
        if (!this.collidingYDown) {//Si esta cayendo
            this.falling = true;
        }
        //ajuste de direccion
//        if (xPlayer > this.x) {
//            this.dir = "R";
//        } else {
//            this.dir = "L";
//        }
//        this.xa = 0;
//        this.contadorAnimacion();
    }

    @Override
    protected void jump() {
        if (((collidingXRight || collidingXLeft)) || yPlayer + 120 < this.hitbox.y) {
            if (!collidingYDown) {
                return;
            }
            this.ya = -9;
            this.spriteActual = SPRINT_ID;
        }
    }

    private void volar() {
        ya = 0;
        if (cooldownMuestraY == 0) {
            if (this.yPlayer + 54 < hitbox.y + (hitbox.height / 2)) {
//                System.out.println("arriba");
                SpeedY = Math.abs(SpeedY) * -1;
            } else if (this.yPlayer + 54 > hitbox.y + (hitbox.height / 2)) {
//                System.out.println("abajo");
                SpeedY = Math.abs(SpeedY);
            }
            cooldownMuestraY++;
        } else if (cooldownMuestraY == COOLDONWMUESTRALIMIT) {
            cooldownMuestraY = 0;
        } else {
            cooldownMuestraY++;
        }
        ya = ((collidingYUp && SpeedY < 0) || (collidingYDown && SpeedY > 0)) ? 0 : SpeedY;
        this.spriteActual = WALK_ID;
    }

    private void perseguirX() {
        if (cooldownMuestra == 0) {
            if (this.xPlayer + 34 < hitbox.x + (hitbox.width / 2)) {
                Speed = Math.abs(Speed) * -1;
            } else if (this.xPlayer + 34 > hitbox.x + (hitbox.width / 2)) {
                Speed = Math.abs(Speed);
            }
            if (collidingXRight || collidingXLeft) {
                Speed = -Speed;
            }
            cooldownMuestra++;
        } else if (cooldownMuestra == COOLDONWMUESTRALIMIT) {
            cooldownMuestra = 0;
        } else {
            cooldownMuestra++;
        }
//                System.out.println(xa);
        if (!(collidingXRight || collidingXLeft)) {
            xa += Speed;
        }
        this.spriteActual = WALK_ID;
    }

    /**
     * si esta cerca
     */
    private void sprint() {
        if (Math.abs(xPlayer + 54 - this.hitbox.x + (hitbox.height / 2)) < 400 && Math.abs(yPlayer + 34 - this.hitbox.y + (hitbox.height / 2)) < 400) {
            xa *= 1.5;
            this.spriteActual = SPRINT_ID;
        }
    }

    private void moverEnX() {
        if (cooldownMuestra == 0) {
            if (collidingXRight || collidingXLeft) {
                Speed = -Speed;
            }
            cooldownMuestra++;
        } else if (cooldownMuestra == COOLDONWMUESTRALIMIT) {
            cooldownMuestra = 0;
        } else {
            cooldownMuestra++;
        }
        xa += Speed;
        this.spriteActual = WALK_ID;
    }

    public int getEnemyType() {
        return enemyType;
    }

    public static int[] establecerAnchuraYAlturaSegunTipo(int type) {
        int widthHeight[] = new int[2];
        switch (type) {
            case 0:
                /**
                 * Anchura
                 */
                widthHeight[0] = 91;
                /**
                 * Altura
                 */
                widthHeight[1] = 100;
                break;
            case 1:
                /**
                 * Anchura
                 */
                widthHeight[0] = 84;
                /**
                 * Altura
                 */
                widthHeight[1] = 84;
                break;
            case 2:
                /**
                 * Anchura
                 */
                widthHeight[0] = 62;
                /**
                 * Altura
                 */
                widthHeight[1] = 60;
                break;
            case 3:
                /**
                 * Anchura
                 */
                widthHeight[0] = 96;
                /**
                 * Altura
                 */
                widthHeight[1] = 107;
                break;
            case 4:
                /**
                 * Anchura
                 */
                widthHeight[0] = 129;
                /**
                 * Altura
                 */
                widthHeight[1] = 192;
                break;
            default:

        }
        return widthHeight;
    }
}
