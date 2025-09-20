# Intentamos de mantener una convención determinada para no
# enfrentarse a posibles problemas que no conocemos. En la convención dada
# ya sabemos el mecanismo de manejo y es más fácil de usar para el usuario.

class PrototypedObject
  def set_property(property_name, property_value)
    instance_variable_set("@#{property_name}", property_value)
    set_method(property_name, proc do
      get_property(property_name)
      end
    )
    # es importante definir el singleton para que otros no puedan entender el metodo

    # Alternativa
    # singleton_class.attr_accessor property_name
    # send("#{property_name}=, property_value)
  end
  def get_property(property_name)
    instance_variable_get("@#{property_name}")

    # Alternativa
    # send(property_name)
  end

  def set_method(method_name, proc)
    @__methods__ ||= {}
    @__methods__[method_name] = proc


    # alternativa antes del __method__
    # define_singleton_method(method_name, &proc)

    # alternativa para javascript
    # yo = self
    # define_singleton_method(method_name) do
    # |*args|
    # yo.instance_exec(*args, &proc)
  end


  private
  def method_missing(symbol, *args)
    method_proc = lookup_method(symbol)
    return super unless method_proc

    instance_exec(*args, &method_proc)

  end

  def set_prototypes(prototype)
    @__prototypes__ = prototype

  end

  private
  def lookup_method(symbol)
    prototypes = (@__prototypes__ || [])
    prototypes.reduce(@__methods__[symbol]) do |a_proc, prototype|
      a_proc || prototype.lookup_method(symbol)
    end
  end
end

# flattening vs linearizacion
# flat: dame lo que tenes y avisame si lo cambias. Lookup mucha mas performante
# tendrá picos de ejecución lenta.
# linearización: ...
#
# Para diferenciar los metodos que creo una clase se usan con listas en ruby
# en js podrías usar un atributo de metodo que te indique si es tuyo o no
# refe

# principio de acceso uniforme: si ambos tienen el msimo nivel de abstraccion
# deberían poder usar

# Problemas de usar el MethodMissing:
# dejar limitaciones bajas
# tener un contrato chico
# declare de forma clara