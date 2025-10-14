# SELF en cualquier instancia podría ser cualquier objeto

x = 10
if x < 2
  z = "es mayor"
  # si no se cumple la condicion se puede no definir z
  # no termina explotando a pesar de no poner z
end

puts x

class C1
  self.attr_accessor :coso
  y = 15
  puts self
  def m1
    puts y
  end
end

C1.new.m1

class C2
  self.attr_accessor :coso
  @x = 15
  puts @x  # varibiable de instacia de clase -> devuelve 15

  def self.x # se define un metodo de clase -> para permitir el acceso
    @x
  end

  self.singleton_class.define_method(:otra_x) do
    @x
  end

  def m1
    puts @x.inspect ## variable de instacia -> NIL
    # el contexto de clase y metodo son diferentes
    #
  end

end

C2.new.m1


class C3
  self.attr_accessor :coso
  @@x = 15 # varible de clase donde las instancias tienen acceso
  puts @x  # varibiable de instacia de clase -> devuelve 15

  def self.x # se define un metodo de clase -> para permitir el acceso
    @x
  end

  self.singleton_class.define_method(:otra_x) do
    @x
  end

  def m1
    puts @@x.inspect
    # no necesita ayuda de las clases para acceder a la variable
  end

end

C3.new.m1

# variables locales
# variables de instancia
# self
# scope gates: class, def, module, if (pero no en ruby)
# si te aparece alguna de esas cosas, el contexto cambia y tengo
# que estar atento de como usar las variables

puts [1,2,3].select {|n| n > 1} ## me filtra los elementos mayores a 1
# la condicion para filtrar no es conocida, entonces lo tengo que hacer

# el bloque es codigo a futuro y necesita otro contexto...

# en ruby hay 3 tipos para diferir un cacho de codigo

puts([1,2,3].select {|n|
  puts "hola pancho"
  n > 1
}.inspect
)
# no define un objeto, es una construccion sintactica despues de
# un envio de mensaje. Donde tudo envio de mensaje de ruby recibe
# todo: ## objeto.mensaje(parametros).opcional{bloque} ##
# porque ruby hizo esto? -> no vamos a tener lamba nativas.. entonces esta mierda

puts m(1,2,3) do
  puts "dentro del bloque"
end

# existen los procedures: son las lambdas y son objetos

def m3(p1,p2,p3, &block)
  block.call
  p1 + p2 + p3
end

def m2(p1,p2,p3)
  yield
  puts block_given?
  p1 + p2 + p3
end

p1 = proc { |x| x + 1} # no checkea los parametros
l1 = lambda { |x| x + 1 } # es un proc que tiene algunas validaciones mas
# mas rigido de parametros

p1.call() #-> rellena con nil
p1.call(1)

l1.call() # -> retorna errorcito de esperar parametros (ArgumentError)
l1.call(1) # -> tudo bien :)
def mi_proc(&block)
  block
end

def m4(p1,p2,p3, &block)
  [1,2,3].select(&block) # con el & puedo pasar de mundo bloque a mundo proc
  p1 + p2 + p3
end

# los procs, lambdas, bloques se llevan el contexto de donde fueron instanciados

pepe = 10
p2 = proc { puts pepe }
p3 = proc { pepe = pepe + 1 }
p2 .call

p3.call
p3.call
p3.call
p2.call


def a(block)
  puts block.call
end
a(p3)
# nos llevamos el contexto en el proc y es posible usar el contexto de block
# en el scope de def a.

p4 = proc {
  self.m3
}

x = 10
define_method(:bla) do
  x = x + 1
end
# son las mismas X

# se define una clase pero con el contexto de main
C5 = Class.new {
  x
  define_method(:bla) do
    x = x + 1
  end
} # C mayuscula define una const
C5.new

nombre = "pepe"
# romperia por edad
edad = "55"
# ahora responde estas variables
# primero busca si hay una variable de forma local del main.
# no puedo overridear
p8 = proc do
  puts "#{nombre} -> #{edad}"
  # es lo mismo a:
  # puts "#{self.nombre} -> #{self.edad}"
  # self es el scope de main
  # PD: ES IGUAL SIN INSTANCIAR VARIABLES
  # SI LE PONGO LOS SELF ELIMINO LA AMBIGUEDAD SOBRE QUE SE DEBERIA LLAMAR
end

pepin.instance_exec(&p8)
pepon.instance_exec(&p8)

# pepin y pepon deberían tener nombre y edad para funcionar correctamente

# ruby tiene un bind de contexto mucho mas interesante

pepin.instance_eval(&p8)


db.transaction do
  alumnos.select { nota >= 8 && !libre }
         .collect { legajo + nota }
          # el receptor es alguien que entiende los mensajes enviados
          # el bloque se envia a un contexto totalmente diferente
          # estoy cambiando la semantica de ruby y puedo escribir consultas de sql
          # en estos bloques, al ser independientes del contexto
          # microlenaguaje dentro de ruby
          # el contexto dentro las llaves se modifican

end

# el select se encarga de ejecutar una consulta con nota y libre sobre alguien que lo entienda