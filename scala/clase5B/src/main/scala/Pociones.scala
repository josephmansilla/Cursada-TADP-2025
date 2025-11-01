object Pociones {
  case class Niveles(suerte: Int, convencimiento: Int, fuerza: Int) {
    def conFuerza(f: Int) : Niveles = copy(fuerza = f)
    def aplicarATodos(f: Int => Int) = copy(f(suerte), f(convencimiento), f(fuerza))
    def invertir = copy(fuerza, convencimiento, suerte)
  }

  type Efecto = Niveles => Niveles

  val f1: Efecto = n => n.copy(suerte = n.suerte + 1, n.convencimiento + 2, n.fuerza + 3)
  val f2: Efecto = _.aplicarATodos(7.max)
  var f2alt: Efecto = niveles => niveles.aplicarATodos(7.max _) // 7.max(unNivel)
  val f3: Efecto = {
    case Niveles(s,c,f)
      if s >= 8 => Niveles(s,c,f+5)
    case n => n.copy(fuerza = n.fuerza - 3)
  }

  val if1: Efecto = n => n.copy(suerte = n.suerte -1, n.convencimiento - 2, n.fuerza - 3)
  val if3: Efecto = {
    case Niveles(s,c,f)
      if s >= 8 => Niveles(s,c,f - 5)
    case n => n.copy(fuerza = n.fuerza + 3)
  }
  case class Ingerediente(nombre: String, cantidadEnGramos: Int, efectos: List[Efecto])

  case class Pocion(nombre: String, ingredientes: List[Ingerediente]) extends (Persona => Persona) {
    def esHeavy: Boolean = efectosDePocion(this).length >= 4

    def efectos: List[Efecto] = efectosDePocion(this)

    // al agregar el extend ahora debemos
    def apply(persona: Persona):Persona = tomarPocion(this, persona)
  }

  case class Persona(nombre: String, niveles: Niveles) {
    def operaNiveles[T](operacion: Niveles => T): T= operacion(niveles)
    def aplicaEfecto(efecto: Efecto): Persona = copy(niveles = efecto(niveles))
  }

  def sumarNiveles(niveles: Niveles): Int = niveles.suerte + niveles.convencimiento + niveles.fuerza

  val sumaNiveles: Niveles => Int = {
    case Niveles(s,c,f) => s + c + f
  }

  val diferenciaNiveles: Niveles => Int = {
    case Niveles(s,c,f) => s.max(c).max(f) - s.min(c).min(f)
  }

  val sumaNivelesPersona: Persona => Int = _.operaNiveles(sumaNiveles)
  val diferenciaNivelesPersona: Persona => Int = _.operaNiveles(diferenciaNiveles)

  val concatenacionNiveles = (_:Persona).operaNiveles(n => s"${n.suerte}${n.convencimiento}${n.fuerza}")

  val efectosDePocion: Pocion => List[Efecto] = _.ingredientes.flatMap(_.efectos)

  object PocionHeavy {
    //def unapply(pocion: Pocion): Option[String] = if(pocion.esHeavy) Some(pocion.nombre) else None
    def unapply(pocion: Pocion): Option[String] = Option(pocion).filter(_.esHeavy).map(_.nombre)
  }
  val pocionesHeavies: List[Pocion] => List[String] = _.collect {
    case PocionHeavy(nombre) => nombre
    // case pocion if pocion.esHeavy => pocion.nombre
  } // _.filter(_.esHeavy).map(_.nombre)

  val tomarPocion: (Pocion, Persona) => Persona = efectosDePocion(_).foldLeft(_)(_.aplicaEfecto(_))
  // QUE HERMOSO QUE ES FUNCIONAL NO SE ENTIENDE NADA PERO ES HERMOSOOOOOOOOOOOOO
  val esAntidoto: (Pocion, Pocion, Persona) => Boolean =
    (po1,po2,persona) => tomarPocion(po2, tomarPocion(po1, persona)) == persona

  val esAntidoto2: (Pocion, Pocion, Persona) => Boolean = {
    (po1,po2,persona) => po1.andThen(po2)(persona) == persona

    val personaMasAfectada: (Pocion, Niveles => Int, List[Persona]) => Persona =
      (po, cirterio, personas) => personas.maxBy(po.andThen(_.niveles).andThen(cirterio))
  }
}
