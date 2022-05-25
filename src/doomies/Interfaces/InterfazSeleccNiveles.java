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
 *
 * @author Víctor Zero
 */
public class InterfazSeleccNiveles extends Interfaz {

    private Boton botonJugar;
    private Boton botonIzquierda;
    private Boton botonDerecha;
    private final BufferedImage unlocked = LoadTools.loadImage("/images/lock1.png");
    private final BufferedImage locked = LoadTools.loadImage("/images/lock0.png");
    private boolean jugar;
    private static int nivel = 1;
    private final static int LVL_MAX = LoadTools.countMap("/mapas");

    /**
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

    @Override
    public void dibujar(final Graphics g) {

        //gradiente
        Graphics2D g2d = (Graphics2D) g;
        GradientPaint verticalGradient = new GradientPaint(0, 0, Color.RED, 0, this.HEIGHT, Color.ORANGE);
        g.setColor(Color.red);
        g2d.setPaint(verticalGradient);
        g2d.fillRect(0, 0, this.WIDTH, this.HEIGHT);
        //Titulo
        g.setColor(Color.BLACK);
        g.setFont(font);
        g.setFont(g.getFont().deriveFont(55f));
        g.drawString("SELECTOR DE NIVELES", ((WIDTH / 2) - (g.getFont().getSize() * (("SELECTOR DE NIVELES").length() / 2))), 100);
        //Nivel
        if (GestorEstados.partida.isDesbloqueado(nivel)) {
            mostrarNivelDesbloqueado(g);
        } else {
            mostrarNivelBloqueado(g);
        }

        //Botones
        botonJugar.dibujar(g);
        botonIzquierda.dibujar(g);
        botonDerecha.dibujar(g);

    }

    public void actualizar() {
        //Botones
        botonIzquierda.actualizar();
        botonDerecha.actualizar();
        botonJugar.actualizar();
        //Control y cambio de label nivel
        if (botonIzquierda.isClicked() || botonDerecha.isClicked()) {
            nivel += (botonDerecha.isClicked()) ? 1 : -1;
            if (nivel < 1 || nivel > LVL_MAX) {
                nivel = (nivel < 1) ? 1 : LVL_MAX;
            }
        }
        //Jugar
        if (botonJugar.isClicked() && GestorEstados.partida.isDesbloqueado(nivel)) {
            this.jugar = true;
        } else if (botonJugar.isClicked()) {
            JOptionPane.showMessageDialog(new Frame(), "Nivel bloqueado, juega el nivel anterior para continuar", "ERROR", 2);

        }
    }

    private void mostrarNivelBloqueado(Graphics g) {
        g.setColor(LoadTools.brighter(Color.GRAY, 20));
        g.setFont(g.getFont().deriveFont(120f));
        g.drawString(nivel + "", (int) ((WIDTH / 2) - (g.getFont().getSize() * ((nivel + "").length() / 2) * 0.5) - 46), (HEIGHT / 2) - (g.getFont().getSize() / 2) + 80);
        g.drawImage(locked, (HEIGHT / 2) - (g.getFont().getSize() / 2) + 80, (int) ((WIDTH / 2) + (g.getFont().getSize() * ((nivel + "").length() / 2) * 0.5) - 46), null);
    }

    private void mostrarNivelDesbloqueado(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(120f));
        g.drawString(nivel + "", (int) ((WIDTH / 2) - (g.getFont().getSize() * ((nivel + "").length() / 2) * 0.5) - 46), (HEIGHT / 2) - (g.getFont().getSize() / 2) + 80);
        g.drawImage(unlocked, (HEIGHT / 2) - (g.getFont().getSize() / 2) + 80, (int) ((WIDTH / 2) + (g.getFont().getSize() * ((nivel + "").length() / 2) * 0.5) - 46), null);
    }

    /**
     *
     * @return Si se ha pulsado jugar
     */
    public boolean isJugar() {
        return jugar;
    }

    public int getNivel() {
        return this.nivel;
    }
}
