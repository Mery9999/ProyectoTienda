/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author Ander
 */
public class Conexion {
    private static Connection conn;

    public static Connection conectar() {
        try {
            String url = "jdbc:sqlserver://localhost:1433;databaseName=BDFluxo;"
                    + "integratedSecurity=false;encrypt=true;trustServerCertificate=true";
            return DriverManager.getConnection(url,"usuario","usuario123456");
        } catch (SQLException e) {
            System.out.println("Error al conectar: " + e.getMessage());
            return null;
        }
    }
    
    public static void main(String[] args){
        try{
            Connection conn=Conexion.conectar();
            System.out.println("Conexion realizada");
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    
}
