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
import prototype.Interfaces.InterfazCargar;
import prototype.Interfaces.InterfazGuardar;
import prototype.Interfaces.InterfazInicio;
import prototype.Interfaces.InterfazSeleccNiveles;

/**
 *
 * @author Víctor Zero
 */
public class GestorMenu implements Gestor {

    private final int WIDTH;
    private final int HEIGHT;
    private final Mouse raton;
    private final Teclado teclado;
    private Interfaz interActual;
    //niveles
    private boolean jugar;
    private int nivel;
    //cargar
    private boolean cargar;
    private int partidas;
    //guardar
    private boolean guardar;
    private int partidasGuardadas;

    /**
     * Crea el gestor de menu, que va cambiando la forma de su gestor actual
     * segun el tipo de menu que sea
     *
     * @param WIDTH Anchura en pantalla
     * @param HEIGHT Altura en pantalla
     * @param teclado Teclado usado
     * @param raton Raton que usa el gestor
     * @param tipo tipo que define que tipo de menu aparece primero
     */
    public GestorMenu(final int WIDTH, final int HEIGHT, final Teclado teclado, final Mouse raton, int tipo) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.raton = raton;
        this.teclado = teclado;
        interActual = null;
        switch (tipo) {
            case 1:
                interActual = (Interfaz) new InterfazCargaGuarda(WIDTH, HEIGHT, teclado, raton);
                break;
            case 2:
                interActual = (Interfaz) new InterfazSeleccNiveles(WIDTH, HEIGHT, teclado, raton);
                break;
            default:
                interActual = (Interfaz) new InterfazInicio(WIDTH, HEIGHT, teclado, raton);
                break;

        }

    }

    @Override
    public void actualizar() {
        interActual.actualizar();
        if (this.isInicio()) {
            this.opcionesInicio();
        } else if (this.isMenuSaveLoad()) {
            this.opcionesCargaGuardado();
        } else if (this.isSeleccNiveles()) {
            this.opcionesSeleccionNivel();
        } else if (this.isMenuLoad()) {
            this.opcionesMenuCarga();
        } else if (this.isMenuSave()) {
            this.opcionesMenuGuardar();
        }
    }

    /**
     * Solo se podra llamar si la interfaz actual es de inicio
     */
    private void opcionesInicio() {
        if (!this.isInicio()) {
            return;
        }
        InterfazInicio temp = (InterfazInicio) interActual;
        if (temp.isStart()) {
            this.setMenuSaveLoad();
        } else if (teclado.escape) {
            System.exit(0);
        }
    }

    /**
     * Solo se podra llamar si la interfaz actual es de carga y guardado
     */
    private void opcionesCargaGuardado() {
        if (!this.isMenuSaveLoad()) {
            return;
        }
        InterfazCargaGuarda temp = (InterfazCargaGuarda) interActual;
        if (temp.isJugar()) {
            this.setSeleccNiveles();
        } else if (temp.isCargar()) {
            this.setMenuLoad();
        } else if (temp.isGuardar()) {
            this.setMenuSave();
        } else if (teclado.escape) {
            this.setInicio();
        }
    }

    /**
     * Solo se podra llamar si la interfaz actual es de seleccion de niveles
     */
    private void opcionesSeleccionNivel() {
        if (!this.isSeleccNiveles()) {
            return;
        }
        InterfazSeleccNiveles temp = (InterfazSeleccNiveles) interActual;
        this.nivel = temp.getNivel();
        if (temp.isJugar()) {
            jugar = true;
        }
        if (teclado.escape) {
            this.setMenuSaveLoad();
        }
    }

    //Solo se podra llamar si la interfaz actual es de cargar partida
    private void opcionesMenuCarga() {
        if (!this.isMenuLoad()) {
            return;
        }
        InterfazCargar temp = (InterfazCargar) interActual;
        this.partidas = temp.getPartida();
        if (temp.isCargar()) {
            cargar = true;
        }
        if (teclado.escape) {
            this.setMenuSaveLoad();
        }
    }

    private void opcionesMenuGuardar() {
        if (!this.isMenuSave()) {
            return;
        }
        InterfazGuardar temp = (InterfazGuardar) interActual;
        this.partidasGuardadas = temp.getPartidasGuardadas();
        if (temp.isGuardar()) {
            guardar = true;
        }
        if (teclado.escape) {
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

    public boolean isMenuLoad() {
        return interActual instanceof InterfazCargar;
    }

    public void setMenuLoad() {
        this.interActual = (Interfaz) new InterfazCargar(WIDTH, HEIGHT, teclado, raton);
    }

    public boolean isMenuSave() {
        return interActual instanceof InterfazGuardar;
    }

    public void setMenuSave() {
        this.interActual = (Interfaz) new InterfazGuardar(WIDTH, HEIGHT, teclado, raton);
    }

    public boolean isSeleccNiveles() {
        return interActual instanceof InterfazSeleccNiveles;
    }

    public void setSeleccNiveles() {
        this.interActual = (Interfaz) new InterfazSeleccNiveles(WIDTH, HEIGHT, teclado, raton);
    }

    //niveles
    public boolean isJugar() {
        return jugar;
    }

    //niveles
    public int getNivel() {
        return nivel;
    }

    //cargar
    public boolean isCargar() {
        return cargar;
    }

    //cargar
    public int getPartida() {
        return this.partidas;
    }

    //guardar
    public boolean isGuardar() {
        return guardar;
    }

    //guardar
    public int getPartidasGuardadas() {
        return partidasGuardadas;
    }

}
