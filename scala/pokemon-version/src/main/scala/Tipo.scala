import scala.util.{Failure, Success, Try}

sealed trait Tipo {
  def unapply(pokemon: Pokemon): Option[(Boolean, Boolean)] = {
    val especie = pokemon.especie
    if especie.esTipo(this) then
      Some(especie.esTipoPrincipal(this),
        especie.esTipoSecundario(this))
    else None
  }
  def esDebil(tipo:Tipo): Boolean = false
}
case object Agua extends Tipo
case object Pelea extends Tipo
case object Fantasma extends Tipo
case object Fuego extends Tipo {
  override def esDebil(t:Tipo):Boolean = t == Agua
}

sealed trait Estado {}
case object Sano extends Estado
case class Dormido(turnos: Int) extends Estado
case object Paralizado extends Estado
case object KO extends Estado

type Actividad = Pokemon => Try[Pokemon]

case object Descansar extends Actividad {
  override def apply(p:Pokemon):Try[Pokemon] = {
    p.resestablecerEnergia()
    if (p.energia < p.energiaMaxima * 0.5) then Failure(CustomException("Quedó dormido", p.cambiaEstado(Dormido(3))))
    else Success(p)
  }
}
case class Nadar(minutos:Int) extends Actividad {
  override def apply(p:Pokemon):Try[Pokemon] = {
    if (p.esDebil(Agua)) Failure(CustomException("Quedó KO",p.cambiaEstado(KO)))
    val pokemon = p
      .ganaExperiencia(20 * minutos)
      .cambiarEnergia(-minutos)
    if p.especie.esTipo(Agua) then
      Success(p.cambiarVelocidad(minutos/60))
    else Success(pokemon)
  }
}
case class LevantarPesas(kg:Int) extends Actividad {
  override def apply(p:Pokemon):Try[Pokemon] = p match {
    case Fantasma(_,_) => Success(p)
    case _ if (p.estado == Paralizado) => Failure(CustomException("Quedó KO", p.cambiaEstado(KO))) // no me gusta pero bue
    case _ if (kg>10) => Failure(CustomException("Quedó Paralizado", p.cambiarEnergia(-10).cambiaEstado(Paralizado)))
    case _ =>
      var delta = 1
      if(p.especie.esTipoPrincipal(Pelea)) then Success(delta * 2)
      Success(p.ganaExperiencia(delta * kg))
  }
}
case class CustomException(message:String, p:Pokemon) extends Exception{}


type Rutina = List[Actividad]