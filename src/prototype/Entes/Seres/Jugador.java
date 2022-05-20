package prototype.Entes.Seres;

import java.awt.Graphics;
import java.awt.Rectangle;
import prototype.Visual.SpriteSheet;
import prototype.Visual.Sprite;
import prototype.HerramientasEntradaSalida.Teclado;
import prototype.Entes.Objetos.Bala;
import java.util.ArrayList;

public class Jugador extends SerVivo {

    private final int COOLBALAS = 10;
    public ArrayList<Bala> balas;
    private int cooldownBalas;
    public Teclado teclado;

    /**
     * Crea el jugador
     *
     * @param x Posicion en eje x
     * @param y Posicion en eje y
     * @param HEIGHT Altura de la hitbox y del personaje
     * @param WIDTH Anchura de la hitbox y del personaje
     * @param teclado El teclado que controla el personaje
     */
    public Jugador(final int x, final int y, final int HEIGHT, final int WIDTH, final Teclado teclado) {
        super(new Sprite[4], 100, 30);
        this.balas = new ArrayList<Bala>();
        this.sprites = new Sprite[]{(Sprite) SpriteSheet.PERSONAJE.getSprite(0, 0), SpriteSheet.PERSONAJE.getSprite(0, 1),
            SpriteSheet.PERSONAJE.getSprite(1, 0), SpriteSheet.PERSONAJE.getSprite(1, 1),
            SpriteSheet.PERSONAJE.getSprite(2, 0), SpriteSheet.PERSONAJE.getSprite(2, 1)};
        this.hitbox = new Rectangle(x, y, WIDTH, HEIGHT);
        this.teclado = teclado;
    }

    /**
     * Dibuja el jugador y todas las balas
     *
     * @param g
     */
    public void dibujar(final Graphics g) {
        super.dibujar(g);
        this.dibujarBalas(g);
    }

    public void actualizar() {
        this.mover();
    }

    protected void definirEstado() {
        if (this.xa != 0) {//Si se esta moviendo en el eje X
            if (this.teclado.left) {//Si anda a la izquierda
                this.walking = true;
                this.dir = "L";
            } else if (this.teclado.right) {//Si anda a la derecha
                this.walking = true;
                this.dir = "R";
            }
        } else {//Si no se esta moviendo
            this.stay = true;
            this.walking = false;
        }
        if (!this.collidingYDown) {//Si esta cayendo
            this.falling = true;
        }
    }

    public void mover() {
        if (collidingYDown && this.teclado.jumping) {
            this.jump();
        }
        super.mover();

    }

    protected void disparar() {
        if (this.teclado.shooting && this.cooldownBalas == 0) {//Si esta intentando disparar y puede disparar(cooldown bala==0) entonces dispara
            // inicio contador balas
            this.shooting = true;
            this.cooldownBalas++;
            if (this.dir.equalsIgnoreCase("L")) {//Si mira a la izquierda dispara a la izquierda (con el boolean bala derecha= false)
                this.balas.add(new Bala(this.hitbox.x + this.hitbox.width, this.hitbox.y + 50, false));
            } else {//Si no, ira a la derecha
                this.balas.add(new Bala(this.hitbox.x, this.hitbox.y + 50, true));
            }
        }
    }

    protected void moverJugador() {
        this.Speed = 4;
        this.xa = 0;
        this.contadorAnimacion();
        this.Speed *= (this.teclado.running ? 2 : 1);
        this.xa -= (((this.teclado.left || this.teclado.right)) ? (this.teclado.left ? this.Speed : (-this.Speed)) : 0);
        if (!this.falling && this.teclado.jumping) {
            this.jump();
        }
    }

    private void dibujarBalas(final Graphics g) {//Dibuja las balas que existan
        for (int i = 0; i < this.balas.size(); ++i) {
            this.balas.get(i).dibujar(g);
        }
    }

    private void comprobarBalas() {
        //ESTO SE PODRA HACER FUERA EN ALGUN MOMENTO
        for (int i = 0; i < this.balas.size(); i++) {
            if (this.balas.get(i).getX() + this.balas.get(i).getWIDTH() < 0) {
                this.balas.get(i).setVisible(false);
                this.balas.remove(i);
                i--;
            } else {
                this.balas.get(i).actualizar();
            }
        }
        //Cooldown de las balas
        if (this.cooldownBalas != 0 && this.cooldownBalas < 10) {
            this.cooldownBalas++;
        } else if (this.cooldownBalas == 10) {
            this.cooldownBalas = 0;
        }
    }

    public void setStates(boolean run, boolean walking, boolean fall) {
        running = run;
        this.walking = walking;
        if (!this.collidingYDown) {//Si esta cayendo
            this.falling = true;
        }
    }

    private void addBalasAsEntidadtidad(final ArrayList entes) {
        for (int i = 0; i < this.balas.size(); i++) {
            entes.add(this.balas.get(i));
        }
    }

    private void direction() {//Si cambia de direccion a la anterior, se resetea la animacion
        if (!this.walking) {
            return;
        }
        if ((this.dir.equalsIgnoreCase("L") && !this.teclado.left) || (this.dir.equalsIgnoreCase("R") && !this.teclado.right)) {
            this.animacion = 0;
        }
    }
}
