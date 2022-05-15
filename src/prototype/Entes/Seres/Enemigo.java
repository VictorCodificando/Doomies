/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prototype.Entes.Seres;

import java.awt.Graphics;
import java.awt.Rectangle;
import prototype.Visual.Sprite;
import prototype.Visual.SpriteSheet;

/**
 *
 * @author Víctor Zero
 */
public class Enemigo extends SerVivo {

    private final Sprite[] sprites;
    private int enemyType;

    public Enemigo(final int x, final int y, final int HEIGHT, final int WIDTH, int type) {
        super(new Sprite[8], WIDTH, HEIGHT);
        switch (type) {
            case 0:
                this.sprites = new Sprite[]{SpriteSheet.IMP.getSprite(0, 0), SpriteSheet.IMP.getSprite(0, 1), SpriteSheet.IMP.getSprite(1, 0), SpriteSheet.IMP.getSprite(1, 1), SpriteSheet.IMP.getSprite(2, 0), SpriteSheet.IMP.getSprite(2, 1), SpriteSheet.IMP.getSprite(2, 2), SpriteSheet.IMP.getSprite(3, 0), SpriteSheet.IMP.getSprite(3, 1)};
                this.enemyType = 0;
                break;
            case 1:
                this.sprites = new Sprite[]{SpriteSheet.PINKIE.getSprite(0, 0), SpriteSheet.PINKIE.getSprite(0, 1), SpriteSheet.PINKIE.getSprite(1, 0), SpriteSheet.PINKIE.getSprite(1, 1), SpriteSheet.PINKIE.getSprite(2, 0), SpriteSheet.PINKIE.getSprite(2, 1), SpriteSheet.PINKIE.getSprite(2, 2), SpriteSheet.PINKIE.getSprite(3, 0), SpriteSheet.PINKIE.getSprite(3, 1)};
                this.enemyType = 1;
                break;
            case 2:
                this.sprites = new Sprite[]{SpriteSheet.SOUL.getSprite(0, 0), SpriteSheet.SOUL.getSprite(0, 1), SpriteSheet.SOUL.getSprite(1, 0), SpriteSheet.SOUL.getSprite(1, 1), SpriteSheet.SOUL.getSprite(2, 0), SpriteSheet.SOUL.getSprite(2, 1)};
                this.enemyType = 2;
                break;
            case 3:
                this.sprites = new Sprite[]{SpriteSheet.CACODEMON.getSprite(0, 0), SpriteSheet.CACODEMON.getSprite(0, 1), SpriteSheet.CACODEMON.getSprite(1, 0), SpriteSheet.CACODEMON.getSprite(1, 1), SpriteSheet.CACODEMON.getSprite(2, 0), SpriteSheet.CACODEMON.getSprite(2, 1), SpriteSheet.CACODEMON.getSprite(2, 2), SpriteSheet.CACODEMON.getSprite(3, 0), SpriteSheet.CACODEMON.getSprite(3, 1)};
                this.enemyType = 3;
                break;
            case 4:
                this.sprites = new Sprite[]{SpriteSheet.BARON.getSprite(0, 0), SpriteSheet.BARON.getSprite(0, 1), SpriteSheet.BARON.getSprite(1, 0), SpriteSheet.BARON.getSprite(1, 1), SpriteSheet.BARON.getSprite(2, 0), SpriteSheet.BARON.getSprite(2, 1), SpriteSheet.BARON.getSprite(2, 2), SpriteSheet.BARON.getSprite(3, 0), SpriteSheet.BARON.getSprite(3, 1)};
                this.enemyType = 4;
                break;
            default:
                sprites = null;
        }

        this.hitbox = new Rectangle(x, y, WIDTH, HEIGHT);
    }

    public void dibujar(final Graphics g) {
        super.dibujar(g);
    }

    public void actualizar() {
        super.actualizar();
        this.moverEnemigo();
    }

    protected void moverEnemigo() {
//        switch (this.enemyType) {
//            //IMP
//            case 0:
//                this.Speed = 4;
//                this.vida = 10;
//                break;
//            //PINKIE
//            case 1:
//                this.Speed = 5;
//                this.vida = 12;
//                if ((this.x - xPlayer <= 200 || xPlayer - this.x <= 200)) {
//                    this.Speed = 7;
//                }
//                break;
//            //SOUL
//            case 2:
//                this.Speed = 5;
//                this.vida = 8;
//                this.hitbox.y = this.hitbox.y - 100;
//                if ((this.x - xPlayer <= 400 || xPlayer - this.x <= 400)) {
//                    this.Speed = 6;
//                }
//                break;
//            //CACODEMON
//            case 3:
//                this.Speed = 4;
//                this.vida = 18;
//                this.hitbox.y = this.hitbox.y - 100;
//                break;
//            //BARON
//            case 4:
//                this.Speed = 2;
//                this.vida = 30;
//                break;
//            default:
//                break;
//        }
//        if (!this.collidingY) {//Si esta cayendo
//            this.falling = true;
//        }
//        //ajuste de direccion
//        if (xPlayer > this.x) {
//            this.dir = "R";
//        } else {
//            this.dir = "L";
//        }
//        this.xa = 0;
//        this.contadorAnimacion();
    }
}
