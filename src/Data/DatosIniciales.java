/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Data;

import java.util.ArrayList;
import modelos.Administrador;
import modelos.Atc;
import modelos.Encargadoalmacen;
import modelos.Usuario;

/**
 *
 * @author Ander
 */
public class DatosIniciales {
    public static ArrayList<Usuario> usuarios = new ArrayList<>();

    static {
        usuarios.add(new Administrador(1, "admin", "123"));
        usuarios.add(new Encargadoalmacen(2, "almacen", "123"));
        usuarios.add(new Atc(3, "atc", "123"));}
    
}
