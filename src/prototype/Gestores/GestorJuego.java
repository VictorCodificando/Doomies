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
    
    public GestorJuego(int width, int height,int ID_MAPA) {
        mapa = new Mapa(null, width, height,ID_MAPA);
        Teclado teclado = null;
        jugador = new Jugador(10, 10, 200, 300, teclado);
    }

    @Override
    public void actualizar() {
        mapa.actualizar();
    }

    @Override
    public void dibujar(Graphics g) {
        mapa.dibujar(g);
    }

}
