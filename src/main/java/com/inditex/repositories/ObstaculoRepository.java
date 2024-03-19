package com.inditex.repositories;

import java.util.List;
import java.util.Optional;

import com.inditex.entities.Obstaculo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ObstaculoRepository extends JpaRepository<Obstaculo, Long> {
    List<Obstaculo> findAll();
    Optional<Obstaculo> findByDireccionXAndDireccionY(int direccionX, int direccionY);
    List<Obstaculo> findByDireccionX(int direccionX);
    List<Obstaculo> findByDireccionY(int direccionY);
    Obstaculo save(Obstaculo obs);
    void delete(Obstaculo obs);
}
