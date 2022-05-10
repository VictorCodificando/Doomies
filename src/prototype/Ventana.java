package prototype;

import prototype.HerramientasEntradaSalida.LoadTools;
import java.awt.Component;
import java.awt.BorderLayout;
import javax.swing.JFrame;

/**
 *
 * @author Víctor Zero
 */
public class Ventana extends JFrame {
    /**
     * Crea el frame personalizado para nuestro proyecto
     * 
     * @param canvas El lienzo que va ser añadido al frame
     */
    public Ventana(final Lienzo canvas) {//Declaracion principal del Frame
        setTitle("Prototype");//PONEMOS EL TITULO(LO QUE SE VE ARRIBA)
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Si le das a cerrar que se acabe la ejecucion
        setResizable(false);//Que no se pueda redimensionar
        setLayout(new BorderLayout());//Divide la pantalla en 5 y los elementos que metamos se ajustaran al tamaño
        add(canvas, BorderLayout.CENTER);//de los 5 espacios usamos 1 el cual usara todo el espacio
        pack();//Para poder usar PreferredSize en cada elemento
        setIconImage(LoadTools.loadImage("/icon/icon.png"));
        setLocationRelativeTo(null);//Para que aparezca en el centro de la pantalla
        setVisible(true);//Que sea visible la ventana
    }
}
