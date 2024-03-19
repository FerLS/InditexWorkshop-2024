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

import com.inditex.controllers.ProductoController;
import com.inditex.repositories.*;
import com.inditex.entities.*;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ProductoController.class)
class ProductoControllerUnitTest{

    @MockBean
    private ProductoRepository productoRepository;

    @Autowired 
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateProducto() throws Exception {
	Producto prod = new Producto("TestProducto", 12);
	mockMvc.perform(post("/api/producto").contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(prod)))
			.andExpect(status().isCreated());
    }

    @Test
    void shouldGetNoProductos() throws Exception{
        List<Producto> prods= new ArrayList<Producto>();
        when(productoRepository.findAll()).thenReturn(prods);
        mockMvc.perform(get("/api/productos"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldGetTwoProductos() throws Exception{
	Producto p1 = new Producto("ProductoUno", 12);
	Producto p2 = new Producto("ProductoDos", 13);

        List<Producto> prods = new ArrayList<Producto>();
        prods.add(p1);
        prods.add(p2);

        when(productoRepository.findAll()).thenReturn(prods);
        mockMvc.perform(get("/api/productos"))
                .andExpect(status().isOk());
                
    }
    @Test
    void shouldGetProductoById() throws Exception {
	Producto p1 = new Producto("ProductoUno", 12);
	p1.setId(1);

	Optional<Producto> opt = Optional.of(p1);

	assertThat(opt).isPresent();
	assertThat(opt.get().getId()).isEqualTo(p1.getId());
	assertThat(p1.getId()).isEqualTo(1);

        when(productoRepository.findById(p1.getId())).thenReturn(opt);
        mockMvc.perform(get("/api/productos/" + p1.getId()))
                .andExpect(status().isOk());

    }
    
    @Test
    void shouldNotGetAnyProductosById() throws Exception{
        long id = 31;
        mockMvc.perform(get("/api/productos/" + id))
                .andExpect(status().isNotFound());
    }
}

