/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prototype;

import java.util.ArrayList;

/**
 *
 * @author Víctor Zero
 */
public class Partida {
    private String nombre;
    private ArrayList<Boolean> nivelesDesbloqueados=new ArrayList<Boolean>();
    
    /**
     * Constructor para la partida cargada
     * 
     * @param nombre El nombre del jugador que haya metido el usuario
     * @param nivelesDesbloqueados Un arrayList (tamaño variable) de los niveles desbloqueados, TODO LO QUE ESTE EN FALSE O NO ESTE ES BLOQUEADO
     */
    public Partida(String nombre,ArrayList<Boolean> nivelesDesbloqueados) {
        this.nombre = nombre;
        this.nivelesDesbloqueados=nivelesDesbloqueados;
    }
    
    /**
     * Constructor para la partida nueva
     * 
     * @param nombre El nombre del jugador que haya metido el usuario
     */
    public Partida(String nombre){
        this.nombre=nombre;
    }
    
    /**
     * Devuelve true false, si el nivel esta bloqueado o no
     * 
     * @param nivel El nivel del que queremos saber la información
     * @return 
     */
    public boolean isDesbloqueado(int nivel){//Te dice si el nivel esta desbloqueado, si no esta en el array, quiere decir que esta bloqueado.
        boolean desbloqueado=false;
        try{
            desbloqueado=nivelesDesbloqueados.get(nivel);
        }catch(Exception e){
           
        }
        return desbloqueado;
    }
    
}
