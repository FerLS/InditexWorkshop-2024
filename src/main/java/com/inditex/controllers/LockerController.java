package com.inditex.controllers;

import com.inditex.repositories.*;
import com.inditex.entities.Locker;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class LockerController {
    // Implementa aquí la solución requerida

    LockerRepository lockerRepository;

    @Autowired
    public LockerController(LockerRepository lockerRepository){
        this.lockerRepository = lockerRepository;
    }

    
    // Métodos de ejemplo
    @DeleteMapping("/lockers/{id}")
    public ResponseEntity<HttpStatus> deleteLocker(@PathVariable("id") long id){
        Optional<Locker> locker = lockerRepository.findById(id);

        if (! locker.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        lockerRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/lockers")
    public ResponseEntity<HttpStatus> deleteAllLockers(){
        lockerRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }



    @PostMapping("/locker")
    public ResponseEntity<Locker> createLocker(@RequestBody Locker locker){
        try {
            Locker _locker = lockerRepository
                    .save(new Locker(locker.getDireccionX(), locker.getDireccionY()));
            return new ResponseEntity<>(_locker, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/lockers")
    public ResponseEntity<List<Locker>> getAllLockers() {
        try {
            List<Locker> lockers = new ArrayList<Locker>();

            lockerRepository.findAll().forEach(lockers::add);

            if (lockers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(lockers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/lockers/{id}")
    public ResponseEntity<Locker> getLockerById(@PathVariable("id") long id){
        Optional<Locker> locker = lockerRepository.findById(id);
        if (locker.isPresent()){
            return new ResponseEntity<>(locker.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }






}
