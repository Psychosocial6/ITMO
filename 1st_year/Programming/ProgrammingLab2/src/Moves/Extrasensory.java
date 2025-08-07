package Moves;

import ru.ifmo.se.pokemon.*;

public class Extrasensory extends SpecialMove {

    private static final Type TYPE = Type.PSYCHIC;
    private static final double POWER = 80;
    private static final double ACCURACY = 1;

    public Extrasensory() {
        super(TYPE, POWER, ACCURACY);
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        Effect effect = new Effect();
        effect.turns(1).chance(0.1);
        effect.flinch(pokemon);
    }

    @Override
    protected String describe() {
        return "Использует атаку Extrasensory";
    }
}
