/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package doomies;

import java.util.ArrayList;

/**
 *
 * @author Víctor Zero
 */
public class Partida {

    private String nombre;
    private ArrayList<Boolean> nivelesDesbloqueados = new ArrayList<Boolean>();

    /**
     * Constructor para la partida cargada
     *
     * @param nombre El nombre del jugador que haya metido el usuario
     * @param nivelesDesbloqueados Un arrayList (tamaño variable) de los niveles
     * desbloqueados, TODO LO QUE ESTE EN FALSE O NO ESTE ES BLOQUEADO
     */
    public Partida(String nombre, ArrayList<Boolean> nivelesDesbloqueados) {
        this.nombre = nombre;
        this.nivelesDesbloqueados = nivelesDesbloqueados;
    }

    /**
     * Constructor para la partida nueva
     *
     * @param nombre El nombre del jugador que haya metido el usuario
     */
    public Partida(String nombre) {
        this.nombre = nombre;
        this.nivelesDesbloqueados.add(true);//Por defecto el primero te viene desbloqueado
        this.nivelesDesbloqueados.add(true);
        this.nivelesDesbloqueados.add(true);
        this.nivelesDesbloqueados.add(true);
        this.nivelesDesbloqueados.add(true);
        this.nivelesDesbloqueados.add(true);

    }

    /**
     * Devuelve true false, si el nivel esta bloqueado o no
     *
     * @param nivel El nivel del que queremos saber la información
     * @return
     */
    public boolean isDesbloqueado(int nivel) {//Te dice si el nivel esta desbloqueado, si no esta en el array, quiere decir que esta bloqueado.
        boolean desbloqueado = false;
        try {
            desbloqueado = nivelesDesbloqueados.get(nivel - 1);
        } catch (Exception e) {
        }
        return desbloqueado;
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
    public ArrayList<Boolean> getNivelesDesbloqueados() {
        return nivelesDesbloqueados;
    }

    /**
     * Cambia los niveles desbloqueados en una partida
     *
     * @param nivelesDesbloqueados ArrayList de booleans con los niveles
     * desbloqueados
     */
    public void setNivelesDesbloqueados(ArrayList<Boolean> nivelesDesbloqueados) {
        this.nivelesDesbloqueados = nivelesDesbloqueados;
    }

}
