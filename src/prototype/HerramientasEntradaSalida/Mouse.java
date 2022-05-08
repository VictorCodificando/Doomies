package prototype.HerramientasEntradaSalida;

import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author Víctor Zero
 */
public class Mouse implements MouseListener {

    private int AjusteX;
    private int AjusteY;
    public int x = 0;
    public int y = 0;
    public boolean pressed;
    public boolean released;
    public boolean clicked;

    public Mouse(int AjusteX, int AjusteY) {
        this.AjusteX = AjusteX;
        this.AjusteY = AjusteY;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        pressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        released=true;
        clicked=true;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void actualizarCoordenadas() {
        x = Math.abs((int) MouseInfo.getPointerInfo().getLocation().x - this.AjusteX);
        y = Math.abs((int) MouseInfo.getPointerInfo().getLocation().y - this.AjusteY);
        if (clicked=true) {
            pressed=false;
            released=false;
            clicked=false;
        }
    }

    public int getAjusteX() {
        return AjusteX;
    }

    public void setAjusteX(int AjusteX) {
        this.AjusteX = AjusteX;
    }

    public int getAjusteY() {
        return AjusteY;
    }

    public void setAjusteY(int AjusteY) {
        this.AjusteY = AjusteY;
    }

}
