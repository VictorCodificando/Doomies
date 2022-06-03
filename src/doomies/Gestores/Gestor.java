/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package doomies.Gestores;

import java.awt.Graphics;

/**
 * Interfaz Gestor que sirve de plantilla para los demás gestores, que tendran
 * como minimo actualizar y dibujar
 *
 * @author Víctor
 * @version 1
 * @since 3
 */
public interface Gestor {

    /**
     * Actualiza el gestor y sus variables
     */
    public void actualizar();

    /**
     * Dibujara el gestor en la pantalla
     *
     * @param g Clase graphics de la pantalla
     */
    public void dibujar(final Graphics g);
}
