package com.inditex;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import com.inditex.repositories.*;
import com.inditex.entities.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
class PedidoJpaUnitTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    LockerRepository lockerRepository;

    @Autowired
    PedidoRepository pedidoRepository;

    private Producto producto1;
    private Producto producto2;

    private Cliente cliente1;
    private Cliente cliente2;

    private Locker locker1;
    private Locker locker2;

    @BeforeEach
    void setUp(){
        producto1 = new Producto("Camiseta", 3);
        producto2 = new Producto("Bolso", 12);

        cliente1 = new Cliente("Paco", 1, 1);
        cliente2 = new Cliente("Lucia", 2, 3);

        locker1 = new Locker(1, 2);
        locker2 = new Locker(2, 4);

	entityManager.persist(producto1);
	entityManager.persist(producto1);

	entityManager.persist(cliente1);
	entityManager.persist(cliente2);

	entityManager.persist(locker1);
	entityManager.persist(locker2);
    }


    @Test
    void should_find_no_pedidos_if_repository_is_empty(){
        Iterable<Pedido> pedidos = pedidoRepository.findAll();
        assertThat(pedidos).isEmpty();
    }

    @Test
    void should_create_a_pedido(){
        Pedido pedido1 = new Pedido(producto1, cliente1, locker1);

        assertThat(pedido1)
            .hasFieldOrPropertyWithValue("producto", producto1)
            .hasFieldOrPropertyWithValue("cliente", cliente1)
            .hasFieldOrPropertyWithValue("locker", locker1);
    }

    @Test
    void should_find_all_pedidos(){

        Pedido pedido1 = new Pedido(producto1, cliente1, locker1);
        Pedido pedido2 = new Pedido(producto1, cliente2, locker1);
        Pedido pedido3 = new Pedido(producto2, cliente2, locker1);

        entityManager.persist(pedido1);
        entityManager.persist(pedido2);
        entityManager.persist(pedido3);

        Iterable pedidos = pedidoRepository.findAll();

        assertThat(pedidos).hasSize(3).contains(pedido1, pedido2, pedido3);
    }

    @Test
    void should_find_pedido_by_id(){
        Pedido pedido1 = new Pedido(producto1, cliente1, locker1);
        Pedido pedido2 = new Pedido(producto1, cliente2, locker1);

        entityManager.persist(pedido1);
        entityManager.persist(pedido2);

        Pedido foundPedido = pedidoRepository.findById(pedido2.getId()).get();

        assertThat(foundPedido).isEqualTo(pedido2);
    }
}
