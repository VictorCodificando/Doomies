package doomies;

import doomies.HerramientasEntradaSalida.ConectionDatabase;

/**
 * Clase principal de las que salen todas
 *
 * @author Víctor
 * @version 4
 * @since 1
 */
//CLASE PRINCIPAL QUE TENDRA EL MAIN Y LAS CLASES QUE INICIALIZAN EL DIBUJO
public class Doomies {

    //Declaramos la ventana y el lienzo(donde se dibujaran las cosas)
    /**
     * Frame principal
     */
    private final Ventana ventana;
    /**
     * Canvas donde se dibujara todo
     */
    private final Lienzo canvas;
    /**
     * Si la apliacion esta en primer plano
     */
    private static boolean onTop;
    /**
     * Nos produce un bucle infinito
     */
    private boolean enEjecucion = false;
    /**
     * Hz de la pantalla del usuario
     */
    private final long refresco = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getRefreshRate();
    /**
     * Sincronizacion vertical
     */
    public static boolean vSync = false;
    /**
     * Limitar a 60 fps
     */
    public static boolean fps60 = false;

    /**
     * Crea el juego principal
     *
     * @param width Anchura de la pantalla
     * @param height Altura de la pantalla
     */
    public Doomies(final int width, final int height) {//inicializo el canvas primero y luego se lo paso al frame(ventana)
        this.canvas = new Lienzo(width, height);
        this.ventana = new Ventana(canvas);
    }

    public static void main(String[] args) {
        //Intento conexion a la base de datos
        ConectionDatabase.intentarCargarMapas();
        //Inicializo la clase principal con las medidas adecuadas
        Doomies pr = new Doomies(1280, 720);
        pr.iniciar();
    }

    /**
     * Inicia el bucle principal
     */
    private void iniciar() {//Dice que esta en ejecucion y empieza el contador
        enEjecucion = true;
        bucleContador();
    }

    /**
     * El bucle contador llama a actualizar y a dibujar
     */
    private void bucleContador() {
        long actual = 0;
        long anterior = 0;
        long anterior2 = 0;
        long contador = System.nanoTime();
        long dif = 0;
        long dif2 = 0;
        int fps = 0;
        int aps = 0;
        while (enEjecucion) {
            actual = System.nanoTime();//coje el tiempo actual en nanosegundos 1* 10 E9
            dif = actual - anterior;//mide la diferencia con la anterior iteracion
            dif2 = actual - anterior2;
            onTop = ventana.isActive();
            if ((dif) > (1000000000 / 60)) {//Dice si la diferencia llega al tiempo exacto para que halla 60 actualizaciones por segundo
                anterior = System.nanoTime();
                actualizar();//actualiza las variables
                aps++;
            }
            if (((dif2) > (1000000000 / refresco) && vSync && !fps60) || (!vSync && !fps60) || (fps60 && (dif2) > (1000000000 / 60))) {
                anterior2 = System.nanoTime();
                dibujar();//luego dibuja y aumenta los fps
                fps++;
            }

            if ((actual - contador) >= 1000000000) {//Cada segundo se ejecuta y muestra cuantos fps y aps hay
                contador = System.nanoTime();
                ventana.setTitle("Doomies APS: " + aps + "    FPS: " + fps);
                aps = 0;
                fps = 0;
            }
        }
    }

    /**
     * Actualizar mas elevado en jerarquia
     */
    private void actualizar() {
        canvas.actualizar();
    }

    /**
     * Dibujar mas elevado en jerarquia
     */
    private void dibujar() {
        canvas.dibujar();
    }

    public static boolean onTop() {
        return onTop;
    }

}
