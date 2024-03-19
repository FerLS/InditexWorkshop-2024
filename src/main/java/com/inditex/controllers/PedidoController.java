package com.inditex.controllers;

import com.inditex.repositories.*;
import com.inditex.entities.*;
import com.inditex.AStar.*;

import java.util.*;

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
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/api")
public class PedidoController {
    // Implementa aquí la solución requerida

	PedidoRepository pedidoRepository;

	ProductoRepository productoRepository;
	ClienteRepository clienteRepository;
	LockerRepository lockerRepository;
	@Autowired
	public PedidoController(PedidoRepository pedidoRepository, ProductoRepository productoRepository, ClienteRepository clienteRepository, LockerRepository lockerRepository){

		this.pedidoRepository = pedidoRepository;
	    this.productoRepository = productoRepository;
		this.clienteRepository = clienteRepository;
		this.lockerRepository = lockerRepository;


	}



    // Método de ejemplo
    @DeleteMapping("/pedidos")
    public ResponseEntity<HttpStatus> deleteAllPedidos(){
	List<Pedido> pedidos = pedidoRepository.findAll();
	for (Pedido p : pedidos){
	    Producto prod = p.getProducto();
	    prod.setStock(prod.getStock() + 1);
	    productoRepository.save(prod);
	}

	pedidoRepository.deleteAll();

        return new ResponseEntity<>(HttpStatus.OK);
    }



	@GetMapping("/pedidos")

	public ResponseEntity<List<Pedido>> getAllPedidos(){
		List<Pedido> pedidos = pedidoRepository.findAll();
		if (pedidos.isEmpty()){
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(pedidos, HttpStatus.OK);
	}







	@PostMapping("/pedido")
	public ResponseEntity createPedido(@RequestParam("clienteId") long clienteId, @RequestParam("productoId") long productoId) throws Exception {
		Optional<Cliente> cliente = clienteRepository.findById(clienteId);
		Optional<Producto> producto = productoRepository.findById(productoId);



		if (!producto.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado");
		}
		if (!cliente.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado");
		}

		if (producto.get().getStock() <=0 ) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no disponible");
		}


		List<Locker> lockers = lockerRepository.findAll();
		if (lockers.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.LOCKED, "No hay lockers disponibles");
		}

		// Convertir los lockers a nodos para el algoritmo A*
		Set<Node> obstaculos = new HashSet<>();
		for (Locker locker : lockers) {
			obstaculos.add(new Node(locker.getDireccionX(), locker.getDireccionY()));
		}

		// Convertir la dirección del cliente a un nodo
		Node inicio = new Node(cliente.get().getDireccionX(), cliente.get().getDireccionY());

		// Encontrar el locker más óptimo para el cliente usando A*
		Locker lockerOptimo = encontrarLockerOptimo(inicio, lockers, obstaculos);

		// Crear el pedido y asignar el locker óptimo
		Pedido pedido = new Pedido();
		pedido.setCliente(cliente.get());
		pedido.setLocker(lockerOptimo);
		pedido.setProducto(producto.get());

		// Actualizar el stock del producto
		producto.get().setStock(producto.get().getStock() - 1);
		productoRepository.save(producto.get());

		// Guardar el pedido en la base de datos
		pedidoRepository.save(pedido);

		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	private Locker encontrarLockerOptimo(Node inicio, List<Locker> lockers, Set<Node> obstaculos) {
		// Crear el nodo objetivo (puede ser el centro del área de lockers, por ejemplo)
		Node objetivo = new Node(0, 0); // Se debe ajustar según tus necesidades

		// Ejecutar el algoritmo A*
		AStar astar = new AStar();
		List<Node> ruta = astar.encontrarRuta(inicio, objetivo, obstaculos);

		// Encontrar el locker más cercano en la ruta encontrada
		double distanciaMinima = Double.MAX_VALUE;
		Locker lockerOptimo = null;
		for (Locker locker : lockers) {
			Node nodoLocker = new Node(locker.getDireccionX(), locker.getDireccionY());
			double distancia = distanciaEntreNodos(ruta.get(ruta.size() - 1), nodoLocker);
			if (distancia < distanciaMinima) {
				distanciaMinima = distancia;
				lockerOptimo = locker;
			}
		}

		return lockerOptimo;
	}

	private double distanciaEntreNodos(Node nodo1, Node nodo2) {
		return Math.sqrt(Math.pow(nodo2.x - nodo1.x, 2) + Math.pow(nodo2.y - nodo1.y, 2));
	}

}
