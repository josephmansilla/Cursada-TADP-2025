require_relative "../lib/Document"

###################################
#
# Prueba de Punto 1 DSL:
# documento1 = Document.new do
#   alumno nombre: "Matias", legajo: "123456-7" do
#     telefono { "1234567890" }
#     estado es_regular: true do
#       finales_rendidos { 3 }
#       materias_aprobadas { 5 }
#     end
#     direccion calle: "Calle Falsa 123", piso:1, depto:"A", cp:1234 do
#       provincia {"CABA"}
#       pais {"Argentina"}
#     end
#   end
# end
# puts documento1.xml
#
###################################
#
# Prueba de Punto 2 serialize:
# class Alumno
#   attr_accessor :nombre, :legajo,:telefono, :estado
#
#   def initialize(nombre, legajo, telefono, estado)
#     @nombre = nombre
#     @legajo = legajo
#     @telefono = telefono
#     @estado = estado
#     @nuevo = Array.new
#   end
# end
#
# class Estado
#   attr_accessor :finales_rendidos, :materias_aprobadas, :es_regular
#   def initialize(finales, materias, es_regular)
#     @finales_rendidos = finales
#     @materias_aprobadas = materias
#     @es_regular = es_regular
#   end
# end
#
# unEstado = Estado.new(3, 5, true)
# unAlumno = Alumno.new("Matias","123456-8", "1234567890", unEstado)
#
# documento_manual = Document.new do
#   alumno nombre: unAlumno.nombre, legajo: unAlumno.legajo, telefono:"1234567890" do
#     estado finales_rendidos: unAlumno.estado.finales_rendidos,
#            materias_aprobadas: unAlumno.estado.materias_aprobadas,
#            es_regular: unAlumno.estado.es_regular
#   end
# end
#
# documento_automatico = Document.serialize(unAlumno)
#
# puts documento_manual.xml == documento_automatico.xml
#
###################################
#
# Prueba de Punto 3 Annotations:
✨Label✨("alumno2")
✨Extra✨(:dni) { @telefono - 1234567889 }
class Alumno
  ✨Inline✨ {|campo| campo.upcase }
  attr_reader :nombre

  attr_accessor :legajo , :telefono

  ✨Label✨("pepe")
  attr_accessor :estado

  def initialize(nombre, legajo, telefono, estado)
    @nombre = nombre
    @legajo = legajo
    @telefono = telefono
    @estado = estado
  end
end

✨Custom✨ do |estado|
  regular { estado.es_regular }
  pendientes { estado.materias_aprobadas - estado.finales_rendidos }
end
class Estado
  attr_accessor :finales_rendidos, :materias_aprobadas, :es_regular
  def initialize(finales, materias, es_regular)
    @finales_rendidos = finales
    @materias_aprobadas = materias
    @es_regular = es_regular
  end
end

otroEstado = Estado.new(3, 5, true)
otroAlumno = Alumno.new("Matias","123456-8a", 1234567890, otroEstado)

documento_annotations = Document.serialize(otroAlumno)
puts documento_annotations.xml
#
###################################