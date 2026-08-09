package edu.unisabana.pizzafactory.model;

public class FabricaPizzaIntegral implements AFabricaPizza {

    @Override
    public AmasadorPizza crearAmasador() {
        return new AmasadorPizzaIntegral();
    }

    @Override
    public HorneadorPizza crearHorneador() {
        return new HorneadorPizzaIntegral();
    }

    @Override
    public MoldeadorPizza crearMoldeador() {
        return new MoldeadorPizzaIntegral();
    }
}