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
 *
 * @author Víctor Zero
 */
public class InterfazGuardar extends Interfaz {//Clase para la parte VISUAL

    private final int WIDTH;
    private final int HEIGHT;
    private final Font font = LoadTools.loadFont("/fonts/kongtext.ttf");
    //private final Image imagen = LoadTools.loadImage("img/mapa.png");
    //private Boton botonCargar;
    //private ProgressBar pb;
    //private ThreadProgreso tp;
    private boolean guardar;
    private boolean salir = false;
    private Boton botonGuardar;
    private Boton botonIzquierda;
    private Boton botonDerecha;
    private Boton botonDatos;
    private int index = 0;
    private final int LVL_MAX = 10;
    private int partidasGuardadas;

    public InterfazGuardar(final int WIDTH, final int HEIGHT, final Teclado teclado, final Mouse raton) {
        super(WIDTH, HEIGHT, teclado, raton);
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.partidasGuardadas = 1;
        botonGuardar = new Boton(450, 550, 400, 70, "GUARDAR PARTIDA", font.deriveFont(20f), Color.gray, 2, 4, raton);
        botonGuardar.setFormat(10);
        botonIzquierda = new Boton(((WIDTH / 4) - 50), 300, 100, 100, "<", font.deriveFont(100f), Color.GRAY, 17, 1, raton);
        botonIzquierda.setFormat(15);
        botonDerecha = new Boton((((WIDTH / 4) * 3) - 50), 300, 100, 100, ">", font.deriveFont(100f), Color.GRAY, 16, 1, raton);
        botonDerecha.setFormat(15);
        botonDatos = new Boton(425, 150, 450, 350, "", font.deriveFont(30f), Color.orange, 27, 35, raton); //425,150,450,350
        botonDatos.setFormat(40);
        botonDatos.setLabel("");
        //this.datosPartida = //conexionbasededatos o fichero
    }

    //@Override
    public void dibujar(final Graphics g) {

        //gradiente
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
        g.drawString("GUARDAR PARTIDA", ((WIDTH / 2) - (g.getFont().getSize() * (("GUARDAR PARTIDA").length() / 2))), 100);
        g.drawString(partidasGuardadas + "", (int) ((WIDTH / 2) - (g.getFont().getSize() * ((partidasGuardadas + "").length() / 2) * 0.5) - 46), (HEIGHT / 2) - (g.getFont().getSize() / 2) + 80);

        //Botones
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

    private void dibujarPartidaActual(Graphics g) {
        if (GestorEstados.partidas.size() <= 0 || index >= GestorEstados.partidas.size()) {
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

    //MENU
    public void actualizar() {

        if (index > 0) {
            botonIzquierda.actualizar();
        } else {
            botonIzquierda.setClicked(false);
        }
        if (index < GestorEstados.partidas.size()) {
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
                index += (botonDerecha.isClicked()) ? 1 : -1;//comparar id de partida guardada en la bbdd o fichero
                String error = GestorEstados.partidas.get(index);
            } catch (Exception e) {
                index += (botonDerecha.isClicked()) ? -1 : 1;
            }

        }
        //cargar
        if (botonGuardar.isClicked()) {
            this.salir = guardar();
        }
    }

    /**
     *
     * Guarda la partida con el formato: nombre;{tiempo1,...},fechaActual
     *
     * @return
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

    public boolean isGuardar() {
        return guardar;
    }

    public int getPartidasGuardadas() {
        return this.partidasGuardadas;
    }

    public boolean isSalir() {
        return this.salir;
    }
}
