/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Formularios;

import java.util.Scanner;

/**
 *
 * @author Ander
 */
public class Consola {
    private Scanner sc;

    public Consola() {
        sc = new Scanner(System.in);
    }

    public int leerInt(String mensaje) {
        System.out.print(mensaje);
        return sc.nextInt();
    }

    public double leerDouble(String mensaje) {
        System.out.print(mensaje);
        return sc.nextDouble();
    }

    public String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return sc.next();
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void cerrar() {
        sc.close();
    }
}
