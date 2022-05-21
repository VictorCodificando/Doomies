package doomies.Visual;

import doomies.HerramientasEntradaSalida.LoadTools;
import java.awt.image.BufferedImage;

/**
 *
 * @author Víctor Zero
 */
public class SpriteSheet {

    private final int WIDTH;
    private final int HEIGHT;

    private final int SPRITE_WIDTH;
    private final int SPRITE_HEIGHT;
    private final BufferedImage img;
    private final Sprite[][] sprites;
    public static final SpriteSheet PERSONAJE = new SpriteSheet(160, 636, 80, 106, "/spritesSheet/Character.png");
    public static SpriteSheet MAPA = new SpriteSheet(512, 512, 64, 64, "/spritesSheet/Mapa.png");
    public static SpriteSheet IMP = new SpriteSheet(96, 195, 48, 48, "/spritesSheet/Imp.png");
    public static SpriteSheet PINKIE = new SpriteSheet(96, 195, 48, 48, "/spritesSheet/Pinkie.png");
    public static SpriteSheet CACODEMON = new SpriteSheet(258, 468,129, 117, "/spritesSheet/Cacodemon.png");
    public static SpriteSheet SOUL = new SpriteSheet(64, 96, 32, 32, "/spritesSheet/Soul.png");
    public static SpriteSheet BARON = new SpriteSheet(96, 296, 48, 74, "/spritesSheet/Baron.png");

    /**
     * Crea la hoja de sprite, siendo sprites de un ancho y un alto diferente
     *
     * @param width int que representa la anchura de la hoja (px)
     * @param height int que representa la altura de la hoja (px)
     * @param sprite_width int que representa la anchura del sprite (px)
     * @param sprite_height int que representa la altura del sprite (px)
     */
    public SpriteSheet(final int width, final int height, int sprite_width, final int sprite_height, final String path) {
        this.WIDTH = width;
        this.HEIGHT = height;
        this.SPRITE_WIDTH = sprite_width;
        this.SPRITE_HEIGHT = sprite_height;
        this.img = LoadTools.loadImage(path);
        sprites = new Sprite[HEIGHT / SPRITE_HEIGHT][WIDTH / SPRITE_WIDTH];//Array de Sprites que contienen toda la hoja
        getAllSprites();
    }

    /**
     * Crea la hoja de sprite, siendo sprites cuadrados
     *
     * @param width int que representa la anchura de la hoja (px)
     * @param height int que representa la altura de la hoja (px)
     * @param size int que representa la el tamaño del sprite (alto y ancho)
     * @param path String que indica la ruta donde se encuentra la imagen que
     * representa la hoja de sprites
     */
    public SpriteSheet(final int width, final int height, final int size, final String path) {
        this(width, height, size, size, path);
    }

    private void getAllSprites() {//Recorre la hoja de sprites entera y coje todos los sprites
        for (int i = 0; i < HEIGHT / SPRITE_HEIGHT; i++) {
            for (int j = 0; j < WIDTH / SPRITE_WIDTH; j++) {
                this.sprites[i][j] = new Sprite(img.getSubimage(j * SPRITE_WIDTH, i * SPRITE_HEIGHT, SPRITE_WIDTH, SPRITE_HEIGHT));
            }
        }
    }

    public Sprite[][] getSprites() {
        return sprites;
    }

    public Sprite getSprite(final int x, final int y) {
        return sprites[x][y];
    }
}