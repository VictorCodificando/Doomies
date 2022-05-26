/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package doomies.Entes.Seres;

import java.awt.Graphics;
import java.awt.Rectangle;
import doomies.Visual.Sprite;
import doomies.Visual.SpriteSheet;
import java.awt.Frame;
import javax.swing.JOptionPane;

/**
 *
 * @author Víctor Zero
 */
public class Enemigo extends SerVivo {

    private int enemyType;
    public int xPlayer;
    public int yPlayer;
    private int cooldownMuestra = 0;
    private int cooldownMuestraY = 0;
    private int SpeedY;

    public Enemigo(final int x, final int y, final int HEIGHT, final int WIDTH, int type) {
        super(new Sprite[8], WIDTH, HEIGHT, 5);
        this.COOLDOWNDAÑOTOTAL = 1;
        switch (type) {
            //IMP
            case 0:
                this.Speed = 4;
                this.vida = 10;
                this.sprites = new Sprite[]{SpriteSheet.IMP.getSprite(0, 0), SpriteSheet.IMP.getSprite(0, 1), SpriteSheet.IMP.getSprite(1, 0), SpriteSheet.IMP.getSprite(1, 1), SpriteSheet.IMP.getSprite(2, 0), SpriteSheet.IMP.getSprite(2, 1), SpriteSheet.IMP.getSprite(3, 0), SpriteSheet.IMP.getSprite(3, 1)};
                this.enemyType = 0;
                break;
            case 1:
                this.Speed = 5;
                this.vida = 12;
                this.sprites = new Sprite[]{SpriteSheet.PINKIE.getSprite(0, 0), SpriteSheet.PINKIE.getSprite(0, 1), SpriteSheet.PINKIE.getSprite(1, 0), SpriteSheet.PINKIE.getSprite(1, 1), SpriteSheet.PINKIE.getSprite(2, 0), SpriteSheet.PINKIE.getSprite(2, 1), SpriteSheet.PINKIE.getSprite(2, 2), SpriteSheet.PINKIE.getSprite(3, 0), SpriteSheet.PINKIE.getSprite(3, 1)};
                this.enemyType = 1;
                break;
            case 2:
                this.Speed = 5;
                this.vida = 8;
                this.sprites = new Sprite[]{SpriteSheet.SOUL.getSprite(0, 0), SpriteSheet.SOUL.getSprite(0, 1), SpriteSheet.SOUL.getSprite(1, 0), SpriteSheet.SOUL.getSprite(1, 1), SpriteSheet.SOUL.getSprite(2, 0), SpriteSheet.SOUL.getSprite(2, 1)};
                this.enemyType = 2;
                break;
            case 3:
                this.Speed = 4;
                this.vida = 18;
                this.sprites = new Sprite[]{SpriteSheet.CACODEMON.getSprite(0, 0), SpriteSheet.CACODEMON.getSprite(0, 1), SpriteSheet.CACODEMON.getSprite(1, 0), SpriteSheet.CACODEMON.getSprite(1, 1), SpriteSheet.CACODEMON.getSprite(2, 0), SpriteSheet.CACODEMON.getSprite(2, 1), SpriteSheet.CACODEMON.getSprite(3, 0), SpriteSheet.CACODEMON.getSprite(3, 1)};
                this.enemyType = 3;
                break;
            case 4:
                this.Speed = 2;
                this.vida = 30;
                this.sprites = new Sprite[]{SpriteSheet.BARON.getSprite(0, 0), SpriteSheet.BARON.getSprite(0, 1), SpriteSheet.BARON.getSprite(1, 0), SpriteSheet.BARON.getSprite(1, 1), SpriteSheet.BARON.getSprite(2, 0), SpriteSheet.BARON.getSprite(2, 1), SpriteSheet.BARON.getSprite(3, 0), SpriteSheet.BARON.getSprite(3, 1)};
                this.enemyType = 4;
                break;
            default:
                JOptionPane.showMessageDialog(new Frame(), "ERROR FATAL", "ERROR", 2);
                System.exit(0);
                sprites = null;
        }
        this.SpeedY = Speed;
        this.hitbox = new Rectangle(x, y, WIDTH, HEIGHT);
    }

    public void dibujar(final Graphics g) {
        this.sprites[0].dibujar(g, hitbox.x, hitbox.y);
    }

    public void actualizar() {
        this.moverEnemigo();
        super.actualizar();

    }

    protected void moverEnemigo() {
        switch (this.enemyType) {
            //IMP
            case 0:
                /**
                 * Mover en x
                 *
                 */
                moverInX();
                break;
            //PINKIE
            case 1:
                /**
                 * Mover en x Sprint(try) Saltar
                 */
                moverInX();
                jump();
                break;
            //SOUL
            case 2:
                /**
                 * mover en x volar(intento) sprint si se cae que cambie de
                 * direccion
                 */
                moverInX();
                break;
            //CACODEMON
            case 3:
                /**
                 * mover en x intento volar
                 */
                moverInX();
                volar();
                break;
            //BARON
            case 4:
                /**
                 * mover en x
                 */
                moverInX();
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
            super.jump();
        }
    }

    private void volar() {
        ya = 0;
        if (cooldownMuestraY == 0) {
            System.out.println(this.yPlayer);
//            System.out.println(ya);
            if (this.yPlayer < hitbox.y) {
//                System.out.println("arriba");
                SpeedY = Math.abs(SpeedY) * -1;
            } else {
//                System.out.println("abajo");
                SpeedY = Math.abs(SpeedY);
            }
            cooldownMuestraY++;
        } else if (cooldownMuestraY == 100) {
            cooldownMuestraY = 0;
        } else {
            cooldownMuestraY++;
        }
        ya=((collidingYUp && Speed<0)||(collidingYDown && Speed>0))? 0:Speed;
    }

    private void moverInX() {
        if (cooldownMuestra == 0) {
            if (this.xPlayer - hitbox.x < 0) {
                Speed = Math.abs(Speed) * -1;
            } else {
                Speed = Math.abs(Speed);
            }
            cooldownMuestra++;
        } else if (cooldownMuestra == 10) {
            cooldownMuestra = 0;
        } else {
            cooldownMuestra++;
        }
//                System.out.println(xa);
        if (!(collidingXRight || collidingXLeft)) {
            xa += Speed;
        }
    }

    public int getEnemyType() {
        return enemyType;
    }
    
}
