package doomies.Entes.Seres;

import java.awt.Graphics;
import java.awt.Rectangle;
import doomies.Visual.SpriteSheet;
import doomies.Visual.Sprite;
import doomies.HerramientasEntradaSalida.Teclado;
import doomies.Entes.Objetos.Bala;
import java.awt.Color;
import java.awt.image.BufferedImage;
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
        super(new Sprite[4], 100, 30, 3);
        this.balas = new ArrayList<Bala>();
        this.vida = 3;
        this.sprites = new Sprite[]{(Sprite) SpriteSheet.PERSONAJE.getSprite(0, 0), SpriteSheet.PERSONAJE.getSprite(0, 1),
            SpriteSheet.PERSONAJE.getSprite(1, 0), SpriteSheet.PERSONAJE.getSprite(1, 1),
            SpriteSheet.PERSONAJE.getSprite(2, 0), SpriteSheet.PERSONAJE.getSprite(2, 1),
            SpriteSheet.PERSONAJE.getSprite(3, 0), SpriteSheet.PERSONAJE.getSprite(3, 1),
            SpriteSheet.PERSONAJE.getSprite(4, 0), SpriteSheet.PERSONAJE.getSprite(4, 1),
            SpriteSheet.PERSONAJE.getSprite(5, 0), SpriteSheet.PERSONAJE.getSprite(5, 1)};
        this.hitbox = new Rectangle(x, y, WIDTH, HEIGHT);
        this.teclado = teclado;
        this.COOLDOWNDAÑOTOTAL = 100;
    }

    /**
     * Dibuja el jugador y todas las balas
     *
     * @param g
     */
    public void dibujar(final Graphics g) {
        super.dibujar(g);
    }

    public void actualizar() {
        activarContadorBalas();
        if (cooldownDaño != 0) {
            if (inmunidad > 0 && inmunidad < 40) {
                inmunidad++;
            } else if (inmunidad == 40) {
                inmunidad = 0;
            }
            if (inmunidad < 20 && inmunidad >= 0) {
                inmunidad++;
            }
        } else {
            inmunidad = 0;
        }
        super.actualizar();
    }

    private void activarContadorBalas() {
        if (cooldownBalas != 0 && cooldownBalas < COOLBALAS) {
            cooldownBalas++;
        } else if (cooldownBalas == COOLBALAS) {
            cooldownBalas = 0;
        }
    }

    @Override
    protected void paintHoveredSprites() {
        this.spritesHovered = new Sprite[sprites.length];
        Sprite spActual;
        int incremento = 150;
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
                    int azul = oldColor.getBlue();
                    int verde = oldColor.getGreen();
                    Color color = new Color(((rojo < (255 - incremento)) ? (rojo + incremento) : 255), ((azul < (255 - incremento)) ? (azul + incremento) : 255), ((verde < (255 - incremento)) ? (verde + incremento) : 255));
                    g.setColor(color);
                    g.fillRect(j, k, 1, 1);
                }
            }

            g.dispose();
            spritesHovered[i] = new Sprite(img);

        }
    }

    public void mover() {
        if (this.teclado.jumping && !falling) {
            this.jump();
        }
        super.mover();
    }

    public void disparar() {

        if (cooldownBalas == 0) {//Si esta intentando disparar y puede disparar(cooldown bala==0) entonces dispara
            // inicio contador balas
            this.shooting = true;
            cooldownBalas++;
            if (this.dir.equalsIgnoreCase("L")) {//Si mira a la izquierda dispara a la izquierda (con el boolean bala derecha= false)
                this.balas.add(new Bala(this.hitbox.x - 12, this.hitbox.y + 45 - 3, false));
            } else {//Si no, ira a la derecha
                this.balas.add(new Bala(this.hitbox.x + this.hitbox.width + 12, this.hitbox.y + 45 - 3, true));
            }
        }
    }

    protected void moverJugador() {
        this.Speed = 4;
        this.xa = 0;
        this.contadorAnimacion();
        this.Speed *= (this.teclado.running ? 2 : 1);
        this.xa -= (((this.teclado.left || this.teclado.right)) ? (this.teclado.left ? this.Speed : (-this.Speed)) : 0);
    }

    private void dibujarBalas(final Graphics g) {//Dibuja las balas que existan
        for (int i = 0; i < this.balas.size(); ++i) {
            this.balas.get(i).dibujar(g);
        }
    }

    /**
     * @deprecated No se usa ya que se comprueba desde GestorJuego
     */
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
    }

    public void setStates(boolean run, boolean walking, boolean fall) {
        running = run;
        this.walking = walking;
        if (!this.collidingYDown) {//Si esta cayendo
            this.falling = true;
        }
    }

    public void addBalasAsEntidadtidad(final ArrayList entes) {
        if (this.balas.size() <= 0) {
            return;
        }
        for (int i = 0; i < this.balas.size(); i = i + 0) {
            if (balas.get(i) == null) {
                balas.remove(i);
            }
            entes.add(this.balas.get(i));
            balas.remove(i);
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

    /**
     * Resetea la posicion del jugador
     */
    public void resetPos() {
        this.ya = 0;
        this.hitbox.x = 1280 / 2 - hitbox.width;
        this.hitbox.y = 100;
    }

}
