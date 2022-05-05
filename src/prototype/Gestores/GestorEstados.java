/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prototype.Gestores;

import java.awt.Graphics;

/**
 *
 * @author Víctor Zero
 */
public class GestorEstados implements Gestor{

    private boolean jugando;
    private boolean inicio;
    private Gestor gestorActual;

    public GestorEstados() {
        inicio=true;
        jugando=false;
        gestorActual=(Gestor) new GestorMenu();
    }

    public void dibujar(Graphics g) {
        gestorActual.dibujar(g);
    }

    public void actualizar() {
        gestorActual.actualizar();
    }

}
