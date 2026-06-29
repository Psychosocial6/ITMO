package Moves;

import ru.ifmo.se.pokemon.*;

import java.util.Random;

public class Swagger extends StatusMove {

    private static final Type TYPE = Type.NORMAL;
    private static final double POWER = 0;
    private static final double ACCURACY = 0.85D;

    public Swagger() {
        super(TYPE, POWER, ACCURACY);
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        pokemon.setMod(Stat.ATTACK, 2);
        Random random = new Random();
        int turns = random.nextInt(2, 6);
        Effect effect = new Effect().turns(turns);
        effect.confuse(pokemon);
    }

    @Override
    protected String describe() {
        return "Использует атаку Swagger";
    }
}
