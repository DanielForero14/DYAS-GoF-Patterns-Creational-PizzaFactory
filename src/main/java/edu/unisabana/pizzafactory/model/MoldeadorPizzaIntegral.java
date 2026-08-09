package edu.unisabana.pizzafactory.model;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MoldeadorPizzaIntegral implements MoldeadorPizza {

    @Override
    public void moldearPizzaPequena() {
        Logger.getLogger(MoldeadorPizzaIntegral.class.getName())
            .log(Level.INFO, "[O] Moldeando pizza pequena de masa integral.");
    }

    @Override
    public void moldearPizzaMediana() {
        Logger.getLogger(MoldeadorPizzaIntegral.class.getName())
            .log(Level.INFO, "[O] Moldeando pizza mediana de masa integral.");
    }
}