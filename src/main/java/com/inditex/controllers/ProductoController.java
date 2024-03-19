package com.inditex.controllers;

import com.inditex.repositories.*;
import com.inditex.entities.Producto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class ProductoController {
    // Implementa aquí la solución requerida

    ProductoRepository productoRepository;

    @Autowired

    public ProductoController(ProductoRepository productoRepository){
        this.productoRepository = productoRepository;
    }



    // Métodos de ejemplo
    @DeleteMapping("/productos/{id}")
    public ResponseEntity<HttpStatus> deleteProducto(@PathVariable("id") long id){
        Optional<Producto> producto = productoRepository.findById(id);

        if (! producto.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        productoRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/productos")
    public ResponseEntity<HttpStatus> deleteAllProductos(){
        productoRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }



    @PostMapping("/producto")

    public ResponseEntity<Producto> createProducto(@RequestBody Producto producto){
        try {
            Producto _producto = productoRepository
                    .save(new Producto(producto.getNombre(), producto.getStock()));
            return new ResponseEntity<>(_producto, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/productos")

    public ResponseEntity<List<Producto>> getAllProductos() {
        try {
            List<Producto> productos = new ArrayList<Producto>();

            productoRepository.findAll().forEach(productos::add);

            if (productos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(productos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/productos/{id}")

    public ResponseEntity<Producto> getProductoById(@PathVariable("id") long id){
        Optional<Producto> producto = productoRepository.findById(id);
        if (producto.isPresent()){
            return new ResponseEntity<>(producto.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }




}
