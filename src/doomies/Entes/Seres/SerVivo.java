package doomies.Entes.Seres;

import java.awt.Graphics;
import doomies.Visual.Sprite;
import doomies.Entes.Entidad;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Clase de la que heredan todos los seres vivos, es la clase que define el
 * comportamiento basico de todo ser vivo, siendo lo mas característico su vida
 *
 * @see doomies.Entes.Entidad
 * @author Víctor
 * @version 4
 * @since 2
 */
public class SerVivo extends Entidad {

    /**
     * Representa la vida que le queda al ser, si llega a 0 deberá de morir
     */
    protected int vida;
    /**
     * Representa la direccion del serVivo puede ser: "R"-derecha "L"- izquierda
     */
    protected String dir;
    /**
     * BOOLEANS QUE DEFINEN ESTADO DEL SER VIVO PARA LOS SPRITES
     */
    /**
     * Boolean que expresa si se esta movimendo en el eje x
     */
    protected boolean walking;
    /**
     * Boolean que expresa si esta corriendo
     */
    protected boolean running;
    /**
     * Boolean que expresa si esta cayendo
     */
    protected boolean falling;
    /**
     * Boolean que expresa si esta disparando
     */
    protected boolean shooting;
    /**
     * Boolean que expresa que esta quieto sin moverse
     */
    protected boolean stay;
    /**
     * ID de SPRITES
     */
    /**
     * ID del sprite de estar quieto
     */
    protected static final int STAY_ID = 0;
    /**
     * ID del sprite de estar andando
     */
    protected static final int WALK_ID = 2;
    /**
     * ID del sprite de estar cayendo
     */
    protected static final int FALL_ID = 4;
    /**
     * Representa la posicion dentro del array de sprites
     */
    protected int spriteActual;
    /**
     * Donde se almacenaran los sprites que tengan daño
     */
    protected Sprite[] spritesHovered = null;
    /**
     * Contador de animacion cuando este andando para que cambie entre sus
     * diferentes estados
     */
    protected int animacion;
    /**
     * Contador del daño para que haya un tiempo mínimo entre que recibe daño
     */
    protected int cooldownDaño = 0;
    /**
     * Tiempo minimo entre daño y daño
     */
    protected int COOLDOWNDAÑOTOTAL = 100;
    /**
     * Periodo de inmunidad usado para el parpadeo en Jugador(podrá ser usado en
     * otros seres mas adelante, tales como compañeros o algo asi...)
     */
    protected int inmunidad = 0;

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
    /**
     * Crea un Ser vivo de tipo generico, con una vida un tamaño y unos sprites
     * determinados
     *
     * @see doomies.Entes.Entidad
     * @param sprite Sprite que representa al serVivo
     * @param WIDTH Anchura de su hitbox
     * @param HEIGHT Altura de su hitbox
     * @param vida Vida inicial del ser
     */
    public SerVivo(final Sprite[] sprite, final int WIDTH, final int HEIGHT, final int vida) {
        super(sprite, WIDTH, HEIGHT);
        /**
         * Por defecto esta mirando hacia la derecha
         */
        this.dir = "R";
        this.spriteActual = 0;
        this.vida = vida;
    }

    /**
     * Dibuja los sprites con daño y los almancena en spritesHovered Coje imagen
     * por imagen pixel a pixel, los pone mas rojo segun la variable incremento
     * y lo almcena en un array
     *
     * Se carga antes de la partida para que sea mas optimo
     */
    protected void paintHoveredSprites() {
        this.spritesHovered = new Sprite[sprites.length];
        Sprite spActual;
        /**
         * Incrementa el rojo con un valor de 100
         */
        int incremento = 100;
        for (int i = 0; i < this.sprites.length; i++) {
            spActual = sprites[i];
            BufferedImage img = spActual.getImgCopy();
            Graphics g = img.getGraphics();
            g.drawImage(img, 0, 0, null);
            for (int k = 0; k < img.getHeight(); k++) {
                for (int j = 0; j < img.getWidth(); j++) {
                    /**
                     * Si el pixel es transparente se salta
                     */
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

    /**
     * Dibuja el ser, dependiendo de si tiene daño o no lo dibujara en un array
     * o en otro
     *
     * @param g Graphics que dibujará en la pantalla principal
     */
    @Override
    public void dibujar(final Graphics g) {//Dibujamos si es visible
        /**
         * Si no tiene sprites de daño pintalos
         */
        if (spritesHovered == null || spritesHovered[0] == null) {
            paintHoveredSprites();
        }
        /**
         * Si no es visible se sale
         */
        if (!this.visible) {
            return;
        }
        if (cooldownDaño != 0) {
            /**
             * Si es jugador y no esta en su periodo de parpadeo se dibujara el
             * sprite normal
             */
            if ((this instanceof Jugador && cooldownDaño >= COOLDOWNDAÑOTOTAL - 5) || (this instanceof Jugador && inmunidad < 20 && inmunidad >= 0)) {
                this.sprites[spriteActual].dibujar(g, this.hitbox.x, this.hitbox.y);
                return;
            }
            this.spritesHovered[spriteActual].dibujar(g, this.hitbox.x, this.hitbox.y);
            return;
        }
        this.sprites[spriteActual].dibujar(g, this.hitbox.x, this.hitbox.y);
    }

    /**
     * Actualiza a cada ser, pasa por aqui cada vez que se actualiza un ser
     */
    @Override
    public void actualizar() {
        this.mover();
        this.actualizarSprite();
    }

    /**
     * Salto default, usado por ahora por el jugador unicamente, le da una
     * velocidad inicial hacia arriba en Y y la gravedad lo va frenando
     *
     * @see doomies.Entes.CuerpoGravitatorio
     */
    protected void jump() {
        this.ya = -11;
    }
    /**
     * Mueve las variables de posicion
     */
    protected void mover() {
        /**
         * Mueve la hitbox en x
         */
        hitbox.x += this.xa;
        /**
         * Mueve la hitbox en y
         */
        hitbox.y += this.ya;
        /**
         * Si colisiona en y por abajo quiere decir que no esta cayendo
         */
        if (this.collidingYDown) {
            this.falling = false;
            this.contGravedad = 0;/**Se resetea el contador de gravedad*/
        }
        /**
         * Si la y se mueve entonces esta cayendo
         */
        if (ya != 0) {
            falling = true;
        }
        activarCooldownVida();/**Se comprueba el cooldown de la vida*/
    }
    /**
     * Hace perder 1 vida al ser y activa el cooldown del daño
     */
    public void perderVida() {
        if (cooldownDaño == 0) {
            this.vida--;
            cooldownDaño++;
        }
    }
    /**
     * Contador de animacion para saber cada cuanto se tienen que cambiar las
     * poses dinamicas(andar)
     */
    protected void contadorAnimacion() {
        if (this.animacion <= 30) {/**La variable aumenta hasta 30*/
            ++this.animacion;
        } else {/**Si llega al tope se resetea*/
            this.animacion = 0;
        }
    }
    /**
     * Actualizamos el sprite que debe tener en este instante dependiendo del
     * estado del ser y de la variable contador de animacion para poses
     * dinamicas
     */
    public void actualizarSprite() {
        /**Actualizamos el sprite actual*/
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
        this.spriteActual += (dir.equals("R") ? 0 : 1);/**Se suma 1 si esta mirando a la izquierda*/
    }
    /**
     * Actualiza el contador de la vida, para respetar un tiempo minimo para
     * recibir daño
     */
    private void activarCooldownVida() {
        if (cooldownDaño == 0) {
        } else if (cooldownDaño < COOLDOWNDAÑOTOTAL && cooldownDaño > 0) {
            this.cooldownDaño++;
        } else {
            cooldownDaño = 0;
        }
    }
    /**
     * Obtener la vida del ser en este instante
     *
     * @return La vida restante del ser
     */
    public int getVida() {
        return vida;
    }

    /**
     * Define la vida restante del ser
     *
     * @param vida La vida que tendra el ser
     */
    public void setVida(int vida) {
        this.vida = vida;
    }

    /**
     * Obtiene la direccion actual del ser
     *
     * @return La direccion actual del ser
     */
    public String getDir() {
        return dir;
    }

    /**
     * Establece la direccion actual del ser
     *
     * @param dir La direccion a darle al ser
     */
    public void setDir(String dir) {
        this.dir = dir;
    }

    /**
     * Establece si el ser esta andando o no
     *
     * @param walking True o false segun el ser este andando o no
     */
    public void setWalking(boolean walking) {
        this.walking = walking;
    }

    /**
     * Establece si el ser esta cayendo o no
     *
     * @param falling True o false si esta cayendo o no
     */
    public void setFalling(boolean falling) {
        this.falling = falling;
    }

    /**
     * Establecer si el ser esta disparando
     *
     * @param shooting true o false si esta disparando o no
     */
    public void setShooting(boolean shooting) {
        this.shooting = shooting;
    }

    /**
     * Establece si el ser esta quieto o no
     *
     * @param stay Si el personaje esta quieto
     */
    public void setStay(boolean stay) {
        this.stay = stay;
    }
    /**
     * Saber el ser esta vivo o no, si tiene 0 o menos devuelve true
     *
     * @return Boolean true si esta muerto y false si esta muerto
     */
    public boolean isDead() {
        return this.vida <= 0;
    }
}
