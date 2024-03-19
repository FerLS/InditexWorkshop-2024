package com.inditex.AStar;

import java.util.*;

public class AStar {
    private static final int[][] DIRECCIONES = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public List<Node> encontrarRuta(Node inicio, Node objetivo, Set<Node> obstaculos) {
        PriorityQueue<Node> abiertos = new PriorityQueue<>(Comparator.comparingDouble(node -> node.f));
        Set<Node> cerrados = new HashSet<>();

        inicio.g = 0;
        inicio.h = distanciaHeuristica(inicio, objetivo);
        inicio.f = inicio.g + inicio.h;

        abiertos.add(inicio);

        while (!abiertos.isEmpty()) {
            Node actual = abiertos.poll();

            if (actual.x == objetivo.x && actual.y == objetivo.y) {
                return reconstruirRuta(actual);
            }

            cerrados.add(actual);

            for (int[] direccion : DIRECCIONES) {
                int nuevoX = actual.x + direccion[0];
                int nuevoY = actual.y + direccion[1];

                if (!esValido(nuevoX, nuevoY, obstaculos)) {
                    continue;
                }

                Node sucesor = new Node(nuevoX, nuevoY);
                double costoG = actual.g + 1; // Costo de movimiento de una celda a otra (asumiendo que todas las celdas tienen el mismo costo)

                if (cerrados.contains(sucesor) && costoG >= sucesor.g) {
                    continue;
                }

                if (!abiertos.contains(sucesor) || costoG < sucesor.g) {
                    sucesor.parent = actual;
                    sucesor.g = costoG;
                    sucesor.h = distanciaHeuristica(sucesor, objetivo);
                    sucesor.f = sucesor.g + sucesor.h;

                    if (!abiertos.contains(sucesor)) {
                        abiertos.add(sucesor);
                    }
                }
            }
        }

        return new ArrayList<>(); // No se encontró una ruta
    }

    private boolean esValido(int x, int y, Set<Node> obstaculos) {
        return x >= 0 && x < 10 && y >= 0 && y < 10 && !obstaculos.contains(new Node(x, y));
    }

    private double distanciaHeuristica(Node inicio, Node objetivo) {
        return Math.sqrt(Math.pow(objetivo.x - inicio.x, 2) + Math.pow(objetivo.y - inicio.y, 2));
    }

    private List<Node> reconstruirRuta(Node nodo) {
        List<Node> ruta = new ArrayList<>();
        while (nodo != null) {
            ruta.add(nodo);
            nodo = nodo.parent;
        }
        Collections.reverse(ruta);
        return ruta;
    }

    public static void main(String[] args) {
        AStar astar = new AStar();
        Node inicio = new Node(0, 0);
        Node objetivo = new Node(9, 9);
        Set<Node> obstaculos = new HashSet<>();
        obstaculos.add(new Node(2, 2));
        obstaculos.add(new Node(3, 2));
        obstaculos.add(new Node(4, 2));
        obstaculos.add(new Node(5, 2));

        List<Node> ruta = astar.encontrarRuta(inicio, objetivo, obstaculos);
        for (Node nodo : ruta) {
            System.out.println("(" + nodo.x + ", " + nodo.y + ")");
        }
    }
}
