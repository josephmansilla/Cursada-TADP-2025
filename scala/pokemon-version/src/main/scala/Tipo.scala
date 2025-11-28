import scala.util.{Failure, Success, Try}

sealed trait Tipo {
  def unapply(pokemon: Pokemon): Option[(Boolean, Boolean)] = {
    val especie = pokemon.especie
    if especie.esTipo(this) then
      Some(especie.esTipoPrincipal(this), especie.esTipoSecundario(this))
    else None
  }
  def esDebil(tipo: Tipo): Boolean = false
}
case object Agua extends Tipo
case object Pelea extends Tipo
case object Fantasma extends Tipo
case object Fuego extends Tipo {
  override def esDebil(t: Tipo): Boolean = t == Agua
}

sealed trait Estado {}
case object Sano extends Estado
case class Dormido(turnos: Int) extends Estado
case object Paralizado extends Estado
case object KO extends Estado

type Actividad = Pokemon => Try[Pokemon]

case object Descansar extends Actividad {
  override def apply(p: Pokemon): Try[Pokemon] = {
    val pokemonDescansado = p.resestablecerEnergia()
    if (p.energia < p.energiaMaxima * 0.5) then Failure(CustomException("Quedo dormido", pokemonDescansado.cambiaEstado(Dormido(3))))
    else Success(pokemonDescansado)
  }
}
case class Nadar(minutos: Int) extends Actividad {
  override def apply(p: Pokemon): Try[Pokemon] = {
    if (p.esDebil(Agua)) return Failure(CustomException("Quedo KO",p.cambiaEstado(KO)))
    val pokemon = p
      .ganaExperiencia(20 * minutos)
      .cambiarEnergia(-minutos)
    if p.especie.esTipo(Agua) then
      Success(p.cambiarVelocidad(minutos/60))
    else Success(pokemon)
  }
}
case class LevantarPesas(kg: Int) extends Actividad {
  override def apply(p: Pokemon): Try[Pokemon] = p match {
    case Fantasma(_, _) => Failure(CustomException("No puede levantar pesas", p))
    case _ if p.estado == Paralizado => Failure(CustomException("Quedo KO", p.cambiaEstado(KO)))
    case _ if kg > 10 => Failure(CustomException("Quedo Paralizado", p.cambiarEnergia(-10).cambiaEstado(Paralizado)))
    case _ =>
      val experienciaGanada = if p.especie.esTipoPrincipal(Pelea) then kg * 2 else kg
      Success(p.ganaExperiencia(experienciaGanada))
  }
}
case class CustomException(message: String, p: Pokemon) extends Exception {}

type Rutina = List[Actividad]

def realizarRutina(pokemon: Pokemon, rutina: Rutina): Try[Pokemon] = pokemon.hacerActividades(rutina)

sealed trait CriterioRutina {
  protected def rutinasEjecutables(rutinas: List[Rutina], pokemon: Pokemon): List[(Rutina, Pokemon)] =
    rutinas.flatMap(r => pokemon.hacerActividades(r).toOption.map(r -> _))

  def apply(rutinas: List[Rutina], pokemon: Pokemon): Option[Rutina]
}
case object MayorNivel extends CriterioRutina {
  override def apply(rutinas: List[Rutina], pokemon: Pokemon): Option[Rutina] = {
    rutinasEjecutables(rutinas, pokemon).maxByOption(_._2.nivel).map(_._1)
  }
}
case object MayorEnergia extends CriterioRutina {
  override def apply(rutinas: List[Rutina], pokemon: Pokemon): Option[Rutina] = {
    rutinasEjecutables(rutinas, pokemon).maxByOption(_._2.energia).map(_._1)
  }
}
case object MasExtensa extends CriterioRutina {
  override def apply(rutinas: List[Rutina], pokemon: Pokemon): Option[Rutina] = {
    val candidatas = rutinasEjecutables(rutinas, pokemon).filter { case (_, resultado) =>
      resultado.energia * 2 >= resultado.energiaMaxima
    }
    candidatas.maxByOption(_._1.length).map(_._1)
  }
}
