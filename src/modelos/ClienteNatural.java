/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 *
 * @author Ander
 */
public class ClienteNatural extends Cliente {

    private String dni;

    public ClienteNatural(int idcliente,
                          String nombre,
                          String telefono,
                          String email,
                          String dni) {

        super(idcliente, nombre, telefono, email);
        this.dni = dni;
    }

    @Override
    public String mostrarDatos() {
        return getNombre() + " - DNI: " + dni;
    }

    public String getDni() {
        return dni;
    }
}