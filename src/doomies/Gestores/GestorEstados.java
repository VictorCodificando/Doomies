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
 *
 * @author Víctor Zero
 */
public class GestorEstados implements Gestor {

    private Gestor gestorActual;
    public static Partida partida;
    public static ArrayList<String> partidas;
    private final int WIDTH;
    private final int HEIGHT;
    private final Teclado teclado;
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
     * @param g Objeto Graphics que dibujara en memoria
     */
    public void dibujar(Graphics g) {
        gestorActual.dibujar(g);
    }

    /**
     * Actualiza el gestorActual
     */
    public void actualizar() {
        gestorActual.actualizar();
        if (this.isGestorJuego()) {
            this.opcionesJugando();
        } else {
            this.opcionesMenu();
        }
    }

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
