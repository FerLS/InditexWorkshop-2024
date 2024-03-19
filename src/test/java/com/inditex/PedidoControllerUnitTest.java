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

import com.inditex.controllers.PedidoController;
import com.inditex.repositories.*;
import com.inditex.entities.*;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(PedidoController.class)
class PedidoControllerUnitTest{

    @MockBean
    private PedidoRepository pedidoRepository;

    @MockBean
    private ClienteRepository clienteRepository;

    @MockBean
    private ProductoRepository productoRepository;

    @MockBean
    private LockerRepository lockerRepository;

    @Autowired 
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreatePedido() throws Exception {
	Locker locker1 = new Locker(2,1); 
	Locker locker2 = new Locker(2,2); 
	Producto producto = new Producto("Camiseta", 1); 
	Cliente cliente = new Cliente("Jose", 3,2); 

	List<Locker> lockers = new ArrayList<>();
	lockers.add(locker1);
	lockers.add(locker2);

	when(lockerRepository.findAll()).thenReturn(lockers);
	when(productoRepository.findById(producto.getId())).thenReturn(Optional.ofNullable(producto));
	when(clienteRepository.findById(cliente.getId())).thenReturn(Optional.ofNullable(cliente));

	String url ="/api/pedido?productoId=" + producto.getId()
	    + "&clienteId="  + cliente.getId();
	mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated());
    }

    @Test
    void shouldNotCreatePedido() throws Exception {
	Locker locker1 = new Locker(2,1); 
	Locker locker2 = new Locker(2,2); 
	Producto producto = new Producto("Camiseta", 0); 
	Cliente cliente = new Cliente("Jose", 3,2); 

	List<Locker> lockers = new ArrayList<>();
	lockers.add(locker1);
	lockers.add(locker2);

	when(lockerRepository.findAll()).thenReturn(lockers);

	String url ="/api/pedido?productoId=" + producto.getId()
	    + "&clienteId="  + cliente.getId();
	mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound());


	when(productoRepository.findById(producto.getId())).thenReturn(Optional.ofNullable(producto));

	mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound());

	when(clienteRepository.findById(cliente.getId())).thenReturn(Optional.ofNullable(cliente));

	mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON))
		 .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateMultiplesPedido() throws Exception {
	Locker locker1 = new Locker(2,1); 
	Locker locker2 = new Locker(2,2); 
	Locker locker3 = new Locker(20,32); 
	Producto producto1 = new Producto("Camiseta", 2); 
	Producto producto2 = new Producto("Bolso", 10); 
	Cliente cliente1 = new Cliente("Jose", 3,2); 
	Cliente cliente2 = new Cliente("Paula", 4,4); 
	Cliente cliente3 = new Cliente("Juan", 6,3); 
	Cliente cliente4 = new Cliente("Natalia", 8,2); 

	List<Locker> lockers = new ArrayList<>();
	lockers.add(locker1);
	lockers.add(locker2);
	lockers.add(locker3);

	List<Producto> productos = new ArrayList<>();
	productos.add(producto1);
	productos.add(producto2);

	List<Cliente> clientes = new ArrayList<>();
	clientes.add(cliente1);
	clientes.add(cliente2);
	clientes.add(cliente3);
	clientes.add(cliente4);

	when(lockerRepository.findAll()).thenReturn(lockers);
	when(productoRepository.findAll()).thenReturn(productos);
	when(clienteRepository.findAll()).thenReturn(clientes);

	when(productoRepository.findById(producto1.getId())).thenReturn(Optional.ofNullable(producto1));
	when(productoRepository.findById(producto2.getId())).thenReturn(Optional.ofNullable(producto2));

	when(clienteRepository.findById(cliente1.getId())).thenReturn(Optional.ofNullable(cliente1));
	when(clienteRepository.findById(cliente2.getId())).thenReturn(Optional.ofNullable(cliente2));

	String url ="/api/pedido?productoId=" + producto1.getId()
	    + "&clienteId="  + cliente1.getId();

	mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated());

	url ="/api/pedido?productoId=" + producto2.getId()
	    + "&clienteId="  + cliente2.getId();

	mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated());

    }
    
    @Test
    void shouldGetNoPedidos() throws Exception{
        List<Pedido> pedidos = new ArrayList<Pedido>();
        when(pedidoRepository.findAll()).thenReturn(pedidos);
        mockMvc.perform(get("/api/pedidos"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldGetTwoPedidos() throws Exception{
	Locker locker1 = new Locker(2,1); 
	Locker locker2 = new Locker(2,2); 
	Producto producto = new Producto("Camiseta", 2); 
	Cliente cliente1 = new Cliente("Jose", 3,2); 
	Cliente cliente2 = new Cliente("Paula", 8,2); 

	Pedido p1 = new Pedido(producto, cliente1, locker2);
	Pedido p2 = new Pedido(producto, cliente2, locker2);

	List<Pedido> pedidos = new ArrayList<>();
	pedidos.add(p1);
	pedidos.add(p2);
	    

        when(pedidoRepository.findAll()).thenReturn(pedidos);
        mockMvc.perform(get("/api/pedidos"))
                .andExpect(status().isOk());
    }
    
    @Test
    void shouldNotGetAnyPedidosById() throws Exception{
        long id = 31;
        mockMvc.perform(get("/api/pedidos/" + id))
                .andExpect(status().isNotFound());
    }
}

