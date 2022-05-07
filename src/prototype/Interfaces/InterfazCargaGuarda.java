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
public class InterfazCargaGuarda implements Interfaz {

    private final int WIDTH;
    private final int HEIGHT;
    private final Font font = LoadTools.loadFont("fonts/kongtext.ttf");
    /*    private final Image imagen = LoadTools.loadImage("img/mapa.png");*/
    private final Boton botonJugar;
    private final Boton botonCargar;
    private final Boton botonGuardar;
    private final Boton botonSalir;
    private boolean jugar = false;
    private boolean cargar = false;
    private boolean guardar = false;
    private boolean salir = false;

    public InterfazCargaGuarda(final int WIDTH, final int HEIGHT, final Teclado teclado, final Mouse raton) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        botonJugar = new Boton(450, 100, 400, 70, "JUGAR", font.deriveFont(20f), Color.gray, -10, 4, raton);
        botonCargar = new Boton(450, 250, 400, 70, "CARGAR PARTIDA", font.deriveFont(20f), Color.gray, 2, 4, raton);
        botonGuardar = new Boton(450, 400, 400, 70, "GUARDAR PARTIDA", font.deriveFont(20f), Color.gray, 3, 4, raton);
        botonSalir = new Boton(450, 550, 400, 70, "SALIR", font.deriveFont(20f), Color.gray, -10, 4, raton);
    }

    @Override
    public void dibujar(final Graphics g) {

        //FONDO IMAGEN
        //g.drawImage(imagen, 0, 0, null);
        //FONDO IMAGEN     
        //gradiente
        Graphics2D g2d = (Graphics2D) g;
        GradientPaint verticalGradient = new GradientPaint(0, 0, Color.RED, 0, this.HEIGHT, Color.ORANGE);
        g.setColor(Color.red);
        g2d.setPaint(verticalGradient);
        g2d.fillRect(0, 0, this.WIDTH, this.HEIGHT);
        //gradiente

        //g.setColor(Color.red);
        //g.fillRect(0, 0, this.WIDTH, this.HEIGHT);
        botonJugar.dibujar(g);
        botonCargar.dibujar(g);
        botonGuardar.dibujar(g);
        botonSalir.dibujar(g);

    }

    //MENU
    @Override
    public void actualizar() {
        botonJugar.actualizar();
        botonCargar.actualizar();
        botonGuardar.actualizar();
        botonSalir.actualizar();
        if (botonJugar.isClicked()) {
            jugar = true;
        }

        if (botonCargar.isClicked()) {
            cargar = true;
        }

        if (botonGuardar.isClicked()) {
            guardar = true;
        }

        if (botonSalir.isClicked()) {
            salir = true;
        }

    }

}
