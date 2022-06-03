/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package doomies.Interfaces.Elementos;

/**
 * Es un hilo que se abre junto a un contador, para poder contar tiempo sin ser
 * afectado por los otros hilos
 *
 * @see doomies.Gestores.GestorJuego
 * @author Víctor
 * @version 4
 * @since 4
 */
public class Cronometro extends Thread {

    /**
     * Tiempo ahora en nanosegundos
     */
    private volatile long timeNow;
    /**
     * Timepo ahora en segundos
     */
    private long timeInSeconds;
    /**
     * Indicara el tiempo inicial
     */
    private long ZERO;
    /**
     * String que sera sacado por pantalla y tendra en cada momento las
     * horas:minutos:segundos transcurridos
     */
    private volatile String formatTime = "0:00:00";
    /**
     * Si esta en pausa
     */
    private volatile boolean pause = false;

    /**
     * Se crea estbleciendo un tiempo inicial
     *
     * @param ZERO tiempo inicial
     */
    public Cronometro(long ZERO) {
        this.ZERO = ZERO;
        timeNow = 0;
        this.setName("Contador partida");//Hace que el hilo se llame de cierta manera
    }

    /**
     * Empieza la ejecucion del contador
     */
    public void run() {
        while (true) {
            timeNow = System.nanoTime();
            if (pause) {//Si esta en pausa
                pause();
                continue;
            }
            if (System.nanoTime() - ZERO >= 1000000000) {//Si llega a un segundo, se restablece y cuenta un segundo
                passOneSecond();
                ZERO = System.nanoTime();
            }
        }
    }

    /**
     * Pausa el contador guardandose la diferencia para no perder el tiempo
     * transcurrido
     */
    private void pause() {
        long dif = System.nanoTime() - ZERO;
        ZERO = System.nanoTime() - dif;
        timeNow = System.nanoTime();
    }

    /**
     * Suma un segundo y lo pasa al String con el formato correcto
     */
    private void passOneSecond() {
        timeInSeconds++;
        formatTime = String.format("%d:%02d:%02d", timeInSeconds / 3600, (timeInSeconds % 3600) / 60, (timeInSeconds % 60));
    }

    /**
     * Devuelve el tiempo con el formato correcto
     *
     * @return Tiempo con el formato correcto
     */
    public String getFormatTime() {
        return formatTime;
    }

    /**
     * Establece si esta en pausa o no
     *
     * @param pause Pausa
     */
    public void setPause(boolean pause) {
        this.pause = pause;
    }

    /**
     * Devuelve el tiempo transcurrido en segundos
     *
     * @return tiempo en segundos
     */
    public long getTimeInSeconds() {
        return timeInSeconds;
    }

}
