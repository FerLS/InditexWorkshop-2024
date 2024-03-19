package com.inditex.controllers;

import com.inditex.repositories.*;
import com.inditex.entities.Cliente;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class ClienteController {
    // Implementa aquí la solución requerida
    ClienteRepository clienteRepository;

    @Autowired
    public ClienteController(ClienteRepository clienteRepository){
        this.clienteRepository = clienteRepository;
    }


    // Métodos de ejemplo
    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<HttpStatus> deleteCliente(@PathVariable("id") long id){
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if (! cliente.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        clienteRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/clientes")
    public ResponseEntity<HttpStatus> deleteAllClientes(){
        clienteRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }



    @PostMapping("/cliente")
    public ResponseEntity<Cliente> createCliente(@RequestBody Cliente cliente){
        try {
            Cliente _cliente = clienteRepository
                    .save(new Cliente(cliente.getNombre(), cliente.getDireccionX(), cliente.getDireccionY()));
            return new ResponseEntity<>(_cliente, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/clientes")
    public ResponseEntity<List<Cliente>> getAllClientes() {
        try {
            List<Cliente> clientes = new ArrayList<Cliente>();

            clienteRepository.findAll().forEach(clientes::add);

            if (clientes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(clientes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/clientes/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable("id") long id){
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if (cliente.isPresent()){
            return new ResponseEntity<>(cliente.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }




}
