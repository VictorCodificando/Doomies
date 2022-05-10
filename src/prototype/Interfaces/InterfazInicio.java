/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prototype.Interfaces;

import java.awt.Color;
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
public class InterfazInicio extends Interfaz {

    private int x = 0;
    private boolean right = true;
    private final BufferedImage background = LoadTools.loadImage("/images/BG.png");
    private final BufferedImage logo = LoadTools.loadImage("/images/Logo.png");
    private final Boton botonStart;
    private boolean start;

    /**
     *
     * @param WIDTH Altura de la interfaz
     * @param HEIGHT Anchura de la interfaz
     * @param teclado Teclado que podra usar la interfaz
     * @param raton Raton que usaran los botones de la interfaz
     */
    public InterfazInicio(final int WIDTH, final int HEIGHT, final Teclado teclado, final Mouse raton) {
        super(WIDTH,HEIGHT,teclado,raton);
        start = false;
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

    public void actualizar() {
        //Actualizacion de boton
        botonStart.actualizar();
        /**
         * Control de la posicion de la imagen
         */
        //Limite derecho
        if (x == 0) {
            right = true;
        } //limite izquierdo
        else if (x == -720) {
            right = false;
        }
        //Movimiento de la imagen hacia la derecha
        if (right == true) {
            x--;
        } //Movimiento de la imagen hacia la izquierda
        else if (right == false) {
            x++;
        }
        /*
        Si se ha pulsado al boton
         */
        if (botonStart.isClicked()) {
            start = true;
        }
    }

    /**
     *
     * @return Si se ha pulsado el boton Start
     */
    public boolean isStart() {
        return start;
    }
}
