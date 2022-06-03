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
 * Clase enemigo que hereda de SerVivo, en ella se definen los enemigos que
 * habra por el mapa Hereda de:
 *
 * @see SerVivo
 * @see doomies.Entes.Entidad
 * @author Javier
 * @version 4
 * @since 3
 */
public class Enemigo extends SerVivo {

    /**
     * Indica la vida total del enemigo usado en la barra de vida
     *
     * @see Enemigo#dibujarBarraVida(java.awt.Graphics)
     */
    private final int VIDA_TOTAL;
    /**
     * Indica el tipo de enemigo que es
     */
    private final int ENEMY_TYPE;
    /**
     * Posicion x del jugador
     */
    public static int xPlayer;
    /**
     * Posicion y del jugador
     */
    public static int yPlayer;
    /**
     * Contador del cooldown en el que el enemigo sabe donde esta el jugador
     * en x
     */
    private int cooldownMuestra = 0;
    /**
     * Contador del cooldown en el que el enemigo sabe donde esta el jugador
     * en y
     */
    private int cooldownMuestraY = 0;
    /**
     * Constante que indica que sprite se usa para sprintar
     */
    protected static final int SPRINT_ID = 6;
    /**
     * Constante limite al que llega el cooldown para poder cumplir la condicion
     */
    private final int COOLDONWMUESTRALIMIT = (int) (Math.random() * 15 + 10);
    /**
     * La velocidad en y del enemigo
     */
    private int speedY;
    /**
     * Crea un Enemigo de un tipo determinado
     * 
     * @param x Posicion inicial en X
     * @param y Posicion inicial en Y
     * @param WIDTH Anchura enemigo
     * @param HEIGHT Altura enemigo
     * @param type Tipo de enemigo
     */
    public Enemigo(final int x, final int y, final int WIDTH, final int HEIGHT, final int type) {
        super(new Sprite[8], WIDTH, HEIGHT, 5);
        this.COOLDOWNDAÑOTOTAL = 10;
        switch (type) {
            /**
             * IMP
             * Velocidad rango: [2-5]
             * Vida 10
             * Sprites:
             *      -Quieto
             *      -Andar
             *      -Andar2
             */
            case 0:
                this.speed = (int) (Math.floor(Math.random() * 4 + 2));
                this.vida = 10;
                this.sprites = new Sprite[]{SpriteSheet.IMP.getSprite(0, 0), SpriteSheet.IMP.getSprite(0, 1), 
                                            SpriteSheet.IMP.getSprite(1, 0), SpriteSheet.IMP.getSprite(1, 1), 
                                            SpriteSheet.IMP.getSprite(2, 0), SpriteSheet.IMP.getSprite(2, 1)};
                this.ENEMY_TYPE = 0;
                break;
           /**
            * PINKIE
            * Velocidad rango: [3-6]
            * Vida 12
            * Sprites:
            *      -Quieto
            *      -Andar
            *      -Andar2
            *      -Caer
            */
            case 1:
                this.speed = (int) (Math.floor(Math.random() * 4 + 3));
                this.vida = 12;
                this.sprites = new Sprite[]{SpriteSheet.PINKIE.getSprite(0, 0), SpriteSheet.PINKIE.getSprite(0, 1), 
                                            SpriteSheet.PINKIE.getSprite(1, 0), SpriteSheet.PINKIE.getSprite(1, 1), 
                                            SpriteSheet.PINKIE.getSprite(2, 0), SpriteSheet.PINKIE.getSprite(2, 1), 
                                            SpriteSheet.PINKIE.getSprite(3, 0), SpriteSheet.PINKIE.getSprite(3, 1)};
                this.ENEMY_TYPE = 1;
                break;
           /**
            * SOUL
            * 
            * Velocidad rango: [1-4]
            * Vida 8
            * Sprites:
            *      -Quieto
            *      -Andar
            *      -Andar2
            *      -Sprint
            */
            case 2:
                this.speed = (int) (Math.floor(Math.random() * 4 + 1));
                this.vida = 8;
                this.sprites = new Sprite[]{SpriteSheet.SOUL.getSprite(0, 0), SpriteSheet.SOUL.getSprite(0, 1),
                                            SpriteSheet.SOUL.getSprite(0, 0), SpriteSheet.SOUL.getSprite(0, 1),
                                            SpriteSheet.SOUL.getSprite(0, 0), SpriteSheet.SOUL.getSprite(0, 1),
                                            SpriteSheet.SOUL.getSprite(0, 0), SpriteSheet.SOUL.getSprite(0, 1)};
                this.ENEMY_TYPE = 2;
                break;
           /**
            * CACODEMON
            * Velocidad rango: [2-5]
            * Vida 18
            * Sprites:
            *      -Quieto
            *      -Volar
            *      -Volar2
            *      -Sprint
            */
            case 3://(2-5)
                this.speed = (int) (Math.floor(Math.random() * 4 + 2));
                this.vida = 18;
                this.sprites = new Sprite[]{SpriteSheet.CACODEMON.getSprite(0, 0), SpriteSheet.CACODEMON.getSprite(0, 1), 
                                            SpriteSheet.CACODEMON.getSprite(1, 0), SpriteSheet.CACODEMON.getSprite(1, 1), 
                                            SpriteSheet.CACODEMON.getSprite(2, 0), SpriteSheet.CACODEMON.getSprite(2, 1), 
                                            SpriteSheet.CACODEMON.getSprite(3, 0), SpriteSheet.CACODEMON.getSprite(3, 1)};
                this.ENEMY_TYPE = 3;
                break;
           /**
            * BARON
            * Velocidad rango: [2-4]
            * Vida 12
            * Sprites:
            *      -Quieto
            *      -Andar
            *      -Andar2
            *      -Sprint
            */
            case 4:
                this.speed = (int) (Math.floor(Math.random() * 3 + 2));
                this.vida = 30;
                this.sprites = new Sprite[]{SpriteSheet.BARON.getSprite(0, 0), SpriteSheet.BARON.getSprite(0, 1), 
                                            SpriteSheet.BARON.getSprite(1, 0), SpriteSheet.BARON.getSprite(1, 1), 
                                            SpriteSheet.BARON.getSprite(2, 0), SpriteSheet.BARON.getSprite(2, 1), 
                                            SpriteSheet.BARON.getSprite(3, 0), SpriteSheet.BARON.getSprite(3, 1)};
                this.ENEMY_TYPE = 4;
                break;
            /**
             * SE MUESTRA MENSAJE DE ERROR ENEMIGO NO CONOCIDO
             */
            default:
                this.ENEMY_TYPE = -1;
                JOptionPane.showMessageDialog(new Frame(), "ERROR FATAL", "ERROR", 2);
                System.exit(0);
                sprites = null;
        }
        /**
         * Establecemos vida total como la vida ya establecida
         */
        this.VIDA_TOTAL = vida;
        this.speedY = speed;
        this.hitbox = new Rectangle(x, y, WIDTH, HEIGHT);
    }
    /**
     * Dibuja el enemigo junto con su barra de vida
     * @see SerVivo#dibujar(java.awt.Graphics) 
     * @param g Clase graphics que representa la pantalla
     */
    public void dibujar(final Graphics g) {
        super.dibujar(g);
        dibujarBarraVida(g);
    }
    /**
     * Actualiza todo el enemigo
     * @see SerVivo#actualizar()
     */
    public void actualizar() {
        this.moverEnemigo();
        super.actualizar();
    }
    /**
     * Dibuja la barra de vida encima del enemigo
     * @see Enemigo#dibujar(java.awt.Graphics) 
     * @param g Clase graphics que representa la pantalla
     */
    private void dibujarBarraVida(final Graphics g) {
        final int ANCHURA_BARRA = 50;
        final int ALTURA_BARRA = 4;
        final int POS_X = this.hitbox.x + (this.hitbox.width / 2) - (ANCHURA_BARRA / 2);
        final int POS_Y = this.hitbox.y - 20;
        final float PORCT = ((vida * 1f) / VIDA_TOTAL);
        final int FORMAT = 1;
        /**
         * Borde de la barra
         */
        g.setColor(Color.BLACK);
        g.fillRect(POS_X, POS_Y + FORMAT, FORMAT, ALTURA_BARRA - FORMAT * 2);
        g.fillRect(POS_X + ANCHURA_BARRA - FORMAT, POS_Y + FORMAT, FORMAT, ALTURA_BARRA - FORMAT * 2);
        g.fillRect(POS_X + FORMAT, POS_Y + ALTURA_BARRA - FORMAT, ANCHURA_BARRA - (FORMAT * 2), FORMAT);
        g.fillRect(POS_X + FORMAT, POS_Y, ANCHURA_BARRA - (FORMAT * 2), FORMAT);
        /**
         * Barra
         */
        g.setColor(Color.red);
        g.fillRect(POS_X + 1, POS_Y + 1, (int) (ANCHURA_BARRA * PORCT) - 2, ALTURA_BARRA - 2);
    }
    /**
     * Actualiza los sprites para el enemigo es llamado desde el actualizar de SerVivo
     * @see SerVivo#actualizarSprite() 
     */
    @Override
    public void actualizarSprite() {
        /**
         * Se actualiza el sprite segun la variable max la cual siempre vale 20
         * (Hecho asi para facilitar el cambio)
         */
        final int max = 20;
        /**
         * Si esta andando va cambiando de sprite con un intervalo ya visto,
         * va cambiando entre Andar(WALK_ID)-> quieto(STAY_ID) -> andar2(FALL_ID)
         * 
         * Esto hace que en enemigos como el IMP se vea tal que:
         * pie izquierdo delante -> quieto -> pie derecho delante.
         * Simulando perfectamente un andar real.
         */
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
        /**
         * Establece la direccion, si esta mirando a la izquierda,
         * su sprite esta en una posicion mas adelantada dentro del array
         */
        if (speed < 0) {
            spriteActual += 1;
        }

    }
    /**
     * Mueve el enemigo dependiendo de que tipo sea tendra un comportamiento
     * diferente
     * 
     * Si es invisible cancela el movimiento y resetea su aceleracion
     */
    protected void moverEnemigo() {
        if (!visible) {
            ya = 0;
            return;
        }
        spriteActual = 0;
        switch (this.ENEMY_TYPE) {
            /**
             * IMP
             * Se mueve en el eje de la x sin choca con algo se gira.
             */
            case 0:
                moverEnX();
                break;
            /**
             * PINKIE
             * Persigue al jugador en el eje de la x
             * Salta si esta por debajo del jugador
             * Corre si esta cerca del jugador
             */
            case 1:
                perseguirX();
                jump();
                sprint();
                break;
            /**
             * SOUL
             * Persigue al jugador en el eje de la x
             * Vuela intentando "perseguir al jugador" en el eje y
             */
            case 2:
                perseguirX();
                volar();
                break;
            /**
             * CACODEMON
             * Persigue al jugador en el eje de la x
             * Vuela intentando "perseguir al jugador" en el eje y
             * Corre si el jugador esta cerca
             */
            case 3:
                perseguirX();
                volar();
                sprint();
                break;
            /**
             * BARON
             * Persigue al jugador en el eje de la x
             */
            case 4:
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
    }
    /**
     * Salto del enemigo, salta menos que el jugador pero basandose en la
     * posicion en el eje y de este
     */
    @Override
    protected void jump() {
        if (((collidingXRight || collidingXLeft)) || yPlayer + 120 < this.hitbox.y) {
            if (!collidingYDown) {
                /**
                 * Si no esta tocando suelo no puede saltar
                 */
                return;
            }
            this.ya = -9;
            this.spriteActual = SPRINT_ID;
            /**
             * Cambia su sprite al de sprint ya que correr y saltar comparten sprite
             */
        }
    }

    /**
     * Define el comportamiento del vuelo del enemigo
     */
    private void volar() {
        /**
         * Se resetea su velocidad en y
         */
        ya = 0;
        /**
         * Se le enseña la posicion del jugador dependiendo del contador, para
         * determinar su movimiento
         */
        if (cooldownMuestraY == 0) {
            if (this.yPlayer + 54 < hitbox.y + (hitbox.height / 2)) {
                speedY = Math.abs(speedY) * -1;
            } else if (this.yPlayer + 54 > hitbox.y + (hitbox.height / 2)) {
                speedY = Math.abs(speedY);
            }
            cooldownMuestraY++;
        } else if (cooldownMuestraY == COOLDONWMUESTRALIMIT) {
            cooldownMuestraY = 0;
        } else {
            cooldownMuestraY++;
        }
        /**
         * La velocidad será posible siempre y cuando no colisione ni abajo ni
         * arriba
         */
        ya = ((collidingYUp && speedY < 0) || (collidingYDown && speedY > 0)) ? 0 : speedY;
        this.spriteActual = WALK_ID;
        /**
         * Cambia el sprite a andar, ya que para los enemigos voladores ahi se
         * encuentra su sprite de volar
         */
    }
    /**
     * Persigue al jugador en el eje x, cuando la variable cooldown alcanza un
     * valor se le comparte la informacion de la posicion del jugador
     *
     * IMPORTANTE: La velocidad se sumara y no se asignara debido a que ya lleva
     * una velocidad de movimiento asignada por el gestor del juego
     */
    private void perseguirX() {
        if (cooldownMuestra == 0) {
            if (this.xPlayer + 34 < hitbox.x + (hitbox.width / 2)) {
                speed = Math.abs(speed) * -1;
            } else if (this.xPlayer + 34 > hitbox.x + (hitbox.width / 2)) {
                speed = Math.abs(speed);
            }
            if (collidingXRight || collidingXLeft) {
                speed = -speed;
            }
            cooldownMuestra++;
        } else if (cooldownMuestra == COOLDONWMUESTRALIMIT) {/**Reseta el contador*/
            cooldownMuestra = 0;
        } else {
            cooldownMuestra++;
        }
        if (!(collidingXRight || collidingXLeft)) {/**Si no choca a los lados se hace su movimiento posible*/
            xa += speed;
        }
        this.spriteActual = WALK_ID;
    }

    /**
     * Hace que el enemigo corra cuando el centro del jugador esta a 400 pixeles
     * del centro del enemigo
     * Si cumple la condicion su velocidad se multiplica por 1.5
     */
    private void sprint() {
        if (Math.abs(xPlayer + 54 - this.hitbox.x + (hitbox.height / 2)) < 400 && Math.abs(yPlayer + 34 - this.hitbox.y + (hitbox.height / 2)) < 400) {
            xa *= 1.5;
            this.spriteActual = SPRINT_ID;
        }
    }
    /**
     * Establece el comportamiento basico de movimiento en x Se mueve por un
     * contador pero siendo este contador muy bajo para que si detecta una
     * colision se de la vuelta
     */
    private void moverEnX() {
        if (cooldownMuestra == 0) {
            if (collidingXRight || collidingXLeft) {/**Si colisiona da la vuelta*/
                speed = -speed;
            }
            cooldownMuestra++;
        } else if (cooldownMuestra == COOLDONWMUESTRALIMIT) {
            cooldownMuestra = 0;
        } else {
            cooldownMuestra++;
        }
        xa += speed;
        this.spriteActual = WALK_ID;
    }

    /**
     * Te da a conocer la altura y anchura
     * 
     * @see doomies.HerramientasEntradaSalida.LoadTools#loadEntes(java.lang.String, java.util.ArrayList) 
     * @param type Tipo de enemigo
     * @return Array que contiene altura y anchura 
     */
    public static int[] establecerAnchuraYAlturaSegunTipo(final int type) {
        /**
         * Array de int, que indica:
         * 0 - Anchura
         * 1 - Altura
         */
        int widthHeight[] = new int[2];
        switch (type) {
            case 0:
                widthHeight[0] = 91;
                widthHeight[1] = 100;
                break;
            case 1:
                widthHeight[0] = 84;
                widthHeight[1] = 84;
                break;
            case 2:
                widthHeight[0] = 62;
                widthHeight[1] = 60;
                break;
            case 3:
                widthHeight[0] = 96;
                widthHeight[1] = 107;
                break;
            case 4:
                widthHeight[0] = 129;
                widthHeight[1] = 192;
                break;
            default:
        }
        return widthHeight;
    }
    /**
     * Obtienes el tipo de enemigo que es: 
     * 0-IMP 
     * 1-PINKIE 
     * 2-SOUL 
     * 3-CACODEMON
     * 4-BARON
     *
     * @see Enemigo#ENEMY_TYPE
     * @return El tipo de enemigo que es
     */
    public int getEnemyType() {
        return ENEMY_TYPE;
    }
}
