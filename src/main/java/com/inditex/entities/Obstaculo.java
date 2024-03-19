package com.inditex.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="obstaculos")
@IdClass(ObstaculoId.class)
public class Obstaculo {

    @Id
    @Column(name = "direccionX")
    private int direccionX;

    @Id
    @Column(name = "direccionY")
    private int direccionY;

    public Obstaculo(){
	super();
    }
 
    public Obstaculo(int direccionX, int direccionY){
	this.direccionX = direccionX;
	this.direccionY = direccionY;
    }

    public int getDireccionX(){
	return this.direccionX;
    }

    public int getDireccionY(){
	return this.direccionY;
    }

    public void setDireccionX(int direccionX){
	this.direccionX= direccionX;
    }

    public void setDireccionY(int direccionY){
	this.direccionY= direccionY;
    }
}

