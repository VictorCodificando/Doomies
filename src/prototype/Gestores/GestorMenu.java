/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prototype.Gestores;

import java.awt.Graphics;
import prototype.HerramientasEntradaSalida.Mouse;
import prototype.HerramientasEntradaSalida.Teclado;
import prototype.Interfaces.Interfaz;
import prototype.Interfaces.InterfazCargaGuarda;
import prototype.Interfaces.InterfazInicio;
import prototype.Interfaces.InterfazSeleccNiveles;

/**
 *
 * @author Víctor Zero
 */
public class GestorMenu implements Gestor {

    private int WIDTH;
    private int HEIGHT;
    private Mouse raton;
    private Teclado teclado;
    private Interfaz interActual;

    public GestorMenu(final int WIDTH, final int HEIGHT, final Teclado teclado, final Mouse raton) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.raton = raton;
        this.teclado=teclado;
        interActual = (Interfaz) new InterfazInicio(WIDTH, HEIGHT, teclado, raton);
    }

    @Override
    public void actualizar() {
        interActual.actualizar();
        if (this.isInicio() && ((InterfazInicio)interActual).isStart()) {
                this.setMenuSaveLoad();
        }
    }

    @Override
    public void dibujar(Graphics g) {
        interActual.dibujar(g);
    }

    public boolean isInicio() {
        return interActual instanceof InterfazInicio;
    }

    public void setInicio() {
        this.interActual = (Interfaz) new InterfazInicio(WIDTH, HEIGHT, teclado, raton);
    }

    public boolean isMenuSaveLoad() {
        return interActual instanceof InterfazCargaGuarda;
    }

    public void setMenuSaveLoad() {
        this.interActual = (Interfaz) new InterfazCargaGuarda(WIDTH, HEIGHT, teclado, raton);
    }

    public boolean isSeleccNiveles() {
        return interActual instanceof InterfazSeleccNiveles;
    }

    public void setSeleccNiveles() {
        //this.interActual = (Interfaz) new InterfazSeleccNiveles(WIDTH, HEIGHT, teclado, raton);
    }

}
