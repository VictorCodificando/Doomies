/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package doomies.Interfaces;

import doomies.Gestores.GestorEstados;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import doomies.HerramientasEntradaSalida.LoadTools;
import doomies.HerramientasEntradaSalida.Teclado;
import doomies.HerramientasEntradaSalida.Mouse;
import doomies.Interfaces.Elementos.Boton;
import java.awt.Frame;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Interfaz de guardado de mapas
 *
 * @author Lena
 * @version 4
 * @since 2
 */
public class InterfazGuardar extends Interfaz {//Clase para la parte VISUAL

    /**
     * Si va a guardar la partida
     */
    private boolean guardar;
    /**
     * Si va a salir sin guardar la partida
     */
    private boolean salir = false;
    /**
     * Boton de cargar partida
     */
    private Boton botonGuardar;
    /**
     * Boton para desplazarse -1 en las partidas
     */
    private Boton botonIzquierda;
    /**
     * Boton para desplazarse +1 en las partidas
     */
    private Boton botonDerecha;
    /**
     * Boton que muestra los datos de las partidas
     */
    private Boton botonDatos;
    /**
     * Indice en el que se encuentra actualmente
     */
    private int index = 0;

    /**
     * Crea una interfaz de guardado de partida
     *
     * @param WIDTH Altura de pantalla
     * @param HEIGHT Anchura de pantalla
     * @param teclado Teclado que usa la interfaz
     * @param raton Raton que usa la interfaz
     */
    public InterfazGuardar(final int WIDTH, final int HEIGHT, final Teclado teclado, final Mouse raton) {
        super(WIDTH, HEIGHT, teclado, raton);
        botonGuardar = new Boton(450, 550, 400, 70, "GUARDAR PARTIDA", font.deriveFont(20f), Color.gray, 2, 4, raton);
        botonGuardar.setFormat(10);
        botonIzquierda = new Boton(((WIDTH / 4) - 50), 300, 100, 100, "<", font.deriveFont(100f), Color.GRAY, 17, 1, raton);
        botonIzquierda.setFormat(15);
        botonDerecha = new Boton((((WIDTH / 4) * 3) - 50), 300, 100, 100, ">", font.deriveFont(100f), Color.GRAY, 16, 1, raton);
        botonDerecha.setFormat(15);
        botonDatos = new Boton(425, 150, 450, 350, "", font.deriveFont(30f), Color.orange, 27, 35, raton); //425,150,450,350
        botonDatos.setFormat(40);
        botonDatos.setLabel("");
    }

    /**
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
         * Titulo de GUARDAR PARTIDA
         */
        g.setColor(Color.BLACK);
        g.setFont(font);
        g.setFont(g.getFont().deriveFont(55f));
        g.drawString("GUARDAR PARTIDA", ((WIDTH / 2) - (g.getFont().getSize() * (("GUARDAR PARTIDA").length() / 2))), 100);

        /**
         * Dibuja los botones, el izquierdo no se muestra si esta en la posicion
         * minima, el derecho tampoco si esta en la posicion maxima+1
         */
        if (index > 0) {
            botonIzquierda.dibujar(g);
        }
        if (index < GestorEstados.partidas.size()) {
            botonDerecha.dibujar(g);
        }
        botonGuardar.dibujar(g);
        botonDatos.dibujar(g);
        dibujarPartidaActual(g);
    }

    /**
     * Dibuja la partida en la que se encuentra
     *
     * @param g Clase graphics que dibuja todo en la pantalla
     */
    private void dibujarPartidaActual(Graphics g) {
        if (GestorEstados.partidas.size() <= 0 || index >= GestorEstados.partidas.size()) {//Si las partida no existe no mostrar informacion
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
        if (index < GestorEstados.partidas.size()) {//En el indice de partida maxima +1 , no puede haber nada mas hacia la derecha asi que el boton derecho no se actualiza
            botonDerecha.actualizar();
        } else {
            botonDerecha.setClicked(false);
        }
        botonGuardar.actualizar();
        if (botonIzquierda.isClicked() || botonDerecha.isClicked()) {
            System.out.println(index + "  " + GestorEstados.partidas.size());
            if (botonDerecha.isClicked() && index == GestorEstados.partidas.size() - 1) {
                index += 1;
                return;
            }
            try {
                index += (botonDerecha.isClicked()) ? 1 : -1;
                String error = GestorEstados.partidas.get(index);
            } catch (Exception e) {
                index += (botonDerecha.isClicked()) ? -1 : 1;
            }

        }
        //Pulsa el boton de guardar y se sale
        if (botonGuardar.isClicked()) {
            this.salir = guardar();
        }
    }

    /**
     *
     * Guarda la partida con el formato: nombre;{tiempo1,...},fechaActual
     *
     * @return Si se ha cargado con exito
     */
    private boolean guardar() {
        String nivelesTiempos = "{";
        for (int i = 0; i < GestorEstados.partida.getNivelesDesbloqueados().size(); i++) {
            nivelesTiempos += GestorEstados.partida.getNivelesDesbloqueados().get(i) + ",";
        }
        nivelesTiempos += "}";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        String fecha = dtf.format(now);
        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        String nombre = GestorEstados.partida.getNombre();
        if (GestorEstados.partida.getNombre().equals("default")) {
            nombre = JOptionPane.showInputDialog(jFrame, "Introduce el nombre de la partida:");
        }

        if (nombre == null || nombre.equals("") || nombre.equals("default")) {
            return false;
        }
        String linea = nombre + ";" + nivelesTiempos + ";" + fecha;
        try {
            GestorEstados.partidas.set(index, linea);
        } catch (Exception e) {
            GestorEstados.partidas.add(linea);
        }

        JOptionPane.showMessageDialog(new Frame(), "¡Partida guardada con exito!\n");
        return true;
    }

    /**
     *
     * @return Si sale habiendo guardado
     */
    public boolean isGuardar() {
        return guardar;
    }

    /**
     *
     * @return Si sale normal
     */
    public boolean isSalir() {
        return this.salir;
    }
}
