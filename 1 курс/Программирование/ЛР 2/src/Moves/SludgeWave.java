package Moves;

import ru.ifmo.se.pokemon.*;

public class SludgeWave extends SpecialMove {

    private static final Type TYPE = Type.POISON;
    private static final double POWER = 95;
    private static final double ACCURACY = 1;

    public SludgeWave() {
        super(TYPE, POWER, ACCURACY);
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        Effect effect = new Effect();
        effect.turns(1).chance(0.1D);
        effect.poison(pokemon);
    }

    @Override
    protected String describe() {
        return "Использует атаку Sludge Wave";
    }
}
