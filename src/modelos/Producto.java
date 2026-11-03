/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 *
 * @author Ander
 */
public class Producto {
    private int idProducto;
    private String codigo;
    private String marca;
    private String modelo;
    private double talla;
    private String color;
    private String categoria;
    private double precio;
    private int stock;

    public Producto(int idProducto, String codigo, String marca, String modelo, String color, String categoria, double precio, int stock) {

        this.idProducto = idProducto;
        this.codigo = codigo;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
        this.categoria = categoria;
        this.precio = precio;
        this.talla = 0;
        this.stock = stock;
    }

    public void actualizarStock(int cantidad) {
        stock += cantidad;
    }
    public void actualizarPrecio(double nuevoPrecio){
    precio = nuevoPrecio;
    }

    @Override
    public String toString() {
        return codigo + " - " + marca +
               " " + modelo;
    }
    public String getCodigo() 
    {
    return codigo;
    }
    public double getPrecio() 
    {
    return precio;
    }
    
    public int getIdProducto() {
        return idProducto;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    public String getMarca(){
        return marca;
    }
    public String getColor(){
        return color;
    }
    public String getCategoria(){
        return categoria;
    }

    public String getModelo() {
        return modelo;
    }
    
    public double getTalla() {
        return talla;
    }


}
