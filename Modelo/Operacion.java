/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

//import com.mysql.jdbc.Connection;
//import com.mysql.jdbc.PreparedStatement;
//import com.mysql.jdbc.Statement;
import java.sql.*;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.LinkedList;


/**
 *
 * @author mmf19
 */
public class Operacion {
    private String operacion;
    private Double resultado;
    
    public Operacion(String operacion, Double resultado){
        this.operacion = operacion;
        this.resultado = resultado;
    }
    public String getOperacion() {
        return operacion;
    }
    public Double getResultado() {
        return resultado;
    }
    
    /**
     * Inserta la operacion en la BBDD
     * @param operacion
     * @param resultado 
     */
    public static void insertarOperacion(String operacion, double resultado) {
        
        Conexion mysql = new Conexion();
        Connection con = (Connection) mysql.conectar();
        
        String insert = "INSERT INTO operaciones(ope_operacion , ope_resultado) VALUES (? , ?)";
        
        try {
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(insert);
            pst.setString(1, operacion);
            pst.setDouble(2, resultado);
           
            pst.executeUpdate();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    ex);
        }
        // dps de esto actualizar tabla
    }
    
    /**
     * carga todos los datos en una LinkedList
     * @return LinkedList<Operacion> Con todas las operacions y resultados
     */
    public static LinkedList<Operacion> selectOperaciones(){
        
        LinkedList<Operacion> lista = new LinkedList<>();
        Conexion mysql = new Conexion();
        Connection con = (Connection) mysql.conectar();
        
        String select = "SELECT ope_operacion, ope_resultado from operaciones";
        
        try {
            Statement st = (Statement) con.createStatement();
            
            ResultSet res = st.executeQuery(select);
            while(res.next()){
                Operacion op = new Operacion(res.getString("ope_operacion"),res.getDouble("ope_resultado"));
                lista.add(op);
            }
            return lista;
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * borra todas las celdas de la BBDD
     */
    public static void borrarCeldas(){
        Conexion mysql = new Conexion();
        Connection cn = (Connection) mysql.conectar();        
        
        String delete = "delete from operaciones";
        
        try {
            PreparedStatement pst =(PreparedStatement) cn.prepareStatement(delete);
            
            pst.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
