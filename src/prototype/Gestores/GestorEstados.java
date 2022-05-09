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
public class GestorEstados implements Gestor {

    private Gestor gestorActual;
    public static Partida partida;
    private final int WIDTH;
    private final int HEIGHT;
    private final Teclado teclado;
    private final Mouse raton;

    public GestorEstados(final int WIDTH, final int HEIGHT, final Teclado teclado, final Mouse raton) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.teclado = teclado;
        this.raton = raton;
        this.gestorActual = (Gestor) new GestorMenu(WIDTH, HEIGHT, teclado, raton);
    }

    public void dibujar(Graphics g) {
        gestorActual.dibujar(g);
    }

    public void actualizar() {
//CAMBIAR TENDRA QUE SABER EN QUE MENU ESTAAAAAAAAAAAAAA
        gestorActual.actualizar();
        if (this.isGestorJuego()) {
            this.opcionesJugando();
        } else {
            this.opcionesMenu();
        }
    }

    public void opcionesMenu() {
        if (!this.isGestorMenu()) {
            return;
        }
        GestorMenu temp = (GestorMenu) gestorActual;
        if (temp.isJugar()) {
            this.setGestorJuego();
        }
    }

    public void opcionesJugando() {
        if (!this.isGestorJuego()) {
            return;
        }
        GestorJuego temp = (GestorJuego) gestorActual;
        if (teclado.escape) {
            this.setGestorMenu();
        }
    }

    public void setGestorMenu() {
        this.gestorActual = (Gestor) new GestorMenu(WIDTH, HEIGHT, teclado, raton);
    }

    public boolean isGestorMenu() {
        return gestorActual instanceof GestorMenu;
    }

    public void setGestorJuego() {
        this.gestorActual = (Gestor) new GestorJuego(WIDTH, HEIGHT);
    }

    public boolean isGestorJuego() {
        return gestorActual instanceof GestorJuego;
    }

}
