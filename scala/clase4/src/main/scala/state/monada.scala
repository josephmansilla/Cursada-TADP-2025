package state

import cats.data.State

// Estado: un contador
val incrementar: State[Int, Int] = State { estado =>
  val nuevo = estado + 1
  (nuevo, nuevo)
}

val programa = for {
  a <- incrementar
  b <- incrementar
  c <- incrementar
} yield (a, b, c)

// Correr la monada con estado inicial 0
val (estadoFinal, resultado) = programa.run(0).value

//println(estadoFinal) // 3
//println(resultado)   // (1,2,3)
