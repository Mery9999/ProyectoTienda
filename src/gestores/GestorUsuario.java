/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestores;

import Data.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import modelos.Administrador;
import modelos.Atc;
import modelos.Encargadoalmacen;

import modelos.Usuario;

/**
 *
 * @author Ander
 */
public class GestorUsuario {
    private ArrayList<Usuario> usuarios;

    public GestorUsuario() {
        usuarios = new ArrayList<>();
    }

   public boolean registrarUsuario(Usuario usuario, String tipoUsuario) {

    String sql = "INSERT INTO Usuario (IdUsuario, Username, contraseña, tipoUsuario, activo) VALUES (?,?,?,?,?)";

    try {

        Connection conn = Conexion.conectar();
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setInt(1, usuario.getId());
        stmt.setString(2, usuario.getUsername());
        stmt.setString(3, usuario.getPassword());
        stmt.setString(4, tipoUsuario);
        stmt.setBoolean(5, usuario.isActivo());

        stmt.executeUpdate();

        stmt.close();
        conn.close();

        return true;

    } catch (Exception ex) {
        System.out.println(ex.getMessage());
        return false;
    }
}
   public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public Usuario buscarUsuario(int id) {

        String sql = "SELECT * FROM Usuario WHERE IdUsuario = ?";

    try {

        Connection conn = Conexion.conectar();
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {

            String username = rs.getString("Username");
            String password = rs.getString("contraseña");
            String tipo = rs.getString("tipoUsuario");
            boolean activo = rs.getBoolean("activo");

            Usuario usuario = null;

            if (tipo.equalsIgnoreCase("Administrador")) {

                usuario = new Administrador(id, username, password);

            } else if (tipo.equalsIgnoreCase("ATC")) {

                usuario = new Atc(id, username, password);

            } else if (tipo.equalsIgnoreCase("Encargado Almacen")) {

                usuario = new Encargadoalmacen(id, username, password);

            }

            if (usuario != null && !activo) {
                usuario.desactivar();
            }

            

            return usuario;
        }

        

    } catch (Exception ex) {
        System.out.println(ex.getMessage());
    }

    return null;
    }

    public void listarUsuarios() {
        for(Usuario u : usuarios) {
            System.out.println(u);
        }
    }
    public ArrayList<Usuario> listaUsuario() {

    ArrayList<Usuario> listaUsuario = new ArrayList<>();

    String sql = "SELECT IdUsuario, Username, contraseña, tipoUsuario, activo FROM Usuario";

    try {

        Connection conn = Conexion.conectar();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {

            int id = rs.getInt("IdUsuario");
            String username = rs.getString("Username");
            String password = rs.getString("contraseña");
            String tipo = rs.getString("tipoUsuario");

            Usuario u = null;

            if (tipo.equalsIgnoreCase("Administrador")) {
                u = new Administrador(id, username, password);

            } else if (tipo.equalsIgnoreCase("ATC")) {
                u = new Atc(id, username, password);

            } else if (tipo.equalsIgnoreCase("Encargado Almacen")) {
                u = new Encargadoalmacen(id, username, password);
            }

            if (u != null) {

                if (!rs.getBoolean("activo")) {
                    u.desactivar();
                }

                listaUsuario.add(u);
            }
        }

        rs.close();
        stmt.close();
        conn.close();

    } catch (Exception ex) {
        System.out.println(ex.getMessage());
    }

    return listaUsuario;
}

  
    public boolean modificarUsuario(Usuario usuario, String tipoUsuario) {

    String sql = "UPDATE Usuario SET Username=?, contraseña=?, tipoUsuario=?, activo=? WHERE IdUsuario=?";

    try {

        Connection conn = Conexion.conectar();

        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, usuario.getUsername());
        stmt.setString(2, usuario.getPassword());
        stmt.setString(3, tipoUsuario);
        stmt.setBoolean(4, usuario.isActivo());
        stmt.setInt(5, usuario.getId());

        stmt.executeUpdate();

        stmt.close();
        conn.close();

        return true;

    } catch (Exception ex) {

        System.out.println(ex.getMessage());
        return false;

    }

}
    
    public boolean desactivarUsuario(int id) {

    String sql = "UPDATE Usuario SET activo = 0 WHERE IdUsuario = ?";

    try {

        Connection conn = Conexion.conectar();

        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setInt(1, id);

        int filas = stmt.executeUpdate();

        stmt.close();
        conn.close();

        return filas > 0;

    } catch (Exception ex) {

        System.out.println(ex.getMessage());
        return false;
    }
}

    public Usuario validarLogin(String username, String password) {

        String sql = "SELECT IdUsuario, Username, contraseña, tipoUsuario, activo "
                + "FROM Usuario WHERE Username = ? AND contraseña = ? AND activo = 1";

        try {
            Connection conn = Conexion.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("IdUsuario");
                String tipo = rs.getString("tipoUsuario");
                Usuario usuario = null;

                if (tipo.equalsIgnoreCase("Administrador")) {
                    usuario = new Administrador(id, username, password);
                } else if (tipo.equalsIgnoreCase("ATC")) {
                    usuario = new Atc(id, username, password);
                } else if (tipo.equalsIgnoreCase("Encargado Almacen")) {
                    usuario = new Encargadoalmacen(id, username, password);
                }

                rs.close();
                stmt.close();
                conn.close();
                return usuario;
            }

            

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return null;
    }

    public int obtenerSiguienteId() {

        String sql = "SELECT ISNULL(MAX(IdUsuario),0)+1 AS siguiente FROM Usuario";

        try {
            Connection conn = Conexion.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int siguiente = rs.getInt("siguiente");
                rs.close();
                stmt.close();
                conn.close();
                return siguiente;
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return 1;
    }
}
