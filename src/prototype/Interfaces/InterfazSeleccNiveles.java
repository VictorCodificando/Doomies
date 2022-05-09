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
public class InterfazSeleccNiveles extends Interfaz {

    private Boton botonJugar;
    private Boton botonIzquierda;
    private Boton botonDerecha;
    private boolean jugar;
    private int nivel;
    private final int LVL_MAX = 10;

    /**
     *
     * @param WIDTH Altura de la interfaz
     * @param HEIGHT Anchura de la interfaz
     * @param teclado Teclado que podra usar la interfaz
     * @param raton Raton que usaran los botones de la interfaz
     */
    public InterfazSeleccNiveles(final int WIDTH, final int HEIGHT, final Teclado teclado, final Mouse raton) {
        super(WIDTH, HEIGHT, teclado, raton);
        //Inicializacion del label del nivel
        this.nivel = 1;
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
        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(120f));
        g.drawString(nivel + "", (int) ((WIDTH / 2) - (g.getFont().getSize() * ((nivel + "").length() / 2) * 0.5) - 46), (HEIGHT / 2) - (g.getFont().getSize() / 2) + 80);
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
        if (botonJugar.isClicked()) {
            this.jugar = true;
        }
    }

    /**
     *
     * @return Si se ha pulsado jugar
     */
    public boolean isJugar() {
        return jugar;
    }
    public int getNivel(){
        return this.nivel;
    }
}
