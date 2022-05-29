package doomies.Visual;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Sprite {

    private final int WIDTH;
    private final int HEIGHT;
    private final BufferedImage img;

    /**
     *
     * @param img BufferedImage que representa el sprite
     */
    public Sprite(final BufferedImage img) {
        this.img = img;
        this.WIDTH = img.getWidth();
        this.HEIGHT = img.getHeight();
    }

    /**
     *
     * @param g Graphics: La clase graphics que dibuja el sprite
     * @param x int: Posicion x en la pantalla donde se dibuja el sprite
     * @param y int: Posicion y en la pantalla donde se dibuja el sprite
     */
    public void dibujar(final Graphics g, final int x, final int y) {
        g.drawImage(this.img, x, y, null);
    }

    /**
     *
     * @return La anchura del sprite
     */
    public int getWIDTH() {
        return this.WIDTH;
    }

    /**
     *
     * @return La altura del sprite
     */
    public int getHEIGHT() {
        return this.HEIGHT;
    }

    public BufferedImage getImgCopy() {
        BufferedImage img2 = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TRANSLUCENT);
        Graphics g = img2.getGraphics();
        g.drawImage(img, 0, 0, null);
        return img2;
    }

}
