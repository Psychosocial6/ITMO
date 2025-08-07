package Moves;

import ru.ifmo.se.pokemon.*;

public class DazzlingGleam extends SpecialMove {

    private static final Type TYPE = Type.FAIRY;
    private static final double POWER = 80;
    private static final double ACCURACY = 1;

    public DazzlingGleam() {
        super(TYPE, POWER, ACCURACY);
    }

    @Override
    protected String describe() {
        return "Использует атаку Dazzling Gleam";
    }
}
