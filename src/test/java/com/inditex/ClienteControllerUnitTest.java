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

import com.inditex.controllers.ClienteController;
import com.inditex.repositories.*;
import com.inditex.entities.*;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ClienteController.class)
class ClienteControllerUnitTest{

    @MockBean
    private ClienteRepository clienteRepository;

    @Autowired 
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateCliente() throws Exception {
	Cliente lock = new Cliente("Kiwi", 13, 12);
	mockMvc.perform(post("/api/cliente").contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(lock)))
			.andExpect(status().isCreated());
    }
    
    @Test
    void shouldGetNoClientes() throws Exception{
        List<Cliente> clientes = new ArrayList<Cliente>();
        when(clienteRepository.findAll()).thenReturn(clientes);
        mockMvc.perform(get("/api/clientes"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldGetTwoClientes() throws Exception{
	Cliente l1 = new Cliente("Strawberry", 13, 12);
	Cliente l2 = new Cliente("Kiwi", 3, 23);

        List<Cliente> clientes = new ArrayList<Cliente>();
        clientes.add(l1);
        clientes.add(l2);

        when(clienteRepository.findAll()).thenReturn(clientes);
        mockMvc.perform(get("/api/clientes"))
                .andExpect(status().isOk());
                
    }
    @Test
    void shouldGetClienteById() throws Exception {
	Cliente l1 = new Cliente("Cake",10, 3);
	l1.setId(1);

	Optional<Cliente> opt = Optional.of(l1);

	assertThat(opt).isPresent();
	assertThat(opt.get().getId()).isEqualTo(l1.getId());
	assertThat(l1.getId()).isEqualTo(1);

        when(clienteRepository.findById(l1.getId())).thenReturn(opt);
        mockMvc.perform(get("/api/clientes/" + l1.getId()))
                .andExpect(status().isOk());

    }
    
    @Test
    void shouldNotGetAnyClientesById() throws Exception{
        long id = 31;
        mockMvc.perform(get("/api/clientes/" + id))
                .andExpect(status().isNotFound());
    }

    //Quiero que me devuelva un cliente hciendo una peticion get


}

