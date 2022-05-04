package prototype.mapa;

import java.awt.Graphics;
import prototype.Entes.Entidad;
import prototype.Visual.Sprite;

/**
 *
 * @author Víctor Zero
 */
public class Tile extends Entidad {//Nada todavia

    public Tile(final int x,final int y, Sprite sprite[]) {
        super(sprite, sprite[0].getWIDTH(), sprite[0].getHEIGHT());
        this.x = x;
        this.y = y;
    }

    @Override
    public void dibujar(Graphics g) {
        this.sprites[0].dibujar(g, x, y);
    }

    @Override
    public void actualizar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
