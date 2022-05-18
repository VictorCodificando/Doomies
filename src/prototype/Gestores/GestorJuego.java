/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prototype.Gestores;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import prototype.Entes.Entidad;
import prototype.Entes.Seres.Jugador;
import prototype.HerramientasEntradaSalida.Teclado;
import prototype.mapa.Mapa;
import prototype.mapa.Tile;

/**
 *
 * @author Víctor Zero
 */
public class GestorJuego implements Gestor {

    private Jugador jugador;
    private Mapa mapa;
    //Velocidades
    private int xa;
    private int ya;
    private boolean pausa;
    private boolean jugando;
    private final int WIDTH;
    private final int HEIGHT;
    private ArrayList<Entidad> entesEnMapa = new ArrayList();
    private Teclado teclado;
    private int Speed = 4;

    public GestorJuego(int width, int height, int ID_MAPA, Teclado teclado) {
        mapa = new Mapa(null, width, height, ID_MAPA);
        this.WIDTH = width;
        this.HEIGHT = height;
        this.teclado = teclado;
        entesEnMapa = mapa.entesEnMapa;
        this.jugador = new Jugador(100, 100, 107, 69, teclado);
        entesEnMapa.add(jugador);
    }

    @Override
    public void actualizar() {
        //Comprobamos los controles y determinamos la velocidad DE CADA OBJETO
        detMovimientoJugador();
        actualizarColisiones();
        cancelarMovimiento();
        mover();
    }

    @Override
    public void dibujar(Graphics g) {
        mapa.dibujar(g);
        //Dibujamos todos los entes
        for (int i = 0; i < entesEnMapa.size(); i++) {
            entesEnMapa.get(i).dibujar(g);
        }
    }
    
    public void detMovimientoJugador() {
        xa = 0;
        jugador.caer();
        if (!(teclado.left || teclado.right || teclado.jumping)) {//AÑADIR DISPARO
            return;
        }
        xa = (teclado.left) ? -Speed : (teclado.right) ? Speed : 0;
        xa *= (teclado.running) ? 2 : 1;
        jugador.setXa(xa);
    }

    private void actualizarColisiones() {
        for (int i = 0; i < entesEnMapa.size(); i++) {//Se comprueba ente a ente
            //Si no es visible no comprobar
            if (!entesEnMapa.get(i).isVisible()) {
                continue;
            }
            //Si es null, lo eliminamos
            if (entesEnMapa.get(i) == null) {
                entesEnMapa.remove(i);
            }
            //Movidas de colisiones
            entesEnMapa.get(i).setCollidingXRight(false);
            entesEnMapa.get(i).setCollidingXLeft(false);
            entesEnMapa.get(i).setCollidingYUp(false);
            entesEnMapa.get(i).setCollidingYDown(false);
            for (int j = 0; j < entesEnMapa.size(); j++) {//Se comprueba las colisiones de un objeto con todos  
                if (entesEnMapa.get(i) instanceof Tile) {//Si es un tile ni se comprueba ya que este no se puede chocar con nada,
                    break;                               //sino que lo demas choca con el
                }
                if (i == j || entesEnMapa.get(j) == null) {//Si es null, luego desparecera, pero ahora nos lo saltamos
                    continue;
                }
                entesEnMapa.get(i).isGoingToCollide(entesEnMapa.get(j).getHitbox());//Comprobamos si colisiona al fin
            }
            //Si esta fuera de la pantalla es invisible
            if (isOutSideScreen(entesEnMapa.get(i).getHitbox())) {
                entesEnMapa.get(i).setVisible(false);
            } else {
                entesEnMapa.get(i).setVisible(true);
            }
        }
    }

    private void cancelarMovimiento() {
        if ((jugador.isCollidingYUp() && ya < 0) || (jugador.isCollidingYDown() && ya > 0)) {
            ya = 0;
        }
        if ((jugador.isCollidingXRight() && xa > 0) || (jugador.isCollidingXLeft() && xa < 0)) {
            xa = 0;
        }

    }

    private void mover() {
        for (int i = 0; i < entesEnMapa.size(); i++) {
            if (entesEnMapa.get(i) instanceof Jugador) {
                entesEnMapa.get(i).setXa(xa);
                entesEnMapa.get(i).actualizar();
                continue;
            }
            entesEnMapa.get(i).setXa(-xa);
            entesEnMapa.get(i).actualizar();
        }
    }

    public boolean colisionX(Rectangle r1, Rectangle r2) {
        return (r1.x < r2.x + r2.width
                && r1.x + r1.width > r2.x
                && r1.y < r2.y + r2.height
                && r1.height + r1.y > r2.y);
    }

    public boolean isOutSideScreen(Rectangle rect) {
        if (WIDTH > rect.x || HEIGHT > rect.y || rect.x + rect.width < 0 || rect.y + rect.height < 0) {
            return false;
        }
        return true;
    }
}
