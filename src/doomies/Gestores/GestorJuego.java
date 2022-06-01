/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package doomies.Gestores;

import doomies.Doomies;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import doomies.Entes.Entidad;
import doomies.Entes.Objetos.Bala;
import doomies.Entes.Seres.Enemigo;
import doomies.Entes.Seres.Jugador;
import doomies.Entes.Seres.SerVivo;
import doomies.HerramientasEntradaSalida.LoadTools;
import doomies.HerramientasEntradaSalida.Mouse;
import doomies.HerramientasEntradaSalida.Teclado;
import doomies.Interfaces.Elementos.Boton;
import doomies.Interfaces.Elementos.Cronometro;
import doomies.Interfaces.InterfazPausa;
import doomies.mapa.Mapa;
import doomies.mapa.Tile;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;

/**
 *
 * @author Víctor Zero
 */
public class GestorJuego implements Gestor {

    private Jugador jugador;
    private Mapa mapa;
    //Velocidades
    private static int xa;
    private int ya;
    private long tiempoAlcanzado;
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
    private long timeInicio;
    private Mouse raton;
    private int Speed = 4;
    private boolean salir;
    private InterfazPausa interfaz = null;
    private static boolean mapaMoving = false;
    private int vidasJugador = 0;
    private Boton[] tutorial;
    private Cronometro timer;
    protected final Font font = LoadTools.loadFont("/fonts/kongtext.ttf");

    /**
     * Crea el gestor de juegos actual.
     *
     * @param width int Anchura de la pantalla
     * @param height int Altura de la pantalla
     * @param ID_MAPA int ID del mapa
     * @param teclado Teclado teclado que controla al jugador
     * @param raton Mouse raton que se usa como deteccion a los botones
     */
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
        entesEnMapa.add(jugador);
        teclado.resetAllKeys();
        if (mapa.getID() == 1) {
            tutorial = new Boton[3];
            tutorial[0] = new Boton(100, 200, 600, 100, "Muevete con " + KeyEvent.getKeyText(teclado.leftKey) + " y " + KeyEvent.getKeyText(teclado.rightKey) + ",/salta con \"" + KeyEvent.getKeyText(teclado.jumpKey) + "\"", LoadTools.loadFont("/fonts/kongtext.ttf").deriveFont(20f), Color.gray, 60, 50, raton);
            tutorial[0].setFormat(60);
            tutorial[1] = new Boton(1000, 200, 630, 100, "Para destruir a los enemigos /has de dispararlos con \"" + KeyEvent.getKeyText(teclado.shootKey) + "\"", LoadTools.loadFont("/fonts/kongtext.ttf").deriveFont(20f), Color.gray, 60, 50, raton);
            tutorial[1].setFormat(60);
            tutorial[2] = new Boton(2000, 200, 600, 100, "Para correr, manten \"" + KeyEvent.getKeyText(teclado.runKey) + "\" /mientras te mueves", LoadTools.loadFont("/fonts/kongtext.ttf").deriveFont(20f), Color.gray, 60, 50, raton);
            tutorial[2].setFormat(60);
        }
        timer = new Cronometro(System.nanoTime());
        timer.start();

    }

    /**
     * Actualiza todos los elementos que hay en juego
     */
    @Override
    public void actualizar() {
        if (cambiarAInterfazPausa()) {
            timer.setPause(true);
            return;
        }
        timer.setPause(false);
        if (gameOver || win) {
            waitScreen();
            timer.stop();
            return;
        }
        detMovimientoJugador();
        actualizarPosicionJugadorEnEnemigo();
        actualizarColisiones();
        cancelarMovimiento();
        actualizarJugador();
        mover();
        if (jugador.isDead()) {
            this.gameOver = true;
            this.timeInicio = System.nanoTime();
        } else if (this.win()) {
            win = true;
            this.timeInicio = System.nanoTime();
            tiempoAlcanzado = timer.getTimeInSeconds();
            ArrayList<Integer> levels = GestorEstados.partida.getNivelesDesbloqueados();
            try {
                int tiempoAnterior = levels.get(mapa.getID());
                if (tiempoAnterior <= tiempoAlcanzado) {
                    return;
                }
                levels.set(mapa.getID()-1, (int) tiempoAlcanzado);
            } catch (Exception e) {
                levels.add((int) tiempoAlcanzado);
            }
        }

    }

    /**
     * Tiempo de espera
     */
    private void waitScreen() {
        if (System.nanoTime() - this.timeInicio >= 1700000000) {
            this.salir = true;
        }
    }

    /**
     * Comprobamos si la interfaz esta en modo pausa
     *
     * @return Devuelve un true si el juego esta en pausa
     */
    private boolean cambiarAInterfazPausa() {

        if ((teclado.escape || !Doomies.onTop()) && (!win && !gameOver)) {
            interfaz = new InterfazPausa(WIDTH, HEIGHT, teclado, raton);
            teclado.resetAllKeys();
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

    /**
     * Dibuja todo lo que hay en juego
     *
     * @param g Graphics clase que dibuja todo en pantalla viene desde
     *
     * @see GestorEstados#dibujar(java.awt.Graphics)
     */
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
        if (this.tutorial != null) {
            for (int i = 0; i < tutorial.length; i++) {
                tutorial[i].dibujar(g);
            }
        }
        //Dibujamos todos los entes
        for (int i = 0; i < entesEnMapa.size(); i++) {
            if (entesEnMapa.get(i).isVisible()) {
                entesEnMapa.get(i).dibujar(g);
            }

        }
        if (interfaz != null) {
            interfaz.dibujar(g);
        }
        dibujarVidas(g);
        g.setColor(Color.WHITE);
        g.setFont(font.deriveFont(40f));
        g.drawString(timer.getFormatTime(), 100, 700);
    }

    /**
     * A traves de lo controles de los listeners se determina el movimiento del
     * jugador
     */
    public void detMovimientoJugador() {
        xa = 0;
        jugador.setXa(0);
//        jugador.caer();
        if (jugador.getHitbox().x + jugador.getHitbox().width >= WIDTH) {
            teclado.right = false;
        } else if (jugador.getHitbox().x <= 0) {
            teclado.left = false;
        }
        if (!(teclado.left || teclado.right || teclado.jumping || teclado.shooting)) {
            return;
        }
        xa = (teclado.left) ? -Speed : (teclado.right) ? Speed : 0;
        xa *= (teclado.running) ? 2 : 1;
        jugador.setXa(xa);
        if (teclado.shooting) {
            jugador.disparar();
            jugador.setShooting(true);
        } else {
            jugador.setShooting(false);
        }

    }

    /**
     * Comprueba ente a ente que colision y que tipo de colision tiene, si
     * colisiona segun el tipo que sea entonces o no se movera o se golpeara...
     */
    private void actualizarColisiones() {
        for (int i = 0; i < entesEnMapa.size(); i++) {//Se comprueba ente a ente
            //Si no es visible no comprobar

            if (!entesEnMapa.get(i).isVisible()) {
                if (entesEnMapa.get(i) instanceof Bala) {
                    entesEnMapa.set(i, null);
                }
            }
            if (entesEnMapa.get(i) instanceof Enemigo) {
                Enemigo eActual = (Enemigo) entesEnMapa.get(i);
//                System.out.println(eActual.getVida());
                if (eActual.isDead()) {
                    entesEnMapa.set(i, null);
                }
            }
            //Si es null, lo eliminamos
            if (entesEnMapa.get(i) == null) {
                entesEnMapa.remove(i);
            }

            if (i == entesEnMapa.size()) {
                break;
            }
            if (entesEnMapa.get(i) instanceof SerVivo) {
                if (entesEnMapa.get(i) instanceof Enemigo) {
                    Enemigo eActual = (Enemigo) entesEnMapa.get(i);
                    if (!(eActual.getEnemyType() == 3 || eActual.getEnemyType() == 2)) {
                        entesEnMapa.get(i).caer();
                    }
                } else {
                    entesEnMapa.get(i).caer();
                }
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
                //Colisiones de bala enemigo
                if ((entesEnMapa.get(i) instanceof Bala && entesEnMapa.get(j) instanceof Enemigo) || (entesEnMapa.get(j) instanceof Bala && entesEnMapa.get(i) instanceof Enemigo)) {
                    Enemigo eActual = (Enemigo) ((entesEnMapa.get(i) instanceof Enemigo) ? entesEnMapa.get(i) : entesEnMapa.get(j));
                    if (this.colisionX(entesEnMapa.get(j).getHitbox(), entesEnMapa.get(i).getHitbox())) {
                        if (entesEnMapa.get(i) instanceof Enemigo) {
                            entesEnMapa.get(j).setCollidingXRight(true);
                        } else {
                            entesEnMapa.get(i).setCollidingXRight(true);
                        }

                        eActual.perderVida();
                    }
                    continue;
                }
                //Colisiones de jugador enemigo
                if ((entesEnMapa.get(i) instanceof Jugador && entesEnMapa.get(j) instanceof Enemigo) || (entesEnMapa.get(j) instanceof Jugador && entesEnMapa.get(i) instanceof Enemigo)) {
                    Jugador eActual = (Jugador) ((entesEnMapa.get(j) instanceof Enemigo) ? entesEnMapa.get(i) : entesEnMapa.get(j));
                    Rectangle hitboxExp = new Rectangle(entesEnMapa.get(i).getHitbox().x + 5, entesEnMapa.get(i).getHitbox().y + 5, entesEnMapa.get(i).getHitbox().width - 10, entesEnMapa.get(i).getHitbox().height - 10);
                    int vidainic = eActual.getVida();
                    if (this.colisionX(entesEnMapa.get(j).getHitbox(), entesEnMapa.get(i).getHitbox())) {
                        eActual.perderVida();
                    }
                    continue;
                }
                if (entesEnMapa.get(i) instanceof Enemigo && entesEnMapa.get(j) instanceof Enemigo) {
                    continue;
                }
                if (entesEnMapa.get(i) instanceof Bala && entesEnMapa.get(j) instanceof Tile && this.colisionX(entesEnMapa.get(i).getHitbox(), entesEnMapa.get(j).getHitbox())) {
                    entesEnMapa.set(i, null);
                    break;
                }
                entesEnMapa.get(i).isGoingToCollide(entesEnMapa.get(j).getHitbox());//Comprobamos si colisiona al fin
            }
            if (entesEnMapa.get(i) == null) {
                entesEnMapa.remove(i);
                continue;
            }
            if ((entesEnMapa.get(i).isCollidingXLeft() || entesEnMapa.get(i).isCollidingXRight() || entesEnMapa.get(i).isCollidingYUp() || entesEnMapa.get(i).isCollidingYDown()) && entesEnMapa.get(i) instanceof Bala) {
                entesEnMapa.remove(i);
                continue;
            }
            if (entesEnMapa.get(i) instanceof Enemigo && fallOutSideMap(entesEnMapa.get(i).getHitbox())) {
                Enemigo eActual = (Enemigo) entesEnMapa.get(i);
                eActual.setVida(0);
            }
            if (isOutSideScreen(entesEnMapa.get(i).getHitbox())) {
                entesEnMapa.get(i).setVisible(false);
            } else {
                entesEnMapa.get(i).setVisible(true);
            }
        }
    }

    public void actualizarPosicionJugadorEnEnemigo() {
        Enemigo.xPlayer = jugador.getHitbox().x;
        Enemigo.yPlayer = jugador.getHitbox().y;
    }

    /**
     * Cancela el movimiento del jugador si esta colisionando
     */
    private void cancelarMovimiento() {
        if ((jugador.isCollidingYUp() && ya < 0) || (jugador.isCollidingYDown() && ya > 0)) {
            ya = 0;
        }
        if ((jugador.isCollidingXRight() && xa > 0) || (jugador.isCollidingXLeft() && xa < 0)) {
            xa = 0;
        }
    }

    /**
     * Mueve todos los entes en pantalla
     */
    private void mover() {
        for (int i = 0; i < entesEnMapa.size(); i++) {
            if (entesEnMapa.get(i) instanceof Jugador) {
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
        if (this.tutorial != null && mapaMoving) {
            for (int i = 0; i < tutorial.length; i++) {
                tutorial[i].moveInX(-xa);
            }
        }
        mapa.actualizar();
        jugador.addBalasAsEntidadtidad(entesEnMapa);

    }

    /**
     * Actualiza el estado del jugador y su velocidad respeto a la pantalla
     */
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
        /**
         * System.out.println("limite derecho mapa: " + mapa.isLimit_fin() +
         * "\nLimite izquierdo mapa: " + mapa.isLimit());
         */
        jugador.actualizar();
        if (fallOutSideMap(jugador.getHitbox())) {
            jugador.resetPos();
            this.mapaMoving = true;
            jugador.setVida(jugador.getVida() - 1);
        }
        this.definirEstadoJugador();
        jugador.actualizarSprite();
    }

    /**
     * Define y cambia los booleans del jugador segun su estado
     */
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
        /**
         * colisiona abajo
         */
        if (!jugador.isCollidingYDown()) {
            jugador.setFalling(true);
        }
    }

    /**
     * Comprueba colisiones
     *
     * @param r1 Rectangle que representa el primer rectangulo.
     * @param r2 Rectangle que representa el segundo rectangulo.
     * @return Devuelve true o false si a colisionado o no.
     */
    public boolean colisionX(Rectangle r1, Rectangle r2) {
        return (r1.x < r2.x + r2.width
                && r1.x + r1.width > r2.x
                && r1.y < r2.y + r2.height
                && r1.height + r1.y > r2.y);
    }

    /**
     *
     * @param rect Rectangle que representa el rectangulo a probar si se ha
     * salido de la pantalla
     * @return Devuelve true si el rectangulo a salido de pantalla y false si
     * no.
     */
    public boolean isOutSideScreen(Rectangle rect) {
        Rectangle Screen = new Rectangle(-200, -900, WIDTH + 400, HEIGHT + 900);
        if (Screen.contains(rect) || Screen.intersects(rect)) {
            return false;
        }
        return true;
    }

    /**
     * Comprueba si gana o no
     *
     * @return Devuelve si se ha conseguido ganar el nivel
     */
    public boolean win() {
        if (jugador.getHitbox().x + jugador.getHitbox().width >= WIDTH) {
            return true;
        }
        return false;
    }

    /**
     * Comprueba si el jugador se ha caido del mapa
     *
     * @return Devuelve si se ha caido del mapa
     */
    public boolean fallOutSideMap(Rectangle rect) {
        if (rect.y > HEIGHT) {
            return true;
        }
        return false;
    }

    /**
     * @return Devuelve si se va a salir del juego
     */
    public boolean isSalir() {
        if (salir) {
            timer.stop();
        }
        return salir;
    }

    /**
     * Dibuja la pantalla de gameover
     *
     * @param g Graphics que dibuja el gameOver
     * @see GestorJuego#dibujar(java.awt.Graphics)
     */
    private void DibujarGameOver(Graphics g) {
        g.drawImage(PANTALLA_GAMEOVER, 0, 0, null);
    }

    /**
     * Dibuja la pantalla de nivel finalizado
     *
     * @param g Graphics que dibuja la pantalla de nivel finalizado
     * @see GestorJuego#dibujar(java.awt.Graphics)
     */
    private void DibujarWin(Graphics g) {
        g.drawImage(PANTALLA_WIN, 0, 0, null);
    }

    /**
     * Dibuja en pantalla las vidas actuales del jugador
     *
     * @param g Graphics que dibuja las vidas actuales en pantalla
     * @see GestorJuego#dibujar(java.awt.Graphics)
     */
    public void dibujarVidas(Graphics g) {
        int posInicialX = 50;
        int posInicialY = 20;
        int separacion = 10 + (HEART.getWidth());
        int vidas = jugador.getVida() - 1;
        if (vidasJugador == 0) {
            vidasJugador = jugador.getVida();
        }
        for (int i = 0; i < vidasJugador; i++) {
            if (i > vidas) {
                g.drawImage(HEART_BROKEN, posInicialX + (separacion * i), posInicialY, null);
            } else {
                g.drawImage(HEART, posInicialX + (separacion * i), posInicialY, null);
            }
        }
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public Mapa getMapa() {
        return mapa;
    }

    public void setMapa(Mapa mapa) {
        this.mapa = mapa;
    }

    public static int getXa() {
        return GestorJuego.xa;
    }

    public static void setXa(int xa) {
        GestorJuego.xa = xa;
    }

    public int getYa() {
        return ya;
    }

    public void setYa(int ya) {
        this.ya = ya;
    }

    public boolean isPausa() {
        return pausa;
    }

    public void setPausa(boolean pausa) {
        this.pausa = pausa;
    }

    public int getVidas() {
        return vidas;
    }

    public void setVidas(int vidas) {
        this.vidas = vidas;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public boolean isJugando() {
        return jugando;
    }

    public void setJugando(boolean jugando) {
        this.jugando = jugando;
    }

    public ArrayList<Entidad> getEntesEnMapa() {
        return entesEnMapa;
    }

    public void setEntesEnMapa(ArrayList<Entidad> entesEnMapa) {
        this.entesEnMapa = entesEnMapa;
    }

    public Teclado getTeclado() {
        return teclado;
    }

    public void setTeclado(Teclado teclado) {
        this.teclado = teclado;
    }

    public long getTimeInicio() {
        return timeInicio;
    }

    public void setTimeInicio(long timeInicio) {
        this.timeInicio = timeInicio;
    }

    public Mouse getRaton() {
        return raton;
    }

    public void setRaton(Mouse raton) {
        this.raton = raton;
    }

    public int getSpeed() {
        return Speed;
    }

    public void setSpeed(int Speed) {
        this.Speed = Speed;
    }

    public InterfazPausa getInterfaz() {
        return interfaz;
    }

    public void setInterfaz(InterfazPausa interfaz) {
        this.interfaz = interfaz;
    }

    public static boolean isMapaMoving() {
        return GestorJuego.mapaMoving;
    }

    public static void setMapaMoving(boolean mapaMoving) {
        GestorJuego.mapaMoving = mapaMoving;
    }

}
