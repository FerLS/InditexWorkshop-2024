package com.inditex.controllers;

import com.inditex.repositories.*;
import com.inditex.entities.Obstaculo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class ObstaculoController {
    // Implementa aquí la solución requerida


    ObstaculoRepository obstaculoRepository;

    @Autowired
    public ObstaculoController(ObstaculoRepository obstaculoRepository){
        this.obstaculoRepository = obstaculoRepository;
    }





    // Métodos de ejemplo
    @DeleteMapping("/obstaculos/{direccionX}/{direccionY}")
    public ResponseEntity<HttpStatus> deleteObstaculo(@PathVariable("direccionX") int direccionX, @PathVariable("direccionY") int direccionY){
        Optional<Obstaculo> obstaculo = obstaculoRepository.findByDireccionXAndDireccionY(direccionX, direccionY);

        if (! obstaculo.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        obstaculoRepository.delete(obstaculo.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/obstaculos")
    public ResponseEntity<HttpStatus> deleteAllObstaculos(){
        obstaculoRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/obstaculos")

    public ResponseEntity<List<Obstaculo>> getAllObstaculos(){
        try {
            List<Obstaculo> obstaculos = new ArrayList<Obstaculo>();

            obstaculoRepository.findAll().forEach(obstaculos::add);

            if (obstaculos.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(obstaculos, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/obstaculo")

    public ResponseEntity<Obstaculo> createObstaculo(@RequestBody Obstaculo obstaculo){
        try {
            Obstaculo _obstaculo = obstaculoRepository
                    .save(new Obstaculo(obstaculo.getDireccionX(), obstaculo.getDireccionY()));
            return new ResponseEntity<>(_obstaculo, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/obstaculos/id")

    public ResponseEntity<Obstaculo> getObstaculoById(@RequestParam("x") int direccionX, @RequestParam("y") int direccionY){
        Optional<Obstaculo> obstaculo = obstaculoRepository.findByDireccionXAndDireccionY(direccionX, direccionY);
        if (obstaculo.isPresent()){
            return new ResponseEntity<>(obstaculo.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
