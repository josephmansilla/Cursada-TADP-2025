package microprocesador

import scala.util.Try

object Operations3 {

  type Program = List[Instruction]
  class ExecutionHaltException(val micro: Micro) extends RuntimeException

  // ---------------------------------------------------------------------------------------------------------------------------------
  // RUN: TRY MONAD
  // ---------------------------------------------------------------------------------------------------------------------------------

  def run(program: Program, initialMicro: Micro): Try[Micro] = {
    program.foldLeft(Try(initialMicro)) { (previousResult, instruction) =>
      instruction match {
        case Add =>
          previousResult.map(micro => micro.copy(a = micro.a + micro.b))

        case Mul =>
          previousResult.map(micro => micro.copy(a = micro.a * micro.b))

        case Swap =>
          previousResult.map(micro => micro.copy(a = micro.b, b = micro.a))

        case Load(address) =>
          previousResult.map(micro => micro.copy(a = micro.mem(address)))

        case Store(address) =>
          previousResult.map(micro => micro.copy(mem = micro.mem.updated(address, micro.a)))

        case If(subInstructions) =>
          previousResult.flatMap(micro =>
            if (micro.a == 0) run(subInstructions, micro)
            else previousResult
          )

        case Halt =>
          previousResult.flatMap(micro => Try(throw new ExecutionHaltException(micro)))
      }
    }
  }

}