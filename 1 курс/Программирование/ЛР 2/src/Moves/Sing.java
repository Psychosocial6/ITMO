package Moves;

import ru.ifmo.se.pokemon.*;
import java.util.Random;

public class Sing extends StatusMove {

    private static final Type TYPE = Type.NORMAL;
    private static final double POWER = 0;
    private static final double ACCURACY = 0.55D;

    public Sing() {
        super(TYPE, POWER, ACCURACY);
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        Random random = new Random();
        int moves = random.nextInt(2, 5);
        Effect effect = new Effect().chance(1).turns(moves);
        effect.sleep(pokemon);
    }

    @Override
    protected String describe() {
        return "Использует атаку Sing";
    }
}
