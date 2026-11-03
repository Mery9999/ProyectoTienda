/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 *
 * @author Ander
 */
public class Boleta extends Comprobante{
      public Boleta(
            String numero,
            Venta venta) {

        super(numero, venta);
    }

    @Override
    public void generarComprobante() {

        System.out.println(
                "Boleta generada");
    }

    @Override
    public String imprimir() {

        return "BOLETA N° " + numero +
               "\nCliente: " +
               venta.getCliente()
                    .mostrarDatos() +
               "\nTotal: S/. " +
               venta.getTotal();
    }
}
