package doomies.HerramientasEntradaSalida;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

/**
 * CLASE TECLADO:ES UN LISTENER QUE SE ENCARGA DE VER SI SE PULSA O NO UNA TECLA
 *
 * @author Víctor
 * @version 4
 * @since 1
 */
public class Teclado implements KeyListener {

    //Se usan booleans para confirmar si la tecla pulsada es la correcta
    /**
     * Si se ha pulsado la tecla izquierda
     */
    public boolean left;
    /**
     * Si se ha pulsado la tecla derecha
     */
    public boolean right;
    /**
     * Si se ha pulsado la tecla de correr
     */
    public boolean running;
    /**
     * Si se ha pulsado la tecla de salto
     */
    public boolean jumping;
    /**
     * Si se ha pulsado la tecla de disparar
     */
    public boolean shooting;
    /**
     * Si se ha pulsado la tecla de escape
     */
    public boolean escape;
    /**
     * Indica la tecla actual pulsada
     *
     * @see doomies.Interfaces.InterfazOpciones
     */
    public int teclaActual;
    /**
     * Tecla izquierda
     */
    public int leftKey;
    /**
     * Tecla derecha
     */
    public int rightKey;
    /**
     * Tecla correr
     */
    public int runKey;
    /**
     * Tecla de salto
     */
    public int jumpKey;
    /**
     * Tecla de disparo
     */
    public int shootKey;
    /**
     * Si la tecla se esta pulsando
     */
    public boolean teclaPulsada = false;
    /**
     * El caracter actual de la tecla
     */
    public String caracterActual;

    /**
     * Crea un teclado nuevo con las teclas por defecto
     */
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
    }

    /**
     * Nuevo teclado pero metiendo numeros de teclas personalizados
     *
     * @param lKey Tecla izquierda
     * @param rKey Tecla derecha
     * @param runKey Tecla correr
     * @param jumpKey Tecla saltar
     * @param shootKey Tecla disparar
     */
    public Teclado(int lKey, int rKey, int runKey, int jumpKey, int shootKey) {
        this();
        this.leftKey = lKey;
        this.rightKey = rKey;
        this.runKey = runKey;
        this.jumpKey = jumpKey;
        this.shootKey = shootKey;

    }

    /**
     * @deprecated
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Establece en true las teclas pulsadas
     *
     * @param e Evento
     */
    @Override
    public void keyPressed(KeyEvent e) {
        right = (e.getKeyCode() == rightKey) ? true : right;
        left = (e.getKeyCode() == leftKey) ? true : left;
        running = (e.getKeyCode() == runKey) ? true : running;
        jumping = (e.getKeyCode() == jumpKey) ? true : jumping;
        shooting = (e.getKeyCode() == shootKey) ? true : shooting;

    }

    /**
     * Establece en false las teclas soltadas,menos escape
     *
     * @param e Evento
     */
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

    /**
     * Reseta las teclas que se establecen en true al ser soltadas
     */
    public void resetKeys() {
        escape = false;
        teclaPulsada = false;
    }

    /**
     * Restea todas las teclas
     */
    public void resetAllKeys() {
        left = false;
        right = false;
        running = false;
        jumping = false;
        shooting = false;
        escape = false;

    }
}
