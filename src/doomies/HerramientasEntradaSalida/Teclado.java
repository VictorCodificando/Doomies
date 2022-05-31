package doomies.HerramientasEntradaSalida;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

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
    public int teclaActual;
    public int leftKey;
    public int rightKey;
    public int runKey;
    public int jumpKey;
    public int shootKey;
    public boolean teclaPulsada = false;
    public String caracterActual;

    public Teclado() {//se inicializan todas en false
        left = false;
        right = false;
        running = false;
        jumping = false;
        shooting = false;
        escape = false;
        leftKey = KeyEvent.VK_LEFT;
        rightKey = KeyEvent.VK_RIGHT;
        runKey = KeyEvent.VK_SHIFT;
        jumpKey = KeyEvent.VK_UP;
        shootKey = KeyEvent.VK_C;
        System.out.println(leftKey + ";" + rightKey + ";" + runKey + ";" + jumpKey + ";" + shootKey);
    }

    public Teclado(int lKey, int rKey, int runKey, int jumpKey, int shootKey) {
        this();
        this.leftKey = lKey;
        this.rightKey = rKey;
        this.runKey = runKey;
        this.jumpKey = jumpKey;
        this.shootKey = shootKey;

    }

    @Override
    public void keyTyped(KeyEvent e) {//NO SE NECESITA IMPLEMENTAR
    }

    @Override
    public void keyPressed(KeyEvent e) {//Si se ha pulsado D es derecha si es A es izquierda, shift correr y espacio es saltar
        right = (e.getKeyCode() == rightKey) ? true : right;
        left = (e.getKeyCode() == leftKey) ? true : left;
        running = (e.getKeyCode() == runKey) ? true : running;
        jumping = (e.getKeyCode() == jumpKey) ? true : jumping;
        shooting = (e.getKeyCode() == shootKey) ? true : shooting;

    }

    @Override
    public void keyReleased(KeyEvent e) {//si se suelta la tecla entonces se cancela este boolean y se pone en false
        right = (e.getKeyCode() == rightKey) ? false : right;
        left = (e.getKeyCode() == leftKey) ? false : left;
        running = (e.getKeyCode() == runKey) ? false : running;
        jumping = (e.getKeyCode() == jumpKey) ? false : jumping;
        shooting = (e.getKeyCode() == shootKey) ? false : shooting;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                escape = true;
                break;
        }
        teclaActual = e.getKeyCode();
        teclaPulsada = true;
        caracterActual = KeyEvent.getKeyText(teclaActual);
    }

    public void resetKeys() {
        escape = false;
        teclaPulsada = false;
    }

    public void resetAllKeys() {
        left = false;
        right = false;
        running = false;
        jumping = false;
        shooting = false;
        escape = false;

    }
}
