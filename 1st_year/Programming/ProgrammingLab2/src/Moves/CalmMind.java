package Moves;

import ru.ifmo.se.pokemon.*;

public class CalmMind extends StatusMove {

    private static final Type TYPE = Type.PSYCHIC;
    private static final double POWER = 0;
    private static final double ACCURACY = 1;

    public CalmMind() {
        super(TYPE, POWER, ACCURACY);
    }

    @Override
    protected void applySelfEffects(Pokemon pokemon) {
        pokemon.setMod(Stat.SPECIAL_ATTACK, 1);
        pokemon.setMod(Stat.SPECIAL_DEFENSE, 1);
    }

    @Override
    protected boolean checkAccuracy(Pokemon pokemon, Pokemon pokemon1) {
        return true;
    }

    @Override
    protected String describe() {
        return "Использует атаку Calm Mind";
    }
}
