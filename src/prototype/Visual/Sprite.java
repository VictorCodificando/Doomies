package prototype.Visual;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Sprite {

    private final int WIDTH;
    private final int HEIGHT;
    private final BufferedImage img;

    public Sprite(final BufferedImage img) {
        this.img = img;
        this.WIDTH = img.getWidth();
        this.HEIGHT = img.getHeight();
    }

    public void dibujar(final Graphics g, final int x, final int y) {
        g.drawImage(this.img, x, y, null);
    }

    public int getWIDTH() {
        return this.WIDTH;
    }

    public int getHEIGHT() {
        return this.HEIGHT;
    }
}
