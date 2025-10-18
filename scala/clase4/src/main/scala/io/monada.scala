package io

import cats.effect.IO

val programaIO: IO[Unit] = for {
  _ <- IO(println("¿Cómo te llamás?"))
  nombre <- IO(scala.io.StdIn.readLine())
  _ <- IO(println(s"Hola, $nombre!"))
} yield ()

@main def runIO(): Unit =
  programaIO.unsafeRunSync() // Ejecuta el efecto
