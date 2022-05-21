package prototype.HerramientasEntradaSalida;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Víctor Zero
 */
//CLASE TECLADO:
//      ES UN LISTENER QUE SE ENCARGA DE VER SI SE PULSA O NO UNA TECLA
public class Teclado implements KeyListener {

    //Se usan booleans para confirmar si la tecla pulsada es la correcta
    public boolean left;
    public boolean right;
    public boolean running;
    public boolean jumping;
    public boolean shooting;
    public boolean escape;

    public Teclado() {//se inicializan todas en false
        left = false;
        right = false;
        running = false;
        jumping = false;
        shooting = false;
        escape = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {//NO SE NECESITA IMPLEMENTAR
    }

    @Override
    public void keyPressed(KeyEvent e) {//Si se ha pulsado D es derecha si es A es izquierda, shift correr y espacio es saltar
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                right = true;
                break;
            case KeyEvent.VK_LEFT:
                left = true;
                break;
            case KeyEvent.VK_SHIFT:
                running = true;
                break;
            case KeyEvent.VK_UP:
                jumping = true;
                break;
            case KeyEvent.VK_C:
                shooting = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {//si se suelta la tecla entonces se cancela este boolean y se pone en false
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                right = false;
                break;
            case KeyEvent.VK_LEFT:
                left = false;
                break;
            case KeyEvent.VK_SHIFT:
                running = false;
                break;
            case KeyEvent.VK_UP:
                jumping = false;
                break;
            case KeyEvent.VK_C:
                shooting = false;
                break;
            case KeyEvent.VK_ESCAPE:
                escape = true;
                break;
        }
    }

    public void resetKeys() {
        escape = false;
        
    }
}
