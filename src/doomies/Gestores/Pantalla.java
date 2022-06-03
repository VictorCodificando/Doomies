package doomies.Gestores;

import java.awt.Graphics;
import doomies.HerramientasEntradaSalida.Mouse;
import doomies.HerramientasEntradaSalida.Teclado;

/**
 * Clase que simplemente contiene el Gestor de Estados y lo dibuja, es desde la
 * que parte todo
 *
 * @author Víctor
 * @version 4
 * @since 1
 */
public class Pantalla {

    /**
     * Anchura de la pantalla
     */
    public final int WIDTH;
    /**
     * Altura de la pantalla
     */
    private final int HEIGHT;
    /**
     * Gestor de Estados que controla todos los estados y comportamientos del
     * juego
     */
    private GestorEstados ge;

    /**
     * Crea una pantalla que contiene todo lo relacionado con el juego
     *
     * @param WIDTH Anchura de la pantalla
     * @param HEIGHT Altura de la pantalla
     * @param teclado Tecladoa a usar en la ejecucion
     * @param raton Raton a usar en la ejecucion
     */
    public Pantalla(final int WIDTH, final int HEIGHT, final Teclado teclado, final Mouse raton) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        ge = new GestorEstados(WIDTH, HEIGHT, teclado, raton);
    }

    /**
     * Actualiza el gestor de estados
     */
    public void actualizar() {
        ge.actualizar();
    }

    /**
     * Muestra lo que haya en gestor de estados en la pantalla
     * @param g Objeto graphics que dibuja en la pantalla
     */
    public void dibujar(final Graphics g) {
        ge.dibujar(g);
    }

}
