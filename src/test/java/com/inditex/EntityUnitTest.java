package com.inditex;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import com.inditex.entities.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@TestInstance(Lifecycle.PER_CLASS)
class EntityUnitTest {

	@Autowired
	private TestEntityManager entityManager;

    private Producto producto1;
    private Producto producto2;

    private Cliente cliente1;
    private Cliente cliente2;

    private Locker locker1;
    private Locker locker2;

    private Obstaculo obstaculo1;
    private Obstaculo obstaculo2;

    @BeforeEach
    void setUp() {
        producto1 = new Producto("ProductoUno", 134);
        producto2 = new Producto("ProductoDos", 32);

        cliente1 = new Cliente("ClienteUno", 1, 1);
        cliente2 = new Cliente("ClienteDos", 2, 3);

        locker1 = new Locker(1, 2);
        locker2 = new Locker(2, 3);

        obstaculo1 = new Obstaculo(1, 1);
        obstaculo2 = new Obstaculo(2, 3);

        entityManager.persistAndFlush(producto1);
        entityManager.persistAndFlush(producto2);
        entityManager.persistAndFlush(cliente1);
        entityManager.persistAndFlush(cliente2);
        entityManager.persistAndFlush(locker1);
        entityManager.persistAndFlush(locker2);
        entityManager.persistAndFlush(obstaculo1);
        entityManager.persistAndFlush(obstaculo2);
    }

    @Test
    void testProductoEntity() {
        Producto retrievedProducto = entityManager.find(Producto.class, producto1.getId());
        assertThat(retrievedProducto).isEqualTo(producto1);
        assertThat(retrievedProducto.getNombre()).isEqualTo(producto1.getNombre());
        assertThat(retrievedProducto.getStock()).isEqualTo(producto1.getStock());
    }

    @Test
    void testClienteEntity() {
        Cliente retrievedCliente = entityManager.find(Cliente.class, cliente1.getId());
        assertThat(retrievedCliente).isEqualTo(cliente1);
        assertThat(retrievedCliente.getNombre()).isEqualTo(cliente1.getNombre());
        assertThat(retrievedCliente.getDireccionX()).isEqualTo(cliente1.getDireccionX());
        assertThat(retrievedCliente.getDireccionY()).isEqualTo(cliente1.getDireccionY());
    }

    @Test
    void testLockerEntity() {
        Locker retrievedLocker = entityManager.find(Locker.class, locker1.getId());
        assertThat(retrievedLocker).isEqualTo(locker1);
        assertThat(retrievedLocker.getDireccionX()).isEqualTo(locker1.getDireccionX());
        assertThat(retrievedLocker.getDireccionY()).isEqualTo(locker1.getDireccionY());
    }

    @Test
    void testObstaculoEntity() {
	ObstaculoId obsId = new ObstaculoId(obstaculo1.getDireccionX(), obstaculo1.getDireccionY());
        Obstaculo retrievedObstaculo = entityManager.find(Obstaculo.class, obsId );
        assertThat(retrievedObstaculo).isEqualTo(obstaculo1);
        assertThat(retrievedObstaculo.getDireccionX()).isEqualTo(obstaculo1.getDireccionX());
        assertThat(retrievedObstaculo.getDireccionY()).isEqualTo(obstaculo1.getDireccionY());
    }
}
