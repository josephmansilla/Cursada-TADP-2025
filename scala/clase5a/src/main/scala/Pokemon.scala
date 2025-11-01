package pokemon

/** Formas de resolver Actividades:
 *    1_ Metodo en Pokemon
 *    2_ Cada Actividad tiene una clase
 *    3_ Funciones sueltas
 *    4_ Pattern Matching sobre tipos de actividades
 * */


object GimnasioPokemon {

  case class Pokemon(experiencia: Int, energia: Int, energiaMaxima: Int, fuerza: Int, velocidad: Int, especie: Especie) {
    require(energia <= energiaMaxima && energia > 0)

    def ganarExperiencia(cantidad: Int): Pokemon = copy(experiencia = experiencia+cantidad)
    def perderEnergia(cantidad: Int): Pokemon = copy(energia = energia-cantidad)
    def ganarVelocidad(cantidad: Int): Pokemon = copy(velocidad = velocidad+cantidad)

    val nivel = ???

    def descansar(): Pokemon = this.copy(energia = this.energiaMaxima)

    def hacerActividad(actividad: Actividad_T): Pokemon = actividad(this)

    def recuperarEnergia(energiaARecuperar:Int) = copy(energia = energiaARecuperar)
  }

  type Actividad_T = Pokemon => Pokemon // type alias

  /*
  type HeroeConTrabajo = Heroe => Heroe

  class Guerrero extends HeroeConTrabajo {
    override def apply(h:Heroe):Heroe = h.copy(fuerza = h.fuerza + 1.2 * h.nivel)
  }

  val listaHeroe = List(Guerrero(unHeroe))
  */

  class Actividad() extends (Pokemon => Pokemon) {
    // def realizar(pokemon: Pokemon): Pokemon = ???
    // override def apply(p: Pokemon):Pokemon = p.copy(energia = p.energiaMaxima)
    override def apply(p: Pokemon):Pokemon = ???
  }

  case class Descansar() extends Actividad {
    override def apply(p: Pokemon):Pokemon = p.recuperarEnergia(p.energiaMaxima)
  }

  case class LevantarPesas(kilos: Int) extends Actividad {
    /*
    override def apply(p: Pokemon): Pokemon = p.especie match {
      case e if e.esTipo(Fantasma) => throw new Exception("No puede levantar pesas")
      case _ if kilos >= (p.fuerza*10) => p.perderEnergia(10)
      case e if e.esTipo(Peleador) => p.ganarExperiencia(kilos*2)
      case _ => p.ganarExperiencia(experienciaAGanar(p))
    }
    */

    override def apply(p: Pokemon): Pokemon = p match {
      case Fantasma(_,_) => throw new Exception("No puede levantar pesas")
      case _ if kilos >= (p.fuerza*10) => p.perderEnergia(10)
      case Peleador(_,_) => p.ganarExperiencia(kilos*2)
      case _ => p.ganarExperiencia(experienciaAGanar(p))
    }

    def experienciaAGanar(p:Pokemon): Int =
      if p.especie.esTipo(Peleador) then kilos*2 else kilos
  }

  case class Nadar(minutos: Int) extends Actividad {
    override def apply(p: Pokemon): Pokemon = {
      val pokemonEntrenado = p.perderEnergia(minutos).ganarExperiencia(200*minutos) // Sin composición

      // Con composición
      // val pokemonPierdeEnergia: (Pokemon=>Pokemon) = _.perderEnergia(minutos)
      // val pokemonGanaXP: (Pokemon=>Pokemon) = _.ganarExperiencia(200*minutos)
      // val entrenarPokemon = pokemonPierdeEnergia.compose(pokemonGanaXP)
      // val entrenarPokemon = pokemonGanaXP.andThen(pokemonPierdeEnergia)

      // val pokemonEntrenado = entrenarPokemon(p)

      if p.especie.esTipo(Agua) then
        pokemonEntrenado.ganarVelocidad(minutos / 60)
      else
        pokemonEntrenado
    }
  }

  // def descansar(pokemon: Pokemon): Pokemon = p => p.copy(energia = p.energiaMaxima)

  // val descansar: Actividad_T = p => p.copy(energia = p.energiaMaxima)
  val descansar: Actividad_T = p => p.recuperarEnergia(p.energiaMaxima)

  def realizarActividad(pokemon: Pokemon, actividad: Actividad ):
  Pokemon = actividad match {
    case Descansar() => pokemon.recuperarEnergia(pokemon.energiaMaxima)
    case _ => ???
  }

  case class Especie(tipoPrimario: Tipo, tipoSecundario: Option[Tipo]) {
    def esTipo(tipo: Tipo): Boolean = {
      esTipoPrimario(tipo) || esTipoSecundario(tipo)
    }
    def esTipoPrimario(tipo: Tipo): Boolean = {
      tipoPrimario == tipo
    }
    def esTipoSecundario(tipo: Tipo): Boolean = {
      tipoSecundario.exists(_ == tipo)
    }
  }

  sealed trait Tipo {
    def unapply(pokemon: Pokemon): Option[(Boolean, Boolean)] = {
      val especie = pokemon.especie
      if especie.esTipo(this) then
        Some(especie.esTipoPrimario(this), especie.esTipoSecundario(this))
      else
        None
    }
  }
  case object Roca extends Tipo
  case object Agua extends Tipo
  case object Peleador extends Tipo
  case object Fantasma extends Tipo
  case object Fuego extends Tipo
  case object Electrico extends Tipo

  /**
   * {case (a,b) => ....} no falla en la evaluacion con tipos que no coincidan
   * (x => .....)
   * */

  val l = List(Roca, Agua, Peleador)
  val l2 = l.collect({
    case Roca => Agua
    case Agua => Electrico
  })


  val pikachu = Pokemon(10, 50, 100,25,30, Especie(Electrico, null))

  val pikachuEntrenado = pikachu.hacerActividad(descansar)
  val pikachuEntrenado2 = pikachu.hacerActividad(realizarActividad(_, Descansar()))
  val pikachuEntrenado3 = pikachu.hacerActividad(_.descansar())
  //val pikachuEntrenado4 = pikachu.hacerActividad(Descansar().realizar) // trait Actividad
  val pikachuEntrenado4 = pikachu.hacerActividad(Descansar()) // class Actividad extends (Pokemon => Pokemon)
}
