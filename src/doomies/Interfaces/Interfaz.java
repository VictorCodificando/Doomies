/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package doomies.Interfaces;

import java.awt.Font;
import java.awt.Graphics;
import doomies.HerramientasEntradaSalida.LoadTools;
import doomies.HerramientasEntradaSalida.Mouse;
import doomies.HerramientasEntradaSalida.Teclado;

/**
 * Clase abstracta que representa lo mas generico que tiene una interfaz,
 * obligando a tener metodos como dibujar y actualizar
 *
 * @author Víctor
 * @version 4
 * @since 2
 */
public abstract class Interfaz {

    /**
     * Anchura de la pantalla
     */
    protected final int WIDTH;
    /**
     * Altura de la pantalla
     */
    protected final int HEIGHT;
    /**
     * Teclado que usara la interfaz
     */
    protected final Teclado teclado;
    /**
     * Raton que usara la interfaz
     */
    protected final Mouse raton;
    /**
     * Fuente a usar en las letras de la interfaz
     */
    protected final Font font = LoadTools.loadFont("/fonts/kongtext.ttf");

    /**
     * Crea una interfaz generica
     *
     * @param WIDTH Anchura de la pantalla
     * @param HEIGHT Altura de la pantalla
     * @param teclado Teclado a usar
     * @param raton Raton a usar
     */
    public Interfaz(int WIDTH, int HEIGHT, Teclado teclado, Mouse raton) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.teclado = teclado;
        this.raton = raton;
    }

    /**
     * Dibujara la interfaz con todos sus elementos
     *
     * @param g Clase graphics que dibuja todo en la pantalla
     */
    public abstract void dibujar(final Graphics g);

    /**
     * Actualizara todos los elementos que componen la interfaz
     *
     */
    public abstract void actualizar();
}
