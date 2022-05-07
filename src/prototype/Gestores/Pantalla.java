package prototype.Gestores;

import java.awt.Color;
import java.awt.Graphics;
import prototype.HerramientasEntradaSalida.LoadTools;
import prototype.HerramientasEntradaSalida.Mouse;
import prototype.HerramientasEntradaSalida.Teclado;
import prototype.Interfaces.Elementos.Boton;

public class Pantalla {//Clase donde ocurre todo en la pantalla

    public final int WIDTH;
    private final int HEIGHT;
    private GestorEstados ge;
    
    public Pantalla(final int WIDTH, final int HEIGHT, final Teclado teclado, final Mouse raton) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        ge=new GestorEstados(WIDTH,HEIGHT,teclado,raton);
    }

    public void actualizar() {
        ge.actualizar();
    }

    public void dibujar(final Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.WIDTH, this.HEIGHT);
        ge.dibujar(g);
    }
}
