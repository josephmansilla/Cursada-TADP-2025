puts "Hello World"
# prints hello world

def hi
  puts "hello world"
end

def hi2(name)
  puts "hello #{name}"
end

hi2("pepe")
# Hello pepe


class Greeter
  def initialize(name = "world")
    @name = name
  end
  def say_hi
    puts "Hello #{@name}!"
  end
  def say_bye
    puts "Goodbye #{@name}!"
  end
end

pepe = Greeter.new("pepe")
pepe.say_hi
pepe.say_bye

# pepe.@name

Greeter.instance_methods

pepe.respond_to?("name")
# false
pepe.respond_to?("say_hi")
# true
pepe.respond_to?("to_s")
# true

# In ruby you can reopen a class to alter it
class Greeter2
  attr_accessor :name
  def initialize(name)
    @name = name
  end
end

greeter = Greeter2.new("Pedro")
greeter.respond_to?("name")
# true
greeter.respond_to?("name=")
# true
greeter.say_hi
# Hi Pedro
# nil
greeter.name="Betty"
# Betty
greeter
#<Greeter:0x3c9b0 @name="Betty">
greeter.name
# Betty
greeter.say_hi
# Hi Betty!

# Usar attr_accessor nos define dos metedos
# name para obtener el valor y
# name= para setearlo

class MegaGreeter
  attr_accessor :names
  def initialize(names = "World")
    @names = names
  end
  def say_hi
    if @names.nil?
      puts "..."
    end
    if @names.respond_to?("each")
    # @names is a list, iterate!
      @names.each do |name|
        puts "Hello #{name}!"
      end
    else
      puts "Hello #{@names}!"
    end
  end

  def say_bye
    if @names.nil?
      puts "..."
    end
    if @names.respond_to?("join")
      # Join the list with commas
      puts "Goodbye #{@names.join(", ")}. Come back soon!"
    end
    puts "Goodbye #{@names}!"
  end
end


if __FILE__ == $0
  mg = MegaGreeter.new
  mg.say_hi
  mg.say_bye

  # Change name to Zeke
  mg.names = "Zeke"
  mg.say_hi
  mg.say_bye

  # change the name to an array of names
  mg.names = ["Albert", "Brenda", "Charles", "Dave", "Engelbert"]
  mg.say_hi
  mg.say_bye

  #change to nil
  mg.names = nil
  mg.say_hi
  mg.say_bye
end