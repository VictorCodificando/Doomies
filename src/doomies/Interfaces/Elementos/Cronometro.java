/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package doomies.Interfaces.Elementos;

/**
 *
 * @author Víctor Zero
 */
public class Cronometro extends Thread {

    private volatile long timeNow;//IN SECONDS
    private long timeInSeconds;
    private long ZERO;
    private volatile String formatTime = "0:00:00";
    private volatile boolean pause = false;

    public Cronometro(long ZERO) {
        this.ZERO = ZERO;
        timeNow = 0;
        this.setName("Contador partida");
    }

    public void run() {
        while (timeInSeconds < 1000000) {
            timeNow = System.nanoTime();
            if (pause) {
                pause();
                continue;
            }
            if (System.nanoTime() - ZERO >= 1000000000) {
                passOneSecond();
                ZERO = System.nanoTime();
            }
        }
    }

    private void pause() {
        long dif = System.nanoTime() - ZERO;
        ZERO = System.nanoTime() - dif;
        timeNow = System.nanoTime();
    }

    private void passOneSecond() {
        timeInSeconds++;
        formatTime = String.format("%d:%02d:%02d", timeInSeconds / 3600, (timeInSeconds % 3600) / 60, (timeInSeconds % 60));
    }

    public String getFormatTime() {
        return formatTime;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public long getTimeInSeconds() {
        return timeInSeconds;
    }

}
