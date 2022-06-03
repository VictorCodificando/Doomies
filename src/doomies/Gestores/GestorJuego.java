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
 * Crea el gestor que se encargara de mover tanto los entes como el mapa y
 * recibir la informacion del teclado para mover la escena entera
 *
 * @see Gestor
 * @author Víctor
 * @version 4
 * @since 3
 */
public class GestorJuego implements Gestor {

    /**
     * Jugador que será controlado por el usuario
     */
    private Jugador jugador;
    /**
     * Mapa que contiene los tiles y el fondo, propio de cada nivel
     */
    private Mapa mapa;
    /**
     * Velocidad de la escena en x
     */
    private static int xa;
    /**
     * Velocidad de la escena en y
     */
    private int ya;
    /**
     * Tiempo alcanzado al final del nivel
     */
    private long tiempoAlcanzado;
    /**
     * Indica si esta en pausa, para saber que no se ha de mover nada
     */
    private boolean pausa;
    /**
     * Corazones totales a dibujar en pantalla
     */
    private int vidas;
    /**
     * Indica si el estado del juego es gameOver
     */
    private boolean gameOver;
    /**
     * Indica si el estado del juego es ganado
     */
    private boolean win;
    /**
     * Indica si se sigue jugando
     */
    private boolean jugando;
    /**
     * Anchura de la pantalla
     */
    private final int WIDTH;
    /**
     * Altura de pantalla
     */
    private final int HEIGHT;
    /**
     * Imagen de corazon normal
     */
    private final BufferedImage HEART = LoadTools.loadImage("/images/heart.png");
    /**
     * Imagen de corazon roto significando que esa vida falta
     */
    private final BufferedImage HEART_BROKEN = LoadTools.loadImage("/images/heart_broken.png");
    /**
     * Imagen para la pantalla gameover
     */
    private final BufferedImage PANTALLA_GAMEOVER = LoadTools.loadImage("/images/gameOver.png");
    /**
     * Imagen para la pantalla nivel completado
     */
    private final BufferedImage PANTALLA_WIN = LoadTools.loadImage("/images/win.png");
    /**
     * ArrayList donde se guardan todos los entes que se encuentran en el mapa e
     * interactuarán entre si
     */
    private ArrayList<Entidad> entesEnMapa = new ArrayList();
    /**
     * Teclado que usará el usuario para controlar el personaje
     */
    private final Teclado teclado;
    /**
     * Tiempo de inicio de la pantalla para que tenga un tiempo minimo
     */
    private long timeInicio;
    /**
     * Raton que controlara el usuario para pulsar los botones
     */
    private final Mouse raton;
    /**
     * Velocidad de la escena
     */
    private int speed = 4;
    /**
     * Si esto es true, entonces se va de este Gestor al Gestor de juegos,
     * eliminando este Gestor por completo
     */
    private boolean salir;
    /**
     * Interfaz de pausa que se invocará cuando este en pausa el juego
     */
    private InterfazPausa interfaz = null;
    /**
     * Indica si el mapa se esta moviendo para aplicar su respectiva velocidad
     * sobre los personajes
     */
    private static boolean mapaMoving = false;
    /**
     * Las vidas que tiene el jugador
     */
    private int vidasJugador = 0;
    /**
     * Botones que representa los paneles de texto del tutorial
     */
    private Boton[] tutorial;
    /**
     * El hilo del cronometro, que se iniciara al iniciar la partida y se parará
     * en pausa, si se gana se captura su tiempo
     */
    private Cronometro timer;
    /**
     * Fuente que usarán los textos del tutorial
     */
    protected final Font font = LoadTools.loadFont("/fonts/kongtext.ttf");

    /**
     * Crea el gestor de juegos actual
     *
     * @param width int Anchura de la pantalla
     * @param height int Altura de la pantalla
     * @param ID_MAPA int ID del mapa
     * @param teclado Teclado teclado que controla al jugador
     * @param raton Mouse raton que se usa como deteccion a los botones
     */
    public GestorJuego(int width, int height, int ID_MAPA,final Teclado teclado,final Mouse raton) {
        mapa = new Mapa(width, height, ID_MAPA);//Creo el mapa
        this.WIDTH = width;
        this.HEIGHT = height;
        this.teclado = teclado;
        interfaz = null;
        this.raton = raton;
        this.salir = false;
        entesEnMapa = mapa.entesEnMapa;
        this.jugador = new Jugador(100, 100, 107, 69, teclado);
        entesEnMapa.add(jugador);
        teclado.resetAllKeys();//Reseteo las teclas por si hubiese alguna pulsada
        if (mapa.getID() == 1) {
            /**
             * Si es el tutorial(mapa 1) entonces se invocan los botones adecuados
             */
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
     * Actualiza todos los elementos que hay en juego, pasando por varias fases
     */
    @Override
    public void actualizar() {
        if (cambiarAInterfazPausa()) {//Comprueba si esta eb pausa
            timer.setPause(true);//Paramos el contador si es asi
            return;
        }
        timer.setPause(false);//Si no es asi seguimos el contador
        if (gameOver || win) {//Si ha ganado o perdido(se establece al final)
            waitScreen();//Espera mientras se muestras
            timer.stop();
            return;
        }
        detMovimientoJugador();//Determinamos el movimiento del jugador
        actualizarPosicionJugadorEnEnemigo();//Damos la posicion del jugador a los enemigos
        actualizarColisiones();//Actualizamos las colisiones de todos los entes
        cancelarMovimiento();//Cancelamos el movimiento segun las colisiones calculadas
        actualizarJugador();//Actualizamos segun los movimientos ya cancelados y determinados del jugador
        mover();//Movemos todo lo demas
        if (jugador.isDead()) {//Si el jugador esta muerto entramos en gameover
            this.gameOver = true;
            this.timeInicio = System.nanoTime();
        } else if (this.win()) {//Si el jugador ha ganado entramos en pantalla de nivel completado
            win = true;
            this.timeInicio = System.nanoTime();
            tiempoAlcanzado = timer.getTimeInSeconds();
            ArrayList<Integer> levels = GestorEstados.partida.getNivelesDesbloqueados();//Obtenemos los tiempos de la partida
            try {
                int tiempoAnterior = levels.get(mapa.getID() - 1);//Intenamos cojer el tiempo
                if (tiempoAnterior <= tiempoAlcanzado) {//Si lo hemos conseguido comprobamos si tenemos mejor tiempo
                    return;
                }
                levels.set(mapa.getID() - 1, (int) tiempoAlcanzado);//Lo intenamos asignar
            } catch (Exception e) {
                levels.add((int) tiempoAlcanzado);//Si no nos deja es porque el nivel no existe todavia, asi que lo añadimos al arrayList
            }
        }

    }

    /**
     * Tiempo de espera de pantalla de gameover o nivel completado
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

        if ((teclado.escape || !Doomies.onTop()) && (!win && !gameOver)) {//Si la pantalla no esta en primer plano o se ha dado al escape, se pausa
            interfaz = new InterfazPausa(WIDTH, HEIGHT, teclado, raton);
            teclado.resetAllKeys();//Resetamos teclas para que no se queden presionadas
        }
        if (interfaz != null) {//Si la interfaz no es nula se actualiza y se muestran sus opciones
            interfaz.actualizar();
            if (interfaz.isSalir()) {
                salir = true;
            }
            if (interfaz.isJugar()) {//Si esta jugando no hay interfaz con lo cual se 
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

        if (gameOver) {//Si esta en gameover dibujamos y volvemos
            this.dibujarGameOver(g);
            return;
        } else if (this.win) {//Si esta en pantalla de nivel completado dibujamos y volvemos
            this.dibujarWin(g);
            return;
        }
        mapa.dibujar(g);//Dibujamos el mapa(background)
        if (this.tutorial != null) {//Si los cuadros de texto del tutorial no son nulos los mostramos
            for (int i = 0; i < tutorial.length; i++) {
                tutorial[i].dibujar(g);
            }
        }
        //Dibujamos todos los entes visibles
        for (int i = 0; i < entesEnMapa.size(); i++) {
            if (entesEnMapa.get(i).isVisible()) {
                entesEnMapa.get(i).dibujar(g);
            }

        }
        if (interfaz != null) {//Si la interfaz no es nula se dibuja, es nula si no esta en pausa
            interfaz.dibujar(g);
        }
        dibujarVidas(g);//Dibujamos las vidas
        g.setColor(Color.WHITE);
        g.setFont(font.deriveFont(40f));
        g.drawString(timer.getFormatTime(), 100, 700);//Dibujamos el tiempo
    }

    /**
     * A traves de lo controles de los listeners se determina el movimiento del
     * jugador
     */
    public void detMovimientoJugador() {
        xa = 0;//Resetamos toda la velocidad
        jugador.setXa(0);
        if (jugador.getHitbox().x + jugador.getHitbox().width >= WIDTH) {//Si en los bordes del mapa cancelamos los movimientos del teclado
            teclado.right = false;
        } else if (jugador.getHitbox().x <= 0) {
            teclado.left = false;
        }
        if (!(teclado.left || teclado.right || teclado.jumping || teclado.shooting)) {//Si no se hace ningun movimiento no le damos ninguna velocidad
            return;
        }
        xa = (teclado.left) ? -speed : (teclado.right) ? speed : 0;//Segun que tecla haya pulsado le damos una velocidad
        xa *= (teclado.running) ? 2 : 1;//Si corre le multiplicamos la velocidad por 2
        jugador.setXa(xa);//Definimos la velocidad
        if (teclado.shooting) {// Si esta disparando
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
                if (entesEnMapa.get(i) instanceof Bala) {//Las balas no visibles se eliminan
                    entesEnMapa.set(i, null);
                }
            }
            if (entesEnMapa.get(i) instanceof Enemigo) {
                Enemigo eActual = (Enemigo) entesEnMapa.get(i);
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
            if (entesEnMapa.get(i) instanceof SerVivo) {//Segun el tipo de ser vivo que sea(enemigos del tipo 3 y 2) caeran o no
                if (entesEnMapa.get(i) instanceof Enemigo) {
                    Enemigo eActual = (Enemigo) entesEnMapa.get(i);
                    if (!(eActual.getEnemyType() == 3 || eActual.getEnemyType() == 2)) {
                        entesEnMapa.get(i).caer();
                    }
                } else {
                    entesEnMapa.get(i).caer();
                }
            }
            //Comprobacion de colisiones
            //Como no se ha comprobado todo ente "no ha colisionado" por el momento
            entesEnMapa.get(i).setCollidingXRight(false);
            entesEnMapa.get(i).setCollidingXLeft(false);
            entesEnMapa.get(i).setCollidingYUp(false);
            entesEnMapa.get(i).setCollidingYDown(false);
            for (int j = 0; j < entesEnMapa.size(); j++) {//Se comprueba las colisiones de un ente con todos  
                if (entesEnMapa.get(i) instanceof Tile) {//Si es un tile ni se comprueba ya que este no se puede chocar con nada
                    break;                               //sino que lo demas choca con el
                }
                if (i == j || entesEnMapa.get(j) == null) {//Si es null, luego desparecera, pero ahora nos lo saltamos
                    continue;
                }
                /**
                 * Si es una bala con el jugador nos lo saltamos para evitar bugs
                 */
                if ((entesEnMapa.get(i) instanceof Bala && entesEnMapa.get(j) instanceof Jugador) || (entesEnMapa.get(j) instanceof Bala && entesEnMapa.get(i) instanceof Jugador)) {
                    continue;
                }
                //Colisiones de bala enemigo
                if ((entesEnMapa.get(i) instanceof Bala && entesEnMapa.get(j) instanceof Enemigo) || (entesEnMapa.get(j) instanceof Bala && entesEnMapa.get(i) instanceof Enemigo)) {
                    Enemigo eActual = (Enemigo) ((entesEnMapa.get(i) instanceof Enemigo) ? entesEnMapa.get(i) : entesEnMapa.get(j));
                    if (this.colision(entesEnMapa.get(j).getHitbox(), entesEnMapa.get(i).getHitbox())) {//Si colisionan
                        if (entesEnMapa.get(i) instanceof Enemigo) {//Establecemos al objeto bala la colision para que desaparezca
                            entesEnMapa.get(j).setCollidingXRight(true);
                        } else {
                            entesEnMapa.get(i).setCollidingXRight(true);
                        }
                        eActual.perderVida();//A enemigo le quitamos una vida
                    }
                    continue;//No hace falta seguir comprobando para el conjunto enemigo/bala
                }
                //Colisiones de jugador enemigo
                if ((entesEnMapa.get(i) instanceof Jugador && entesEnMapa.get(j) instanceof Enemigo) || (entesEnMapa.get(j) instanceof Jugador && entesEnMapa.get(i) instanceof Enemigo)) {
                    Jugador eActual = (Jugador) ((entesEnMapa.get(j) instanceof Enemigo) ? entesEnMapa.get(i) : entesEnMapa.get(j));//Capturamos al jugador
                    if (this.colision(entesEnMapa.get(j).getHitbox(), entesEnMapa.get(i).getHitbox())) {//Vemos si colisionan
                        eActual.perderVida();//Hacemos que el jugador pierda vida
                    }
                    continue;
                }
                if (entesEnMapa.get(i) instanceof Enemigo && entesEnMapa.get(j) instanceof Enemigo) {//Los enemigos no colisionan entre si asi que nos lo saltamos
                    continue;
                }
                if (entesEnMapa.get(i) instanceof Bala && entesEnMapa.get(j) instanceof Tile && this.colision(entesEnMapa.get(i).getHitbox(), entesEnMapa.get(j).getHitbox())) {//Las balas desaparecen si chocan contra los tiles
                    entesEnMapa.set(i, null);
                    break;
                }
                entesEnMapa.get(i).isGoingToCollide(entesEnMapa.get(j).getHitbox());//Comprobamos si va a colisionar
            }
            if (entesEnMapa.get(i) == null) {//Si es null lo quitamos y seguimos
                entesEnMapa.remove(i);
                continue;
            }
            /**
             * Si es una bala y ha colsionado en algun lado la eliminamos
             */
            if ((entesEnMapa.get(i).isCollidingXLeft() || entesEnMapa.get(i).isCollidingXRight() || entesEnMapa.get(i).isCollidingYUp() || entesEnMapa.get(i).isCollidingYDown()) && entesEnMapa.get(i) instanceof Bala) {
                entesEnMapa.remove(i);
                continue;
            }
            //Si es un enemigo y ha caido del mapa lo matamos
            if (entesEnMapa.get(i) instanceof Enemigo && fallOutSideMap(entesEnMapa.get(i).getHitbox())) {
                Enemigo eActual = (Enemigo) entesEnMapa.get(i);
                eActual.setVida(0);
            }
            if (isOutSideScreen(entesEnMapa.get(i).getHitbox())) {//Comprobamos si esta fuera o no de la pantalla
                entesEnMapa.get(i).setVisible(false);
            } else {
                entesEnMapa.get(i).setVisible(true);
            }
        }
    }
    /**
     * Damos la posicion del jugador al enemigo
     */
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
        for (int i = 0; i < entesEnMapa.size(); i++) {//Recorre ente a ente para moverlo y actualizarlo
            if (entesEnMapa.get(i) instanceof Jugador) {//Al jugador lo haremos en un metodo a parte
                continue;
            }
            if (!mapaMoving) {//Si el mapa no se mueve no tenemos que añadir ninguna velocidad
                entesEnMapa.get(i).setXa(0);
                mapa.setXa(0);
            } else {//Sino le añadimos la velocidad correspondiente
                mapa.setXa(-xa);
                entesEnMapa.get(i).setXa(-xa);
            }
            entesEnMapa.get(i).actualizar();//Al final lo actualizamos
        }
        if (this.tutorial != null && mapaMoving) {//Si el mapa se esta moviendo y el tutorial no es nulo, hay que aplicar velocidad en este
            for (int i = 0; i < tutorial.length; i++) {
                tutorial[i].moveInX(-xa);
            }
        }
        mapa.actualizar();//Actualizamos el mapa
        jugador.addBalasAsEntidad(entesEnMapa);//Añadimos las balas que correspondan

    }

    /**
     * Actualiza el estado del jugador y su velocidad respeto a la pantalla
     */
    private void actualizarJugador() {
        mapaMoving = false;
        int middleScreenPos = WIDTH / 2 - jugador.getHitbox().width;//Posicion que define que el jugador este en el centro de la pantalla
        if (mapa.isLimit() && xa != 0) {
            /**
             * Esto quiere decir que el mapa esta en el limite izquierdo(de donde
             * parte)
             */
            if (jugador.getHitbox().x < middleScreenPos) {//Si esta fuera del centro de la pantalla se le aplica la velocida solo a el y el mapa no se mueve
                jugador.setXa(xa);
            } else if (xa > 0) {//Si no se le considera quieto en relacion a la pantalla
                jugador.setXa(0);
                mapaMoving = true;
            }

        } else if (mapa.isLimit_fin() && xa != 0) {
            /**
             * Esto quiere decir que el mapa esta en el limite derecho(donde
             * termina)
             */
            if (jugador.getHitbox().x > middleScreenPos) {//Si esta fuera del centro de la pantalla se le aplica la velocida solo a el y el mapa no se mueve
                jugador.setXa(xa);
            } else if (xa < 0) {//Si no se le considera quieto en relacion a la pantalla
                jugador.setXa(0);
                mapaMoving = true;
            }
        } else if (xa != 0 && (middleScreenPos + 10 > jugador.getHitbox().x && middleScreenPos - 10 < jugador.getHitbox().x)) {//Si esta en el centro de la pantalla y se esta moviendo
            //Se mueve todo
            if (jugador.isCollidingXRight() && xa > 0) {//Si colisiona a la derecha
                xa = 0;
            } else if (jugador.isCollidingXLeft() && xa < 0) {//Si colisiona a la izquierda
                xa = 0;
            } else {//Si no colisiona
                jugador.setXa(0);
                mapaMoving = true;
            }
        }
        jugador.actualizar();//Actualizamos al jugador
        if (fallOutSideMap(jugador.getHitbox())) {//Si sale del mapa le quitamos una vida y reseteamos su posicion
            jugador.resetPos();
            this.mapaMoving = true;
            jugador.perderVida();
        }
        this.definirEstadoJugador();//Definimos su estado y sprites
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
     * Comprueba colision de choque entre dos rectangulos
     *
     * @param r1 Rectangle que representa el primer rectangulo.
     * @param r2 Rectangle que representa el segundo rectangulo.
     * @return Devuelve true o false si a colisionado o no.
     */
    public boolean colision(Rectangle r1, Rectangle r2) {
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
     * @param rect Rectangulo que se comprueba si esta fuera del mapa
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
    private void dibujarGameOver(Graphics g) {
        g.drawImage(PANTALLA_GAMEOVER, 0, 0, null);
    }

    /**
     * Dibuja la pantalla de nivel finalizado
     *
     * @param g Graphics que dibuja la pantalla de nivel finalizado
     * @see GestorJuego#dibujar(java.awt.Graphics)
     */
    private void dibujarWin(Graphics g) {
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
    /**
     * Obtienes la velocidad en el eje x de la escena en general
     * 
     * @return La velocidad del eje x
     */
    public static int getXa() {
        return GestorJuego.xa;
    }
}
