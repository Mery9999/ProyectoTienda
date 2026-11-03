/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 *
 * @author Ander
 */
public class Atc extends Usuario{
    public Atc(int idUsuario, String username, String password) {
        super(idUsuario, username, password);
    }

    @Override
    public void gestionarModulo() {
        System.out.println("Gestionando ventas");
    }
}
