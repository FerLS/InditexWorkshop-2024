package com.inditex.AStar;

import java.util.*;

public class Node {
    public int x, y;
    double g; // Costo del camino desde el inicio al nodo actual
    double h; // Costo heurístico desde el nodo actual al objetivo
    double f; // Costo total (g + h)
    Node parent;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.g = 0;
        this.h = 0;
        this.f = 0;
        this.parent = null;
    }
}

