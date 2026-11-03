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
import modelos.MovimientoInventario;
import modelos.Producto;

/**
 *
 * @author Ander
 */
public class GestorInventario {
    
     private ArrayList<MovimientoInventario> movimientos;

    public GestorInventario() {
        movimientos = new ArrayList<>();
    }

    private Producto mapearProducto(ResultSet rs) throws Exception {
        return new Producto(
                rs.getInt("idProducto"),
                rs.getString("codigo"),
                rs.getString("marca"),
                rs.getString("modelo"),
                rs.getString("color"),
                rs.getString("categoria"),
                rs.getDouble("precio"),
                rs.getInt("stock")
        );
    }
    
    public boolean registrarProducto(Producto producto)
    {
        String sql = "INSERT INTO Producto (IdProducto, codigo, marca, modelo, talla, color, categoria, precio, stock) VALUES (?,?,?,?,?,?,?,?,?)";

    try {

        Connection conn = Conexion.conectar();
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setInt(1, producto.getIdProducto());
        stmt.setString(2, producto.getCodigo());
        stmt.setString(3, producto.getMarca());
        stmt.setString(4, producto.getModelo());
        stmt.setDouble(5, producto.getTalla());
        stmt.setString(6, producto.getColor());
        stmt.setString(7, producto.getCategoria());
        stmt.setDouble(8, producto.getPrecio());
        stmt.setInt(9, producto.getStock());
        

        stmt.executeUpdate();

        stmt.close();
        conn.close();

        return true;

    } catch (Exception ex) {
        System.out.println(ex.getMessage());
        return false;
    }
    }

    public Producto buscarProducto(String codigo) {

        String sql = "SELECT * FROM Producto WHERE codigo = ?";

        try {
            Connection conn = Conexion.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, codigo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Producto producto = mapearProducto(rs);
                rs.close();
                stmt.close();
                conn.close();
                return producto;
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception ex) {
            System.out.println("Error al buscar producto: " + ex.getMessage());
        }

        return null;
    }
    
    public Producto buscarProducto(int id) {

    String sql = "SELECT * FROM Producto WHERE idProducto = ?";

    try {
        Connection conn = Conexion.conectar();
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {

            Producto producto = mapearProducto(rs);

            rs.close();
            stmt.close();
            conn.close();

            return producto;
        }

        rs.close();
        stmt.close();
        conn.close();

    } catch (Exception ex) {
        System.out.println("Error al buscar producto: " + ex.getMessage());
    }

    return null;
}

  
    public void actualizarPrecio(String codigo, double nuevoPrecio) {

        String sql = "UPDATE Producto SET precio = ? WHERE codigo = ?";

        try {
            Connection conn = Conexion.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, nuevoPrecio);
            stmt.setString(2, codigo);
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    

    public int cantidadProductos() {
           return listarProductos().size();
    }

    public ArrayList<Producto> getProductos() {
         return listarProductos();
     }
    
    public void salidaProducto(String codigo, int cantidad) {

        String sql = "UPDATE Producto SET stock = stock - ? WHERE codigo = ? AND stock >= ?";

        try {
            Connection conn = Conexion.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, cantidad);
            stmt.setString(2, codigo);
            stmt.setInt(3, cantidad);
            stmt.executeUpdate();
            stmt.close();
            conn.close();

            Producto p = buscarProducto(codigo);
            if (p != null) {
                movimientos.add(new MovimientoInventario("SALIDA", p, cantidad));
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
   
   
    public boolean existeCodigo(String codigo) {
        return buscarProducto(codigo) != null;
    }

    public boolean existeCodigoOtroProducto(String codigo, int idProducto) {
        Producto p = buscarProducto(codigo);
        return p != null && p.getIdProducto() != idProducto;
    }
    
    public boolean modificarProducto(Producto productoModificado) {

        String sql = "UPDATE Producto SET codigo=?, marca=?, modelo=?, talla=?, color=?, categoria=?, precio=?, stock=? WHERE idProducto=?";

        try {
            Connection conn = Conexion.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, productoModificado.getCodigo());
            stmt.setString(2, productoModificado.getMarca());
            stmt.setString(3, productoModificado.getModelo());
            stmt.setDouble(4, productoModificado.getTalla());
            stmt.setString(5, productoModificado.getColor());
            stmt.setString(6, productoModificado.getCategoria());
            stmt.setDouble(7, productoModificado.getPrecio());
            stmt.setInt(8, productoModificado.getStock());
            stmt.setInt(9, productoModificado.getIdProducto());

            int filas = stmt.executeUpdate();
            stmt.close();
            conn.close();

            return filas > 0;

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public ArrayList<Producto> listarProductos() {
        ArrayList<Producto> listaProducto = new ArrayList<>();
        String sql = "SELECT idProducto, codigo, marca, modelo, color, categoria, precio, stock FROM Producto";

        try {
            Connection conn = Conexion.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                listaProducto.add(mapearProducto(rs));
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return listaProducto;
    }

    public int obtenerSiguienteId() {

        String sql = "SELECT ISNULL(MAX(idProducto),0)+1 AS siguiente FROM Producto";

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
