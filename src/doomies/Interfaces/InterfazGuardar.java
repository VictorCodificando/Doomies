/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
<<<<<<< HEAD:src/doomies/Interfaces/InterfazGuardar.java
package doomies.Interfaces;

import doomies.Gestores.GestorEstados;
=======
package prototype.Interfaces;

>>>>>>> origin/Nestor:src/prototype/Interfaces/InterfazGuardar.java
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
<<<<<<< HEAD:src/doomies/Interfaces/InterfazGuardar.java
import doomies.HerramientasEntradaSalida.LoadTools;
import doomies.HerramientasEntradaSalida.Teclado;
import doomies.HerramientasEntradaSalida.Mouse;
import doomies.Interfaces.Elementos.Boton;
import java.awt.Frame;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
=======
import prototype.HerramientasEntradaSalida.LoadTools;
import prototype.HerramientasEntradaSalida.Teclado;
import prototype.HerramientasEntradaSalida.Mouse;
import prototype.Interfaces.Elementos.Boton;
>>>>>>> origin/Nestor:src/prototype/Interfaces/InterfazGuardar.java

/**
 *
 * @author Víctor Zero
 */
public class InterfazGuardar extends Interfaz {//Clase para la parte VISUAL

    private final int WIDTH;
    private final int HEIGHT;
<<<<<<< HEAD:src/doomies/Interfaces/InterfazGuardar.java
    private final Font font = LoadTools.loadFont("/fonts/kongtext.ttf");
=======
    private final Font font = LoadTools.loadFont("fonts/kongtext.ttf");
>>>>>>> origin/Nestor:src/prototype/Interfaces/InterfazGuardar.java
    //private final Image imagen = LoadTools.loadImage("img/mapa.png");
    //private Boton botonCargar;
    //private ProgressBar pb;
    //private ThreadProgreso tp;
    private boolean guardar;
<<<<<<< HEAD:src/doomies/Interfaces/InterfazGuardar.java
    private boolean salir = false;
=======
>>>>>>> origin/Nestor:src/prototype/Interfaces/InterfazGuardar.java
    private Boton botonGuardar;
    private Boton botonIzquierda;
    private Boton botonDerecha;
    private Boton botonDatos;
<<<<<<< HEAD:src/doomies/Interfaces/InterfazGuardar.java
    private int index = 0;
=======
>>>>>>> origin/Nestor:src/prototype/Interfaces/InterfazGuardar.java
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
<<<<<<< HEAD:src/doomies/Interfaces/InterfazGuardar.java
        botonDatos = new Boton(425, 150, 450, 350, "", font.deriveFont(30f), Color.orange, 27, 35, raton); //425,150,450,350
        botonDatos.setFormat(40);
        botonDatos.setLabel("");
=======
        botonDatos = new Boton(425,150,450,350, "",font.deriveFont(30f), Color.orange, 27,35, raton); //425,150,450,350
        botonDatos.setFormat(40);
        botonDatos.setLabel("DATOS////NOMBRE");
>>>>>>> origin/Nestor:src/prototype/Interfaces/InterfazGuardar.java
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
<<<<<<< HEAD:src/doomies/Interfaces/InterfazGuardar.java
        /**
         * Titulo
         */
=======
        //Titulo
>>>>>>> origin/Nestor:src/prototype/Interfaces/InterfazGuardar.java
        g.setColor(Color.BLACK);
        g.setFont(font);
        g.setFont(g.getFont().deriveFont(55f));
        g.drawString("GUARDAR PARTIDA", ((WIDTH / 2) - (g.getFont().getSize() * (("GUARDAR PARTIDA").length() / 2))), 100);
        g.drawString(partidasGuardadas + "", (int) ((WIDTH / 2) - (g.getFont().getSize() * ((partidasGuardadas + "").length() / 2) * 0.5) - 46), (HEIGHT / 2) - (g.getFont().getSize() / 2) + 80);
<<<<<<< HEAD:src/doomies/Interfaces/InterfazGuardar.java

=======
        
>>>>>>> origin/Nestor:src/prototype/Interfaces/InterfazGuardar.java
        //Botones
        botonIzquierda.dibujar(g);
        botonDerecha.dibujar(g);
        botonGuardar.dibujar(g);
        botonDatos.dibujar(g);
<<<<<<< HEAD:src/doomies/Interfaces/InterfazGuardar.java
        dibujarPartidaActual(g);
    }

    private void dibujarPartidaActual(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(20f));
        String nombre = GestorEstados.partidas[index].split(";")[0];
        String niveles = GestorEstados.partidas[index].split(";")[1];
        String fecha = GestorEstados.partidas[index].split(";")[2];
        g.drawString("Nombre: " + nombre, 450, 225);
        g.drawString("\nNivel alcanzado: " + niveles, 450, 325);
        g.drawString("\nFecha: " + fecha, 450, 425);
=======
>>>>>>> origin/Nestor:src/prototype/Interfaces/InterfazGuardar.java
    }

    //MENU
    public void actualizar() {
<<<<<<< HEAD:src/doomies/Interfaces/InterfazGuardar.java

        botonIzquierda.actualizar();
        botonDerecha.actualizar();
        botonGuardar.actualizar();
        if (botonIzquierda.isClicked() || botonDerecha.isClicked()) {
            try {
                index += (botonDerecha.isClicked()) ? 1 : -1;//comparar id de partida guardada en la bbdd o fichero
                String error = GestorEstados.partidas[index];
            } catch (Exception e) {
                index += (botonDerecha.isClicked()) ? -1 : 1;
            }
        }
        //cargar
        System.out.println(botonGuardar.isClicked());
        if (botonGuardar.isClicked()) {
            this.salir = guardar();
        }
    }

    private boolean guardar() {
        int niveles = 1;
        while (GestorEstados.partida.isDesbloqueado(niveles)) {
            niveles++;
        }
        niveles--;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        String fecha = dtf.format(now);
        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        String nombre = GestorEstados.partida.getNombre();
        if (GestorEstados.partida.getNombre().equals("default")) {
            nombre = JOptionPane.showInputDialog(jFrame, "Introduce el nombre de la partida:");
        }

        if (nombre == null) {
            return false;
        }
        String linea = nombre + ";" + niveles + ";" + fecha;
        GestorEstados.partidas[index] = linea;
        JOptionPane.showMessageDialog(new Frame(), "¡Partida guardada con exito!\n");
        return true;
    }

=======
        
        botonIzquierda.actualizar();
        botonDerecha.actualizar();
        botonGuardar.actualizar();

        if (botonIzquierda.isClicked() || botonDerecha.isClicked()) {
            partidasGuardadas += (botonDerecha.isClicked()) ? 1 : -1;//comparar id de partida guardada en la bbdd o fichero
            if (partidasGuardadas < 1 || partidasGuardadas > LVL_MAX) {
                partidasGuardadas = (partidasGuardadas < 1) ? 1 : LVL_MAX;
            }
}
        
        //cargar
        if (botonGuardar.isClicked()) {
            this.guardar = true;
        }
    }

>>>>>>> origin/Nestor:src/prototype/Interfaces/InterfazGuardar.java
    public boolean isGuardar() {
        return guardar;
    }

    public int getPartidasGuardadas() {
        return this.partidasGuardadas;
    }
<<<<<<< HEAD:src/doomies/Interfaces/InterfazGuardar.java

    public boolean isSalir() {
        return this.salir;
    }
=======
>>>>>>> origin/Nestor:src/prototype/Interfaces/InterfazGuardar.java
}
