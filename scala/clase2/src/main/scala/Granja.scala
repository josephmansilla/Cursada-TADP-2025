import scala.collection.mutable
import scala.collection.mutable.Set

class Corral[A <: Animal](val animales: List[A]) {
  def bla(a: A): String = {
    "bleblebleblablablablublublu"
  }
}

class Pastor {
  def pastorear(animales: mutable.Set[Animal]) = animales.foreach(_.come())
  
}

class Lechero {
  def ordeniar(corral: Corral[Vaca]) = corral.animales.foreach(_.ordeniate())
}