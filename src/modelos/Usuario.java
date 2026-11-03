/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 *
 * @author Ander
 */
public abstract class Usuario {
    private int idUsuario;
    private String username;
    private String password;

    public Usuario(int idUsuario, String username, String password) {
        this.idUsuario = idUsuario;
        this.username = username;
        this.password = password;
    }

    public abstract void gestionarModulo();

    public boolean iniciarSesion() {
        return true;
    }

    public void cerrarSesion() {
        System.out.println("Sesión cerrada");
    }

    public int getId() {
        return idUsuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    private boolean activo = true;
    public void desactivar() {
            activo = false;
        }

        public boolean isActivo() {
            return activo;
        }

    @Override
    public String toString() {
        return "ID: " +idUsuario +
               ", Usuario: " + username;
    }
    public String getPassword() {
    return password;
    }
    public void activar(){
        activo = true;
    }
}

