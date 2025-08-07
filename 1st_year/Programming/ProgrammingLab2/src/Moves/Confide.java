package Moves;

import ru.ifmo.se.pokemon.*;

public class Confide extends StatusMove {

    private static final Type TYPE = Type.NORMAL;
    private static final double POWER = 0;
    private static final double ACCURACY = 0;

    public Confide() {
        super(TYPE, POWER, ACCURACY);
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {;
        pokemon.setMod(Stat.SPECIAL_ATTACK, -1);
    }

    @Override
    protected boolean checkAccuracy(Pokemon pokemon, Pokemon pokemon1) {
        return true;
    }

    @Override
    protected String describe() {
        return "Использует атаку Confide";
    }
}
