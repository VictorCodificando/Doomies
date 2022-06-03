/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package doomies.Gestores;

import doomies.HerramientasEntradaSalida.LoadTools;
import java.awt.Graphics;
import doomies.HerramientasEntradaSalida.Mouse;
import doomies.HerramientasEntradaSalida.Teclado;
import doomies.Interfaces.InterfazOpciones;
import doomies.Interfaces.Interfaz;
import doomies.Interfaces.InterfazCargaGuarda;
import doomies.Interfaces.InterfazCargar;
import doomies.Interfaces.InterfazGuardar;
import doomies.Interfaces.InterfazInicio;
import doomies.Interfaces.InterfazSeleccNiveles;

/**
 * Gestor que se encarga de mostrar las diferentes interfaces segun la
 * interacción del usuario con estas
 *
 * @see Gestor
 * @author Víctor
 * @version 4
 * @since 3
 */
public class GestorMenu implements Gestor {

    /**
     * Anchura de la pantalla
     */
    private final int WIDTH;
    /**
     * Altura de la pantalla
     */
    private final int HEIGHT;
    /**
     * Raton con el que el usuario pulsara los botones
     */
    private final Mouse raton;
    /**
     * Teclado que usara el usuario
     */
    private final Teclado teclado;
    /**
     * Interfaz que va cambiando su contenido segun como interactue el usuario
     */
    private Interfaz interActual;
    /**
     * Si ha seleccionado jugar
     */
    private boolean jugar;
    /**
     * Nivel que ha seleccionado para
     */
    private int nivel;
    /**
     * Si ha pulsado en cargar
     */
    private boolean cargar;
    /**
     * Si ha pulsado en guardar
     */
    private boolean guardar;

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
    public GestorMenu(final int WIDTH, final int HEIGHT, final Teclado teclado, final Mouse raton, final int tipo) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.raton = raton;
        this.teclado = teclado;
        interActual = null;
        switch (tipo) {//Usado para poder volver desde jugar hasta seleccion de niveles
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

    /**
     * Actualiza la interfaz actual y pasa por su opcion correspondiente segun
     * su tipo
     */
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
        } else if (this.isOpciones()) {
            this.opcionesMenuOpciones();
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
        if (temp.isStart()) {//Si le ha dado a start pasamos al menu de cargar guardar y opciones
            this.setMenuSaveLoad();
        } else if (teclado.escape) {//Si le ha dado a escape, terminamos al ejecucion
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
        if (temp.isJugar()) {//Si se le ha dado a jugar entramos la seleccion de niveles
            this.setSeleccNiveles();
        } else if (temp.isCargar()) {//Si le da cargar pasamos a cargar
            this.setMenuLoad();
        } else if (temp.isGuardar()) {//Con Guardar igual
            this.setMenuSave();
        } else if (temp.isOpciones()) {//con opciones igual
            this.setOpciones();
        } else if (teclado.escape) {//Si le da a escape volvemos a la anterior
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
        if (temp.isJugar()) {//Si le da a jugar nos guardamos el nivel y activamos el boolean
            jugar = true;
        }
        if (teclado.escape) {//Si le da a escape volvemos a la interfaz anterior
            this.setMenuSaveLoad();
        }
    }

    /**
     * Solo se podra llamar si la interfaz actual es de cargar partida
     *
     */
    private void opcionesMenuCarga() {
        if (!this.isMenuLoad()) {
            return;
        }
        InterfazCargar temp = (InterfazCargar) interActual;
        if (teclado.escape || temp.isSalir()) {//Si tiene un estado de salir(partida cargada o escape), salimos
            this.setMenuSaveLoad();
        }
    }

    /**
     * Solo se puede llamar cuando el menu es de guardado
     */
    private void opcionesMenuGuardar() {
        if (!this.isMenuSave()) {
            return;
        }
        InterfazGuardar temp = (InterfazGuardar) interActual;
        if (teclado.escape || temp.isSalir()) {//Si ha decidido salir se guardan las partidas en el fichero y se sigue
            LoadTools.guardarPartidas("/save/save.txt", GestorEstados.partidas);
            this.setMenuSaveLoad();
        }
    }

    /**
     * Solo se puede llamar cuando el menu es de opciones
     */
    private void opcionesMenuOpciones() {
        if (!this.isOpciones()) {
            return;
        }
        InterfazOpciones temp = (InterfazOpciones) interActual;
        if (temp.isGuardar()) {//Si se guardan las opciones, se guardan en un fichero y se cambia al menu save load
            LoadTools.saveSettings(teclado);
            this.setMenuSaveLoad();
        }
    }

    /**
     * Dibuja la interfaz actual en pantalla
     *
     * @param g Graphics la clase que dibuja todo en pantalla
     */
    @Override
    public void dibujar(final Graphics g) {
        interActual.dibujar(g);
    }

    /**
     * Si esta en la interfaz de inicio
     *
     * @return Boolean que dice si esta o no en la interfaz de inicio
     */
    public boolean isInicio() {
        return interActual instanceof InterfazInicio;
    }

    /**
     * Establece la interfaz actual en la interfaz de inicio
     *
     */
    public void setInicio() {
        this.interActual = (Interfaz) new InterfazInicio(WIDTH, HEIGHT, teclado, raton);
    }

    /**
     * Si el menu esta en la interfaz de cargar guardar opciones
     *
     * @return Si esta en el menu principal
     */
    public boolean isMenuSaveLoad() {
        return interActual instanceof InterfazCargaGuarda;
    }

    /**
     * Establece la interfaz actual en la interfaz Principal
     */
    public void setMenuSaveLoad() {
        this.interActual = (Interfaz) new InterfazCargaGuarda(WIDTH, HEIGHT, teclado, raton);
    }

    /**
     *
     * @return Si la interfaz esta es la interfaz de carga
     */
    public boolean isMenuLoad() {
        return interActual instanceof InterfazCargar;
    }

    /**
     * Establece la interfaz actual en la interfaz de carga
     */
    public void setMenuLoad() {
        GestorEstados.partidas = LoadTools.cargarPartidas("/save/save.txt");
        this.interActual = (Interfaz) new InterfazCargar(WIDTH, HEIGHT, teclado, raton);
    }

    /**
     *
     * @return Si la interfaz es de guardado
     */
    public boolean isMenuSave() {
        return interActual instanceof InterfazGuardar;
    }

    /**
     * Establece la interfaz actual en interfaz de guardado primero cargando las
     * partidas
     */
    public void setMenuSave() {
        GestorEstados.partidas = LoadTools.cargarPartidas("/save/save.txt");
        this.interActual = (Interfaz) new InterfazGuardar(WIDTH, HEIGHT, teclado, raton);
    }

    /**
     *
     * @return Si la interfaz es de seleccion de niveles
     */
    public boolean isSeleccNiveles() {
        return interActual instanceof InterfazSeleccNiveles;
    }

    /**
     * Establece la interfaz como seleccion de niveles
     */
    public void setSeleccNiveles() {
        this.interActual = (Interfaz) new InterfazSeleccNiveles(WIDTH, HEIGHT, teclado, raton);
    }

    /**
     *
     * @return Si la interfaz es intefaz de opciones
     */
    public boolean isOpciones() {
        return interActual instanceof InterfazOpciones;
    }

    /**
     * Establece la interfaz actual en interfaz de opciones
     */
    public void setOpciones() {
        this.interActual = (Interfaz) new InterfazOpciones(WIDTH, HEIGHT, teclado, raton);
    }

    /**
     *
     * @return Si le ha pulsado a jugar
     */
    public boolean isJugar() {
        return jugar;
    }

    /**
     *
     * @return El nivel actual en el que se quedó en la interfaz de seleccion
     */
    public int getNivel() {
        return nivel;
    }

    /**
     *
     * @return Si ha pulsado en cargar
     */
    public boolean isCargar() {
        return cargar;
    }

    /**
     *
     * @return Si ha pulsado en guardar
     */
    public boolean isGuardar() {
        return guardar;
    }

}
