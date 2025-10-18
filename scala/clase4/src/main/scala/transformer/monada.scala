package transformer

import cats.data.EitherT
import cats.effect.IO

type ErrorOr[A] = EitherT[IO, String, A]

def leerNumero: ErrorOr[Int] = EitherT(IO {
  Right(42)
})

def dividirPorDos(n: Int): ErrorOr[Int] =
  EitherT.fromEither(if (n == 0) Left("División por cero") else Right(n / 2))

val programa: ErrorOr[Int] = for {
  n <- leerNumero
  res <- dividirPorDos(n)
} yield res

@main def runEitherT(): Unit = {
  val resultadoIO: IO[Either[String, Int]] = programa.value
  println(resultadoIO.unsafeRunSync()) // Right(21)
}
