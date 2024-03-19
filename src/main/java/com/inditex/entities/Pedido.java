package com.inditex.entities;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="pedidos")
public class Pedido{
	
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "producto_id", referencedColumnName = "id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Producto producto;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cliente_id", referencedColumnName = "id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Cliente cliente;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "locker_id", referencedColumnName = "id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Locker locker;

    public Pedido(){
	super();
    }
 
    public Pedido( Producto producto, Cliente cliente, Locker locker) {
	this.producto = producto;
	this.cliente = cliente;
	this.locker = locker;
    }

    public long getId(){
	return this.id;
    }

    public void setId(long id){
	this.id = id;
    }

    public Producto getProducto(){
	return this.producto;
    }

    public void setProducto(Producto producto){
	this.producto = producto;
    }

    public Cliente getCliente(){
	return this.cliente;
    }

    public void setCliente(Cliente cliente){
	this.cliente = cliente;
    }

    public Locker getLocker(){
	return this.locker;
    }

    public void setLocker(Locker locker){
	this.locker = locker;
    }

    /**
       Calcula la distancia entre un Cliente y un Locker.
     */
    public static double distanciaEntreClienteLocker(Cliente c, Locker l){

	return 0L;
    }
}
