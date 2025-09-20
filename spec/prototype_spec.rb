RSpec.describe "Prototype" do
  it "Puedo setear y getear una propiedad de un objecto prototipado" do
    guerrero = PrototypedObject.new
    guerrero.set_property(:energia, 100)
    expect(guerrero.get_property(:energia)).to eq 100
  end
  it "Propiedad es nula en un object prototipado" do
    guerrero = PrototypedObject.new
    guerrero.set_property(:energia, 100) # esta property vale 100
    expect(guerrero.get_property(:vida)).to be_nil
  end

  it "las propiedades de un prototypeobject son solo de ese prototyped object" do
    guerrero = PrototypedObject.new
    alquimista = PrototypedObject.new
    guerrero.set_property(:poder_ofensivo, 100)
    alquimista.set_property(:poder_ofensivo, 100)

    expect(alquimista.get_property(:poder_ofensivo)).to eq 100
    expect(guerrero.get_property(:poder_ofensivo)).to eq(100)
  end

  it "Puedo setear y getear una propiedad de un objecto prototipado 2" do
    guerrero = PrototypedObject.new
    guerrero.set_property(:energia, 100)
    expect(guerrero.energia).to eq 100
  end
  it "se pueden definir metodso con set_method" do
    guerrero = PrototypedObject.new
    guerrero.set_method(:recibe_danio, proc do
      |danio|
      nueva_energia = self.energia - danio
      set_property(:energia, nueva_energia)
    end)

    guerrero.set_property(:energia, 100)
    guerrero.recibe_danio(10)

    expect(guerrero.energia).to eq 90
  end
  it "multiples prototipos" do
    guerrero = PrototypedObject.new
    alquimista = PrototypedObject.new
    guerrero.set_method(:hola, proc {"Hola"})
    alquimista.set_method(:hola, proc {"se te apetece alguna de mis pociones"})

    coso = PrototypedObject.new
    coso.set_prototypes([guerrero, alquimista])
    expect(coso.hola).to eq "Hola"
  end
end