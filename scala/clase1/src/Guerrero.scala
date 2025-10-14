trait Defensor{
    def
    def recibeDanio(danio: Int){}
}

class Guerrero(var potencialOfensivo: Int = 20)
    extends Defensor {
    var energia: Int = 100
    var potencialDefensivo: Int = 10
    PotencialDefensivo = 0

    def atacaA(otro: Guerrero) = {
        if (otro.potencialDefensivo < this.potencialOfensivo) {
            otro.recibeDanio(this.potencialOfensivo - otro.potencialDefensivo)
        }
    }
    def recibeDanio(danio: Int) = {
        this.energia = (this.energia - danio).max(0)
    }
}

val zorro = new Espadachin(50)

var espadachin : Espadachin = zorro
var guerrero : Guerrero = zorro

connan.atacaA(zorro)

zorro.potencialOfensivoDeEspada

val muralla = new Murallañ
connan.atacaA(muralla)

class Misil(val anioFabricacion: Int) {
    def atacaA(otro: Any) = ???

    def potencialOfensivo: Int = {
        (2016 - anioFabricacion
    }

}

class Lista {

    var elementos = new Array()

    def tamaño: Int
    def concatenar(otra: Lista) : Lista
    def incluye(elemento: Object): Boolean
    def filtrar(condicion: Object => Boolean): Lista {
        val respuesta = new Array()
        for (elemento <- elementos){
            if(condicion(elemento)){
                respuesta.add(elemento)
            }
        }
        return respuesta
    }
}

val lista = new Lista()
lista.filrrar((guerrero) => guerrero.energia > 10)