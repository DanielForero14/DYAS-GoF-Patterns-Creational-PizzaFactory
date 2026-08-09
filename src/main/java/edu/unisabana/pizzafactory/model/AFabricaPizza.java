package edu.unisabana.pizzafactory.model;

public interface AFabricaPizza {
    AmasadorPizza crearAmasador();
    HorneadorPizza crearHorneador();
    MoldeadorPizza crearMoldeador();
}