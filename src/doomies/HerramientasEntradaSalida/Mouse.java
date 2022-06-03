package doomies.HerramientasEntradaSalida;

import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Clase raton que actua como mouseListener, esta pendiente del estado del raton
 *
 * @author Víctor
 * @version 4
 * @since 3
 *
 */
public class Mouse implements MouseListener {

    /**
     * Ajuste de x que representa la distancia entre el inicio de la pantalla y
     * el incio de la x del programa
     */
    private int AjusteX;
    /**
     * Ajuste de Y que representa la distancia entre el inicio de la pantalla y
     * el incio de la Y del programa
     */
    private int AjusteY;
    /**
     * posicion en x del raton
     */
    public int x = 0;
    /**
     * posicion en y del raton
     */
    public int y = 0;
    /**
     * Si esta pulsado
     */
    public boolean pressed;
    /**
     * Si se ha soltado
     */
    public boolean released;
    /**
     * Si se ha clickado(nos fijaremos en esta)
     */
    public boolean clicked;

    /**
     * Crea el listener del mouse que sera clave para el uso de los botones
     *
     * @param AjusteX int que ajustara la posicion del mouse dependiendo de
     * donde se encuentre la pantalla
     * @param AjusteY int que ajustara la posicion del mouse dependiendo de
     * donde se encuentre la pantalla
     */
    public Mouse(int AjusteX, int AjusteY) {
        this.AjusteX = AjusteX;
        this.AjusteY = AjusteY;
    }

    /**
     * @deprecated No usado
     * @param e Evento de mouse
     */
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    /**
     * Metodo que representa el primer movimiento posible del ciclo del mouse
     *
     * @param e Evento de mouse que recibe la información del evento acutal
     */
    @Override
    public void mousePressed(MouseEvent e) {
        pressed = true;
    }

    /**
     * Metodo que representa el segundo movimiento del ciclo del mouse
     *
     * @param e Evento de mouse que recibe la información del evento actual
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        released = true;
        clicked = true;
    }

    /**
     * @deprecated No usado
     * @param e Evento de mouse que recibe la información del evento actual
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * @deprecated No usado
     * @param e Evento de mouse que recibe la información del evento actual
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     * Actualiza las coordenadas dependiendo de la posicion de pantalla
     */
    public void actualizarCoordenadas() {
        x = Math.abs((int) MouseInfo.getPointerInfo().getLocation().x - this.AjusteX);
        y = Math.abs((int) MouseInfo.getPointerInfo().getLocation().y - this.AjusteY);
        if (clicked = true) {
            pressed = false;
            released = false;
            clicked = false;
        }
    }

    /**
     *
     * @return Devuelve el ajuste de X
     */
    public int getAjusteX() {
        return AjusteX;
    }

    /**
     *
     * @param AjusteX int el Ajuste que tendra X respeto a la posición de la
     * ventana
     */
    public void setAjusteX(int AjusteX) {
        this.AjusteX = AjusteX;
    }

    /**
     *
     * @return int el Ajuste de Y respeto a la posicion de la ventana
     */
    public int getAjusteY() {
        return AjusteY;
    }

    /**
     *
     * @param AjusteY int Ajuste Y que define el ajuste en y respeto a la
     * posicion de la ventana en pantalla.
     */
    public void setAjusteY(int AjusteY) {
        this.AjusteY = AjusteY;
    }

}
