/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package doomies.Gestores;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import doomies.Entes.Entidad;
import doomies.Entes.Objetos.Bala;
import doomies.Entes.Seres.Enemigo;
import doomies.Entes.Seres.Jugador;
import doomies.HerramientasEntradaSalida.LoadTools;
import doomies.HerramientasEntradaSalida.Mouse;
import doomies.HerramientasEntradaSalida.Teclado;
import doomies.Interfaces.InterfazPausa;
import doomies.mapa.Mapa;
import doomies.mapa.Tile;

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
    private int vidas;
    private boolean gameOver;
    private boolean win;
    private boolean jugando;
    private final int WIDTH;
    private final BufferedImage HEART = LoadTools.loadImage("/images/heart.png");
    private final BufferedImage HEART_BROKEN = LoadTools.loadImage("/images/heart_broken.png");
    private final BufferedImage PANTALLA_GAMEOVER = LoadTools.loadImage("/images/gameOver.png");
    private final BufferedImage PANTALLA_WIN = LoadTools.loadImage("/images/win.png");
    private final int HEIGHT;
    private ArrayList<Entidad> entesEnMapa = new ArrayList();
    private Teclado teclado;
    private Mouse raton;
    private int Speed = 4;
    private boolean salir;
    private InterfazPausa interfaz = null;
    private boolean mapaMoving = false;

    public GestorJuego(int width, int height, int ID_MAPA, Teclado teclado, Mouse raton) {
        mapa = new Mapa(null, width, height, ID_MAPA);
        this.WIDTH = width;
        this.HEIGHT = height;
        this.teclado = teclado;
        interfaz = null;
        this.raton = raton;
        this.salir = false;
        entesEnMapa = mapa.entesEnMapa;
        this.jugador = new Jugador(100, 100, 107, 69, teclado);
        //entesEnMapa.add(new Enemigo(200, 200, 117, 129, 0));
        entesEnMapa.add(jugador);

    }

    @Override
    public void actualizar() {
        if (cambiarAInterfazPausa()) {
            return;
        }
        if (gameOver || win) {
            waitScreen();
            return;
        }
        detMovimientoJugador();
        actualizarColisiones();
        cancelarMovimiento();
        actualizarJugador();
        mover();
        if (jugador.isDead()) {
            this.gameOver = true;
        } else if (this.win()) {
            win = true;
            ArrayList<Boolean> levels = GestorEstados.partida.getNivelesDesbloqueados();
            levels.add(true);
            GestorEstados.partida.setNivelesDesbloqueados(levels);
        }

    }

    private void waitScreen() {
        long tiempoActual = System.nanoTime();
        long tiempoFinal = System.nanoTime();
        while ((tiempoFinal - tiempoActual) < 1000000000) {
            tiempoFinal = System.nanoTime();
        }
        salir = true;
    }

    private boolean cambiarAInterfazPausa() {
        //Comprobamos los controles y determinamos la velocidad DE CADA OBJETO
        if (teclado.escape) {
            interfaz = new InterfazPausa(WIDTH, HEIGHT, teclado, raton);
        }
        if (interfaz != null) {
            interfaz.actualizar();
            if (interfaz.isSalir()) {
                salir = true;
            }
            if (interfaz.isJugar()) {
                interfaz = null;
            }
            return true;
        }
        return false;
    }

    @Override
    public void dibujar(Graphics g) {
        if (gameOver) {
            this.DibujarGameOver(g);
            return;
        } else if (this.win) {
            this.DibujarWin(g);
            return;
        }
        mapa.dibujar(g);
        //Dibujamos todos los entes
        for (int i = 0; i < entesEnMapa.size(); i++) {
            entesEnMapa.get(i).dibujar(g);
        }
        if (interfaz != null) {
            interfaz.dibujar(g);
        }
        dibujarVidas(g);
    }

    public void detMovimientoJugador() {
        xa = 0;
        jugador.setXa(0);
        jugador.caer();
        if (jugador.getHitbox().x + jugador.getHitbox().width >= WIDTH) {
            teclado.right = false;
        } else if (jugador.getHitbox().x <= 0) {
            teclado.left = false;
        }
        if (!(teclado.left || teclado.right || teclado.jumping||teclado.shooting)) {
            return;
        }
        xa = (teclado.left) ? -Speed : (teclado.right) ? Speed : 0;
        xa *= (teclado.running) ? 2 : 1;
        jugador.setXa(xa);
        jugador.calcularCooldownBalas();
        if (teclado.shooting) {
            jugador.disparar();
            jugador.setShooting(true);
        }else{
            jugador.setShooting(false);
        }

    }

    private void actualizarColisiones() {
        for (int i = 0; i < entesEnMapa.size(); i++) {//Se comprueba ente a ente
            //Si no es visible no comprobar
            if (!entesEnMapa.get(i).isVisible()) {
                if (entesEnMapa.get(i) instanceof Bala) {
                    entesEnMapa.set(i, null);   
                } else {
                    continue;
                }
            }
            //Si es null, lo eliminamos
            if (entesEnMapa.get(i) == null) {
                entesEnMapa.remove(i);
            }
            if (i == entesEnMapa.size()) {
                break;
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
                if ((entesEnMapa.get(i) instanceof Bala && entesEnMapa.get(j) instanceof Jugador) || (entesEnMapa.get(j) instanceof Bala && entesEnMapa.get(i) instanceof Jugador)) {
                    continue;
                }
                entesEnMapa.get(i).isGoingToCollide(entesEnMapa.get(j).getHitbox());//Comprobamos si colisiona al fin
            }
            if ((entesEnMapa.get(i).isCollidingXLeft() || entesEnMapa.get(i).isCollidingXRight() || entesEnMapa.get(i).isCollidingYUp() || entesEnMapa.get(i).isCollidingYDown()) && entesEnMapa.get(i) instanceof Bala) {
                entesEnMapa.remove(i);
                continue;
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
            if (entesEnMapa.get(i) instanceof Jugador || !entesEnMapa.get(i).isVisible()) {
                continue;
            }
            if (!mapaMoving) {
                entesEnMapa.get(i).setXa(0);
                mapa.setXa(0);
                entesEnMapa.get(i).actualizar();
                continue;
            } else {
                mapa.setXa(-xa);
            }

            entesEnMapa.get(i).setXa(-xa);
            entesEnMapa.get(i).actualizar();
        }
        mapa.actualizar();
        jugador.addBalasAsEntidadtidad(entesEnMapa);
    }

    private void actualizarJugador() {
        mapaMoving = false;
        //System.out.println(xa);
        int middleScreenPos = WIDTH / 2 - jugador.getHitbox().width;

        if (mapa.isLimit() && xa != 0) {
            /**
             * Esto quiere decir que el mapa esta en el limite izquierdo(donde
             * parte)
             */
            if (jugador.getHitbox().x < middleScreenPos) {
                jugador.setXa(xa);
            } else if (xa > 0) {
                jugador.setXa(0);
                mapaMoving = true;
            }

        } else if (mapa.isLimit_fin() && xa != 0) {
            /**
             * Esto quiere decir que el mapa esta en el limite derecho(donde
             * termina)
             */
            if (jugador.getHitbox().x > middleScreenPos) {
                jugador.setXa(xa);
            } else if (xa < 0) {
                // Se mueve todo
                jugador.setXa(0);
                mapaMoving = true;
            }
        } else if (xa != 0 && (middleScreenPos + 10 > jugador.getHitbox().x && middleScreenPos - 10 < jugador.getHitbox().x)) {
//            System.out.println("pasa por aqui?");
            //Se mueve todo
            if (jugador.isCollidingXRight() && xa > 0) {
                xa = 0;
            } else if (jugador.isCollidingXLeft() && xa < 0) {
                xa = 0;
            } else {
                jugador.setXa(0);
                mapaMoving = true;
            }
        }
//        System.out.println("limite derecho mapa: " + mapa.isLimit_fin() + "\nLimite izquierdo mapa: " + mapa.isLimit());
        jugador.actualizar();
        if (fallOutSideMap()) {
            jugador.resetPos();
            jugador.setVida(jugador.getVida() - 1);
        }
        this.definirEstadoJugador();
        jugador.actualizarSprite();
    }

    public void definirEstadoJugador() {
        if (this.xa != 0) {//Si se esta moviendo en el eje X
            if (this.teclado.left) {//Si anda a la izquierda
                jugador.setWalking(true);
                jugador.setDir("L");
            } else if (this.teclado.right) {//Si anda a la derecha
                jugador.setWalking(true);
                jugador.setDir("R");
            }
        } else {//Si no se esta moviendo
            jugador.setStay(true);
            jugador.setWalking(false);
        }
        if (!jugador.isCollidingYDown()) {//Si esta cayendo
            jugador.setFalling(true);
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

    public boolean win() {
        if (jugador.getHitbox().x + jugador.getHitbox().width >= WIDTH) {
            return true;
        }
        return false;
    }

    public boolean fallOutSideMap() {
        if (jugador.getHitbox().y > HEIGHT) {
            return true;
        }
        return false;
    }

    public boolean isSalir() {
        return salir;
    }

    private void DibujarGameOver(Graphics g) {
        g.drawImage(PANTALLA_GAMEOVER, 0, 0, null);
    }

    private void DibujarWin(Graphics g) {
        g.drawImage(PANTALLA_WIN, 0, 0, null);
    }

    public void dibujarVidas(Graphics g) {
        int posInicialX = 50;
        int posInicialY = 20;
        int separacion = 10 + (HEART.getWidth());
        int vidas = jugador.getVida() - 1;
        for (int i = 0; i < 3; i++) {
            if (i > vidas) {
                g.drawImage(HEART_BROKEN, posInicialX + (separacion * i), posInicialY, null);
            } else {
                g.drawImage(HEART, posInicialX + (separacion * i), posInicialY, null);
            }
        }
    }
}
