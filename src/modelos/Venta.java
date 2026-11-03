/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Ander
 */
public class Venta {

    private int idVenta;
    private Date fecha;

    private Cliente cliente;
    private Usuario vendedor;

    private ArrayList<DetalleVenta> detalles;

    private double total;

    public Venta(int idVenta,
                 Cliente cliente,
                 Usuario vendedor) {

        this.idVenta = idVenta;
        this.cliente = cliente;
        this.vendedor = vendedor;

        fecha = new Date();
        detalles = new ArrayList<>();
    }

    public void agregarDetalle(DetalleVenta detalle) {
        detalles.add(detalle);
        calcularTotal();
    }

    private void calcularTotal() {

        total = 0;

        for (DetalleVenta d : detalles) {
            total += d.getSubtotal();
        }
    }

    public int getIdVenta() {
        return idVenta;
    }

    public Date getFecha() {
        return fecha;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Usuario getVendedor() {
        return vendedor;
    }

    public void setVendedor(Usuario vendedor) {
        this.vendedor = vendedor;
    }

    public ArrayList<DetalleVenta> getDetalles() {
        return detalles;
    }

    public double getTotal() {
        return total;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {

        return "Venta N° " + idVenta +
               " Cliente: " + cliente.mostrarDatos() +
               " Total: S/. " + total;
    }

}
