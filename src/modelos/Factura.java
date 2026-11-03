/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 *
 * @author Ander
 */
public class Factura extends Comprobante{
     public Factura (String numero,
            Venta venta) {

        super(numero, venta);
    }

    @Override
    public void generarComprobante() {

        System.out.println(
                "Factura generada");
    }

    @Override
    public String imprimir() {

        return "FACTURA N° " + numero +
               "\nCliente: " +
               venta.getCliente()
                    .mostrarDatos() +
               "\nTotal: S/. " +
               venta.getTotal();
    }
}
