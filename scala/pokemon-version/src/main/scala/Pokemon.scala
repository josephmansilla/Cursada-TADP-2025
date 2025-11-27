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
  def cambiarEnergia(cantidad:Int):Pokemon = this.copy(energia = energia + cantidad)
  def cambiarVelocidad(cantidad: Int):Pokemon = this.copy(stats= this.stats.copy(velocidad = this.stats.velocidad + cantidad.min(100)))

  lazy val energiaMaxima: Int = stats.energiaMaxima
  lazy val nivel: Int = {
    @tailrec
    def nivelR(experienciaParaNivel: Int, nivel: Int): Int = {
      val experienciaParaProximoNivel = (2^nivel-1) * especie.resistenciaEvolutiva
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
    case Sano => actividad(this)
  }
}

case class Especie(tipoPrincipal: Tipo, tipoSecundario: Option[Tipo], multiplicador: Stats, resistenciaEvolutiva: Int, condicionEvolutiva: Option[List[Evolucion]]) {
  def esTipo(tipo: Tipo): Boolean = {
    esTipoPrincipal(tipo) || esTipoSecundario(tipo)
  }
  def esTipoPrincipal(tipo: Tipo): Boolean = tipoPrincipal == tipo
  def esTipoSecundario(tipo:Tipo): Boolean = tipoSecundario.contains(tipo)
  def esDebil(t:Tipo):Boolean = tipoPrincipal.esDebil(t) || tipoSecundario.exists(_.esDebil(t))
}


sealed trait Evolucion {}
