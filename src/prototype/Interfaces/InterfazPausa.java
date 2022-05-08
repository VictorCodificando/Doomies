/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prototype.Interfaces;

import java.awt.Graphics;
import prototype.HerramientasEntradaSalida.Mouse;
import prototype.HerramientasEntradaSalida.Teclado;

/**
 *
 * @author Víctor Zero
 */
public class InterfazPausa extends Interfaz {

    public InterfazPausa(int WIDTH, int HEIGHT, Teclado teclado, Mouse raton) {
        super(WIDTH, HEIGHT, teclado, raton);
    }
    
    

    @Override
    public void dibujar(Graphics g) {
      
    }

    @Override
    public void actualizar() {
      
    }

}
