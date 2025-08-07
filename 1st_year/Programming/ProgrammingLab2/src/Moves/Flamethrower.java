package Moves;

import ru.ifmo.se.pokemon.*;

public class Flamethrower extends SpecialMove {

    private static final Type TYPE = Type.FIRE;
    private static final double POWER = 90;
    private static final double ACCURACY = 1;

    public Flamethrower() {
        super(TYPE, POWER, ACCURACY);
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        Effect effect = new Effect();
        effect.turns(1).chance(0.1);
        effect.burn(pokemon);
    }

    @Override
    protected String describe() {
        return "Использует атаку Flamethrower";
    }
}
