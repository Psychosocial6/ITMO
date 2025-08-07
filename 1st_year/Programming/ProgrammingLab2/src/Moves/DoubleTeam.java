package Moves;

import ru.ifmo.se.pokemon.*;

public class DoubleTeam extends StatusMove {

    private static final Type TYPE = Type.NORMAL;
    private static final double POWER = 0;
    private static final double ACCURACY = 1;

    public DoubleTeam() {
        super(TYPE, POWER, ACCURACY);
    }

    @Override
    protected void applySelfEffects(Pokemon pokemon) {
        pokemon.setMod(Stat.EVASION, 1);
    }

    @Override
    protected boolean checkAccuracy(Pokemon pokemon, Pokemon pokemon1) {
        return true;
    }

    @Override
    protected String describe() {
        return "Использует атаку Double Team";
    }
}