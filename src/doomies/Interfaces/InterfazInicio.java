/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package doomies.Interfaces;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import doomies.HerramientasEntradaSalida.LoadTools;
import doomies.HerramientasEntradaSalida.Mouse;
import doomies.HerramientasEntradaSalida.Teclado;
import doomies.Interfaces.Elementos.Boton;

/**
 * Interfaz de inicio que muestra un fondo en movimiento, el logo y el boton de
 * start
 *
 * @author Javier
 * @version 4
 * @since 2
 */
public class InterfazInicio extends Interfaz {

    /**
     * Poscion en x
     */
    private int x = 0;
    /**
     * Si se esta moviendo hacia la derecha
     */
    private boolean right = true;
    /**
     * Imagen de fondo
     */
    private final BufferedImage background = LoadTools.loadImage("/images/BG_0.png");
    /**
     * Imagen del logo
     */
    private final BufferedImage logo = LoadTools.loadImage("/images/Logo.png");
    /**
     * Boton de start
     */
    private final Boton botonStart;
    /**
     * El boton de start se ha pulsado
     */
    private boolean start;

    /**
     *
     * @param WIDTH Altura de la interfaz
     * @param HEIGHT Anchura de la interfaz
     * @param teclado Teclado que podra usar la interfaz
     * @param raton Raton que usaran los botones de la interfaz
     */
    public InterfazInicio(final int WIDTH, final int HEIGHT, final Teclado teclado, final Mouse raton) {
        super(WIDTH, HEIGHT, teclado, raton);
        start = false;
        botonStart = new Boton(400, 600, 480, 84, "START", font.deriveFont(30f), Color.gray, -10, 4, raton);
    }

    /**
     * Dibuja todo lo que contiene la interfaz de inicio
     *
     * @param g Clase graphics que dibuja todo en la pantalla
     */
    public void dibujar(final Graphics g) {
        /**
         * Se dibuja el fondo
         */
        g.drawImage(background, x, -200, null);
        /**
         * Se dibuja el logo
         */
        g.drawImage(logo, 355, 0, null);
        /**
         *
         */
        botonStart.dibujar(g);
    }
    /**
     * Actualiza la todos los elementos de la interfaz de inicio
     */
    public void actualizar() {
        //Actualizacion de boton
        botonStart.actualizar();
        /**
         * Control de la posicion de la imagen
         */
        //Limite derecho
        if (x == 0) {
            right = true;
        } //limite izquierdo
        else if (x == -720) {
            right = false;
        }
        //Movimiento de la imagen hacia la derecha
        if (right == true) {
            x--;
        } //Movimiento de la imagen hacia la izquierda
        else if (right == false) {
            x++;
        }
        /*
        Si se ha pulsado al boton
         */
        if (botonStart.isClicked()) {
            start = true;
        }
    }

    /**
     *
     * @return Si se ha pulsado el boton Start
     */
    public boolean isStart() {
        return start;
    }
}
