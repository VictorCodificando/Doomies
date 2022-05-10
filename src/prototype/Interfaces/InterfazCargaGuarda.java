/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prototype.Interfaces;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import prototype.HerramientasEntradaSalida.LoadTools;
import prototype.HerramientasEntradaSalida.Mouse;
import prototype.HerramientasEntradaSalida.Teclado;
import prototype.Interfaces.Elementos.Boton;

/**
 *
 * @author Víctor Zero
 */
public class InterfazCargaGuarda extends Interfaz {
    /**
     * Definicion de variables
     */
    
    private final Boton botonJugar;
    private final Boton botonCargar;
    private final Boton botonGuardar;
    private final Boton botonSalir;
    private boolean jugar = false;
    private boolean cargar = false;
    private boolean guardar = false;
    private boolean salir = false;

    /**
     *
     * @param WIDTH Altura de la interfaz
     * @param HEIGHT Anchura de la interfaz
     * @param teclado Teclado que podra usar la interfaz
     * @param raton Raton que usaran los botones de la interfaz
     */
    public InterfazCargaGuarda(final int WIDTH, final int HEIGHT, final Teclado teclado, final Mouse raton) {
        super(WIDTH,HEIGHT,teclado,raton);
        botonJugar = new Boton(450, 100, 400, 70, "JUGAR", font.deriveFont(20f), Color.gray, -10, 4, raton);
        botonCargar = new Boton(450, 250, 400, 70, "CARGAR PARTIDA", font.deriveFont(20f), Color.gray, 2, 4, raton);
        botonGuardar = new Boton(450, 400, 400, 70, "GUARDAR PARTIDA", font.deriveFont(20f), Color.gray, 3, 4, raton);
        botonSalir = new Boton(450, 550, 400, 70, "SALIR", font.deriveFont(20f), Color.gray, -10, 4, raton);
    }

    @Override
    public void dibujar(final Graphics g) {
        //gradiente
        Graphics2D g2d = (Graphics2D) g;
        GradientPaint verticalGradient = new GradientPaint(0, 0, Color.RED, 0, this.HEIGHT, Color.ORANGE);
        g.setColor(Color.red);
        g2d.setPaint(verticalGradient);
        g2d.fillRect(0, 0, this.WIDTH, this.HEIGHT);

        //Botones
        botonJugar.dibujar(g);
        botonCargar.dibujar(g);
        botonGuardar.dibujar(g);
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

        if (botonSalir.isClicked()) {
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

}
