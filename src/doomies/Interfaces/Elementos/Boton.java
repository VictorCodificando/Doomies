package doomies.Interfaces.Elementos;

import java.awt.Graphics;
import doomies.HerramientasEntradaSalida.LoadTools;
import java.util.ArrayList;
import java.awt.Color;
import doomies.HerramientasEntradaSalida.Mouse;
import java.awt.Font;
import java.awt.image.BufferedImage;

/**
 * La clase boton es un elemento tanto visual como funcional, que puede ser
 * clickado y sirve como link entre interfaces
 *
 * @author Víctor
 * @version 4
 * @since 2
 */
public class Boton {//Clase boton, la explicare mas adelante NO TERMINADA

    /**
     * Posicion en x
     */
    private int x;
    /**
     * Posicion en y
     */
    private int y;
    /**
     * Anchura del boton en px
     */
    private int width;
    /**
     * Altura del boton en px
     */
    private int height;
    /**
     * Etiqueta que tiene por encima el boton
     */
    private String label;
    /**
     * Fuente usada
     */
    private Font font;
    /**
     * Raton usado para saber si se esta pulsando o si esta haciendo hover
     */
    private Mouse raton;
    /**
     * Color normal
     */
    private Color color;
    /**
     * Color cuando pasas el raton por encima
     */
    private Color colorHover;
    /**
     * Color de las letras
     */
    private Color colorString;
    /**
     * Relacion entre borde-tamaño, determina que tan grueso es el borde
     */
    private int format;
    /**
     * Ajuste para mover el label a gusto
     */
    private int ajusteX;
    /**
     * Ajuste para mover el label a gusto
     */
    private int ajusteY;
    /**
     * Indica si el raton esta por encima
     */
    private boolean hover;
    /**
     * Contiene todos los pixeles no transparentes
     */
    private final ArrayList transparent = new ArrayList();
    /**
     * Indica si se ha clickado al boton
     */
    private boolean clicked = false;

    /**
     * Crea un boton general
     *
     * @param x es el inicio en las x donde se va a imprimir el boton
     * @param y es el inicio en las y donde se va a imprimir el boton
     */
    private Boton(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Crea el boton con un String y una fuente encima de la imagen por defecto
     *
     * @param x es el inicio en las x donde se va a imprimir el boton
     * @param y es el inicio en las y donde se va a imprimir el boton
     * @param width es la anchura del boton
     * @param height es la altura del boton
     * @param label es el string que ira encima del color
     * @param font es la fuente
     */
    private Boton(int x, int y, int width, int height, String label, Font font) {
        this(x, y);
        this.width = width;
        this.height = height;
        this.label = label;
        this.font = font;
    }

    /**
     * Crea el boton con un String y una fuente encima de la imagen por defecto
     *
     * @param x es el inicio en las x donde se va a imprimir el boton
     * @param y es el inicio en las y donde se va a imprimir el boton
     * @param width es la anchura del boton
     * @param height es la altura del boton
     * @param label es el string que ira encima del color
     * @param font es la fuente
     * @param color es el color de fondo
     * @param ajusteX ajuste de las letras en el eje X
     * @param ajusteY ajuste de las letras en el eje Y
     * @param raton Mouse que se usara para capturar la posicion del raton
     */
    public Boton(final int x, final int y, final int width, final int height, final String label, final Font font, final Color color, final int ajusteX, final int ajusteY, final Mouse raton) {
        this(x, y, width, height, label, font);
        this.raton = raton;
        this.color = color;
        this.colorHover = LoadTools.brighter(color, 10);
        this.colorString = LoadTools.brighter(color, 200);
        this.ajusteX = ajusteX;
        this.ajusteY = ajusteY;
        format = (int) Math.floor(height / 10);
        calcularPixelesTransparentes();

    }

    /**
     * Actualiza el boton
     */
    public void actualizar() {
        hover = false;
        for (int i = 0; i < transparent.size(); i++) {//Recorre todos sus pixeles no transparentes y ve si el raton esta por encima
            double[] posPosible = (double[]) transparent.get(i);
            if (posPosible[0] == raton.x && (posPosible[1]) == raton.y) {
                hover = true;
                break;
            }
        }

        if (hover && raton.clicked) {//Si el raton esta por encima y ha clickado entonces ha clickado al boton
            clicked = true;
        } else {
            clicked = false;
        }
    }

    /**
     * Dibuja el boton en su posicion en pantalla
     *
     * @param g Clase graphics que dibuja todo en la pantalla
     */
    public void dibujar(final Graphics g) {
        g.setColor((hover) ? colorHover : color);
        g.fillRect(x + format, y + format, width - format * 2, height - format * 2);
        this.dibujarBorde(g);
        g.setFont(font);
        g.setColor((hover) ? colorHover : color);
        this.dibujarSombra(g);
        g.setColor((hover) ? LoadTools.brighter(colorString, 10) : colorString);
        dibujarString(g);
    }

    /**
     * Dibuja la sombra del boton con un color algo mas oscuro que su color
     * normal
     *
     * @param g Clase graphics que dibuja todo en la pantalla
     */
    private void dibujarSombra(final Graphics g) {
        g.setColor(LoadTools.darker(g.getColor(), 40));
        //Lineas Y
        g.fillRect(x + format, y + format * 2, format, height - format * 4);
        g.fillRect(x + width - format * 2, y + format * 2, format, height - format * 4);
        //Lineas X
        g.fillRect(x + format * 2, y + height - format * 2, width - (format * 4), format);
        g.fillRect(x + format * 2, y + format, width - format * 4, format);
        //Lineas adicionales
        g.fillRect(x + format * 2, y + format * 2, format, format);
        g.fillRect(x + width - format * 3, y + format * 2, format, format);
        g.fillRect(x + width - format * 3, y + height - format * 3, format, format);
        g.fillRect(x + format * 2, y + height - format * 3, format, format);
    }

    /**
     * Dibuja el label por encima del boton
     *
     * @param g Clase graphics que dibuja todo en la pantalla
     */
    private void dibujarString(final Graphics g) {
        String[] labelString = label.split("/");
        for (int i = 0; i < labelString.length; i++) {
            g.drawString(labelString[i], x + height - format * ajusteX, (y + height - format * ajusteY) + font.getSize() * i);
        }
    }

    /**
     * Dibuja el borde del boton dependiendo su grosor de la variable format
     *
     * @param g Clase graphics que dibuja todo en la pantalla
     */
    private void dibujarBorde(final Graphics g) {
        g.setColor(Color.BLACK);
        //lineas Y
        g.fillRect(x, y + format, format, height - format * 2);
        g.fillRect(x + width - format, y + format, format, height - format * 2);
        //Lineas X
        g.fillRect(x + format, y + height - format, width - (format * 2), format);
        g.fillRect(x + format, y, width - (format * 2), format);
        //Lineas adicionales
        g.fillRect(x + format, y + format, format, format);
        g.fillRect(x + width - format * 2, y + format, format, format);
        g.fillRect(x + width - format * 2, y + height - format * 2, format, format);
        g.fillRect(x + format, y + height - format * 2, format, format);
    }

    /**
     * Dibuja el boton en memoria y lo recorre pixel a pixel viendo si son o no
     * transparentes sus pixeles, si no lo son, se los guarda para comprobar mas
     * adelante
     */
    private void calcularPixelesTransparentes() {
        int provX = x;
        int provY = y;
        this.x = 0;
        this.y = 0;
        BufferedImage img = new BufferedImage(this.width, this.height, BufferedImage.TRANSLUCENT);
        Graphics g = img.getGraphics();
        dibujar(g);
        this.x = provX;
        this.y = provY;
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                if (((img.getRGB(j, i)) >>> 24) != 0x00) {
                    double pos[] = {j + x, i + y};
                    transparent.add(pos);
                }
            }
        }
        g.dispose();

    }

    /**
     * Establece el format del boton
     *
     * @param format El nuevo tamaño de los bordes
     */
    public void setFormat(int format) {
        this.format = (int) Math.floor(height / format);
    }

    /**
     * Dice si se ha clickado o no el botn
     *
     * @return Boolean clicked que significa si el boton esta clickado - true y
     * sino - false
     */
    public boolean isClicked() {
        return clicked;
    }

    /**
     * Establece si el boton esta clickado o no
     *
     * @param clickIn Boolean dice si esta siendo clickado o no
     */
    public void setClicked(boolean clickIn) {
        this.clicked = clickIn;
    }

    /**
     * Obtener la etiqueta
     *
     * @return La etiqueta del boton
     */
    public String getLabel() {
        return label;
    }

    /**
     * Establece la nueva etiqueta para el boton
     *
     * @param label La nueva etiqueta
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Mueve el boton en el eje x
     *
     * @param dx desplazamiento en x
     */
    public void moveInX(int dx) {
        this.x += dx;
    }
}
