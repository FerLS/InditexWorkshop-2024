>[!NOTE]
>No pude llegar a acabar el reto 😅, por ahora se queda en espera, estan hechas las fases 1 y 2


## Introducción

INDITEX amplía su equipo, y por ello se ha creado un reto para encontrar el talento que necesitan incorporar a sus filas.
En este caso, el reto será de **Backend** con Java 21. Para poder desarrollar todo lo propuesto, que se detallará mas adelante, se proporcionará una máquina virtual en formato OVA que tendréis que importar en vuestro software de virtualización: VirtualBox 7 o VMWare Player 16/17.
Dentro de esta máquina virtual os encontraréis con los recursos necesarios:
- IDEs: Eclipse 2024-03, Jetbrain Toolbox & IntelliJ IDEA CM 2023.3.5 y Apache NetBeans 21.0
- Servicios: MySQL 8.0.36
- Tools: Podman 4.6.1, Git, Java 21.0.2
#### Credenciales
- User Password: hackathon032024
- MySQL user: root
- MySQL password: Hackathon032024!

## Challenge

Para este evento se proponen tres fases:
1. La primera fase corresponde a un CRUD básico.
2. La segunda fase corresponde a un reto algorítmico.
3. La tercera fase corresponde a despliegue y documentación.

### Primera fase - API

Aquí se pide crear una API con funcionalidades básicas para poder implementar la lógica base de la aplicación. Para simplificar su desarrollo, únicamente se pedirá crear dos endpoints:

1. [POST] Endpoint que permite crear:
	1. **Producto**: id, nombre, stock. 
	2. **Cliente**: id, nombre, dirección(coordenadas cartesianas). 
	3. **Locker**: id, dirección(coordenadas cartesianas).
	4. **Obstáculo**: dirección. 
2. [GET] Endpoint que permite obtener los datos de:
	1. Productos.
	2. Clientes.
	3. Lockers.
	4. Obstáculos. 

**Los obstáculos sólo deben crearse en caso de resolver el hard path propuesto más adelante.**
### Segunda fase - Algoritmos

En esta fase se pide crear un algoritmo eficiente que resuelva el siguiente problema:
- Inditex ha creado un sistema de Lockers repartidos por varios puntos. Para optimizar las rutas de reparto, se han restringido a un único viaje por conductor, por lo que este debe dejar todos los pedidos realizados en un único Locker que sea el más óptimo para **todos** los clientes. La idea es minimizar la distancia total que deben recorrer los clientes para recoger sus pedidos.

Ejemplo:

Tenemos tres clientes: A, B, C y cinco Lockers: 1, 2, 3, 4 y 5. Supongamos que A hace un pedido y su Locker más cercano es el 1, este es asignado. Sin embargo, el cliente B hace un nuevo pedido, por lo que el Locker debería cambiar al que sea más óptimo para A y B.

**INFORMACIÓN A TENER EN CUENTA: todos los puntos(direcciones) estarán basados en un plano de coordenadas cartesianas.**

Para resolver este problema, hay que crear un nuevo endpoint capaz de crear nuevos pedidos que tenga el siguiente comportamiento:
- Recibe datos: id del cliente e id del producto como parámetros de query.
- Actualiza el stock del producto.
- Toma la dirección del cliente o los clientes y asigna de forma automática el Locker único más cercano a todos.
- Asigna el Locker más óptimo al nuevo pedido y a todos los anteriores.
- En caso de que no haya stock del producto elegido, no se debe crear el pedido y debe lanzar un error **412** (precondition failed).
- En caso de que no haya ningún Locker, no se debe crear el pedido y debe lanzar un error **423** (Locked).

**SOLVE IT THE HARD WAY**: a modo de dificultad añadida, se plantea el mismo escenario pero
añadiendo obstáculos en el plano. Se proporcionarán puntos por los que el camino no puede
pasar, por lo que ya no se podrán hacer caminos directos, será necesario tomar otra ruta y
estas deben ser planificadas alrededor de estos obstáculos. Por lo que el objetivo final será:
desarrollar un algoritmo capaz de encontrar el Locker más óptimo para todos los clientes de la misma forma que en la versión simplificada, pero teniendo en cuenta los obstáculos.
De esta forma se le da un toque más realista a la hora de crear una solución para este
problema.

**Resolver el reto mediante esta vía añadirá puntuación extra, pero debe ser resuelto por
completo y ser funcional para recibir este extra**.

#### Información adicional

Tanto el camino fácil como el difícil deben tener en cuenta que ha de ser escalable, es decir, debe ser eficiente tanto para unos pocos puntos, como para millones de ellos.
Sea cual sea el camino elegido y el algoritmo implementado, habrá que añadir en la documentación una respuesta a la siguiente pregunta:

- **¿Como montarías un algoritmo más óptimo computacionalmente que de soporte a millones de clientes y pedidos?**

En caso de no tener tiempo de implementarlo, podéis desarrollar una solución teórica de todo aquello que se os ocurra para dar una solución a este problema y poder demostrar vuestro ingenio.

### Tercera fase - Despliegue y documentación

Si da tiempo, se deberá desplegar usando **Podman** a modo de microservicio. En la solución que se entregue debe aparecer un **Containerfile** o **Dockerfile** completamente funcional listo para usar y testear el proyecto.

En la documentación se debe incluir lo siguiente:
- **Usage** del proyecto
- **Pasos de instalación** del proyecto para su correcto uso. Tanto el caso en el que se use Podman como si no se usa por falta de tiempo.
- **Lógica del algoritmo** empleado:
	- Nombre del algoritmo
	- Motivo del uso de dicho algoritmo
	- Cómo ayuda a mejorar la optimización de recursos
	- Cómo ayuda a la hora de escalar la aplicación

**NOTA IMPORTANTE:** el uso de podman es un añadido extra pero no prioritario. Hay que priorizar siempre las fases 1 y 2, así como crear una buena documentación y responder a la pregunta propuesta en la fase 2. Implementar únicamente en el caso de tener los conocimientos suficientes y una vez se hayan completado las demás tareas.

## Recursos proporcionados

Para completar el challenge, se proporcionan los siguientes recursos:

- [Github repository](https://github.com/nuwe-io/inditex-nuwe) -> repositorio con todo lo necesario para poder empezar el reto. Contiene un skeleton y más recursos adicionales:
	- **inditex-locker.sql**: archivo SQL que sirve como base para desarrollar la prueba. Bastará con importar el archivo a MySQL.
	- **Unit Test**: se proporciona una serie de test unitarios para que los participantes puedan ir comprobando su progreso.
	- **Skeleton**: estructura básica que sirve como base para no partir desde cero. 
- [OVA RHEL 9](https://drive.google.com/file/d/15S9aU5PQUOMZbysNNUy--COFABd-yVFi/view?usp=drive_link) -> Máquina virtual preconfigurada con todo lo necesario para desarrollar el reto.
  **En caso de problemas de rendimiento, aumentar la RAM a 4GB**.
  Si no es posible aumentar a 4GB la RAM o si el problema persiste, se permitirá usar el entorno habitual de trabajo.
- **Podman Cheatsheet**: se proporciona una cheatsheet con la funcionalidad y comandos básicos de Podman by Red Hat. Se encuentra dentro del mismo repositorio.
- **Containerfile**: archivo de ejemplo que se puede tomar como base para compilar el proyecto en un contenedor usando Podman.
- **Dockerfile**: archivo de ejemplo que se puede tomar como base para crear una BBDD usando Podman. Podman también funciona con Dockerfiles.

### Tree del proyecto

```bash
inditex-nuwe/
├── inditex-locker.sql
├── LICENSE
├── mvnw
├── mvnw.cmd
├── pom.xml
├── README.md
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── inditex
    │   │           ├── controllers
    │   │           │   ├── ClienteController.java (1)
    │   │           │   ├── LockerController.java (1)
    │   │           │   ├── ObstaculoController.java (2)
    │   │           │   ├── PedidoController.java (2) 
    │   │           │   └── ProductoController.java (1)
    │   │           ├── entities
    │   │           │   ├── Cliente.java
    │   │           │   ├── Locker.java
    │   │           │   ├── ObstaculoId.java
    │   │           │   ├── Obstaculo.java
    │   │           │   ├── Pedido.java (2)
    │   │           │   └── Producto.java
    │   │           ├── InditexApplication.java
    │   │           ├── JacksonConfiguration.java
    │   │           ├── repositories
    │   │           │   ├── ClienteRepository.java
    │   │           │   ├── LockerRepository.java
    │   │           │   ├── ObstaculoRepository.java
    │   │           │   ├── PedidoRepository.java
    │   │           │   └── ProductoRepository.java
    │   │           └── ServletInitializer.java
    │   └── resources
    │       ├── application-integrationtest.properties
    │       └── application.properties
    └── test
        ├── java
        │   └── com
        │       └── inditex
        │           ├── ClienteControllerUnitTest.java
        │           ├── ClienteJpaUnitTest.java
        │           ├── EntityUnitTest.java
        │           ├── LockerControllerUnitTest.java
        │           ├── LockerJpaUnitTest.java
        │           ├── ObstaculoControllerUnitTest.java
        │           ├── ObstaculoJpaUnitTest.java
        │           ├── PedidoControllerUnitTest.java
        │           ├── PedidoJpaUnitTest.java
        │           ├── ProductoControllerUnitTest.java
        │           └── ProductoJpaUnitTest.java
        └── resources
            └── application.properties
```

(1): clases a completar en la fase 1.

(2): clases a completar en la fase 2.

- Se pueden crear tantas clases de utilidad como sean necesarias, pero solamente se valorarán aquellos ficheros que están marcados.  
- En caso de optar por el hard path, puede añadirse todo aquello que sea necesario y se valorará. Siempre y cuando esté completamente terminado.
- El código proporcionado es completamente funcional y no hay que modificar nada. Tan sólo añadir todo aquello que falta para completar los objetivos propuestos.
- Los archivos Containerfile y Dockerfile pueden ser modificados según las necesidades de cada uno.
"# InditexWorkshop-2024" 
