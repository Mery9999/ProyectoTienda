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
import modelos.Producto;
import modelos.Proveedor;

/**
 *
 * @author Ander
 */
public class GestorProveedor {

    public GestorProveedor() {
    }

    public boolean registrarProveedor(Proveedor p) {
        if (existeProveedor(p.getIdProveedor())) {
        System.out.println("El proveedor ya existe.");
        return false;
    }

        String sql = "INSERT INTO Proveedor (idProveedor, nombre, telefono) VALUES (?,?,?)";

        try {
            Connection conn = Conexion.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, p.getIdProveedor());
            stmt.setString(2, p.getNombre());
            stmt.setString(3, p.getTelefono());

            stmt.executeUpdate();
            stmt.close();
            conn.close();

            return true;

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public void listarProveedores() {
        for (Proveedor p : getProveedores()) {
            System.out.println(p);
        }
    }

    public ArrayList<Proveedor> getProveedores() {

        ArrayList<Proveedor> lista = new ArrayList<>();
        String sql = "SELECT idProveedor, nombre, telefono FROM Proveedor";

        try {
            Connection conn = Conexion.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Proveedor p = new Proveedor(
                        rs.getInt("idProveedor"),
                        rs.getString("nombre"),
                        rs.getString("telefono")
                );
                cargarProductosAsignados(p, conn);
                lista.add(p);
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return lista;
    }

    private void cargarProductosAsignados(Proveedor proveedor, Connection conn) {

        String sql = "SELECT p.idProducto, p.codigo, p.marca, p.modelo, p.color, "
                + "p.categoria, p.precio, p.stock "
                + "FROM Producto p "
                + "INNER JOIN Producto_Proveedor pp ON p.idProducto = pp.idProducto "
                + "WHERE pp.idProveedor = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, proveedor.getIdProveedor());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Producto producto = new Producto(
                        rs.getInt("idProducto"),
                        rs.getString("codigo"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getString("color"),
                        rs.getString("categoria"),
                        rs.getDouble("precio"),
                        rs.getInt("stock")
                );
                proveedor.agregarProducto(producto);
            }

            rs.close();
            stmt.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Proveedor buscarProveedor(int id) {

        String sql = "SELECT idProveedor, nombre, telefono FROM Proveedor WHERE idProveedor = ?";

        try {
            Connection conn = Conexion.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Proveedor p = new Proveedor(
                        rs.getInt("idProveedor"),
                        rs.getString("nombre"),
                        rs.getString("telefono")
                );
                cargarProductosAsignados(p, conn);
                rs.close();
                stmt.close();
                conn.close();
                return p;
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return null;
    }

    public boolean eliminarProveedor(int id) {

        try {
            Connection conn = Conexion.conectar();

            String sqlAsignaciones = "DELETE FROM Producto_Proveedor WHERE idProveedor = ?";
            PreparedStatement stmtAsig = conn.prepareStatement(sqlAsignaciones);
            stmtAsig.setInt(1, id);
            stmtAsig.executeUpdate();
            stmtAsig.close();

            String sql = "DELETE FROM Proveedor WHERE idProveedor = ?";
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

    public boolean asignarProducto(int idProveedor, int idProducto) {

        String sql = "INSERT INTO Producto_Proveedor (idProveedor, idProducto) VALUES (?,?)";

        try {
            Connection conn = Conexion.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idProveedor);
            stmt.setInt(2, idProducto);
            stmt.executeUpdate();
            stmt.close();
            conn.close();
            return true;

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public boolean existeAsignacion(int idProveedor, int idProducto) {

        String sql = "SELECT 1 FROM Producto_Proveedor WHERE idProveedor = ? AND idProducto = ?";

        try {
            Connection conn = Conexion.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idProveedor);
            stmt.setInt(2, idProducto);
            ResultSet rs = stmt.executeQuery();
            boolean existe = rs.next();
            rs.close();
            stmt.close();
            conn.close();
            return existe;

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public int obtenerSiguienteId() {

        String sql = "SELECT ISNULL(MAX(idProveedor),0)+1 AS siguiente FROM Proveedor";

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
    public boolean modificarProveedor(Proveedor proveedor) {

    String sql = "UPDATE Proveedor SET nombre = ?, telefono = ? WHERE idProveedor = ?";

    try {

        Connection conn = Conexion.conectar();
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, proveedor.getNombre());
        stmt.setString(2, proveedor.getTelefono());
        stmt.setInt(3, proveedor.getIdProveedor());

        int filas = stmt.executeUpdate();

        stmt.close();
        conn.close();

        return filas > 0;

    } catch (Exception ex) {

        System.out.println(ex.getMessage());
        return false;

    }
}
    public boolean existeProveedor(int idProveedor) {

    return buscarProveedor(idProveedor) != null;

}
}
