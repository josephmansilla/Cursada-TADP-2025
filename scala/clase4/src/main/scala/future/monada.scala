package future

import scala.concurrent._
import scala.concurrent.duration._
import ExecutionContext.Implicits.global

val futuro = for {
  x <- Future { 10 / 2 }
  y <- Future { x + 5 }
} yield y

val resultado = Await.result(futuro, 1.second)
//println(resultado) // 10
