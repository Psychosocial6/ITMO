package Moves;

import ru.ifmo.se.pokemon.*;

public class AerialAce extends PhysicalMove {

    private static final Type TYPE = Type.FLYING;
    private static final double POWER = 60;
    private static final double ACCURACY = 0;

    public AerialAce() {
        super(TYPE, POWER, ACCURACY);
    }

    @Override
    protected boolean checkAccuracy(Pokemon pokemon, Pokemon pokemon1) {
        return true;
    }

    @Override
    protected String describe() {
        return "Использует атаку Aerial Ace";
    }
}
