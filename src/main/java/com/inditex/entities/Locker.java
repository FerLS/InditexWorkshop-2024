package com.inditex.entities;

import javax.persistence.*;

@Entity
@Table(name="lockers")
public class Locker{

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    private int direccionX;
    private int direccionY;

    public Locker(){
	super();
    }
 
    public Locker(int direccionX, int direccionY){
	this.direccionX = direccionX;
	this.direccionY = direccionY;
    }

    public long getId(){
	return this.id;
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

    public void setDireccionX(int direccionX){
	this.direccionX= direccionX;
    }

    public void setDireccionY(int direccionY){
	this.direccionY= direccionY;
    }
}
