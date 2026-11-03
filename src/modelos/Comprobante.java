/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

import interfaces.Imprimible;

/**
 *
 * @author Ander
 */
public abstract class Comprobante implements Imprimible{
    
    protected String numero;
    protected Venta venta;

    public Comprobante(
            String numero,
            Venta venta) {

        this.numero = numero;
        this.venta = venta;
    }

    public abstract void generarComprobante();
}

