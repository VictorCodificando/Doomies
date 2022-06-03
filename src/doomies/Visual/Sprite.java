package doomies.Visual;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * Clase Sprite que definira el aspecto visual de toda entidad
 *
 * @see doomies.Entes.Entidad
 * @author Víctor
 * @version 4
 * @since 1
 */
public class Sprite {

    /**
     * Anchura del sprite
     */
    private final int WIDTH;
    /**
     * Altura del sprite
     */
    private final int HEIGHT;
    /**
     * Imagen que representa al sprite
     */
    private final BufferedImage img;

    /**
     * Crea un Sprite a partir de una imagen
     *
     * @param img BufferedImage que representa el sprite
     */
    public Sprite(final BufferedImage img) {
        this.img = img;
        this.WIDTH = img.getWidth();
        this.HEIGHT = img.getHeight();
    }

    /**
     * Dibuja el sprite en una posicion determinada
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

    /**
     * Dibuja una copia del sprite y lo devuelve, si no hiciese esto y la imagen
     * se modifica se modificaria aqui tambien
     *
     * @return Devuelve una copia de la imagen
     */
    public BufferedImage getImgCopy() {
        BufferedImage img2 = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TRANSLUCENT);
        Graphics g = img2.getGraphics();
        g.drawImage(img, 0, 0, null);
        return img2;
    }

}
