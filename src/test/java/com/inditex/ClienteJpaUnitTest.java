package com.inditex;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import com.inditex.repositories.ClienteRepository;
import com.inditex.entities.Cliente;


@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
class ClienteJpaUnitTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    ClienteRepository repository;

    @Test
    void should_find_no_clientes_if_repository_is_empty(){
        Iterable<Cliente> clientes = repository.findAll();
        assertThat(clientes).isEmpty();
    }

    @Test
    void should_create_a_cliente(){
        Cliente cliente1 = new Cliente("ClienteUno",  1, 3);

        assertThat(cliente1)
            .hasFieldOrPropertyWithValue("nombre", "ClienteUno")
            .hasFieldOrPropertyWithValue("direccionX", 1)
            .hasFieldOrPropertyWithValue("direccionY", 3);
    }

    @Test
    void should_find_all_clientes(){

        Cliente cliente1 = new Cliente("ClienteUno", 1 , 1);
        Cliente cliente2 = new Cliente("ClienteDos", 2, 3);
        Cliente cliente3 = new Cliente("ClienteTres", 3, 2);

        entityManager.persist(cliente1);
        entityManager.persist(cliente2);
        entityManager.persist(cliente3);

        Iterable clientes = repository.findAll();

        assertThat(clientes).hasSize(3).contains(cliente1, cliente2, cliente3);
    }

    @Test
    void should_find_cliente_by_id(){
        Cliente cliente1 = new Cliente("ClienteUno",  1, 1);
        Cliente cliente2 = new Cliente("ClienteDos",  2, 3);

        entityManager.persist(cliente1);
        entityManager.persist(cliente2);

        Cliente foundCliente = repository.findById(cliente2.getId()).get();

        assertThat(foundCliente).isEqualTo(cliente2);
    }

    @Test
    void should_delete_cliente(){
        Cliente cliente1 = new Cliente("ClienteUno",  1, 1);
        Cliente cliente2 = new Cliente("ClienteDos",  2, 3);
        Cliente cliente3 = new Cliente("ClienteTres", 3, 2);

        entityManager.persist(cliente1);
        entityManager.persist(cliente2);
        entityManager.persist(cliente3);

        repository.deleteById(cliente2.getId());

        Iterable clientes = repository.findAll();

        assertThat(clientes).hasSize(2).contains(cliente1, cliente3);
    }

    @Test
    void should_delete_all_clientes(){
        Cliente cliente1 = new Cliente("ClienteUno",  1, 1);
        Cliente cliente2 = new Cliente("ClienteDos",  2, 3);
        Cliente cliente3 = new Cliente("ClienteTres", 3, 2);

        entityManager.persist(cliente1);
        entityManager.persist(cliente2);
        entityManager.persist(cliente3);

        repository.deleteAll();
        assertThat(repository.findAll()).isEmpty();
    }
    
}
