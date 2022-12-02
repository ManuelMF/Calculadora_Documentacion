/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Main;

import Controlador.Controlador;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author mmf19
 */
public class Calculadora_ManuelMuñozFelix {
   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // hay que importar la libreria jtatto
        // le metmos un estilo de la libreria Jtatto
        try {
            String look;

            look = "com.jtattoo.plaf.graphite.GraphiteLookAndFeel";

            UIManager.setLookAndFeel(look);

        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        Controlador con = new Controlador();

    }

}
