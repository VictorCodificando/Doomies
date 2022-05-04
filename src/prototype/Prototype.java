package prototype;

/**
 *
 * @author Víctor Zero
 */
//CLASE PRINCIPAL QUE TENDRA EL MAIN Y LAS CLASES QUE INICIALIZAN EL DIBUJO
public class Prototype {

    //Declaramos la ventana y el lienzo(donde se dibujaran las cosas)
    private Ventana ventana;
    private Lienzo canvas;
    private boolean enEjecucion = false;//Variable que funcionara como bucle infinito

    public Prototype(int width, int height) {//inicializo el canvas primero y luego se lo paso al frame(ventana)
        this.canvas = new Lienzo(width, height);
        this.ventana = new Ventana(canvas);
    }

    public static void main(String[] args) {//Inicializo la clase principal con las medidas adecuadas(se pueden cambiar
        Prototype pr = new Prototype(1280, 720);
        pr.iniciar();
    }

    private void iniciar() {//Dice que esta en ejecucion y empieza el contador
        enEjecucion = true;
        bucleContador();
    }

    private void bucleContador() {//El bucle contador llama a actualizar y a llamar
        long actual = 0;
        long anterior = 0;
        long contador = System.nanoTime();
        long dif = 0;
        int fps = 0;
        int aps = 0;
        while (enEjecucion) {
            actual = System.nanoTime();//coje el tiempo actual en nanosegundos 1* 10 E9
            dif = actual - anterior;//mide la diferencia con la anterior iteracion
            if ((dif) > (1000000000 / 60)) {//Dice si la diferencia llega al tiempo exacto para que halla 60 actualizaciones por segundo
                anterior = System.nanoTime();
                actualizar();//actualiza las variables
                aps++;
            }
            dibujar();//luego dibuja y aumenta los fps
            fps++;
            if ((actual - contador) >= 1000000000) {//Cada segundo se ejecuta y muestra cuantos fps y aps hay
                contador = System.nanoTime();
                ventana.setTitle("Prototype APS: " + aps + "    FPS: " + fps);
                aps = 0;
                fps = 0;
            }
        }
    }

    private void actualizar() {//Actualizar mas elevado en jerarquia
        canvas.actualizar();
    }

    private void dibujar() {//Dibujar mas elevado en jerarquia
        canvas.dibujar();
    }

}
