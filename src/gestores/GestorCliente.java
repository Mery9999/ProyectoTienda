/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestores;

import Data.Conexion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import modelos.Cliente;
import modelos.ClienteNatural;
import modelos.Clientejuridico;


/**
 *
 * @author Ander
 */
public class GestorCliente {
    
    private ArrayList<Cliente> clientes;
    
    public ArrayList<Cliente> getClientes() {
    return clientes;
    }

    public GestorCliente() {
        clientes = new ArrayList<>();
    }

   public boolean registrarCliente(Cliente cliente) {

    String sqlCliente =
            "INSERT INTO Cliente(idCliente,nombre,telefono,email) VALUES(?,?,?,?)";

    try {

        java.sql.Connection conn = Conexion.conectar();
        PreparedStatement stmt = conn.prepareStatement(sqlCliente);

        stmt.setInt(1, cliente.getIdcliente());
        stmt.setString(2, cliente.getNombre());
        stmt.setString(3, cliente.getTelefono());
        stmt.setString(4, cliente.getEmail());

        stmt.executeUpdate();

        if (cliente instanceof ClienteNatural) {

            ClienteNatural cn = (ClienteNatural) cliente;

            String sql =
                    "INSERT INTO Cliente_Natural(dniCliente,idCliente) VALUES(?,?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, cn.getDni());
            ps.setInt(2, cn.getIdcliente());

            ps.executeUpdate();

            ps.close();

        } else if (cliente instanceof Clientejuridico) {

            Clientejuridico cj = (Clientejuridico) cliente;

            String sql =
                    "INSERT INTO Cliente_Empresa(ruc,razonSocial,idCliente) VALUES(?,?,?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, cj.getRuc());
            ps.setString(2, cj.getRazonSocial());
            ps.setInt(3, cj.getIdcliente());

            ps.executeUpdate();

            ps.close();
        }

        stmt.close();
        conn.close();

        return true;

    } catch (Exception ex) {

        System.out.println(ex.getMessage());
        return false;
    }
}

   public Cliente buscarCliente(String documento) {

    String sql = "SELECT c.idCliente, c.nombre, c.telefono, c.email, " +
                 "cn.dniCliente, ce.ruc, ce.razonSocial " +
                 "FROM Cliente c " +
                 "LEFT JOIN Cliente_Natural cn ON c.idCliente = cn.idCliente " +
                 "LEFT JOIN Cliente_Empresa ce ON c.idCliente = ce.idCliente " +
                 "WHERE cn.dniCliente = ? OR ce.ruc = ?";

    try {

       java.sql.Connection conn=Conexion.conectar();
        PreparedStatement stmt=conn.prepareStatement(sql);

        stmt.setString(1, documento);
        stmt.setString(2, documento);

        ResultSet rs=stmt.executeQuery();

        if (rs.next()) {

            if (rs.getString("dniCliente") != null) {

                Cliente cliente = new ClienteNatural(
                        rs.getInt("idCliente"),
                        rs.getString("nombre"),
                        rs.getString("telefono"),
                        rs.getString("email"),
                        rs.getString("dniCliente"));
                rs.close();
                stmt.close();
                conn.close();
                return cliente;

            } else {

                Cliente cliente = new Clientejuridico(
                        rs.getInt("idCliente"),
                        rs.getString("nombre"),
                        rs.getString("telefono"),
                        rs.getString("email"),
                        rs.getString("ruc"),
                        rs.getString("razonSocial"));
                rs.close();
                stmt.close();
                conn.close();
                return cliente;
            }
        }

        rs.close();
        stmt.close();
        conn.close();

    } catch (Exception ex) {
        System.out.println(ex.getMessage());
    }

    return null;
   }

   public Cliente buscarClientePorId(int id) {

    String sql = "SELECT c.idCliente, c.nombre, c.telefono, c.email, " +
                 "cn.dniCliente, ce.ruc, ce.razonSocial " +
                 "FROM Cliente c " +
                 "LEFT JOIN Cliente_Natural cn ON c.idCliente = cn.idCliente " +
                 "LEFT JOIN Cliente_Empresa ce ON c.idCliente = ce.idCliente " +
                 "WHERE c.idCliente = ?";

    try {

        java.sql.Connection conn = Conexion.conectar();
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {

            if (rs.getString("dniCliente") != null) {
                return new ClienteNatural(
                        rs.getInt("idCliente"),
                        rs.getString("nombre"),
                        rs.getString("telefono"),
                        rs.getString("email"),
                        rs.getString("dniCliente"));
            } else {
                return new Clientejuridico(
                        rs.getInt("idCliente"),
                        rs.getString("nombre"),
                        rs.getString("telefono"),
                        rs.getString("email"),
                        rs.getString("ruc"),
                        rs.getString("razonSocial"));
            }
        }

        rs.close();
        stmt.close();
        conn.close();

    } catch (Exception ex) {
        System.out.println(ex.getMessage());
    }

    return null;
   }

 public ArrayList<Cliente> listarClientes() {

    ArrayList<Cliente> listaClientes = new ArrayList<>();

    String sql = "SELECT c.idCliente, c.nombre, c.telefono, c.email, " +
                 "cn.dniCliente, ce.ruc, ce.razonSocial " +
                 "FROM Cliente c " +
                 "LEFT JOIN Cliente_Natural cn ON c.idCliente = cn.idCliente " +
                 "LEFT JOIN Cliente_Empresa ce ON c.idCliente = ce.idCliente";

    try {

        java.sql.Connection conn=Conexion.conectar();
        PreparedStatement stmt=conn.prepareStatement(sql);
        ResultSet rs=stmt.executeQuery();

        while (rs.next()) {

            int id = rs.getInt("idCliente");
            String nombre = rs.getString("nombre");
            String telefono = rs.getString("telefono");
            String email = rs.getString("email");

            Cliente cliente;

            if (rs.getString("dniCliente") != null) {

                cliente = new ClienteNatural(
                        id,
                        nombre,
                        telefono,
                        email,
                        rs.getString("dniCliente"));

            } else {

                cliente = new Clientejuridico(
                        id,
                        nombre,
                        telefono,
                        email,
                        rs.getString("ruc"),
                        rs.getString("razonSocial"));
            }

            listaClientes.add(cliente);
        }

        rs.close();
        stmt.close();
        conn.close();

    } catch (Exception ex) {

        System.out.println(ex.getMessage());

    }

    return listaClientes;
}
    
    public int cantidadClientes() {
        return listarClientes().size();
    }
    
    public boolean modificarCliente(Cliente cliente) {

    String sqlCliente =
            "UPDATE Cliente SET nombre=?, telefono=?, email=? WHERE idCliente=?";

    try {

        java.sql.Connection conn = Conexion.conectar();
        PreparedStatement stmt = conn.prepareStatement(sqlCliente);

        stmt.setString(1, cliente.getNombre());
        stmt.setString(2, cliente.getTelefono());
        stmt.setString(3, cliente.getEmail());
        stmt.setInt(4, cliente.getIdcliente());

        stmt.executeUpdate();

        if (cliente instanceof ClienteNatural) {

            ClienteNatural cn = (ClienteNatural) cliente;

            String sql =
            "UPDATE Cliente_Natural SET dniCliente=? WHERE idCliente=?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, cn.getDni());
            ps.setInt(2, cn.getIdcliente());

            ps.executeUpdate();
            ps.close();

        } else {

            Clientejuridico cj = (Clientejuridico) cliente;

            String sql =
            "UPDATE Cliente_Empresa SET ruc=?, razonSocial=? WHERE idCliente=?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, cj.getRuc());
            ps.setString(2, cj.getRazonSocial());
            ps.setInt(3, cj.getIdcliente());

            ps.executeUpdate();
            ps.close();
        }

        stmt.close();
        conn.close();

        return true;

    } catch (Exception ex) {

        System.out.println(ex.getMessage());
        return false;
    }
}
    public int obtenerSiguienteId() {

    String sql = "SELECT ISNULL(MAX(idCliente),0)+1 AS siguiente FROM Cliente";

    try {

        java.sql.Connection conn=Conexion.conectar();
        PreparedStatement stmt=conn.prepareStatement(sql);
        ResultSet rs=stmt.executeQuery();

        if (rs.next()) {
            return rs.getInt("siguiente");
        }

    } catch (Exception ex) {
        System.out.println(ex.getMessage());
    }

    return 1;
}
}

