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

/**
 * Clase Jugador que será el protagonista de cada nivel y de el dependerá todo
 * el movimiento, Hereda de SerVivo y es controlado por GestorJuego
 *
 * @see SerVivo
 * @author Víctor
 */
public class Jugador extends SerVivo {

    /**
     * Tiempo minimo entre bala y bala
     */
    private final int COOLBALAS = 10;
    /**
     * Aqui se almacenan las balas temporalmente antes de que las trate el
     * gestor de juego
     */
    public ArrayList<Bala> balas;
    /**
     * Contador para controlar que hay tiempo entre bala y bala
     */
    private int cooldownBalas;
    /**
     * Teclado que indica
     */
    public Teclado teclado;

    /**
     * Crea el jugador que controlará el usuario
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
        this.vida = 99;
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
     * @param g Graphics que dibuja por la pantalla
     */
    public void dibujar(final Graphics g) {
        super.dibujar(g);
    }

    /**
     * Actualiza el jugador, sus contadores internos(daño y balas) y su posicion
     *
     * @see SerVivo#actualizar()
     */
    public void actualizar() {
        activarContadorBalas();
        activarContadorDaño();
        super.actualizar();
    }

    /**
     * Activa el contador del daño que hara un cooldown entre lo que esta o no
     * el personaje en blanco
     *
     * El intervalo varía entre 40
     */
    private void activarContadorDaño() {
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
    }

    /**
     * Activa el contador de las balas que permite hacer que se respeten los
     * intervalos de disparo
     */
    private void activarContadorBalas() {
        if (cooldownBalas != 0 && cooldownBalas < COOLBALAS) {
            cooldownBalas++;
        } else if (cooldownBalas == COOLBALAS) {
            cooldownBalas = 0;
        }
    }

    /**
     * Sobreescribe el meotodo normal que pinta en rojo el Ser para cuando está
     * dañado y aqui lo pinta en blanco
     */
    @Override
    protected void paintHoveredSprites() {
        /**
         * En el se guardaran los sprites que se mostraran cuando el jugador
         * sufra daño
         */
        this.spritesHovered = new Sprite[sprites.length];
        Sprite spActual;
        /**
         * Cuando incrementara el blanco de los pixeles
         */
        int incremento = 150;
        /**
         * Recorre Sprite a sprite, pixel a pixel y el color de cada pixel a uno
         * mas blanco segun la variable incremento, luego lo guarda en el array
         * de spriteHovered
         */
        for (int i = 0; i < this.sprites.length; i++) {
            spActual = sprites[i];
            /**
             * Obtengo una copia de la imagen
             */
            BufferedImage img = spActual.getImgCopy();
            /**
             * Obtengo su clase graphics para poder empezar a dibujar encima
             */
            Graphics g = img.getGraphics();
            g.drawImage(img, 0, 0, null);
            for (int k = 0; k < img.getHeight(); k++) {
                for (int j = 0; j < img.getWidth(); j++) {
                    /**
                     * Si el pixel es transparente no se tocará, se salta
                     */
                    if (((img.getRGB(j, k)) >>> 24) == 0x00) {
                        continue;
                    }
                    /**
                     * Sumamos al color actual la suma de incremento si el color
                     * es de 255(el maximo) o mas entonces lo establecemos a ese
                     * numero
                     */
                    Color oldColor = new Color(img.getRGB(j, k));
                    int rojo = oldColor.getRed();
                    int azul = oldColor.getBlue();
                    int verde = oldColor.getGreen();
                    Color color = new Color(((rojo < (255 - incremento)) ? (rojo + incremento) : 255),
                            ((azul < (255 - incremento)) ? (azul + incremento) : 255),
                            ((verde < (255 - incremento)) ? (verde + incremento) : 255));
                    g.setColor(color);
                    g.fillRect(j, k, 1, 1);
                }
            }
            /**
             * Vaciamos el espacio en memoria del graphics ya que no vamos a
             * dibujar mas encima de la imagen
             */
            g.dispose();
            /**
             * Guardamos en la imagen
             */
            spritesHovered[i] = new Sprite(img);
        }
    }

    /**
     * Mueve el personaje haciendo las comprobaciones necesarias de si puede
     * saltar
     */
    public void mover() {
        if (this.teclado.jumping && !falling) {
            this.jump();
        }
        super.mover();
    }

    /**
     * Si se cumple las condiciones del cooldown, se crea un objeto bala el cual
     * segun la direccion del jugador tendra una direccion y sera tratado como
     * una entidad mas adelante en el GestorJuego
     *
     * @see doomies.Entes.Objetos.Bala#Bala(int, int, boolean)
     * @see doomies.Gestores.GestorJuego
     */
    public void disparar() {
        /**
         * Si esta intentando disparar y puede disparar(cooldown bala==0)
         * entonces dispara
         */
        if (cooldownBalas == 0) {
            /**
             * inicio contador balas
             */
            this.shooting = true;
            cooldownBalas++;
            /**
             * Si mira a la izquierda dispara a la izquierda (con el boolean
             * bala derecha= false)
             */
            if (this.dir.equalsIgnoreCase("L")) {
                this.balas.add(new Bala(this.hitbox.x - 12, this.hitbox.y + 45 - 3, false));
            } else {
                /**
                 * Si no, ira a la derecha
                 */
                this.balas.add(new Bala(this.hitbox.x + this.hitbox.width + 12, this.hitbox.y + 45 - 3, true));
            }
        }
    }

    /**
     * Añade a un array de entes las balas actuales y las va quitando del
     * jugador
     *
     * @see doomies.Gestores.GestorJuego#mover()
     * @param entes un ArrayList que contiene o no entes
     */
    public void addBalasAsEntidadtidad(final ArrayList entes) {
        /**
         * Si no hay balas no lo recorras
         */
        if (this.balas.size() <= 0) {
            return;
        }
        /**
         * Recorre cada bala y si es null la elimina si no lo es, la añade y
         * luego elimina de aqui(Jugador)
         */
        for (int i = 0; i < this.balas.size(); i = i + 0) {
            if (balas.get(i) == null) {
                balas.remove(i);
            }
            entes.add(this.balas.get(i));
            balas.remove(i);
        }
    }

    /**
     * Resetea la posicion del jugador al centro de la pantalla
     */
    public void resetPos() {
        this.ya = 0;
        this.hitbox.x = 1280 / 2 - hitbox.width;
        this.hitbox.y = 100;
    }

}
