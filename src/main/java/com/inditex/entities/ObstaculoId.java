package com.inditex.entities;

import javax.persistence.*;
import java.io.Serializable;

public class ObstaculoId implements Serializable {
    protected int direccionX;
    protected int direccionY;

    public ObstaculoId(){
    }

    public ObstaculoId(int direccionX, int direccionY){
	this.direccionX = direccionX;
	this.direccionY = direccionY;
    }
}
