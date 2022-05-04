package prototype.Interfaces.Elementos;

import java.awt.Graphics;
import prototype.HerramientasEntradaSalida.LoadTools;
import java.util.ArrayList;
import java.awt.Color;
import prototype.HerramientasEntradaSalida.Mouse;
import java.awt.Font;
import java.awt.image.BufferedImage;

/**
 *
 * @author Víctor Zero
 */
public class Boton {//Clase boton, la explicare mas adelante NO TERMINADA

    private int x;
    private int y;
    private int width;
    private int height;
    private BufferedImage imgNormal;
    private BufferedImage imgHover;
    private String label;
    private BufferedImage labelImg;
    private Font font;
    private Mouse raton;
    private Color color;
    private Color colorHover;
    private Color colorString;
    private String tipo = "";
    private int format;
    private int ajusteX;
    private int ajusteY;
    private boolean hover;
    private final ArrayList transparent = new ArrayList();
    private boolean clicked = false;

    private Boton(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Crea el boton con una imagen encima de la imagen por defecto
     *
     * @param x es el inicio en las x donde se va a imprimir el boton
     * @param y es el inicio en las y donde se va a imprimir el boton
     * @param labelImg es la imagen que entrará como label
     */
    public Boton(int x, int y, BufferedImage labelImg) {
        this(x, y);
        this.labelImg = labelImg;
        this.width = labelImg.getWidth();
        this.height = labelImg.getHeight();
        tipo = "imagen";
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
    public Boton(int x, int y, int width, int height, String label, Font font) {
        this(x, y);
        this.width = width;
        this.height = height;
        this.label = label;
        this.font = font;
        tipo = "String";
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
     */
    public Boton(int x, int y, int width, int height, String label, Font font, Color color, int ajusteX, int ajusteY, Mouse raton) {
        this(x, y, width, height, label, font);

        tipo = "StringColor";
        this.raton = raton;
        this.color = color;
        this.colorHover = LoadTools.brighter(color, 10);
        this.colorString = LoadTools.brighter(color, 200);
        this.ajusteX = ajusteX;
        this.ajusteY = ajusteY;
        format = (int) Math.floor(height / 10);
        calcularPixelesTransparentes();

    }

    public void actualizar() {
        hover = false;
        boolean tocando = false;
        for (int i = 0; i < transparent.size(); i++) {
            double[] posPosible = (double[]) transparent.get(i);
            if (posPosible[0] == raton.x && (posPosible[1]) == raton.y) {
                tocando = true;
                break;
            }
        }
        hover = tocando;
        if (tocando && raton.click) {
            raton.click = false;
            clicked = true;
        }
    }

    public void dibujar(Graphics g) {
        switch (tipo) {
            case "StringColor":
                g.setColor((hover) ? colorHover : color);
                g.fillRect(x + format, y + format, width - format * 2, height - format * 2);
                this.dibujarBorde(g);
                g.setFont(font);
                g.setColor((hover) ? colorHover : color);
                this.dibujarSombra(g);
            case "String":
                g.setColor((hover) ? LoadTools.brighter(colorString, 10) : colorString);
                dibujarString(g);
                break;
            case "imagen":
                break;
            default:
        }
    }

    private void dibujarSombra(Graphics g) {
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

    private void dibujarString(Graphics g) {
        String[] labelString = label.split("/");
        for (int i = 0; i < labelString.length; i++) {
            g.drawString(labelString[i], x + height - format * ajusteX, (y + height - format * ajusteY) + font.getSize() * i);
        }
    }

    private void dibujarBorde(Graphics g) {
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

    private void setFontSize(float size) {
        font = font.deriveFont(size);
    }

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

    public void setFormat(int format) {
        this.format = (int) Math.floor(height / format);
    }

    public boolean isClicked() {
        return clicked;
    }

}
