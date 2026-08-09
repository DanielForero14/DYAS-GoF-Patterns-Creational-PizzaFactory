package edu.unisabana.pizzafactory.consoleview;

import edu.unisabana.pizzafactory.model.AFabricaPizza;
import edu.unisabana.pizzafactory.model.AmasadorPizza;
import edu.unisabana.pizzafactory.model.ExcepcionParametrosInvalidos;
import edu.unisabana.pizzafactory.model.FabricaPizzaGruesa;
import edu.unisabana.pizzafactory.model.HorneadorPizza;
import edu.unisabana.pizzafactory.model.Ingrediente;
import edu.unisabana.pizzafactory.model.MoldeadorPizza;
import edu.unisabana.pizzafactory.model.Tamano;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PreparadorPizza {

    private final AFabricaPizza fabrica;

    public PreparadorPizza(AFabricaPizza fabrica) {
        this.fabrica = fabrica;
    }

    public static void main(String args[]) {

        try {

            Ingrediente[] ingredientes = new Ingrediente[]{
                new Ingrediente("queso", 1),
                new Ingrediente("jamon", 2)
            };

            AFabricaPizza fabrica = new FabricaPizzaGruesa();

            PreparadorPizza pp = new PreparadorPizza(fabrica);

            pp.prepararPizza(ingredientes, Tamano.MEDIANO);

        } catch (ExcepcionParametrosInvalidos ex) {

            Logger.getLogger(PreparadorPizza.class.getName())
                    .log(
                        Level.SEVERE,
                        "Problema en la preparacion de la Pizza",
                        ex
                    );
        }
    }

    public void prepararPizza(Ingrediente[] ingredientes, Tamano tam)
            throws ExcepcionParametrosInvalidos {

        AmasadorPizza am = fabrica.crearAmasador();
        HorneadorPizza hpd = fabrica.crearHorneador();
        MoldeadorPizza mp = fabrica.crearMoldeador();

        am.amasar();

        if (tam == Tamano.PEQUENO) {

            mp.moldearPizzaPequena();

        } else if (tam == Tamano.MEDIANO) {

            mp.moldearPizzaMediana();

        } else {

            throw new ExcepcionParametrosInvalidos(
                    "Tamano de piza invalido:" + tam
            );
        }

        aplicarIngredientes(ingredientes);

        hpd.hornear();
    }

    private void aplicarIngredientes(Ingrediente[] ingredientes) {

        Logger.getLogger(PreparadorPizza.class.getName())
                .log(
                    Level.INFO,
                    "APLICANDO INGREDIENTES!:{0}",
                    Arrays.toString(ingredientes)
                );

        
    }
}