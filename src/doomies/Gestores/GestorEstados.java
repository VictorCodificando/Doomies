/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package doomies.Gestores;

import java.awt.Graphics;
import doomies.HerramientasEntradaSalida.Mouse;
import doomies.HerramientasEntradaSalida.Teclado;
import doomies.Partida;
import java.util.ArrayList;

/**
 * Es el Gestor principal del juego, que jugara con el gestorActual para cambiar
 * de Gestor menu a gestor de Juego
 *
 * @see Gestor
 * @author Víctor
 * @version 4
 * @since 3
 */
public class GestorEstados implements Gestor {

    /**
     * El gestor actual que se actualiza y se dibuja, puede ser o gestorMenu o
     * GestorJuego
     */
    private Gestor gestorActual;
    /**
     * partida actual cargada por defecto "default"
     */
    public static Partida partida;
    /**
     * Partidas cargadas de los ficheros
     */
    public static ArrayList<String> partidas;
    /**
     * Anchura de pantalla
     */
    private final int WIDTH;
    /**
     * Altura de pantalla
     */
    private final int HEIGHT;
    /**
     * Teclado que controlara el usuario
     */
    private final Teclado teclado;
    /**
     * Raton que controlará el usuario
     */
    private final Mouse raton;

    /**
     * Crea el gestor de estados que es aquel que segun su estado invoca todo
     * objeto funcional del juego
     *
     * @param WIDTH Anchura de la pantalla
     * @param HEIGHT Altura de la pantalla
     * @param teclado Teclado usado por el programa
     * @param raton Raton usado por todo el programa
     */
    public GestorEstados(final int WIDTH, final int HEIGHT, final Teclado teclado, final Mouse raton) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.teclado = teclado;
        this.raton = raton;
        partida = new Partida("default");
        this.gestorActual = (Gestor) new GestorMenu(WIDTH, HEIGHT, teclado, raton, 0);
    }

    /**
     * Dibuja el Gestor actual
     *
     * @param g Objeto Graphics que dibujara en pantalla
     */
    @Override
    public void dibujar(Graphics g) {
        gestorActual.dibujar(g);
    }

    /**
     * Actualiza el gestorActual
     */
    @Override
    public void actualizar() {
        gestorActual.actualizar();
        if (this.isGestorJuego()) {
            /**
             * Si es gestorJuego
             */
            this.opcionesJugando();
        } else {
            /**
             * Si es GestorMenu
             */
            this.opcionesMenu();
        }
    }

    /**
     * Pasa por las diferentes opciones que se eligen en el GestorMenu, si
     * decide jugar pasa al Gestor de Juego con el nivel elegido
     */
    public void opcionesMenu() {
        if (!this.isGestorMenu()) {
            return;
        }
        GestorMenu temp = (GestorMenu) gestorActual;
        if (temp.isJugar()) {
            int nivel = temp.getNivel();
            this.setGestorJuego(nivel);
        }
    }

    /**
     * Pasa por las diferentes opciones que se eligen en el GestorJuego, si
     * decide salir pasa a GestorMenu
     */
    public void opcionesJugando() {
        if (!this.isGestorJuego()) {
            return;
        }
        GestorJuego temp = (GestorJuego) gestorActual;
        if (temp.isSalir()) {
            this.setGestorMenu();
        }
    }

    /**
     * Cambia el menu al Gestor de Menu
     */
    public void setGestorMenu() {
        this.gestorActual = (Gestor) new GestorMenu(WIDTH, HEIGHT, teclado, raton, 2);
    }

    /**
     *
     * @return Si el gestor actual es el gestor de los menus
     */
    public boolean isGestorMenu() {
        return gestorActual instanceof GestorMenu;
    }

    /**
     *
     * @param ID_MAPA Introduce el nivel/ID_mapa que se necesita para iniciar el
     * juego
     */
    public void setGestorJuego(int ID_MAPA) {
        this.gestorActual = (Gestor) new GestorJuego(WIDTH, HEIGHT, ID_MAPA, teclado, raton);
    }

    /**
     *
     * @return Si el gestor actual es el gestor de juego
     */
    public boolean isGestorJuego() {
        return gestorActual instanceof GestorJuego;
    }

}
