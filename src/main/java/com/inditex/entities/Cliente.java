package com.inditex.entities;

import javax.persistence.*;

@Entity
@Table(name="clientes")
public class Cliente{

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    private String nombre;
    private int direccionX;
    private int direccionY;

    public Cliente(){
	super();
    }
 
    public Cliente(String nombre, int direccionX, int direccionY){
	this.nombre = nombre;
	this.direccionX = direccionX;
	this.direccionY = direccionY;
    }

    public long getId(){
	return this.id;
    }
    public String getNombre(){
	return this.nombre;
    }

    public int getDireccionX(){
	return this.direccionX;
    }

    public int getDireccionY(){
	return this.direccionY;
    }

    public void setId(long id){
	this.id = id;
    }

    public void setNombre(String nombre){
	this.nombre= nombre;
    }

    public void setDireccionX(int direccionX){
	this.direccionX= direccionX;
    }

    public void setDireccionY(int direccionY){
	this.direccionY= direccionY;
    }
    
}
