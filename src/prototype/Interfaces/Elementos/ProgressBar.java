/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prototype.Interfaces.Elementos;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Víctor Zero
 */
public class ProgressBar {

    public volatile int progreso;//El porcentaje que te muestra
    private int x;
    private int y;
    private int width;
    private int height;

    /**
     *
     * @param x Donde estara la progressBar
     * @param y Donde estara la progressBar
     * @param width Hasta donde llegara AL FINAL
     * @param height La altura de la progressBar
     */
    public ProgressBar(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void dibujar(Graphics g) {
        g.setFont(g.getFont().deriveFont(height / 5 * 1f));
        g.setColor(Color.BLUE);//COLOR DEL PROGRESS BAR CAMBIAR SI ES NECESARIO
        g.fillRect(x, y, (width / 100) * progreso, height);//Dibujo el progreso actual
        g.setColor(Color.BLACK);//COLOR DE LAS LETRAS CAMBIAR SI ES NECESARIO
        g.drawString(progreso + "%", x + (width / 2) - (((progreso + "%").length() * g.getFont().getSize()) / 3), y + height + g.getFont().getSize());//Dibujo el numero del progreso
    }
}
