// abstraer: trabajar con menos detalles para tener otra herramienta sin modificar el resultado obtenido
// el camino se basa en la forma, no en el tipo de dato, se mira la estructura
// la respuesta de una opción de bajo nivel es usar pattern matching

// la lista es aquella que toma una forma básica para pertenecer a un estructura
// con esa cierta estructura va a tener un dominio que nos permite tener un flujo
// un flujo de datos que nos dirige a ciertas operaciones deseadas

// si algo tiene un nil y un cons con cabeza y cola entonces es una lista
// Lista: ([]) Nil :: [a]
//        (:) Cons :: a -> [a] -> [a]

// si yo te doy un arbol:
//    cons
//   /    \
//  7      cons
//         /   \
//        8     nil
//
// sabemos que esto es una lista porque respeta el formato que le designamos que
// debería tener una lista en funcional


// case materia::otras if materia.electiva => materia::electivas(otras)
// el if en este caso no es un control de flujo, decide sobre el resultado que va a tener la funcion
//  pero no redirige ni cambia el flujo del matcheo: para eso está el Pattern matching y guardas
// ademas hay descontruccion al usar uno de los elementos de la lista al usar materia en el match
// se puede usar descontruccion para elegir el flujo: pattern matching por que hay flujo que
// matchea y se puede descontruir.


//en caso de cambiar la estructura de lista entonces se me rompería todo el código existente?
//na.. eso nunca va a pasar, por qué me cambiarían la estructura de algo?
//entropía ... las estructuras siempre van a estar cambiando, especialmente si el diseño inicial es malo
//(malo: no considera la posibilidad de cambio de estructura -> complicado)

//si me das a conocer una forma puedo armar cosas con esa estructura y ver si cumple
//si lo quiero cmabiar debo identificar y construir, con un mismo resultado.

// si uso la interfaz en vez de la forma
//es porque me desacoplo de una cierta forma y puedo implementar varias formas
// la logica de negocio está desacoplado a la forma de una lista
// me liberé de la forma, al encontrar una forma diferente de obtener
// pero a que costo? la capacidad de abstraccion depende totalmente de mí
// antes se encargaba el lenguaje...
// pierdo control de la estructura, no la conozco (pero no es lo quería hacer? Sí, pero ahora hay consecuencias)

// las abstracciones sirven para estar más cerca a la descripción que me dieron, a cambio de perder poder.
// requiere mas efuerzo porque es menos poderoso, pero es más abarcativo.
// menos poderoso -> de forma facil y rapida podes hacer menos cosas.
// no es necesariamente malo ser menos poderoso
// goto es mas poderoso que los for e if

//  si vos tenes poder, es tu responsabilidad de manejarla y te das cuenta
//  la verdad que no tenia tantas ganas de hacerlo, yo queria una hacer
//  una cierta abstraccion, pero me tengo que estar concentrando en como manejarlo
//  a bajo nivel, en vez de solamente resolver el problema inicial.

// abstraccion: tomo algo y lo esconodo detras de un nombre
// fold es la funcion la iteradora y es el catamorfismo de nuauna lista
// por una estructura con nodoss variables y corriebles
//
//el rol del fold es ser el catamofirsmo de un proceso de secuencias (catamofismo)KC

//en el proceso de abstraccion: toodo lo que ganamos lo perdimos y viceversa
// la manera de trabajar en funcional es general una nueva abstraccion
// para trabajar de la misma manera sobre un conjunto de datos.

// monada -> propipedades y caracteristicas disponibles para ser un contenedor
//unit :: T -> Monad[T]
//bind :: (T -> Mondad[R]) -> Monad[T] -> Monad[R]
//si se cumple : identidad izquierada e identidad derecha con asociatividad
//=> es una monada

// el flatmap es exactamente igual al flat map

// zero :: Monad[T] (caso base nil)
// plus :: Monad[T] -> Monad[T] -> Monad[T] (concat)
// ahora se puede trabajar con filter (monada plus)

// Option: optional de java, maybe de haskell
// puede tener algo con un solo T o una caja vacia
// puede estar o no estar, entonces me ahorro de estar consultando si está el contenido
// puedo trabajar sin pensar si hay contenido o no

// Try como el optinal pero con una excepción que hay que manejar con try catch
// lanzo excepcion y alguien debe manejarlo

 
