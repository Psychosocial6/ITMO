package Moves;

import ru.ifmo.se.pokemon.*;
import java.util.Random;

public class LeafBlade extends PhysicalMove {

    private static final Type TYPE = Type.GRASS;
    private static final double POWER = 90;
    private static final double ACCURACY = 1;

    public LeafBlade() {
        super(TYPE, POWER, ACCURACY);
    }

    @Override
    protected double calcCriticalHit(Pokemon pokemon, Pokemon pokemon1) {
        Random random = new Random();
        double percentage = random.nextDouble(1, 101);
        if (percentage <= 12.5D) {
            return 2;
        }
        return 1;
    }

    @Override
    protected String describe() {
        return "Использует атаку Leaf Blade";
    }
}
