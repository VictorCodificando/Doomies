package prototype.Entes.Seres;

import java.awt.Graphics;
import prototype.Visual.Sprite;
import prototype.Entes.Entidad;

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
       y=0 no move izq| no move der| walking izq| walking der|falling izq| falling der|
       y=1 LO MISMO QUE FILA DE ARRIBA PERO DISPARANDO
     */
    public SerVivo(final Sprite[] sprite, final int WIDTH, final int HEIGHT) {
        super(sprite, WIDTH, HEIGHT);
        this.dir = "R";
        this.spriteActual = 0;
    }

    @Override
    public void dibujar(final Graphics g) {//Dibujamos si es visible
        if (!this.visible) {//Si no es visible se sale
            return;
        }
        this.sprites[this.spriteActual].dibujar(g, this.hitbox.x, this.hitbox.y);
    }

    @Override
    public void actualizar() {
        this.mover();
        this.actualizarSprite();
    }

    protected void jump() {
        this.ya = -10;
    }

    protected void mover() {// mueve las variables de posicion
        this.caer();
        hitbox.x += (this.collidingX ? 0 : this.xa);//aumenta la x a la velocidad que debia
        hitbox.y += (this.collidingY ? 0 : this.ya);//aumenta la y a la velocidad que debia
        if (this.collidingY) {// Si esta colisionando en Y, no puede estar cayendo
            this.falling = false;
            this.contGravedad = 0;//Reseteo de contador
        }
    }

    protected void contadorAnimacion() {
        if (this.animacion <= 30) {//La variable aumenta hasta 3'
            ++this.animacion;
        } else {//Si llega al tope se resetea
            this.animacion = 0;
        }
    }

    protected void actualizarSprite() {
        //Actualizamos el sprite actual
        int animNum = 0;
        this.spriteActual = 0;
        if (this.walking && !this.falling) {
            if (this.animacion >= 0 && this.animacion <= 10) {//Del 0-10 es el sprite 1
                animNum = 0;
            } else if (this.animacion > 10 && this.animacion <= 20) {//Del 10 al 20 es el sprite 2
                animNum = 1;
            } else {//Del 20 al 30 es el sprite 3
                animNum = 2;
            }
            this.spriteActual = ((animNum == 1) ? 0 : ((animNum == 2) ? 2 : 2));
        }
        if (this.dir.equalsIgnoreCase("R")) {//Si es derecha
            this.spriteActual += 0;
        } else if (this.dir.equalsIgnoreCase("L")) {//Si es izquierda
            ++this.spriteActual;
        }
    }
}
