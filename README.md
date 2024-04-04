# Parcial Segundo Tercio

En este parcial se va a diseñar, construir y despliegar un aplicación web para investigar los factores de números enteros y los números primos. El programa debe estar desplegado en tres máquinas virtuales de EC2 de AWS como se describe abajo. Las tecnologías usadas en la solución deben ser maven, git, github, sparkjava, html5, y js. No use liberías adicionales.

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

## Funcionamiento en local

Para probar el funcionamiento de manera local, debe configurar las variables de entorno en su IDE. Estas variables se encuentran en la clase ServiceProxy, y en este caso, todas deben establecerse como: http://localhost:4567.

En el caso de utilizar IntelliJ, diríjase a 'Run' en la parte superior y seleccione 'Edit configurations'. Aquí podrá establecer las variables de entorno.

Una vez hecho esto, debe dirigirse al navegador e ingresar la url http://localhost:4568/index, aquí econtrata dos formularios, uno para obtener los números primos en el rango del valor ingresado y el otro para obtener los factores del número ingresado:

![local](https://github.com/lgar000/parcialDosArep/blob/main/Imagenes/local.png)

## Arquitectura

Se tienen tres clases. En la clase MathService se encuentra la lógica implementada para calcular la lista de números primos o factores para el número ingresado en el formulario. Para calcular los números primos, tenemos el método primes, que recibe el valor que vamos a tomar como rango y para cada uno de estos números verificamos que solamente tengan dos divisores: el uno y el mismo. En cuanto al cálculo de los factores, tenemos el método factors que recibe el número. Aquí validamos que el módulo sea cero para considerarlo un factor. Esta verificación se hace desde el uno hasta la mitad del valor ingresado. Adicionalmente, en esta clase tenemos dos endpoints de tipo GET, uno para "primes" y otro para "factors". Ambos retornan las respuestas en formato JSON.

En ProxyService, se hace la llamada a HttpConnection para obtener la respuesta. Esto se logra mediante el enrutamiento a los servidores correspondientes. Las rutas se encuentran definidas en un arreglo. Con la finalidad de tener balanceo de cargas entre las dos instancias definidas para MathService, se implementó un método round-robin que distribuye las solicitudes entre las dos instancias.

Esta arquitectura nos permite tener alta disponibilidad y escalabilidad, dado que estamos desplegando en la nube los servicios de cálculo de primos y factores. Además, ServiceProxy implementa un mecanismo de balanceo de carga utilizando el algoritmo de round-robin.

![instancias](https://github.com/lgar000/parcialDosArep/blob/main/Imagenes/arquitecturaParcial.png)

## Despliegue en Aws

Para desplegar la aplicación en aws, se crearon tres instancias de ec2:

![instancias](https://github.com/lgar000/parcialDosArep/blob/main/Imagenes/instanciasAws.png)

Una vez que se establezca la conexión en las tres instancias se va instalar en cada una de estas maven, java y git. En una de estas instancias se va a ejecutar la clase ServiceProxy y en las dos restantes se va a ejecutar la clase MathService. 

![instanciaServiceProxy](https://github.com/lgar000/parcialDosArep/blob/main/Imagenes/maquina3ServiceProxy.png)

![instanciaUnoMathService](https://github.com/lgar000/parcialDosArep/blob/main/Imagenes/maquina1MathService.png)

![instanciaDosMathService](https://github.com/lgar000/parcialDosArep/blob/main/Imagenes/maquina2MathService.png)

Adicionalmente, se deben configurar las variables de entorno en la instancia EC2 de AWS donde vamos a ejecutar la clase ServiceProxy. Esto se puede hacer mediante el comando: export NOMBRE_DE_LA_VARIABLE=http://ipv4Correspondiente_a_las_instancias_que_en_la_que_se_ejecuta_MathService:puerto. Esto se debe hacer para cada variable que tenga:

![instanciaDosMathService](https://github.com/lgar000/parcialDosArep/blob/main/Imagenes/configurarVariablesEnAws.png)

Puede verificar que las variables han sido configuradas mediante el comando env:

![env](https://github.com/lgar000/parcialDosArep/blob/main/Imagenes/env.png)

Se debe ubicar en la carpeta principal y ejecutar el comando mvn clean install, y ejecutar las respectivas clases en las instancias. Una vez hecho esto, se puede dirigir al navegador con la dirección IPv4 pública correspondiente a la instancia maquina3 e ingresar a la URL que contiene el formulario, teniendo en cuenta que el puerto es el 4568.

![form](https://github.com/lgar000/parcialDosArep/blob/main/Imagenes/formularioAws.png)

Puede comprobar que hay dos instancias de ec2 en los que se ejecuta la clase MathService y cada una de estas tiene la funcionalidad de calcular los primos y los factores de un número:

Instancia 1 en funcionamiento para MathService:

![primesIuno](https://github.com/lgar000/parcialDosArep/blob/main/Imagenes/instanceMathServicePrimes.png)

![factorsIuno](https://github.com/lgar000/parcialDosArep/blob/main/Imagenes/instanceMathServiceFactors.png)

Instancia 2 en funcionamiento para MathService:

![primesIdos](https://github.com/lgar000/parcialDosArep/blob/main/Imagenes/instanceTwoMathServicePrimes.png)

![factorsIdos](https://github.com/lgar000/parcialDosArep/blob/main/Imagenes/instanceTwoMathServiceFactors.png)

En la siguiente imagen puede comprobar que esta funcionando el round robin, ya que las instancias se estan turnando para cada solicitud:

![factorsIdos](https://github.com/lgar000/parcialDosArep/blob/main/Imagenes/funcionamientoRoundRobin.png)

Para verificar el despliegue en aws hecho en clase, consulte el siguiente video:

https://youtu.be/Q49oYjOvpyw

Video actulizado, donde ya se encuentran configuradas las variables de entorno

https://youtu.be/bwGYEhbcpz0

## Construido Con

* [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) - Lenguaje de programación y desarrollo
* [Html](https://developer.mozilla.org/es/docs/Web/HTML) - Lenguaje de marcado para la elaboración de páginas web
* [JavaScript](https://developer.mozilla.org/es/docs/Web/CSS) -JavaScript es un lenguaje de programación interpretado
* [Maven](https://maven.apache.org/) - Gestión de dependencias
* [Intellij](https://www.jetbrains.com/es-es/idea/) - Entorno de desarrollo integrado para el desarrollo de programas informáticos
* [Git](https://rometools.github.io/rome/) - Sistema de control de versiones distribuido
* [AWS](https://aws.amazon.com/es/free/?gclid=CjwKCAjw7-SvBhB6EiwAwYdCAdFmvp0VJz5wsQZcg5anEFJtzJJ2dfpVsGlht9X5DSyRY3Cz7u0B-RoC6ewQAvD_BwE&trk=8fa18207-f2c2-4587-81a1-f2a3648571b3&sc_channel=ps&ef_id=CjwKCAjw7-SvBhB6EiwAwYdCAdFmvp0VJz5wsQZcg5anEFJtzJJ2dfpVsGlht9X5DSyRY3Cz7u0B-RoC6ewQAvD_BwE:G:s&s_kwcid=AL!4422!3!647999789202!e!!g!!amazon%20web%20services!19685287144!146461596856) - Es una colección de servicios de computación en la nube pública que en conjunto forman una plataforma de computación en la nube, ofrecidas a través de Internet por Amazon.com

## Autor

* **Laura García** - [lgar000](https://github.com/lgar000)
