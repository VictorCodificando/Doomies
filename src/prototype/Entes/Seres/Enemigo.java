/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prototype.Entes.Seres;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import prototype.Entes.Objetos.Bala;
import prototype.HerramientasEntradaSalida.Teclado;
import prototype.Visual.Sprite;
import prototype.Visual.SpriteSheet;

/**
 *
 * @author Víctor Zero
 */
public class Enemigo extends SerVivo {
    
    private final Sprite[] sprites;
    private int Type;

    public Enemigo(final int x, final int y, final int HEIGHT, final int WIDTH, final Teclado teclado,int typeIn) {
        super(new Sprite[8], 48, 48);
        this.sprites = new Sprite[]{SpriteSheet.IMP.getSprite(0, 0), SpriteSheet.IMP.getSprite(0, 1), SpriteSheet.IMP.getSprite(1, 0), SpriteSheet.IMP.getSprite(1,1),SpriteSheet.IMP.getSprite(2,0),SpriteSheet.IMP.getSprite(2,1),SpriteSheet.IMP.getSprite(2,2),SpriteSheet.IMP.getSprite(3,0),SpriteSheet.IMP.getSprite(3,1)};
        this.hitbox = new Rectangle(x, y, WIDTH, HEIGHT);
    }
    
    
    protected Rectangle Player;
    
    public void dibujar(final Graphics g) {
        super.dibujar(g);
    }

    public void actualizar() {
        this.definirEstado();
        super.actualizar();
        this.moverEnemigo();
        this.moverRectangle();
    }

    public void moverRectangle() {
        if (Player.x == 0) {
            Player.x = this.x - 1;
            Player.y = this.y - 1;
        } else if (Player.x == 1280) {
            Player.x = this.x + 1;
            Player.y = this.y + 1;
        }
    }

    protected void definirEstado() {
        if (Player.x < this.x) { //En caso de que el jugador este a la izquierda
            this.walking = true;
            this.dir = "L";
        } else { //En caso de que el jugador este a la derecha
            this.walking = true;
            this.dir = "R";
        }
        if (!this.collidingY) {//Si esta cayendo
            this.falling = true;
        }
    }

    protected void moverEnemigo() {
        this.Speed = 5;
        this.xa = 0;
        this.contadorAnimacion();
        this.Speed *= (this.running ? 2 : 1);
        if (!this.falling) {
            this.jump();
        }
    }

}
