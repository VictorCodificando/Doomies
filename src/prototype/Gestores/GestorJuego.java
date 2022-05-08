/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prototype.Gestores;

import java.awt.Graphics;
import prototype.Entes.Seres.Jugador;
import prototype.HerramientasEntradaSalida.Teclado;
import prototype.mapa.Mapa;

/**
 *
 * @author Víctor Zero
 */
public class GestorJuego implements Gestor {

    private Jugador jugador;
    private Mapa mapa;
    private boolean pausa;
    private boolean jugando;

    public GestorJuego(int ID_MAPA) {
        //mapa = new Mapa(LoadTools.loadMap("/Map/mapa1.txt"), null);
        Teclado teclado = null;
        jugador = new Jugador(10, 10, 200, 300, teclado);
    }

    @Override
    public void actualizar() {

    }

    @Override
    public void dibujar(Graphics g) {

    }

}
