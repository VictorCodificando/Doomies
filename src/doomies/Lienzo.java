package doomies;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.Toolkit;
import java.awt.Dimension;
import doomies.HerramientasEntradaSalida.Mouse;
import doomies.HerramientasEntradaSalida.Teclado;
import doomies.Gestores.Pantalla;
import doomies.HerramientasEntradaSalida.LoadTools;
import java.awt.Canvas;

/**
 * Clase usada para dibujar todo, es donde se crea la estrategia de Buffer usada
 * por todo el programa
 *
 * @author Víctor
 * @version 4
 * @since 1
 */
public class Lienzo extends Canvas {

    /**
     * Pantalla donde se dibujara todo
     */
    private final Pantalla pantalla;
    /**
     * Teclado que pasaremos por toda la jerarquia
     */
    private final Teclado teclado;
    /**
     * Raton que pasaremos por toda la jerarquia
     */
    private final Mouse raton;

    /**
     * Crea el lienzo que es donde se dibujara todo en el programa
     *
     * @param WIDTH La anchura del lienzo
     * @param HEIGHT La altura del lienzo
     */
    public Lienzo(final int WIDTH, final int HEIGHT) {
        this.teclado = LoadTools.createTeclado();
        this.raton = new Mouse((int) this.getLocation().getX(), (int) this.getLocation().getY());
        pantalla = new Pantalla(WIDTH, HEIGHT, teclado, raton);//le pasamos el tamaño y el teclado para bajarlo en la jerarquia
        setPreferredSize(new Dimension(WIDTH, HEIGHT));//definimos tamaño del lienzo
        setFocusable(true);//Para que se pueda hacer focus a un listener(sin esto el teclado no funciona)
        addKeyListener(teclado);//Añade el teclado ya inicializado como un listener
        addMouseListener(raton);
    }

    /**
     * Crea el BufferStrategy, que es la estrategia de guardado en memoria que
     * va a usar nuestro graphics BUFFER: Guardado en memoria de bytes En este
     * caso es un bufferImage, lo cual ayuda que no parpadee la pantalla
     */
    public void dibujar() {//Dibujamos TODO lo que hay en la pantalla
        BufferStrategy buffer = getBufferStrategy();//Declaramos el buffer
        if (buffer == null) {//Si el buffer al que apuntamos es nulo, declaramos uno triple(funciona mejor)
            createBufferStrategy(3);
            return;
        }
        Graphics g = buffer.getDrawGraphics();
        pantalla.dibujar(g);//Dibujamos todo de la pantalla
        Toolkit.getDefaultToolkit().sync();//Esto se encarga de sincronizarse con la pantalla
        g.dispose();//Desechamos la memoria que ocupaba g
        buffer.show();//Enseñamos lo que hay en el buffer
    }

    /**
     * Se actualizaran todas las variables partiendo desde aqui
     */
    public void actualizar() {
        raton.setAjusteX((int) this.getLocationOnScreen().getX());
        raton.setAjusteY((int) this.getLocationOnScreen().getY());
        pantalla.actualizar();//Actualiza todo lo que halla en pantalla
        raton.actualizarCoordenadas();
        teclado.resetKeys();
    }
}
