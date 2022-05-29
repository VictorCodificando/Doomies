/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package doomies.Interfaces.Elementos;

import doomies.Doomies;
import doomies.HerramientasEntradaSalida.Mouse;
import doomies.HerramientasEntradaSalida.Teclado;
import doomies.Interfaces.Interfaz;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

/**
 *
 * @author Víctor Zero
 */
public class InterfazOpciones extends Interfaz {

    /**
     * Definicion de variables
     */
    private final Boton botonJugar;
    private final int DERECHA_ID = 0;
    private final int IZQUIERDA_ID = 1;
    private final int SALTAR_ID = 2;
    private final int CORRER_ID = 3;
    private final int DISPARAR_ID = 4;
    private final Boton[] botonesTeclas;
    private final Boton checkVsync;
    private final Boton check60fps;
    private final Boton guardar;
    private int idActual = 0;
    private boolean introduciendoLetra = false;

    /**
     *
     * @param WIDTH Altura de la interfaz
     * @param HEIGHT Anchura de la interfaz
     * @param teclado Teclado que podra usar la interfaz
     * @param raton Raton que usaran los botones de la interfaz
     */
    public InterfazOpciones(final int WIDTH, final int HEIGHT, final Teclado teclado, final Mouse raton) {
        super(WIDTH, HEIGHT, teclado, raton);
        botonJugar = new Boton(450, 80, 400, 70, "JUGAR", font.deriveFont(20f), Color.gray, -10, 4, raton);
        botonesTeclas = new Boton[5];
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

    @Override
    public void dibujar(final Graphics g) {
        //gradiente
        Graphics2D g2d = (Graphics2D) g;
        GradientPaint verticalGradient = new GradientPaint(0, 0, Color.RED, 0, this.HEIGHT, Color.ORANGE);
        g.setColor(Color.red);
        g2d.setPaint(verticalGradient);
        g2d.fillRect(0, 0, this.WIDTH, this.HEIGHT);

        //Botones
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
        checkVsync.dibujar(g);
        check60fps.dibujar(g);
        for (int i = 0; i < botonesTeclas.length; i++) {
            botonesTeclas[i].dibujar(g);
        }
        guardar.dibujar(g);

    }

    @Override
    public void actualizar() {
        if (introduciendoLetra) {
            capturarTeclaInBoton(botonesTeclas[idActual]);
            return;
        }
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
        if (checkVsync.isClicked()) {
            if (checkVsync.getLabel().equals("")) {
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
        if (check60fps.isClicked()) {
            if (check60fps.getLabel().equals("")) {
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

    public boolean isGuardar() {
        return this.guardar.isClicked();
    }

}
