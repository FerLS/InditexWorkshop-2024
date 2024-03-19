package com.inditex.repositories;

import java.util.List;

import com.inditex.entities.Producto;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findAll();
    Producto save(Producto prod);
    void delete(Producto prod);
}
