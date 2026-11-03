/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 *
 * @author Ander
 */
public class DetalleVenta {
    private Producto producto;
    private int cantidad;
    private double subtotal;

    public DetalleVenta(Producto producto, int cantidad) {

        this.producto = producto;
        this.cantidad = cantidad;

        calcularSubtotal();
    }

    public void calcularSubtotal() {

        subtotal =
            producto.getPrecio() * cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getSubtotal() {
        return subtotal;
    }
     @Override
    public String toString() {

        return producto +
               " | Cantidad: " + cantidad +
               " | Subtotal: " + subtotal;
    }
}
