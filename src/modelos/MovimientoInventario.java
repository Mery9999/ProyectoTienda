/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 *
 * @author Ander
 */
public class MovimientoInventario {
    private String tipo;
    private Producto producto;
    private int cantidad;

    public MovimientoInventario(
            String tipo,
            Producto producto,
            int cantidad) {

        this.tipo = tipo;
        this.producto = producto;
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {

        return tipo +
               " | " +
               producto.getCodigo() +
               " | " +
               cantidad;
    }
}
