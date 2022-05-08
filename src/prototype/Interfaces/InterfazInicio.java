/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prototype.Interfaces;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import prototype.HerramientasEntradaSalida.LoadTools;
import prototype.HerramientasEntradaSalida.Mouse;
import prototype.HerramientasEntradaSalida.Teclado;
import prototype.Interfaces.Elementos.Boton;

/**
 *
 * @author Víctor Zero
 */
public class InterfazInicio implements Interfaz {

    private final int WIDTH;
    private final int HEIGHT;
    private int x = 0;
    private boolean right = true;
    private final Font font = LoadTools.loadFont("/fonts/kongtext.ttf");
    private final BufferedImage background = LoadTools.loadImage("/images/BG.png");
    private final BufferedImage logo = LoadTools.loadImage("/images/Logo.png");
    //private Font fuente = new Font("Arial", Font.BOLD,48);
    private Boton botonStart;
    private boolean start;
    public InterfazInicio(final int WIDTH, final int HEIGHT,final Teclado teclado, final Mouse raton) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        start=false;
        botonStart = new Boton(400, 600, 480, 84, "START", font.deriveFont(30f), Color.gray, -10, 4, raton);
    }

    public void dibujar(final Graphics g) {

        //FONDO
        g.drawImage(background, x, -200, null);
        //FONDO    

        //LOGO
        g.drawImage(logo, 355, 0, null);
        //LOGO
        botonStart.dibujar(g);

    }

    //Animacion
    public void actualizar() {
        botonStart.actualizar();
        //control de la posicion de la imagen
        //Limite derecho
        if (x == 0) {
            right = true;
        } //limite izquierdo
        else if (x == -720) {
            right = false;
        }
        //movimiento de la imagen hacia la derecha
        if (right == true) {
            x--;
        } //movimiento de la imagen hacia la izquierda
        else if (right == false) {
            x++;
        }
        if (botonStart.isClicked()) {
            start=true;
        }
    }

    public boolean isStart() {
        return start;
    }
}
