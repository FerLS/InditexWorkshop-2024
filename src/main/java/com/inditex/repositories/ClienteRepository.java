package com.inditex.repositories;

import java.util.List;

import com.inditex.entities.Cliente;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    List<Cliente> findAll();
    List<Cliente> findByDireccionX(int direccionX);
    List<Cliente> findByDireccionY(int direccionY);
    Cliente save(Cliente client);
    void delete(Cliente client);





}
