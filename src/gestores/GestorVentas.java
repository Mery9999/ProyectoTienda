/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestores;

import Data.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import modelos.Administrador;
import modelos.Atc;
import modelos.Cliente;
import modelos.ClienteNatural;
import modelos.Clientejuridico;
import modelos.DetalleVenta;
import modelos.Encargadoalmacen;
import modelos.Producto;
import modelos.Usuario;
import modelos.Venta;

/**
 *
 * @author Ander
 */
public class GestorVentas {

    public GestorVentas() {
    }

        public int obtenerSiguienteIdVenta() {

          String sql = "SELECT ISNULL(MAX(IdVenta),0)+1 AS siguiente FROM Venta";

          try (Connection cn = Conexion.conectar();
               PreparedStatement ps = cn.prepareStatement(sql);
               ResultSet rs = ps.executeQuery()) {

              if (rs.next()) {
                  return rs.getInt("siguiente");
              }

          } catch (SQLException e) {
              System.out.println("Error al obtener ID: " + e.getMessage());
          }

          return 1;
      }
        public int obtenerSiguienteIdDetalle() {

            String sql = "SELECT ISNULL(MAX(IdDetalle),0)+1 AS siguiente FROM Detalle_Venta";

            try (Connection cn = Conexion.conectar();
                 PreparedStatement ps = cn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt("siguiente");
                }

            } catch (SQLException e) {
                System.out.println("Error al obtener ID detalle: " + e.getMessage());
            }

            return 1;
        }
        public boolean registrarVenta(Venta venta) {

            String sqlVenta =
                "INSERT INTO Venta (IdVenta, fecha, subtotal, descuento, impuesto, total, Cliente_idCliente, Usuario_IdUsuario) " +
                "VALUES (?,?,?,?,?,?,?,?)";

            String sqlDetalle =
            "INSERT INTO Detalle_Venta (IdDetalle, cantidad, subtotal, Venta_IdVenta, Producto_idProducto) VALUES (?,?,?,?,?)";

            Connection cn = null;

            try {

                cn = Conexion.conectar();
                cn.setAutoCommit(false);

                // Registrar cabecera de la venta
                PreparedStatement psVenta = cn.prepareStatement(sqlVenta);

                psVenta.setInt(1, venta.getIdVenta());
                psVenta.setTimestamp(2, new Timestamp(venta.getFecha().getTime()));
                psVenta.setDouble(3, venta.getTotal()); // subtotal
                psVenta.setDouble(4, 0);                // descuento
                psVenta.setDouble(5, 0);                // impuesto
                psVenta.setDouble(6, venta.getTotal()); // total
                psVenta.setInt(7, venta.getCliente().getIdcliente());
                psVenta.setInt(8, venta.getVendedor().getId());

                psVenta.executeUpdate();
        psVenta.close();

        // Registrar detalle de venta
        PreparedStatement psDetalle = cn.prepareStatement(sqlDetalle);

        // Actualizar stock
        String sqlStock =
            "UPDATE Producto SET stock = stock - ? WHERE idProducto = ? AND stock >= ?";

        PreparedStatement psStock = cn.prepareStatement(sqlStock);
        int idDetalle = obtenerSiguienteIdDetalle();
        for (DetalleVenta detalle : venta.getDetalles()) {

            // Guardar detalle
            psDetalle.setInt(1, idDetalle++);
            psDetalle.setInt(2, detalle.getCantidad());
            psDetalle.setDouble(3, detalle.getSubtotal());
            psDetalle.setInt(4, venta.getIdVenta());
            psDetalle.setInt(5, detalle.getProducto().getIdProducto());

            psDetalle.executeUpdate();

            // Descontar stock
            psStock.setInt(1, detalle.getCantidad());
            psStock.setInt(2, detalle.getProducto().getIdProducto());
            psStock.setInt(3, detalle.getCantidad());

            int filas = psStock.executeUpdate();

            if (filas == 0) {
                throw new Exception(
                    "Stock insuficiente del producto: "
                    + detalle.getProducto().getCodigo()
                );
            }
        }

        psDetalle.close();
        psStock.close();

        cn.commit();
        cn.close();

        return true;



            } catch (Exception e) {

                try {
                    if (cn != null) {
                        cn.rollback();
                        cn.close();
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }

                System.out.println("Error al registrar la venta: " + e.getMessage());
                return false;
            }
        }
        public ArrayList<Object[]> listarVentas() {

    ArrayList<Object[]> lista = new ArrayList<>();

    String sql =
        "SELECT v.IdVenta, v.fecha, c.nombre, v.total " +
        "FROM Venta v " +
        "INNER JOIN Cliente c ON v.Cliente_idCliente = c.idCliente " +
        "ORDER BY v.IdVenta";

    try {

        Connection cn = Conexion.conectar();
        PreparedStatement ps = cn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            Object[] fila = {
                rs.getInt("IdVenta"),
                rs.getTimestamp("fecha"),
                rs.getString("nombre"),
                rs.getDouble("total")
            };

            lista.add(fila);
        }

        rs.close();
        ps.close();
        cn.close();

    } catch (Exception e) {
        System.out.println("Error al listar ventas: " + e.getMessage());
    }

    return lista;
}
        
    public Venta buscarVenta(int idVenta) {

    String sql =
    "SELECT v.*, c.*, u.* " +
    "FROM Venta v " +
    "INNER JOIN Cliente c ON v.Cliente_idCliente = c.idCliente " +
    "INNER JOIN Usuario u ON v.Usuario_IdUsuario = u.IdUsuario " +
    "WHERE v.IdVenta = ?";

    try {

        Connection cn = Conexion.conectar();

        PreparedStatement ps = cn.prepareStatement(sql);

        ps.setInt(1, idVenta);

        ResultSet rs = ps.executeQuery();

        if(rs.next()){

            int idCliente = rs.getInt("idCliente");

            Cliente cliente = obtenerCliente(idCliente, cn);

            Usuario vendedor = obtenerUsuario(
                    rs.getInt("IdUsuario"),
                    rs.getString("Username"),
                    rs.getString("contraseña"),
                    rs.getString("tipoUsuario")
            );

            Venta venta = new Venta(
                    rs.getInt("IdVenta"),
                    cliente,
                    vendedor
            );

            venta.setFecha(rs.getTimestamp("fecha"));
            venta.setTotal(rs.getDouble("total"));

            
             String sqlDetalle =
                            "SELECT d.*, p.* " +
                            "FROM Detalle_Venta d " +
                            "INNER JOIN Producto p ON d.Producto_idProducto = p.idProducto " +
                            "WHERE d.Venta_IdVenta = ?";

                        PreparedStatement psDetalle = cn.prepareStatement(sqlDetalle);
                        psDetalle.setInt(1, idVenta);

                        ResultSet rd = psDetalle.executeQuery();

                        while (rd.next()) {

                            Producto producto = new Producto(
                                    rd.getInt("idProducto"),
                                    rd.getString("codigo"),
                                    rd.getString("marca"),
                                    rd.getString("modelo"),
                                    rd.getString("color"),
                                    rd.getString("categoria"),
                                    rd.getDouble("precio"),
                                    rd.getInt("stock")
                            );

                            DetalleVenta detalle = new DetalleVenta(
                                    producto,
                                    rd.getInt("cantidad")
                            );

                            venta.agregarDetalle(detalle);
                        }

                        rd.close();
                        psDetalle.close();
            return venta;

        }

        

    } catch(Exception e){

        System.out.println(e.getMessage());

    }

    return null;

}
    private Cliente obtenerCliente(int idCliente, Connection cn) throws SQLException {

    // Primero obtenemos los datos generales del cliente
    String sqlCliente = "SELECT * FROM Cliente WHERE idCliente = ?";

    PreparedStatement psCliente = cn.prepareStatement(sqlCliente);
    psCliente.setInt(1, idCliente);

    ResultSet rsCliente = psCliente.executeQuery();

    if (!rsCliente.next()) {
        rsCliente.close();
        psCliente.close();
        return null;
    }

    String nombre = rsCliente.getString("nombre");
    String telefono = rsCliente.getString("telefono");
    String email = rsCliente.getString("email");

    rsCliente.close();
    psCliente.close();

    // Verificar si es cliente natural
    String sqlNatural =
            "SELECT dniCliente FROM Cliente_Natural WHERE idCliente = ?";

    PreparedStatement psNatural = cn.prepareStatement(sqlNatural);
    psNatural.setInt(1, idCliente);

    ResultSet rsNatural = psNatural.executeQuery();

    if (rsNatural.next()) {

        String dni = rsNatural.getString("dniCliente");

        rsNatural.close();
        psNatural.close();

        return new ClienteNatural(
                idCliente,
                nombre,
                telefono,
                email,
                dni
        );
    }

    rsNatural.close();
    psNatural.close();

    // Si no es natural, buscamos si es empresa
    String sqlEmpresa =
            "SELECT ruc, razonSocial FROM Cliente_Empresa WHERE idCliente = ?";

    PreparedStatement psEmpresa = cn.prepareStatement(sqlEmpresa);
    psEmpresa.setInt(1, idCliente);

    ResultSet rsEmpresa = psEmpresa.executeQuery();

    if (rsEmpresa.next()) {

        String ruc = rsEmpresa.getString("ruc");
        String razon = rsEmpresa.getString("razonSocial");

        rsEmpresa.close();
        psEmpresa.close();

        return new Clientejuridico(
                idCliente,
                nombre,
                telefono,
                email,
                ruc,
                razon
        );
    }

    rsEmpresa.close();
    psEmpresa.close();

    return null;
}
    private Usuario obtenerUsuario(int id,
                               String username,
                               String password,
                               String tipo){

    switch(tipo){

        case "Administrador":
            return new Administrador(id,username,password);

        case "ATC":
            return new Atc(id,username,password);

        default:
            return new Encargadoalmacen(id,username,password);

    }

}
}
