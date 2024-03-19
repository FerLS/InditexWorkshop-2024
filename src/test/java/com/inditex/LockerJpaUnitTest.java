package com.inditex;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import com.inditex.repositories.LockerRepository;
import com.inditex.entities.Locker;


@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
class LockerJpaUnitTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    LockerRepository repository;

    @Test
    void should_find_no_lockers_if_repository_is_empty(){
        Iterable<Locker> lockers = repository.findAll();
        assertThat(lockers).isEmpty();
    }

    @Test
    void should_create_a_locker(){
        Locker locker1 = new Locker(1, 3);

        assertThat(locker1)
            .hasFieldOrPropertyWithValue("direccionX", 1)
            .hasFieldOrPropertyWithValue("direccionY", 3);
    }

    @Test
    void should_find_all_lockers(){

        Locker locker1 = new Locker(1, 1);
        Locker locker2 = new Locker(2, 3);
        Locker locker3 = new Locker(3, 2);

        entityManager.persist(locker1);
        entityManager.persist(locker2);
        entityManager.persist(locker3);

        Iterable lockers = repository.findAll();

        assertThat(lockers).hasSize(3).contains(locker1, locker2, locker3);
    }

    @Test
    void should_find_locker_by_id(){
        Locker locker1 = new Locker(1, 1);
        Locker locker2 = new Locker(2, 3);
        Locker locker3 = new Locker(3, 2);

        entityManager.persist(locker1);
        entityManager.persist(locker2);

        Locker foundLocker = repository.findById(locker2.getId()).get();

        assertThat(foundLocker).isEqualTo(locker2);
    }

    @Test
    void should_delete_locker(){
        Locker locker1 = new Locker(1, 1);
        Locker locker2 = new Locker(2, 3);
        Locker locker3 = new Locker(3, 2);

        entityManager.persist(locker1);
        entityManager.persist(locker2);
        entityManager.persist(locker3);

        repository.deleteById(locker2.getId());

        Iterable lockers = repository.findAll();

        assertThat(lockers).hasSize(2).contains(locker1, locker3);
    }

    @Test
    void should_delete_all_lockers(){
        Locker locker1 = new Locker(1, 1);
        Locker locker2 = new Locker(2, 3);
        Locker locker3 = new Locker(3, 2);

        entityManager.persist(locker1);
        entityManager.persist(locker2);
        entityManager.persist(locker3);

        repository.deleteAll();
        assertThat(repository.findAll()).isEmpty();
    }
    
}
