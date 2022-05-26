/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
<<<<<<< HEAD:src/doomies/Interfaces/Interfaz.java
package doomies.Interfaces;

import java.awt.Font;
import java.awt.Graphics;
import doomies.HerramientasEntradaSalida.LoadTools;
import doomies.HerramientasEntradaSalida.Mouse;
import doomies.HerramientasEntradaSalida.Teclado;
=======
package prototype.Interfaces;

import java.awt.Font;
import java.awt.Graphics;
import prototype.HerramientasEntradaSalida.LoadTools;
import prototype.HerramientasEntradaSalida.Mouse;
import prototype.HerramientasEntradaSalida.Teclado;
>>>>>>> origin/Nestor:src/prototype/Interfaces/Interfaz.java

/**
 *
 * @author Víctor Zero
 */
public abstract class Interfaz {

    protected final int WIDTH;
    protected final int HEIGHT;
    protected final Teclado teclado;
    protected final Mouse raton;
    protected final Font font = LoadTools.loadFont("/fonts/kongtext.ttf");

    public Interfaz(int WIDTH, int HEIGHT, Teclado teclado, Mouse raton) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.teclado = teclado;
        this.raton = raton;
    }

    public abstract void dibujar(final Graphics g);

    public abstract void actualizar();
}
