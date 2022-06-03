/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package doomies.Interfaces;

import java.awt.Color;
import java.awt.Graphics;
import doomies.HerramientasEntradaSalida.Mouse;
import doomies.HerramientasEntradaSalida.Teclado;
import doomies.Interfaces.Elementos.Boton;

/**
 * Interfaz de pausa que se muestra cuando el juego esta en pausa
 *
 * @see doomies.Gestores.GestorJuego
 * @author Nestor
 * @version 4
 * @since 2
 */
public class InterfazPausa extends Interfaz {

    /**
     * Panel que contiene a los otros botones
     */
    private Boton panel;
    /**
     * Boton de seguir jugando
     */
    private Boton botonSeguirJugando;
    /**
     * Boton de salir del nivel
     */
    private Boton botonSalir;
    /**
     * Si pulsa jugar
     */
    private boolean jugar = false;
    /**
     * Si pulsa salir
     */
    private boolean salir = false;

    /**
     * Crea la interfaz de pausa que se mostrara al parar el juego
     *
     * @param WIDTH Altura de la interfaz
     * @param HEIGHT Anchura de la interfaz
     * @param teclado Teclado que podra usar la interfaz
     * @param raton Raton que usaran los botones de la interfaz
     */
    public InterfazPausa(final int WIDTH, final int HEIGHT, final Teclado teclado, final Mouse raton) {
        super(WIDTH, HEIGHT, teclado, raton);
        panel = new Boton(((WIDTH / 2) - 400), ((HEIGHT / 2) - 300), 800, 600, " ", font.deriveFont(23f), new Color(249, 90, 9), 4, 2, raton);
        panel.setFormat(40);
        botonSeguirJugando = new Boton(450, 300, 400, 100, "SEGUIR JUGANDO", font.deriveFont(24f), Color.GRAY, 7, 4, raton);
        botonSalir = new Boton(450, 450, 400, 100, "SALIR", font.deriveFont(28f), Color.GRAY, -3, 4, raton);
    }

    /**
     * Dibuja en pantalla la interfaz con todos sus elementos
     *
     * @param g Clase graphics que dibuja todo en la pantalla
     */
    @Override
    public void dibujar(final Graphics g) {
        panel.dibujar(g);
        g.setColor(Color.BLACK);
        g.setFont(g.getFont().deriveFont(55f));
        g.drawString("Menu de pausa", 280, 200);
        botonSeguirJugando.dibujar(g);
        botonSalir.dibujar(g);

    }

    /**
     * Actualiza la interfaz
     */
    @Override
    public void actualizar() {
        botonSeguirJugando.actualizar();
        botonSalir.actualizar();
        salir = botonSalir.isClicked();
        jugar = botonSeguirJugando.isClicked();
    }

    /**
     *
     * @return Si ha presionado jugar
     */
    public boolean isJugar() {
        return jugar;
    }

    /**
     *
     * @return Si ha presionado salir
     */
    public boolean isSalir() {
        return salir;
    }

}
