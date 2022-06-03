package doomies.Entes;

import doomies.Entes.Seres.Jugador;
import doomies.Gestores.GestorJuego;
import java.awt.Graphics;
import doomies.Visual.Sprite;
import java.awt.Rectangle;

/**
 * Toda entidad, sea objeto, ser o cualquier cosa que tenga hitbox y velocidad
 * sera considerado una entidad
 *
 * @see CuerpoGravitatorio
 * @author Víctor
 * @version 4
 * @since 2
 */
public abstract class Entidad implements CuerpoGravitatorio {

    /**
     * Posicion x de la entidad, se suele usar el hitbox.x en vez de esta
     */
    protected int x;
    /**
     * Posicion y de la entidad, usado mas el hitbox.y
     */
    protected int y;
    /**
     * Velocidad en el eje x
     */
    protected int xa;
    /**
     * Velocidad en el eje y
     */
    protected int ya;
    /**
     * Velocidad a la que puede ir la entidad
     */
    protected int speed;

    /**
     * Colsiona en x a la derecha
     */
    protected boolean collidingXRight;
    /**
     * Colisiona en x a la izquierda
     */
    protected boolean collidingXLeft;
    /**
     * Colsiiona en Y abajo
     */
    protected boolean collidingYDown;
    /**
     * Colisiona en Y arriba
     */
    protected boolean collidingYUp;
    /**
     * Anchura de la entidad
     */
    public final int WIDTH;
    /**
     * Altura de la entidad
     */
    public final int HEIGHT;
    /**
     * Hitbox de la entidad
     */
    protected Rectangle hitbox;
    /**
     * Indica si la entidad es visible, o sea se, que está contenida en pantalla
     */
    protected boolean visible;
    /**
     * Contador de la gravedad
     */
    protected int contGravedad;
    /**
     * Array de sprites donde se almacenan los sprites a usar
     */
    protected Sprite[] sprites;

    /**
     * Toda clase entidad al tener una posicion, velocidad y visibilidad, esta
     * obligada a tener un comportamiento donde se le pueda ver
     *
     * @param g Clase graphics que dibujara en pantalla
     */
    public abstract void dibujar(final Graphics g);

    /**
     * Al tener que moverse la entidad, tiene que tener un metodo actualizar
     */
    public abstract void actualizar();

    /**
     * Crea una entidad con unos sprites y un tamaño en especifico, y define
     * todos sus estados de colision en negativo inicialmente
     *
     * @param sprite Array de sprites que contienen los sprites que lo caracterizan
     * @param WIDTH Anchura de su hitbox
     * @param HEIGHT Altura de su hitbox
     */
    public Entidad(final Sprite[] sprite, final int WIDTH, final int HEIGHT) {
        this.speed = 4;
        collidingYUp = false;
        collidingXRight = false;
        collidingYDown = false;
        collidingXLeft = false;
        this.visible = true;
        this.sprites = sprite;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
    }
    /**
     * Hace caer a la entidad cada cierto tiempo, puede ser usado o no, ya que
     * habra entidades tales como la bala o enemigos que vuelen a los cuales no
     * les es necesario caer
     */
    public void caer() {
        if (this.contGravedad == 10) {/**Si es 10 (numero establecido aleatoriamente porque quedaba mejor) entonces aumenta la velocidad*/
            /** Esto hace a la variable G una aceleracion*/
            this.ya += 4;
            this.contGravedad = 0;
        } else {/**Si no es 10 aumenta esta variable*/
            this.contGravedad++;
        }
    }
    /**
     * Ajusta la hitbox en X, de los dos objetos
     *
     * @param limit La hitbox con la que chocará el rectangulo next
     * @param next La hitbox que tendrá el ente actual si sigue en curso su velocidad en X
     */
    public void ajustarHitboxinX(final Rectangle limit, final Rectangle next) {
        /**
         * Al ser el jugador un ente que se mueve junto al mapa, su
         * comportamiento se distingue de los demas, siendo su distincion la
         * velocidad, mientras que los demas entes cojen la velocidad del
         * GestorJuego + su velocidad, el jugador se basa en su velocidad
         */
        if (!(this instanceof Jugador)) {
            if (limit.x > next.x && limit.x < next.x + next.width) {/**Si va a chocar en la izquierda*/
                collidingXLeft = true;/**Activamos la colision correspondiente*/
                hitbox.x = limit.x - hitbox.width - Math.abs(GestorJuego.getXa()) + Math.abs(this.speed);/**Lo devolvemos hacia atras*/
            } else if (limit.x < next.x && limit.x + limit.width > next.x) {/**Si va a chocar en la derecha*/
                collidingXRight = true;/**Activamos la colision correspondiente*/
                hitbox.x = limit.x + limit.width + Math.abs(GestorJuego.getXa()) + Math.abs(this.speed);/**Lo devolvemos hacia atras*/
            }
        } else {
            if (limit.x > next.x && limit.x < next.x + next.width && xa != 0) {/**Si va a chocar en la izquierda*/
                collidingXLeft = true;/**Activamos la colision correspondiente*/
                hitbox.x = limit.x - hitbox.width - ((Math.abs(xa) == 4) ? 4 : 8);/**Lo devolvemos hacia atras*/
            } else if (limit.x < next.x && limit.x + limit.width > next.x && xa != 0) {/**Si va a chocar en la derecha*/
                collidingXRight = true;/**Activamos la colision correspondiente*/
                hitbox.x = limit.x + limit.width + ((Math.abs(xa) == 4) ? 4 : 8);/**Lo devolvemos hacia atras*/
            }
        }
    }
    /**
     * Ajusta la hitbox en el eje Y
     * 
     * @param limit La hitbox con la que chocará el rectangulo next
     * @param next La hitbox que tendrá el ente actual si sigue en curso su velocidad en Y
     */
    public void ajustarHitboxinY(final Rectangle limit, final Rectangle next) {
        if (!(this instanceof Jugador)) {
            if (limit.y > next.y && limit.y < next.y + next.height) {/**Si va a chocar abajo*/
                collidingYDown = true;/**Activamos la colision correspondiente*/
                ya = 0;/**Resetamos la velocidad para que no pueda caer*/
                hitbox.y = limit.y - hitbox.height;/**Lo devolvemos hacia arriba*/
            } else if (limit.y < next.y && limit.y + limit.height > next.y) {/**Si va a chocar arriba*/
                collidingYUp = true;/**Activamos la colision correspondiente*/
                ya = 0;/**Resetamos la velocidad para que no pueda subir*/
                hitbox.y = limit.y + limit.height;/**Lo devolvemos hacia abajo*/
            }
        } else {
            if (limit.y > next.y && limit.y < next.y + next.height) {/**Si va a chocar abajo*/
                collidingYDown = true;/**Activamos la colision correspondiente*/
                ya = 0;/**Resetamos la velocidad para que no pueda caer*/
                hitbox.y = limit.y - hitbox.height;/**Lo devolvemos hacia arriba*/
            } else if (limit.y < next.y && limit.y + limit.height > next.y) {/**Si va a chocar arriba*/
                collidingYUp = true;/**Activamos la colision correspondiente*/
                ya = 0;/**Resetamos la velocidad para que no pueda subir*/
                hitbox.y = limit.y + limit.height;/**Lo devolvemos hacia abajo*/
            }
        }
    }
    /**
     * Compruba si va a chocar con el rectangulo en el siguiente movimiento
     *
     * @param estatico rectangulo con el que comprueba si chocara el ente actual
     * en el siguiente movimiento, dejando un margen de 10px con el otro eje
     * para que confunda tipos de colisiones
     */
    public void isGoingToCollide(Rectangle estatico) {
        Rectangle nextHitboxinX = new Rectangle(hitbox.x + xa, hitbox.y + 10, hitbox.width, hitbox.height - 20);
        Rectangle nextHitboxinY = new Rectangle(hitbox.x + 10, hitbox.y + ya, hitbox.width - 20, hitbox.height);
        if (estatico.intersects(nextHitboxinY)) {
            this.ajustarHitboxinY(estatico, nextHitboxinY);
        } else if (estatico.intersects(nextHitboxinX)) {
            this.ajustarHitboxinX(estatico, nextHitboxinX);
        }
    }
    /**
     * Establece la velocidad en el eje x
     * @param xa nueva velocidad en el eje x
     */
    public void setXa(final int xa) {
        this.xa = xa;
    }
    /**
     * Obtiene la hitbox del ente actual
     * @return La hitbox del ente
     */
    public Rectangle getHitbox() {
        return this.hitbox;
    }
    /**
     * Obten si el ente es visible en pantalla o no
     * @return Si el ente es visible o no
     */
    public boolean isVisible() {
        return this.visible;
    }   
    /**
     * Establece si el ente es visible en pantalla
     * @param visible Boolean que define si es visible o no
     */
    public void setVisible(final boolean visible) {
        this.visible = visible;
    }
    /**
     * Esta colisionando arriba
     * @return Si hay colision arriba del ente
     */
    public boolean isCollidingYUp() {
        return collidingYUp;
    }
    /**
     * Establece si esta colisionando el ente por arriba
     *
     * @param collidingYUp La colision
     */
    public void setCollidingYUp(boolean collidingYUp) {
        this.collidingYUp = collidingYUp;
    }
    /**
     * Esta colisionando a la derecha
     *
     * @return Si hay colision a la derecha del ente
     */
    public boolean isCollidingXRight() {
        return collidingXRight;
    }
    /**
     * Establece si esta colisionando el ente por la derecha
     *
     * @param collidingXRight La colision
     */
    public void setCollidingXRight(boolean collidingXRight) {
        this.collidingXRight = collidingXRight;
    }
    /**
     * Esta colisionando abajo
     *
     * @return Si hay colision abajo del ente
     */
    public boolean isCollidingYDown() {
        return collidingYDown;
    }
    /**
     * Establece si esta colisionando el ente por abajo
     *
     * @param collidingYDown La colision
     */
    public void setCollidingYDown(boolean collidingYDown) {
        this.collidingYDown = collidingYDown;
    }
    /**
     * Esta colisionando a la izquierda
     *
     * @return Si hay colision a la izquierda del ente
     */
    public boolean isCollidingXLeft() {
        return collidingXLeft;
    }
    /**
     * Establece si esta colisionando el ente por la izquierda
     *
     * @param collidingXLeft La colision
     */
    public void setCollidingXLeft(boolean collidingXLeft) {
        this.collidingXLeft = collidingXLeft;
    }

}
