package com.inditex.repositories;

import java.util.List;

import com.inditex.entities.Pedido;
import com.inditex.entities.Locker;
import com.inditex.entities.Cliente;
import com.inditex.entities.Producto;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findAll();
    Pedido save(Pedido pedido);
    void delete(Pedido pedido);
}
