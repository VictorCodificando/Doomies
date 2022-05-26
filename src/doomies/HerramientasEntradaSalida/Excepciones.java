/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package doomies.HerramientasEntradaSalida;

import java.awt.Frame;
import javax.swing.JOptionPane;

/**
 *
 * @author Víctor Zero
 */
/**
 * CREAR SI HAY TIEMPO CONTROLADOR DE EXCEPCIONES NO IMPORTANTE
 * 
 * 
 * @author Víctor Zero
 */
public class Excepciones extends Exception {

    private Exception exception;

    public Excepciones(Exception exception) {
        this.exception = exception;
    }

    @Override
    public String getMessage() {
        switch (exception + "") {
            case "java.io.FileNotFoundException":
                JOptionPane.showMessageDialog(new Frame(), "Error, archivo no encontrado, revise la ruta");
                break;
            default:
                JOptionPane.showMessageDialog(new Frame(), "Error no registrado: " + exception);
                break;
        }
        return "ERROR";
    }
}
