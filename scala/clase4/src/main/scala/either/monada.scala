package either

// Sin librerías externas
def dividir(a: Int, b: Int): Either[String, Int] =
  if (b == 0) Left("Error: división por cero")
  else Right(a / b)

val resultado = for {
  x <- dividir(10, 2)
  y <- dividir(x, 5)
} yield y

// println(resultado) // Right(1)
