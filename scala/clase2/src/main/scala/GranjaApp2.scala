object GranjaApp2 extends App {
//  var f: Function[Vaca, Vaca] = null
  var f: Vaca => Vaca = null

  def g(vaca: Vaca): Vaca = ??? // cumple ambos
  def h(vaca: Vaca): Animal = new Vaca // cumple la interfaz de vaca pero la salida no 
  def i(vaca: Vaca): VacaLoca = ??? // cumple la interfaz de vaca y de salida también al poder entnder todo lo que entiende la vaquita
  // covarianza: A <: B -> T[A] <: T[B]
  def j(vacaLoca: VacaLoca): Vaca = ??? // cumple la interfaz de entrada de vaca, pero si la salida
  def k(animal: Animal): Vaca =  ??? // contravarianza A :> b -> T[A] <: T[B]
  
  
  f = ???
  
  f.apply(new Vaca).ordeniate()
}
