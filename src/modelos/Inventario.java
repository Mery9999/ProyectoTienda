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
public class Inventario {
    
    private ArrayList<Producto> productos;

    public Inventario() {
        productos = new ArrayList<>();
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public Producto buscarProducto(String codigo) {

        for (Producto p : productos) {

            if (p.getCodigo().equalsIgnoreCase(codigo)) {
                return p;
            }
        }
         return null;
    }

    public void listarProductos() {

        for (Producto p : productos) {
            System.out.println(p);
        }
    }
}
