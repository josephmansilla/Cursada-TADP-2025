val hola = "aaa"

/* triangulo de objetos...
*
*               polimorfismo
*               /           \
*              /             \
*             /               \
*            /                 \
*   encapsulamiento ------ delegación
*
*
* */


// Para entender funcional hay que entender objetos

// Delegación: siempre hay un responsable de una cierta tarea
// un objeto tiene una responsabilidad que expone una interfaz determinada donde
// los objetos son autonomos, autodescubribles, y autoconsistente

// siempre al tener un objeto yo puedo hacer las cosas que me ofrece hacer o explote si no entiendo algo
// En cambio, si tengo una estructura, no tengo idea que puedo hacer con la misma (mas alla de acceder a las variables)

// para una estructura es debo conocer muchos aspectos que no están explícitos...
// el objeto me debe asegurar responsabilidad de mantener el estado del objeto consistente
// los datos están naturalmente acoplados al objeto -> cohesión y acoplamiento tiene sentido solo en objetos

// cohesión: que haga algo que tenga que hacer y no haga más que lo que tenga que hacer
// (delegar responsabilidades de los objetos)

// no delego correctamente los objetos voy a tener un objeto que se comporta como una estructura y pierdo
// todas las ventajas que tenía un objeto y voy a adquirir todas las desventajas de usar estructuras.

// objetos te obliga a elegir un (1) responsable para delegar un comportamiento
// misil y tanque -> quien debe tener la responsabilidad de calcular el daño? no hay rta correcta
// preguntar que sos en objetos es malo! pero para conocer si sos A o B cuando yo soy X o Y se complica
// delegar y encapsular correctamente : termina siendo un antipatron -> double/multiple dispatch
// pero es la herramienta que me da objetos para resolver este cierto problema.
// Entonces es un antipatrón? -> definitivamente es un smell, ya que, se intenta de evitar a toda costa
// es trampa al triangulo de objetos


// encapsulamiento: grado de ocultación de algo... pero cuales son las ventajas
// cuando realmente no conozco algo o me estoy haciendo el boludo...
// alumno.nota = 7 hace lo que quiere que haga
// alumna.setNota(7) hace lo que el objeto quiere que haga para mantener la consistencia del objeto
// principio de acceso uniforme: no deberías tener dos sintaxis diferentes para una noción que puede ser igual
// mantener la consistencia de atributos y metodos me permite hacer menos cagada en tudo el objeto
// si yo quiero cambiar cosas de tudo sin romper la totalidad del programa

// alumno.curso.docente.area.director.telefono.llamar()
// dont talk to strangers: no me mandes mensajes a alguien que está tan lejos
// hay un acoplamiento fuerte entre alumno y telefono, pero rompiendo encapsulamiento
// la rotura de encapsulamiento no se limita al conocer los atributos de otros objetos
// minimizar la capacidad de rotura del objeto.
// la encapsulación es para guiar el uso del objeto y que sea como es.
// Conocer un objeto es conocer su interfaz
// encapsular es el control de acoplamiento de que problemas decompartimentalización
// los objetos siempre deben usar la interfaz de un objeto y si no lo tenes disponible no se puede hacer por X.
// todo pattern matching es antagonico al encapsulamiento
// en funcional para poder comunicarse se debe romper el encapsulamiento
// voy a hacer algo a pesar de lo que me diga el objeto


// polimorfismo: concepto troncal
// permite extensibilidad sin cambiar nada o pocas cosas
// donde tengo que tocar si se me agrega un requerimiento...

// entropia: si un programa se puede adaptar a muchos escenarios entonces es muy complejo
// si un programa no se adapta a muchos casos, cualquier nuevo requerimiento me rompe todo


// patron visitor
// tener una interfaz de T que ahora es un data object. Son dificiles de entender, cambiar y mantener.
// usar: una estructura de objetos contiene muchos objetos con diferentes interfaz y queres 
// realizar operaciones con distintas formas 
// queres evitar contaminar operaciones a varias clases de tipo T y podes dejar su logica en el Visitor
// agregar una nueva clase que entienden los visitors puede ser muy costoso
// si las operaciones cambian