package com.inditex;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import java.time.LocalDateTime;
import java.time.format.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.inditex.controllers.ObstaculoController;
import com.inditex.repositories.*;
import com.inditex.entities.*;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ObstaculoController.class)
class ObstaculoControllerUnitTest{

    @MockBean
    private ObstaculoRepository obstaculoRepository;

    @Autowired 
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateObstaculo() throws Exception {
	Obstaculo obs = new Obstaculo(13, 12);
	mockMvc.perform(post("/api/obstaculo").contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(obs)))
			.andExpect(status().isCreated());
    }
    
    @Test
    void shouldGetNoObstaculos() throws Exception{
        List<Obstaculo> obstaculos = new ArrayList<Obstaculo>();
        when(obstaculoRepository.findAll()).thenReturn(obstaculos);
        mockMvc.perform(get("/api/obstaculos"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldGetTwoObstaculos() throws Exception{
	Obstaculo o1 = new Obstaculo(13, 12);
	Obstaculo o2 = new Obstaculo(3, 23);

        List<Obstaculo> obstaculos = new ArrayList<Obstaculo>();
        obstaculos.add(o1);
        obstaculos.add(o2);

        when(obstaculoRepository.findAll()).thenReturn(obstaculos);
        mockMvc.perform(get("/api/obstaculos"))
                .andExpect(status().isOk());
                
    }
    @Test
    void shouldGetObstaculoById() throws Exception {
	Obstaculo o1 = new Obstaculo(10, 3);

	Optional<Obstaculo> opt = Optional.of(o1);

	assertThat(opt).isPresent();
	assertThat(opt.get().getDireccionX()).isEqualTo(o1.getDireccionX());
	assertThat(opt.get().getDireccionY()).isEqualTo(o1.getDireccionY());
	assertThat(o1.getDireccionX()).isEqualTo(10);
	assertThat(o1.getDireccionY()).isEqualTo(3);

	String url = "/api/obstaculos/id?x=" + o1.getDireccionX() + "&y=" + o1.getDireccionY();
        when(obstaculoRepository.findByDireccionXAndDireccionY(o1.getDireccionX(), o1.getDireccionY())).thenReturn(opt);
        mockMvc.perform(get(url))
	    .andExpect(status().isOk());

    }
    
    @Test
    void shouldNotGetAnyObstaculosById() throws Exception{
	String url = "/api/obstaculos/id?x=1&y=1";
        mockMvc.perform(get(url))
                .andExpect(status().isNotFound());
    }
}

