# Parcial Dos

En este parcial se va a diseñar, construir y despliegar un aplicación web para investigar los factores de números enteros y los números primos. El programa debe estar desplegado en tres máquinas virtuales de EC2 de AWS como se describe abajo. Las tecnologías usadas en la solución deben ser maven, git, github, sparkjava, html5, y js. No use liberías adicionales.

a factorización de números enteros es un problema complejo para el cual no se conocen algoritmos clásicos eficientes. La criptografía clásica está soportada en la dificultad para encontrar los factores primos de números grandes.

### Problema
Diseñe un prototipo de sistema de microservicios que tenga un servicio (En la figura se representa con el nombre Math Services) para computar las funciones numéricas. El servicio de las funciones numéricas debe estar desplegado en al menos dos instancias virtuales de EC2. Además, debe implementar un proxy de servicio que reciba las solicitudes de llamado desde los clientes y se las delega a las dos instancias del servicio numérico usando un algoritmo de round-robin. El proxy deberá estar desplegado en otra máquina EC2. Asegúrese de que se pueden configurar las direcciones y puertos de las instancias del servicio en el proxy usando variables de entorno del sistema operativo. Finalmente, construye un cliente Web mínimo con un formulario que reciba el valor y de manera asíncrona invocar el servicio en el PROXY. Puede hacer un formulario para cada una de las funciones. El cliente debe estar escrito en HTML y JS.

### Prerrequisitos

- Java
- Maven
- Git
- AWS

### Instalación

Para hacer uso del proyecto clone el repositorio usando el siguiente comando

```
git clone https://github.com/lgar000/parcialDosArep.git
```

Ubiquese en la carpeta en la cual clono el repositorio. A continuación
acceda a la carpeta principal del proyecto mediante el siguiente comando

```
cd parcialDosArep
```

Para compilar y empaquetar, ejecute

```
mvn clean install
```

Para ejecutar el proyecto abra su IDE y ejecute las clases HelloSpark1 y HelloSpark2 o ubiquese en la carpeta pricipal y ejecute los siguientes comandos, cada uno en una terminal diferente:

```
java -cp "target/classes;target/dependency/*" edu.escuelaing.arem.ASE.app.MathService
```

```
java -cp "target/classes;target/dependency/*" edu.escuelaing.arem.ASE.app.ServiceProxy
```

## funcionamiento en local

Para probar el funcionamiento en local se debe cambiar las direcciones de los serves que se encuentran en la clase ServiceProxy por localhost y debe dirigirse al navegador e ingresar la url http://localhost:4568/index, aquí econtrata dos formularios, uno para obtener los números primos en el rango del valor ingresado y el otro para obtener los factores del número ingresado:

![local](https://github.com/lgar000/parcialDosArep/blob/main/Imagenes/local.png)

## Arquitectura

Se tiene tres clases, MathService es la encargada de calcular la lista de primos o factores para el numero ingresado por el formulario. En ProxyService se hace la llamada a HttpConection para obtener la respuesta

## Despliegue en Aws

https://youtu.be/Q49oYjOvpyw
