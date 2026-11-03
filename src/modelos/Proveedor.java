/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

import java.util.ArrayList;

/**
 *
 * @author Ander
 */
public class Proveedor {
 private ArrayList<Producto> productos;
    private int idProveedor;
    private String nombre;
    private String telefono;

    public Proveedor(int idProveedor, String nombre, String telefono) {
        this.idProveedor = idProveedor;
        this.nombre = nombre;
        this.telefono = telefono;
        this.productos = new ArrayList<>();
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    @Override
    public String toString() {
        return nombre + " - " + telefono;
    }
      public void agregarProducto(Producto producto) {
    if (!productos.contains(producto)) {
        productos.add(producto);
    }
}

    public ArrayList<Producto> getProductos() {
        return productos;
    }
}
