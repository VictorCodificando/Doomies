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
import prototype.HerramientasEntradaSalida.Teclado;
import prototype.HerramientasEntradaSalida.Mouse;
import prototype.Interfaces.Elementos.Boton;

/**
 *
 * @author Víctor Zero
 */
public class InterfazCargar extends Interfaz {//Clase para la parte VISUAL

    private final int WIDTH;
    private final int HEIGHT;
    private final Font font = LoadTools.loadFont("fonts/kongtext.ttf");
    //private final Image imagen = LoadTools.loadImage("img/mapa.png");
    //private Boton botonCargar;
    //private ProgressBar pb;
    //private ThreadProgreso tp;
    private boolean cargar;
    private Boton botonCargar;
    private Boton botonIzquierda;
    private Boton botonDerecha;
    private Boton botonDatos;
    private final int LVL_MAX = 10;
    private int partidas;

    public InterfazCargar(final int WIDTH, final int HEIGHT, final Teclado teclado, final Mouse raton) {
        super(WIDTH, HEIGHT, teclado, raton);
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.partidas = 1;
        botonCargar = new Boton(450, 550, 400, 70, "CARGAR PARTIDA", font.deriveFont(20f), Color.gray, 2, 4, raton);
        botonCargar.setFormat(10);
        botonIzquierda = new Boton(((WIDTH / 4) - 50), 300, 100, 100, "<", font.deriveFont(100f), Color.GRAY, 17, 1, raton);
        botonIzquierda.setFormat(15);
        botonDerecha = new Boton((((WIDTH / 4) * 3) - 50), 300, 100, 100, ">", font.deriveFont(100f), Color.GRAY, 16, 1, raton);
        botonDerecha.setFormat(15);
        botonDatos = new Boton(425,150,450,350, "",font.deriveFont(30f), Color.orange, 27,35, raton); //425,150,450,350
        botonDatos.setFormat(40);
        botonDatos.setLabel("DATOS///NOMBRE///NIVELES");
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
        //Titulo
        g.setColor(Color.BLACK);
        g.setFont(font);
        g.setFont(g.getFont().deriveFont(55f));
        g.drawString("CARGAR PARTIDA", ((WIDTH / 2) - (g.getFont().getSize() * (("CARGAR PARTIDA").length() / 2))), 100);
        g.drawString(partidas + "", (int) ((WIDTH / 2) - (g.getFont().getSize() * ((partidas + "").length() / 2) * 0.5) - 46), (HEIGHT / 2) - (g.getFont().getSize() / 2) + 80);
        
        //Botones
        botonIzquierda.dibujar(g);
        botonDerecha.dibujar(g);
        botonCargar.dibujar(g);
        botonDatos.dibujar(g);
    }

    //MENU
    public void actualizar() {
        
        botonIzquierda.actualizar();
        botonDerecha.actualizar();
        botonCargar.actualizar();

        if (botonIzquierda.isClicked() || botonDerecha.isClicked()) {
            partidas += (botonDerecha.isClicked()) ? 1 : -1;//comparar id de partida guardada en la bbdd o fichero
            if (partidas < 1 || partidas > LVL_MAX) {
                partidas = (partidas < 1) ? 1 : LVL_MAX;
            }
}
        
        //cargar
        if (botonCargar.isClicked()) {
            this.cargar = true;
        }
    }

    public boolean isCargar() {
        return cargar;
    }

    public int getPartida() {
        return this.partidas;
    }
}
