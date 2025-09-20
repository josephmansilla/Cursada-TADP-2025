class Proc
  def call_in(object, *args)
    object.instance_exec(*args, &self)
  end
end

class Array
  def include_all?(elements)
    elements.all? do |element|
      include?(element)
    end
  end
end

class PartialBlock
  attr_accessor :block, :types

  def initialize(types, &block)
    self.types = types
    self.block = block
  end

  def matches(*args)
    matches_types?(args.map(&:class))
  end

  def matches_types?(parameter_types)
    return false unless types.length == parameter_types.length
    types.zip(parameter_types).all? do |type, parameter_type|
      case type
        when Module; parameter_type <= type
        when Array; parameter_types.instance_methods.include_all?(type)
      end
    end
  end

  def call(*args)
    validate_call(*args)

    block.call(*args)
  end

  def call_with(receiver, *values)
    validate_call(*values)
    block.call_in(receiver, *values)
  end
  private
  def validate_call(*values)
    raise ArgumentError unless matches?(*values)
  end
end

class Module
  def partial_def(name, types, &block)
    multimethods[name].add_partial_block(PartialBlock.new(types, &block))

    multimethods = self.multimethods

    define_method(name) do |*args|
      multimethods[name].call(self, *args)
    end
  end
end

class Multimethod
  attr_reader :partial_blocks

  def initialize(partial_blocks = [])
    @partial_blocks = partial_blocks
  end
  def add_partial_block(block)
    partial_blocks << block
  end

  def call(receiver, *args)
    partial_block = partial_blocks.find do |partial_block|
      partial_block.matches?(*args)
    end
    validate_method(partial_block)
    partial_block.call_with(receiver, *args)
  end

  def matches?(types)
    partial_blocks.any? do |partial_block|
      partial_block.matches_types?(types)
    end
  end

  private
  def validate_method(partial_block)
    raise NoMethodError.new("No existe un multimethod para este método") if partial_block.nil?
  end
end



class Object
  def multimethods
    self.class.multimethods
  end

  def respond_to?(name, include_all = true, types = [])
    return false unless super(name, include_all)
    types == [] || respond_to_multimethod?(name, include_all,types)
  end

  def respond_to_multimethod?(name, include_all, types)
    multimethods.key?(name) && multimethods[name].matches?(types)
  end
end