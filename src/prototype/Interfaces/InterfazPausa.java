/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prototype.Interfaces;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import prototype.HerramientasEntradaSalida.LoadTools;
import prototype.HerramientasEntradaSalida.Mouse;
import prototype.HerramientasEntradaSalida.Teclado;
import prototype.Interfaces.Elementos.Boton;

/**
 *
 * @author Víctor Zero
 */
public class InterfazPausa extends Interfaz {

    private final int WIDTH;
    private final int HEIGHT;
    private final Font font = LoadTools.loadFont("/fonts/kongtext.ttf");
    //private Font fuente = new Font("Arial", Font.BOLD,48);
    private Boton panel;
    private Boton botonSeguirJugando;
    private Boton botonSalir;
    private boolean jugar = false;
    private boolean salir = false;

    public InterfazPausa(int WIDTH, int HEIGHT, Teclado teclado, Mouse raton) {
        super(WIDTH, HEIGHT, teclado, raton);
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        panel = new Boton(((WIDTH / 2) - 400), ((HEIGHT / 2) - 300), 800, 600, " ", font.deriveFont(23f),new Color(249,90,9), 4, 2, raton);
        panel.setFormat(40);
        botonSeguirJugando = new Boton(450, 300, 400, 100, "SEGUIR JUGANDO", font.deriveFont(24f), Color.GRAY, 7, 4, raton);
        botonSalir = new Boton(450, 450, 400, 100, "SALIR", font.deriveFont(28f), Color.GRAY, -3, 4, raton);
    }

    @Override
    public void dibujar(Graphics g) {
        panel.dibujar(g);
        g.setColor(Color.BLACK);
        g.setFont(g.getFont().deriveFont(55f));
        g.drawString("Menu de pausa", 280, 200);
        botonSeguirJugando.dibujar(g);
        botonSalir.dibujar(g);

    }

    @Override
    public void actualizar() {
        botonSeguirJugando.actualizar();
        botonSalir.actualizar();
        salir = botonSalir.isClicked();
        jugar = botonSeguirJugando.isClicked();
    }

    public boolean isJugar() {
        return jugar;
    }

    public boolean isSalir() {
        return salir;
    }

}
