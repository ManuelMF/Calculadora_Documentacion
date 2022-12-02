/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author mmf19
 */
public class Conexion {
     public String bd = "Calculadora_ManuelMuñozFelix";
    public String login = "root";
    public String password = "";
    public String url = "jdbc:mysql://localhost/" + bd;

    public Connection conectar() {
        Connection link = null;
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            link = DriverManager.getConnection(this.url, this.login, this.password);
            
            //JOptionPane.showMessageDialog(null , "Conexión establecida");
        } catch (ClassNotFoundException | SQLException e) {
            //JOptionPane.showMessageDialog(null, e);
            e.printStackTrace();
        }
        return link;
    }
}
