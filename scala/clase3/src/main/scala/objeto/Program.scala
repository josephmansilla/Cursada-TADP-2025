package objeto


// no hay nada de encapsulamiento en el visitor y sus objetos que lo acceden
class Program(private var instructions: collection.Seq[Instruction]) {
  def accept(visitor: Instruction): Unit = {

  }
}


trait Instruction {
  def accept(visitor: InstructionVisitor): Unit
}
object Add extends Instruction {
  override def accept(visitor: Any): Unit = visitor.visitAdd(this)
}
object Mul extends Instruction {
  override def accept(visitor: Any): Unit = visitor.visitMul(this)
}

object Swap extends Instruction {
  override def accept(visitor: Any): Unit = visitor.visitSwap(this)
}

class Store(val address: Int) extends  Instruction {
  def accept(visitor: InstructionVisitor) = visitor.visitorStore(this)
}

class If(subInstructions: Program) extends Instruction {
  def accept(visitor: InstructionVisitor): Unit = {
    visitor.visitIf(this)
    subInstructions.accept(visitor)
    visitor.endVisitIf(this)
  }
}