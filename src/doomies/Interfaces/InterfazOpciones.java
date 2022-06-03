/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package doomies.Interfaces;

import doomies.Doomies;
import doomies.HerramientasEntradaSalida.Mouse;
import doomies.HerramientasEntradaSalida.Teclado;
import doomies.Interfaces.Elementos.Boton;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

/**
 * Interfaz con opciones de cambio de controles y de fps
 *
 * @author Víctor
 * @version 4
 * @since 2
 */
public class InterfazOpciones extends Interfaz {

    /**
     * ID que indica posicion del boton de la tecla derecha
     */
    private final int DERECHA_ID = 0;
    /**
     * ID que indica posicion del boton de la tecla izquierda
     */
    private final int IZQUIERDA_ID = 1;
    /**
     * ID que indica posicion del boton de la tecla de saltar
     */
    private final int SALTAR_ID = 2;
    /**
     * ID que indica posicion del boton de la tecla de correr
     */
    private final int CORRER_ID = 3;
    /**
     * ID que indica posicion del boton de la tecla de disparar
     */
    private final int DISPARAR_ID = 4;
    /**
     * Array de botones que contiene todos los botones
     */
    private final Boton[] botonesTeclas;
    /**
     * Boton que contiene el check de la sincronizacion vertical
     */
    private final Boton checkVsync;
    /**
     * Boton que contiene el check del limitado a 60 fps
     */
    private final Boton check60fps;
    /**
     * Boton de guardado de opciones
     */
    private final Boton guardar;
    /**
     * Indica la id de la que se esta introduciendo una tecla
     */
    private int idActual = 0;
    /**
     * Indica si se esta introduciendo una tecla
     */
    private boolean introduciendoLetra = false;

    /**
     * Crea la interfaz de opciones
     *
     * @param WIDTH Altura de la interfaz
     * @param HEIGHT Anchura de la interfaz
     * @param teclado Teclado que podra usar la interfaz
     * @param raton Raton que usaran los botones de la interfaz
     */
    public InterfazOpciones(final int WIDTH, final int HEIGHT, final Teclado teclado, final Mouse raton) {
        super(WIDTH, HEIGHT, teclado, raton);
        botonesTeclas = new Boton[5];
        /**
         * Muestra las teclas actuales
         */
        botonesTeclas[DERECHA_ID] = new Boton(600, 120, 200, 40, KeyEvent.getKeyText(teclado.rightKey), font.deriveFont(20f), Color.gray, 6, 3, raton);
        botonesTeclas[IZQUIERDA_ID] = new Boton(600, 180, 200, 40, KeyEvent.getKeyText(teclado.leftKey), font.deriveFont(20f), Color.gray, 6, 3, raton);
        botonesTeclas[SALTAR_ID] = new Boton(600, 240, 200, 40, KeyEvent.getKeyText(teclado.jumpKey), font.deriveFont(20f), Color.gray, 6, 3, raton);
        botonesTeclas[CORRER_ID] = new Boton(600, 300, 200, 40, KeyEvent.getKeyText(teclado.runKey), font.deriveFont(20f), Color.gray, 6, 3, raton);
        botonesTeclas[DISPARAR_ID] = new Boton(600, 360, 200, 40, KeyEvent.getKeyText(teclado.shootKey), font.deriveFont(20f), Color.gray, 6, 3, raton);
        checkVsync = new Boton(900, 460, 70, 70, (Doomies.vSync) ? "X" : "", font.deriveFont(30f), Color.gray, 10, 5, raton);
        checkVsync.setFormat(14);
        check60fps = new Boton(900, 560, 70, 70, (Doomies.fps60) ? "X" : "", font.deriveFont(30f), Color.gray, 10, 5, raton);
        check60fps.setFormat(14);
        guardar = new Boton(400, 635, 500, 70, "Guardar", font.deriveFont(30f), Color.gray, -10, 3, raton);
    }

    /**
     * Dibuja las opciones: controles y ajustes fps
     *
     * @param g Clase graphics que dibuja todo en la pantalla
     */
    @Override
    public void dibujar(final Graphics g) {
        /**
         * Fondo con gradiente
         */
        Graphics2D g2d = (Graphics2D) g;
        GradientPaint verticalGradient = new GradientPaint(0, 0, Color.RED, 0, this.HEIGHT, Color.ORANGE);
        g.setColor(Color.red);
        g2d.setPaint(verticalGradient);
        g2d.fillRect(0, 0, this.WIDTH, this.HEIGHT);
        /**
         * Letras en pantalla
         */
        g.setColor(Color.BLACK);
        g.setFont(font);
        g.setFont(g.getFont().deriveFont(55f));
        g.drawString("Controles", ((WIDTH / 2) - (g.getFont().getSize() * (("Controles").length() / 2)) - 20), 70);
        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(25f));
        g.drawString("Derecha:", 300, 150);
        g.drawString("Izquierda:", 300, 210);
        g.drawString("Saltar:", 300, 270);
        g.drawString("Correr:", 300, 330);
        g.drawString("Disparar:", 300, 390);
        g.drawString("Vsync(Experimental):" + "      ", 300, 500);
        g.drawString("Limite 60 fps:" + "      ", 300, 600);
        /**
         * Dibuja los botones
         */
        checkVsync.dibujar(g);
        check60fps.dibujar(g);
        for (int i = 0; i < botonesTeclas.length; i++) {
            botonesTeclas[i].dibujar(g);
        }
        guardar.dibujar(g);

    }

    /**
     * Actualiza la interfaz
     */
    @Override
    public void actualizar() {
        if (introduciendoLetra) {//Se esta introduciendo una letra
            capturarTeclaInBoton(botonesTeclas[idActual]);//Capturamos la letra
            return;
        }
        /**
         * Si no actualizamos lo demas
         */
        /**
         * BOTONES
         */
        guardar.actualizar();
        for (int i = 0; i < botonesTeclas.length; i++) {
            botonesTeclas[i].actualizar();
            if (botonesTeclas[i].isClicked()) {
                idActual = i;
                introduciendoLetra = true;
                return;
            }
        }
        checkVsync.actualizar();
        check60fps.actualizar();
        /**
         * Comprubea si esta pulsado o no
         */
        if (checkVsync.isClicked()) {//Si se va a pulsar vsync es para activarlo o desactivarlo
            if (checkVsync.getLabel().equals("")) {//Si es para activarlo se comprueba que los 60 fps esten desactivados, si no es asi, lo desactivan
                if (check60fps.getLabel().equals("X")) {
                    check60fps.setLabel("");
                    Doomies.fps60 = false;
                }
                checkVsync.setLabel("X");
                Doomies.vSync = true;
            } else {
                checkVsync.setLabel("");
                Doomies.vSync = false;
            }
        }
        if (check60fps.isClicked()) {//Si se va a pulsar 60 fps es para activarlo o desactivarlo
            if (check60fps.getLabel().equals("")) {//Si es para activarlo se comprueba que los vsync esten desactivados, si no es asi, lo desactivan
                check60fps.setLabel("X");
                if (checkVsync.getLabel().equals("X")) {
                    checkVsync.setLabel("");
                    Doomies.vSync = false;
                }
                Doomies.fps60 = true;
            } else {
                check60fps.setLabel("");
                Doomies.fps60 = false;
            }
        }
    }
    /**
     * Captura cualquier letra y lo sustituye dependiendo de cual se haya pulsado
     * @param bt Boton que se esta capturando
     */
    private void capturarTeclaInBoton(final Boton bt) {
        if (!this.teclado.teclaPulsada) {
            return;
        }
        bt.setLabel(this.teclado.caracterActual);
        introduciendoLetra = false;
        switch (idActual) {
            case DERECHA_ID:
                this.teclado.rightKey = teclado.teclaActual;
                break;
            case IZQUIERDA_ID:
                this.teclado.leftKey = teclado.teclaActual;
                break;
            case SALTAR_ID:
                this.teclado.jumpKey = teclado.teclaActual;
                break;
            case CORRER_ID:
                this.teclado.runKey = teclado.teclaActual;
                break;
            case DISPARAR_ID:
                this.teclado.shootKey = teclado.teclaActual;
                break;
            default:
                break;
        }

    }
    /**
     * 
     * @return Si se ha pulsado en guardar, se guardara y se saldrá
     */
    public boolean isGuardar() {
        return this.guardar.isClicked();
    }

}
