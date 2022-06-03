/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package doomies.Interfaces;

import java.awt.Color;
import java.awt.Frame;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;
import doomies.Gestores.GestorEstados;
import doomies.HerramientasEntradaSalida.LoadTools;
import doomies.HerramientasEntradaSalida.Mouse;
import doomies.HerramientasEntradaSalida.Teclado;
import doomies.Interfaces.Elementos.Boton;

/**
 * Interfaz de seleccion de niveles
 *
 * @author Nestor
 * @version 4
 * @since 2
 */
public class InterfazSeleccNiveles extends Interfaz {

    /**
     * Boton de jugar
     */
    private Boton botonJugar;
    /**
     * Boton para desplazarse a la izquierda en los niveles -1
     */
    private Boton botonIzquierda;
    /**
     * Boton para desplazarse a la derecha en los niveles +1
     */
    private Boton botonDerecha;
    /**
     * Imagen de candado desbloqueado
     */
    private final BufferedImage unlocked = LoadTools.loadImage("/images/lock1.png");
    /**
     * Imagen de candado bloqueado
     */
    private final BufferedImage locked = LoadTools.loadImage("/images/lock0.png");
    /**
     * Indica si se quiere jugar el nivel
     */
    private boolean jugar;
    /**
     * Indica el nivel al cual se apunta
     */
    private static int nivel = 1;
    /**
     * Indica el nivel maximo al que se puede llegar
     */
    private final static int LVL_MAX = LoadTools.countMap("/mapas");

    /**
     * Crea la interfaz de seleccion de niveles con sus botones jugar,
     * izquierda, derecha
     *
     * @param WIDTH Altura de la interfaz
     * @param HEIGHT Anchura de la interfaz
     * @param teclado Teclado que podra usar la interfaz
     * @param raton Raton que usaran los botones de la interfaz
     */
    public InterfazSeleccNiveles(final int WIDTH, final int HEIGHT, final Teclado teclado, final Mouse raton) {
        super(WIDTH, HEIGHT, teclado, raton);
        //Creacion de botones
        botonJugar = new Boton(((WIDTH / 2) - 137), 550, 275, 100, "JUGAR", font.deriveFont(46f), Color.GRAY, 8, 3, raton);
        botonJugar.setFormat(10);
        botonIzquierda = new Boton(((WIDTH / 4) - 50), 300, 100, 100, "<", font.deriveFont(100f), Color.GRAY, 17, 1, raton);
        botonIzquierda.setFormat(15);
        botonDerecha = new Boton((((WIDTH / 4) * 3) - 50), 300, 100, 100, ">", font.deriveFont(100f), Color.GRAY, 16, 1, raton);
        botonDerecha.setFormat(15);
    }

    /**
     * Dibuja la interfaz de seleccion de niveles
     *
     * @param g Clase graphics que dibuja todo en la pantalla
     */
    @Override
    public void dibujar(final Graphics g) {
        /**
         * Fondo con gradiente
         */
        Graphics2D g2d = (Graphics2D) g;
        GradientPaint verticalGradient = new GradientPaint(0, 0, Color.RED, 0, this.HEIGHT, Color.ORANGE);
        g.setColor(Color.red);
        g2d.setPaint(verticalGradient);
        g2d.fillRect(0, 0, this.WIDTH, this.HEIGHT);
        /**
         * Titulo
         */
        g.setColor(Color.BLACK);
        g.setFont(font);
        g.setFont(g.getFont().deriveFont(55f));
        g.drawString("SELECTOR DE NIVELES", ((WIDTH / 2) - (g.getFont().getSize() * (("SELECTOR DE NIVELES").length() / 2))), 100);
        /**
         * Nivel
         */
        if (GestorEstados.partida.isDesbloqueado(nivel)) {
            mostrarNivelDesbloqueado(g);
        } else {
            mostrarNivelBloqueado(g);
        }
        /**
         * Botones
         */
        botonJugar.dibujar(g);
        if (nivel != 1) {
            botonIzquierda.dibujar(g);
        }
        if (nivel != LVL_MAX) {
            botonDerecha.dibujar(g);
        }
        mostrarMejorTiempo(g);
    }

    /**
     * Actualiza la interfaz de seleccion de nivel,detectando si se ha pulsado
     * algun boton
     */
    public void actualizar() {
        /**
         * Actualizacion de botones izquierda derecha
         */
        if (nivel != 1) {
            botonIzquierda.actualizar();
        } else {
            botonIzquierda.setClicked(false);
        }
        if (nivel != LVL_MAX) {
            botonDerecha.actualizar();
        } else {
            botonDerecha.setClicked(false);
        }
        botonJugar.actualizar();
        /**
         * Control y cambio de label de nivel
         */
        if (botonIzquierda.isClicked() || botonDerecha.isClicked()) {
            nivel += (botonDerecha.isClicked()) ? 1 : -1;
            if (nivel < 1 || nivel > LVL_MAX) {
                nivel = (nivel < 1) ? 1 : LVL_MAX;
            }
        }
        /**
         * Actualiazcion de boton jugar
         */
        if (botonJugar.isClicked() && GestorEstados.partida.isDesbloqueado(nivel)) {
            this.jugar = true;
        } else if (botonJugar.isClicked()) {
            JOptionPane.showMessageDialog(new Frame(), "Nivel bloqueado, juega el nivel anterior para continuar", "ERROR", 2);

        }
    }

    /**
     * Dibuja el numero actual de nivel mas oscuro como "bloqueado"
     *
     * @param g Clase graphics que dibuja todo en la pantalla
     */
    private void mostrarNivelBloqueado(final Graphics g) {
        g.setColor(LoadTools.brighter(Color.GRAY, 20));
        g.setFont(g.getFont().deriveFont(120f));
        g.drawString(nivel + "", (int) ((WIDTH / 2) - (g.getFont().getSize() * ((nivel + "").length() / 2) * 0.5) - 46), (HEIGHT / 2) - (g.getFont().getSize() / 2) + 80);
        g.drawImage(locked, (HEIGHT / 2) - (g.getFont().getSize() / 2) + 80, (int) ((WIDTH / 2) + (g.getFont().getSize() * ((nivel + "").length() / 2) * 0.5) - 46), null);

    }

    /**
     * Dibuja el numero actual de nivel
     *
     * @param g Clase graphics que dibuja todo en la pantalla
     */
    private void mostrarNivelDesbloqueado(final Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(120f));
        g.drawString(nivel + "", (int) ((WIDTH / 2) - (g.getFont().getSize() * ((nivel + "").length() / 2) * 0.5) - 46), (HEIGHT / 2) - (g.getFont().getSize() / 2) + 80);
        g.drawImage(unlocked, (HEIGHT / 2) - (g.getFont().getSize() / 2) + 80, (int) ((WIDTH / 2) + (g.getFont().getSize() * ((nivel + "").length() / 2) * 0.5) - 46), null);
    }

    /**
     * Dibuja el mejor tiempo en el nivel actual
     *
     * @param g Clase graphics que dibuja todo en la pantalla
     */
    private void mostrarMejorTiempo(final Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(30f));
        try {
            int timeInSeconds = GestorEstados.partida.getNivelesDesbloqueados().get(nivel - 1);
            String formatTime = String.format("Record: %d:%02d:%02d", timeInSeconds / 3600, (timeInSeconds % 3600) / 60, (timeInSeconds % 60));
            g.drawString(formatTime, 400, 500);
        } catch (Exception e) {

        }
    }

    /**
     *
     * @return Si se ha pulsado jugar
     */
    public boolean isJugar() {
        return jugar;
    }

    /**
     *
     * @return Devuelve el nivel actual
     */
    public int getNivel() {
        return this.nivel;
    }
}
