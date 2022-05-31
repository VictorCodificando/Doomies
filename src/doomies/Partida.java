/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package doomies;

import doomies.HerramientasEntradaSalida.LoadTools;
import java.util.ArrayList;

/**
 *
 * @author Víctor Zero
 */
public class Partida {

    private String nombre;
    private ArrayList<Integer> nivelesDesbloqueados = new ArrayList<Integer>();

    /**
     * Constructor para la partida cargada
     *
     * @param nombre El nombre del jugador que haya metido el usuario
     * @param nivelesAlcanzado El nivel hasta el que ha llegado en esta partida
     *
     */
    public Partida(String nombre, ArrayList<Integer> lvls) {
        this(nombre);
        nivelesDesbloqueados = lvls;
    }

    /**
     * Constructor para la partida nueva
     *
     * @param nombre El nombre del jugador que haya metido el usuario
     */
    public Partida(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve true false, si el nivel esta bloqueado o no
     *
     * @param nivel El nivel del que queremos saber la información
     * @return
     */
    public boolean isDesbloqueado(final int nivel) {//Te dice si el nivel esta desbloqueado, si no esta en el array, quiere decir que esta bloqueado.
        return (nivel <= nivelesDesbloqueados.size() + 1);
    }

    /**
     *
     * @return El nombre de la partida
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Modifica el nombre de la partida
     *
     * @param nombre El nombre nuevo
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     *
     * @return Los niveles que estan desbloqueados
     */
    public ArrayList<Integer> getNivelesDesbloqueados() {
        return nivelesDesbloqueados;
    }

    /**
     * Cambia los niveles desbloqueados en una partida
     *
     * @param nivelesDesbloqueados ArrayList de booleans con los niveles
     * desbloqueados
     */
    public void setNivelesDesbloqueados(ArrayList<Integer> nivelesDesbloqueados) {
        this.nivelesDesbloqueados = nivelesDesbloqueados;
    }
}
