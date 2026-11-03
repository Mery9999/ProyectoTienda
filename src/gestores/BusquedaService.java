/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestores;

import modelos.Producto;

/**
 *
 * @author Ander
 */
public class BusquedaService {private GestorInventario inventario;
    



    public BusquedaService(
            GestorInventario inventario) {

        this.inventario = inventario;
    }

    public void buscarPorCodigo(
            String codigo) {

        Producto p =
                inventario
                .buscarProducto(codigo);

        if(p != null){
            System.out.println(p);
        }else{
            System.out.println(
                    "Producto no encontrado"
            );
        }
    }

    public void buscarPorMarca(
            String marca) {

        for(Producto p :
                inventario.getProductos()) {

            if(p.getMarca()
                    .equalsIgnoreCase(
                            marca)) {

                System.out.println(p);
            }
        }
    }

    public void buscarPorColor(
            String color) {

        for(Producto p :
                inventario.getProductos()) {

            if(p.getColor()
                    .equalsIgnoreCase(
                            color)) {

                System.out.println(p);
            }
        }
    }

    public void buscarPorCategoria(
            String categoria) {

        for(Producto p :
                inventario.getProductos()) {

            if(p.getCategoria()
                    .equalsIgnoreCase(
                            categoria)) {

                System.out.println(p);
            }
        }
    }
}
