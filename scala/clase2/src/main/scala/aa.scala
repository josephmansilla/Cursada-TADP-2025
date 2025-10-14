import scala.collection.mutable
import scala.collection.mutable.Set



val corralito: Corral[Vaca] = new Corral(List(new Vaca, new Vaca, new Vaca))
// si yo cambio a Corral[Animal] no funcionaria el lechero.ordeniar
// si yo cambio a Corral[Vaca] no funcionaria el pastorear

//val establo = new Corral(Set(new Caballo, new Caballo, new Caballo))
val lechero = new Lechero
val pastor = new Pastor

pastor.pastorear(corralito.animales)

lechero.ordeniar(corralito)

var vacas: mutable.Set[Vaca] = mutable.Set[Vaca]()
var animal2: Animal = vaca
var aniamles: mutable.Set[Animal] = vacas//.asInstanceOf(Set[Vaca])

// el problema de animales es que puede contener animales,
// que significa esto? que se pueden mezclar subtipos
// hay algo que puedo hacer con un set de ciertos subtipados que no podría hacer
// con el set de otros subtipados

animales.add(new Vaca)
animales.add(new Caballo)

// Varianza