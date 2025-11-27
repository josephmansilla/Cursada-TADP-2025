import scala.annotation.tailrec
import scala.math.Ordering.Implicits.infixOrderingOps
import scala.util.{Failure, Success, Try}
// https://docs.google.com/document/d/1RFpGHNXhrlA1_ADDvQ2CTA7oTQYSGK0OMzGLeNHdsVs/edit?usp=sharing

case class Stats (fuerza:Int, velocidad: Int, energiaMaxima: Int) {
  assert(fuerza > 0 && fuerza <= 100)
  assert(velocidad > 0 && velocidad <= 100)

  def +(otroStats: Stats): Stats = {
    copy(fuerza = otroStats.fuerza + fuerza,
      velocidad = otroStats.velocidad + velocidad,
      energiaMaxima = otroStats.energiaMaxima + energiaMaxima)
  }

  def *(otroStats: Stats): Stats = {
    copy(fuerza = otroStats.fuerza * fuerza,
      velocidad = otroStats.velocidad * velocidad,
      energiaMaxima = otroStats.energiaMaxima * energiaMaxima)
  }

}

case class Pokemon (experiencia: Int, stats: Stats, especie: Especie, energia: Int, estado: Estado) {
  def resestablecerEnergia(): Pokemon = this.copy(energia=stats.energiaMaxima)
  def ganaExperiencia(cantidad:Int):Pokemon = this.copy(experiencia = experiencia + cantidad)
  def cambiarEnergia(cantidad:Int):Pokemon = this.copy(energia = (energia + cantidad).max(0).min(100))
  def cambiarVelocidad(cantidad: Int):Pokemon = this.copy(stats= this.stats.copy(velocidad = this.stats.velocidad + cantidad.min(100)))

  lazy val energiaMaxima: Int = stats.energiaMaxima
  lazy val nivel: Int = {
    @tailrec
    def nivelR(experienciaParaNivel: Int, nivel: Int): Int = {
      val experienciaParaProximoNivel = math.pow(2, nivel - 1).toInt * especie.resistenciaEvolutiva
      if (experienciaParaProximoNivel > experiencia) then nivel
      else nivelR(experienciaParaNivel, nivel + 1)
    }
    nivelR(0,1)
  }
  def aumentarStats: Pokemon = {
    this.copy(stats=this.stats+especie.multiplicador)
  }
  def cambiaEstado(estadoNuevo:Estado):Pokemon = this.copy(estado=estadoNuevo)
  def esDebil(tipo:Tipo): Boolean = especie.esDebil(tipo)
  def hacerActividad(actividad: Actividad): Try[Pokemon] = estado match {
    case KO => throw new Exception("Pokemon está KO")
    case d:Dormido =>
      if (d.turnos > 1) then Failure(CustomException("Sigue dormido", this.cambiaEstado(Dormido(d.turnos - 1))))
      else Success(this.cambiaEstado(Sano))
    case Paralizado => Success(this.cambiarEnergia(-10).cambiaEstado(Sano))
    case Sano => actividad(this).map(_.intentarEvolucionar(this))
  }
  def cambiarEvolucion(evolucion: Especie): Pokemon = copy(especie=evolucion)
  def intentarEvolucionar(pokemon: Pokemon): Pokemon = especie.condicionEvolutiva.getOrElse(Nil).foldLeft(pokemon)((p, ev) => ev(p).getOrElse(p))
}

case class Especie(tipoPrincipal: Tipo, tipoSecundario: Option[Tipo], multiplicador: Stats, resistenciaEvolutiva: Int,
                   condicionEvolutiva: Option[List[Evolucion]]) {
  def esTipo(tipo: Tipo): Boolean = esTipoPrincipal(tipo) || esTipoSecundario(tipo)
  def esTipoPrincipal(tipo: Tipo): Boolean = tipoPrincipal == tipo
  def esTipoSecundario(tipo:Tipo): Boolean = tipoSecundario.contains(tipo)
  def esDebil(t:Tipo):Boolean = tipoPrincipal.esDebil(t) || tipoSecundario.exists(_.esDebil(t))
}

type CondicionEvolutiva = Pokemon => Boolean
case class Evolucion(especie:Especie, condicion: CondicionEvolutiva) {
  def apply(pokemon:Pokemon): Option[Pokemon] = if(condicion(pokemon)) Some (pokemon.cambiarEvolucion(especie)) else None
}
