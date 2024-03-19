package com.inditex;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import com.inditex.repositories.ProductoRepository;
import com.inditex.entities.Producto;


@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
class ProductoJpaUnitTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    ProductoRepository repository;

    @Test
    void should_find_no_productos_if_repository_is_empty(){
        Iterable<Producto> productos = repository.findAll();
        assertThat(productos).isEmpty();
    }

    @Test
    void should_create_a_producto(){
        Producto producto = repository.save(new Producto("ProductoNombre", 13));

        assertThat(producto)
            .hasFieldOrPropertyWithValue("nombre", "ProductoNombre")
            .hasFieldOrPropertyWithValue("stock", 13);
    }

    @Test
    void should_find_all_productos(){

        Producto producto1 = new Producto("ProductoUno", 134);
        Producto producto2 = new Producto("ProductoDos", 32);
        Producto producto3 = new Producto("ProductoTres", 7);

        entityManager.persist(producto1);
        entityManager.persist(producto2);
        entityManager.persist(producto3);

        Iterable productos = repository.findAll();

        assertThat(productos).hasSize(3).contains(producto1, producto2, producto3);
    }

    @Test
    void should_find_producto_by_id(){
        Producto producto1 = new Producto("ProductoUno", 134);
        Producto producto2 = new Producto("ProductoDos", 32);

        entityManager.persist(producto1);
        entityManager.persist(producto2);

        Producto foundProducto = repository.findById(producto2.getId()).get();

        assertThat(foundProducto).isEqualTo(producto2);
    }

    @Test
    void should_delete_producto(){
        Producto producto1 = new Producto("ProductoUno", 134);
        Producto producto2 = new Producto("ProductoDos", 32);
        Producto producto3 = new Producto("ProductoTres", 7);

        entityManager.persist(producto1);
        entityManager.persist(producto2);
        entityManager.persist(producto3);

        repository.deleteById(producto2.getId());

        Iterable productos = repository.findAll();

        assertThat(productos).hasSize(2).contains(producto1, producto3);
    }

    @Test
    void should_delete_all_productos(){
        Producto producto1 = new Producto("ProductoUno", 134);
        Producto producto2 = new Producto("ProductoDos", 32);
        Producto producto3 = new Producto("ProductoTres", 7);

        entityManager.persist(producto1);
        entityManager.persist(producto2);
        entityManager.persist(producto3);

        repository.deleteAll();
        assertThat(repository.findAll()).isEmpty();
    }
    
}
