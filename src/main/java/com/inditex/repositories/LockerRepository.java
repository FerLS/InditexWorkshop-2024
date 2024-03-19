package com.inditex.repositories;

import java.util.List;

import com.inditex.entities.Locker;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LockerRepository extends JpaRepository<Locker, Long> {
    List<Locker> findAll();
    Locker save(Locker locker);
    void delete(Locker locker);
}
