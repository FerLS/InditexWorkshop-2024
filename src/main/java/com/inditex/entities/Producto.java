package com.inditex.entities;

import javax.persistence.*;

@Entity
@Table(name="productos")
public class Producto {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    private String nombre;
    private int stock;

    public Producto(){
	super();
    }
 
    public Producto(String nombre,int stock){
	this.nombre = nombre;
	this.stock = stock;
    }

    public long getId(){
	return this.id;
    }

    public String getNombre(){
	return this.nombre;
    }

    public int getStock(){
	return this.stock;
    }

    public void setStock(int stock){
	this.stock = stock;
    }

    public void setNombre(String nombre){
	this.nombre= nombre;
    }

    public void setId(long id){
	this.id= id;
    }
    
}
