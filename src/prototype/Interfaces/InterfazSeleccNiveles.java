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
public class InterfazSeleccNiveles implements Interfaz {

    private final int WIDTH;
    private final int HEIGHT;
    private final Font font = LoadTools.loadFont("/fonts/kongtext.ttf");
    private Boton botonJugar;
    private Boton botonIzquierda;
    private Boton botonDerecha;
    public boolean derecha;
    public boolean izquierda;
    private boolean pulsado;
    private int nivel;
    private final int LVL_MAX = 10;

    public InterfazSeleccNiveles(final int WIDTH, final int HEIGHT, final Teclado teclado, final Mouse mouse) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        nivel = 1;
        botonJugar = new Boton(((WIDTH / 2) - 137), 550, 275, 100, "JUGAR", font.deriveFont(46f), Color.GRAY, 8, 3, mouse);
        botonJugar.setFormat(10);
        botonIzquierda = new Boton(((WIDTH / 4) - 50), 300, 100, 100, "<", font.deriveFont(100f), Color.GRAY, 17, 1, mouse);
        botonIzquierda.setFormat(15);
        botonDerecha = new Boton((((WIDTH / 4) * 3) - 50), 300, 100, 100, ">", font.deriveFont(100f), Color.GRAY, 16, 1, mouse);
        botonDerecha.setFormat(15);
        //mitad pantalla y mitad de la mitad
    }

    @Override
    public void dibujar(final Graphics g) {

        //gradiente
        Graphics2D g2d = (Graphics2D) g;
        GradientPaint verticalGradient = new GradientPaint(0, 0, Color.RED, 0, this.HEIGHT, Color.ORANGE);
        g.setColor(Color.red);
        g2d.setPaint(verticalGradient);
        g2d.fillRect(0, 0, this.WIDTH, this.HEIGHT);
        g.setColor(Color.BLACK);
        g.setFont(font);
        g.setFont(g.getFont().deriveFont(55f));
        g.drawString("SELECTOR DE NIVELES", ((WIDTH / 2) - (g.getFont().getSize() * (("SELECTOR DE NIVELES").length() / 2))), 100);
        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(120f));
        g.drawString(nivel + "", (int) ((WIDTH / 2) - (g.getFont().getSize() * ((nivel + "").length() / 2) * 0.5) - 46), (HEIGHT / 2) - (g.getFont().getSize() / 2) + 80);
        botonJugar.dibujar(g);
        botonIzquierda.dibujar(g);
        botonDerecha.dibujar(g);

    }

    public void actualizar() {
        botonIzquierda.actualizar();
        botonDerecha.actualizar();
        botonJugar.actualizar();
        if (botonIzquierda.isClicked() ||  botonDerecha.isClicked()) {
            nivel += (botonDerecha.isClicked())? 1:-1;
            if (nivel < 1 || nivel>LVL_MAX) {
                nivel=(nivel<1)?1:LVL_MAX;
            }
        } 
    }
}
