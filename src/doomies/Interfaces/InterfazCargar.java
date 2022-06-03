/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package doomies.Interfaces;

import doomies.Gestores.GestorEstados;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import doomies.HerramientasEntradaSalida.Teclado;
import doomies.HerramientasEntradaSalida.Mouse;
import doomies.Interfaces.Elementos.Boton;
import doomies.Partida;
import java.awt.Frame;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Interfaz de cargado de partidas, tendra boton derecha, izquierda y cargar
 *
 * @author Lena
 * @version 4
 * @since 2
 */
public class InterfazCargar extends Interfaz {//Clase para la parte VISUAL

    /**
     * Dice si va a salir o no de la pantalla de carga
     */
    private boolean salir;
    /**
     * Posicion actual entre las partidas
     */
    private int index;
    /**
     * Boton de cargar partida
     */
    private final Boton botonCargar;
    /**
     * Boton para desplazarse -1 en las partidas
     */
    private final Boton botonIzquierda;
    /**
     * Boton para desplazarse +1 en las partidas
     */
    private final Boton botonDerecha;
    /**
     * Boton que muestra los datos de las partidas
     */
    private final Boton botonDatos;

    /**
     * Crea una interfaz de cargado de partida
     *
     * @param WIDTH Altura de pantalla
     * @param HEIGHT Anchura de pantalla
     * @param teclado Teclado que usa la interfaz
     * @param raton Raton que usa la interfaz
     */
    public InterfazCargar(final int WIDTH, final int HEIGHT, final Teclado teclado, final Mouse raton) {
        super(WIDTH, HEIGHT, teclado, raton);
        botonCargar = new Boton(450, 550, 400, 70, "CARGAR PARTIDA", font.deriveFont(20f), Color.gray, 2, 4, raton);
        botonCargar.setFormat(10);
        botonIzquierda = new Boton(((WIDTH / 4) - 50), 300, 100, 100, "<", font.deriveFont(100f), Color.GRAY, 17, 1, raton);
        botonIzquierda.setFormat(15);
        botonDerecha = new Boton((((WIDTH / 4) * 3) - 50), 300, 100, 100, ">", font.deriveFont(100f), Color.GRAY, 16, 1, raton);
        botonDerecha.setFormat(15);
        botonDatos = new Boton(425, 150, 450, 350, "", font.deriveFont(30f), Color.orange, 27, 35, raton); //425,150,450,350
        botonDatos.setFormat(40);
        botonDatos.setLabel("");
    }

    /**
     * Dibuja la interfaz para que se vea cargar, izquierda y derecha
     *
     * @param g Clase graphics que dibuja todo en la pantalla
     */
    @Override
    public void dibujar(final Graphics g) {
        /**
         * Crea un gradiente de fondo de pantalla
         */
        Graphics2D g2d = (Graphics2D) g;
        GradientPaint verticalGradient = new GradientPaint(0, 0, Color.RED, 0, this.HEIGHT, Color.ORANGE);
        g.setColor(Color.red);
        g2d.setPaint(verticalGradient);
        g2d.fillRect(0, 0, this.WIDTH, this.HEIGHT);
        /**
         * Titulo de CARGAR PARTIDA
         */
        g.setColor(Color.BLACK);
        g.setFont(font);
        g.setFont(g.getFont().deriveFont(55f));
        g.drawString("CARGAR PARTIDA", ((WIDTH / 2) - (g.getFont().getSize() * (("CARGAR PARTIDA").length() / 2))), 100);

        /**
         * Dibuja los botones, el izquierdo no se muestra si esta en la posicion
         * minima, el derecho tampoco si esta en la posicion maxima
         */
        if (index > 0) {
            botonIzquierda.dibujar(g);
        }
        if (index < GestorEstados.partidas.size() - 1) {
            botonDerecha.dibujar(g);
        }
        botonCargar.dibujar(g);
        botonDatos.dibujar(g);
        dibujarPartidaActual(g);
    }

    /**
     * Dibuja la partida en la que se encuentra
     *
     * @param g Clase graphics que dibuja todo en la pantalla
     */
    private void dibujarPartidaActual(final Graphics g) {
        if (GestorEstados.partidas.size() <= 0) {//Si las partida no existe no mostrar informacion
            return;
        }
        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(20f));
        String nombre = GestorEstados.partidas.get(index).split(";")[0];
        String niveles = GestorEstados.partidas.get(index).split(";")[1].replaceAll("\\{|\\}", "");
        int nivelMaximo = niveles.split(";").length;
        String fecha = GestorEstados.partidas.get(index).split(";")[2];
        g.drawString("Nombre: " + nombre, 450, 225);
        g.drawString("\nNivel alcanzado: " + nivelMaximo, 450, 325);
        g.drawString("\nFecha: " + fecha, 450, 425);
    }

    /**
     * Actualiza la interfaz pasando por todos sus botones y elementos
     */
    public void actualizar() {
        if (index > 0) {//En el indice 0 no puede haber menos asi que el boton izquierdo no se muestra ni se actualiza
            botonIzquierda.actualizar();
        } else {
            botonIzquierda.setClicked(false);
        }
        if (index < GestorEstados.partidas.size() - 1) {//En el indice de partida maxima, no puede haber nada mas hacia la derecha asi que el boton derecho no se actualiza
            botonDerecha.actualizar();
        } else {
            botonDerecha.setClicked(false);
        }
        botonCargar.actualizar();
        if (botonIzquierda.isClicked() || botonDerecha.isClicked()) {
            try {
                index += (botonDerecha.isClicked()) ? 1 : -1;
                String error = GestorEstados.partidas.get(index);
            } catch (Exception e) {
                index += (botonDerecha.isClicked()) ? -1 : 1;
            }
        }
        //Pulsa el boton de cargar y se sale
        if (botonCargar.isClicked()) {
            this.salir = cargar();
        }
    }

    /**
     *
     *
     * @return Si se ha cargado con exito
     */
    private boolean cargar() {
        if (GestorEstados.partidas.size() <= 0) {
            return false;
        }
        String nombre = GestorEstados.partidas.get(index).split(";")[0];
        String tiempos[] = GestorEstados.partidas.get(index).split(";")[1].replaceAll("\\{|\\}", "").split(",");
        ArrayList<Integer> times = new ArrayList<Integer>();
        for (int i = 0; i < tiempos.length; i++) {
            if (tiempos[i].equals("")) {
                continue;
            }
            times.add(Integer.parseInt(tiempos[i]));
        }

        /**
         * {tiempo1,tiempo2...}
         */
        GestorEstados.partida = new Partida(nombre, times);
        JOptionPane.showMessageDialog(new Frame(), "¡Partida cargada con exito!\n");
        return true;
    }

    /**
     *
     * @return Si va a salir de la interfaz
     */
    public boolean isSalir() {
        return this.salir;
    }
}
