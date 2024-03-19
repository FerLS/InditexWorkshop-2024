package com.inditex;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import com.inditex.repositories.ObstaculoRepository;
import com.inditex.entities.Obstaculo;


@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
class ObstaculoJpaUnitTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    ObstaculoRepository repository;

    @Test
    void should_find_no_obstaculos_if_repository_is_empty(){
        Iterable<Obstaculo> obstaculos = repository.findAll();
        assertThat(obstaculos).isEmpty();
    }

    @Test
    void should_create_a_obstaculo(){
        Obstaculo obstaculo1 = new Obstaculo(1, 3);

        assertThat(obstaculo1)
            .hasFieldOrPropertyWithValue("direccionX", 1)
            .hasFieldOrPropertyWithValue("direccionY", 3);
    }

    @Test
    void should_find_all_obstaculos(){

        Obstaculo obstaculo1 = new Obstaculo(1, 1);
        Obstaculo obstaculo2 = new Obstaculo(2, 3);
        Obstaculo obstaculo3 = new Obstaculo(3, 2);

        entityManager.persist(obstaculo1);
        entityManager.persist(obstaculo2);
        entityManager.persist(obstaculo3);

        Iterable obstaculos = repository.findAll();

        assertThat(obstaculos).hasSize(3).contains(obstaculo1, obstaculo2, obstaculo3);
    }

    @Test
    void should_find_obstaculo_by_id(){
        Obstaculo obstaculo1 = new Obstaculo(1, 1);
        Obstaculo obstaculo2 = new Obstaculo(2, 3);
        Obstaculo obstaculo3 = new Obstaculo(3, 2);

        entityManager.persist(obstaculo1);
        entityManager.persist(obstaculo2);

        Obstaculo foundObstaculo = repository.findByDireccionXAndDireccionY(obstaculo2.getDireccionX(), obstaculo2.getDireccionY()).get();

        assertThat(foundObstaculo).isEqualTo(obstaculo2);
    }

    @Test
    void should_delete_obstaculo(){
        Obstaculo obstaculo1 = new Obstaculo(1, 1);
        Obstaculo obstaculo2 = new Obstaculo(2, 3);
        Obstaculo obstaculo3 = new Obstaculo(3, 2);

        entityManager.persist(obstaculo1);
        entityManager.persist(obstaculo2);
        entityManager.persist(obstaculo3);

        repository.delete(obstaculo2);

        Iterable obstaculos = repository.findAll();

        assertThat(obstaculos).hasSize(2).contains(obstaculo1, obstaculo3);
    }

    @Test
    void should_delete_all_obstaculos(){
        Obstaculo obstaculo1 = new Obstaculo(1, 1);
        Obstaculo obstaculo2 = new Obstaculo(2, 3);
        Obstaculo obstaculo3 = new Obstaculo(3, 2);

        entityManager.persist(obstaculo1);
        entityManager.persist(obstaculo2);
        entityManager.persist(obstaculo3);

        repository.deleteAll();
        assertThat(repository.findAll()).isEmpty();
    }
    
}
