/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package doomies.Interfaces;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import doomies.HerramientasEntradaSalida.Mouse;
import doomies.HerramientasEntradaSalida.Teclado;
import doomies.Interfaces.Elementos.Boton;

/**
 * Interfaz de cargado y guardado, es la interfaz general que desencadena en
 * todas las demas opciones
 *
 * @see doomies.Gestores.GestorMenu#interActual
 * @author Lena
 * @version 4
 * @since 2
 */
public class InterfazCargaGuarda extends Interfaz {

    /**
     * Boton de jugar
     */
    private final Boton botonJugar;
    /**
     * Boton de cargar
     */
    private final Boton botonCargar;
    /**
     * Boton de guardar
     */
    private final Boton botonGuardar;
    /**
     * Boton de salir
     */
    private final Boton botonSalir;
    /**
     * Boton de opciones
     */
    private final Boton botonOpciones;
    /**
     * Se he pulsado jugar
     */
    private boolean jugar = false;
    /**
     * Se he pulsado cargar
     */
    private boolean cargar = false;
    /**
     * Se he pulsado guardar
     */
    private boolean guardar = false;
    /**
     * Se he pulsado opciones
     */
    private boolean opciones = false;
    /**
     * Se he pulsado salir
     */
    private boolean salir = false;

    /**
     * Crea una interfaz de menu principal, con jugar, cargar, guardar, opciones
     * y salir
     *
     * @param WIDTH Altura de la interfaz
     * @param HEIGHT Anchura de la interfaz
     * @param teclado Teclado que podra usar la interfaz
     * @param raton Raton que usaran los botones de la interfaz
     */
    public InterfazCargaGuarda(final int WIDTH, final int HEIGHT, final Teclado teclado, final Mouse raton) {
        super(WIDTH, HEIGHT, teclado, raton);
        botonJugar = new Boton(450, 80, 400, 70, "JUGAR", font.deriveFont(20f), Color.gray, -10, 4, raton);
        botonCargar = new Boton(450, 220, 400, 70, "CARGAR PARTIDA", font.deriveFont(20f), Color.gray, 2, 4, raton);
        botonGuardar = new Boton(450, 340, 400, 70, "GUARDAR PARTIDA", font.deriveFont(20f), Color.gray, 3, 4, raton);
        botonOpciones = new Boton(450, 480, 400, 70, "OPCIONES", font.deriveFont(20f), Color.gray, -7, 4, raton);
        botonSalir = new Boton(450, 620, 400, 70, "SALIR", font.deriveFont(20f), Color.gray, -10, 4, raton);
    }

    /**
     * Dibuja la interfaz para que se vea jugar, cargar, guardar ,opciones y
     * salir
     *
     * @param g Clase graphics que dibuja todo en la pantalla
     */
    @Override
    public void dibujar(final Graphics g) {
        /**
         * Dibuja un gradiente de fondo de pantalla
         */
        Graphics2D g2d = (Graphics2D) g;
        GradientPaint verticalGradient = new GradientPaint(0, 0, Color.RED, 0, this.HEIGHT, Color.ORANGE);
        g.setColor(Color.red);
        g2d.setPaint(verticalGradient);
        g2d.fillRect(0, 0, this.WIDTH, this.HEIGHT);

        /**
         * Dibuja los botones que van por encima
         */
        botonJugar.dibujar(g);
        botonCargar.dibujar(g);
        botonGuardar.dibujar(g);
        botonOpciones.dibujar(g);
        botonSalir.dibujar(g);
    }

    @Override
    public void actualizar() {
        /**
         * Actualizacion de botones
         */
        botonJugar.actualizar();
        botonCargar.actualizar();
        botonGuardar.actualizar();
        botonOpciones.actualizar();
        botonSalir.actualizar();
        /**
         * Definicion de estados
         */
        if (botonJugar.isClicked()) {
            jugar = true;
        }

        if (botonCargar.isClicked()) {
            cargar = true;
        }

        if (botonGuardar.isClicked()) {
            guardar = true;
        }
        if (botonOpciones.isClicked()) {
            opciones = true;
        }
        if (botonSalir.isClicked()) {//Si le ha dado a salir se termina la ejecucion
            System.exit(0);
        }

    }

    /**
     *
     * @return Si se ha dado al boton jugar
     */
    public boolean isJugar() {
        return jugar;
    }

    /**
     *
     * @return Si se ha dado al boton cargar
     */
    public boolean isCargar() {
        return cargar;
    }

    /**
     *
     * @return Si se ha dado al boton Guardar
     */
    public boolean isGuardar() {
        return guardar;
    }

    /**
     *
     * @return Si se ha dado al boton de Opciones
     */
    public boolean isOpciones() {
        return opciones;
    }

}
