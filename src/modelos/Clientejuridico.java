/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 *
 * @author Ander
 */
public class Clientejuridico extends Cliente{
    
    private String ruc;
    private String razonSocial;

    public Clientejuridico(int idcliente,
                           String nombre,
                           String telefono,
                           String email,
                           String ruc,
                           String razonSocial) {

        super(idcliente, nombre, telefono, email);

        this.ruc = ruc;
        this.razonSocial = razonSocial;
    }

    @Override
    public String mostrarDatos() {
        return razonSocial + " - RUC: " + ruc;
    }

    public String getRuc() {
        return ruc;
    }

    public String getRazonSocial() {
        return razonSocial;
    }
    
}
