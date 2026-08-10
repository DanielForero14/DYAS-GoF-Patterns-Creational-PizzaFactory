
package edu.unisabana.pizzafactory.model;

import java.util.logging.Level;
import java.util.logging.Logger;


public class HorneadorPizzaDelgada implements HorneadorPizza {

    public void hornear() {
        Logger.getLogger(AmasadorPizzaDelgada.class.getName())
            .log(Level.INFO, "[~~] Horneando la pizza delgada con masa convencional.");

        
    }
    
}
