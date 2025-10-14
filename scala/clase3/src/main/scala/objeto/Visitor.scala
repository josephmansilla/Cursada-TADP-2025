package objeto

class InstructionVisitor(micro: Micro) {
  
}

class ExecuteVisitor(micro: Micro) extends InstructionVisitor {}
class PrintVisitor(micro: Micro) extends InstructionVisitor {}
class SimplyVisitor() extends InstructionVisitor {
  
}
