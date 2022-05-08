/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prototype.Gestores;

import java.awt.Graphics;
import prototype.HerramientasEntradaSalida.Mouse;
import prototype.HerramientasEntradaSalida.Teclado;
import prototype.Partida;

/**
 *
 * @author Víctor Zero
 */
public class GestorEstados implements Gestor{

    private boolean jugando;
    private boolean inicio;
    private Gestor gestorActual;
    public static Partida partida;
    
    public GestorEstados(final int WIDTH, final int HEIGHT, final Teclado teclado,final Mouse raton) {
        inicio=true;
        jugando=false;
        gestorActual=(Gestor) new GestorMenu(WIDTH,HEIGHT,teclado,raton);
    }

    public void dibujar(Graphics g) {
        gestorActual.dibujar(g);
    }

    public void actualizar() {
        gestorActual.actualizar();
    }

}
