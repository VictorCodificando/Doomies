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
public class GestorMenu implements Gestor {

    private boolean inicio;
    private boolean menuSaveLoad;
    private boolean seleccNiveles;

    public GestorMenu() {
        inicio = true;
        menuSaveLoad = false;
        seleccNiveles = false;
    }

    @Override
    public void actualizar() {

    }

    @Override
    public void dibujar(Graphics g) {

    }

    public boolean isInicio() {
        return inicio;
    }

    public void setInicio(boolean inicio) {
        this.inicio = inicio;
    }

    public boolean isMenuSaveLoad() {
        return menuSaveLoad;
    }

    public void setMenuSaveLoad(boolean menuSaveLoad) {
        this.menuSaveLoad = menuSaveLoad;
    }

    public boolean isSeleccNiveles() {
        return seleccNiveles;
    }

    public void setSeleccNiveles(boolean seleccNiveles) {
        this.seleccNiveles = seleccNiveles;
    }

}
