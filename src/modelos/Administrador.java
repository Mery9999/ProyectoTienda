/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 *
 * @author Ander
 */
public class Administrador extends Usuario{
    
    public Administrador(int id,
                         String username,
                         String password) {
        super(id, username, password);
    }

    @Override
    public void gestionarModulo() {
        System.out.println("Gestionando usuarios y reportes");
    }
    
}
