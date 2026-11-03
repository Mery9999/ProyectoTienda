/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 *
 * @author Ander
 */
public abstract class Cliente {
    
    private int idCliente;
    private String nombre;
    private String telefono;
    private String email;

    public Cliente(int idCliente,
                   String nombre,
                   String telefono,
                   String email) {

        this.idCliente = idCliente;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
    }

    public abstract String mostrarDatos();

    public int getIdcliente() {
        return idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }
}
