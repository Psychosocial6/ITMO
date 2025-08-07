package Pokemons;

import Moves.DrainingKiss;
import ru.ifmo.se.pokemon.*;

public class Kirlia extends Ralts {

    private static final double HP = 38;
    private static final double ATTACK = 35;
    private static final double DEFENSE = 35;
    private static final double SPECIAL_ATTACK = 65;
    private static final double SPECIAL_DEFENCE = 55;
    private static final double SPEED = 50;

    public Kirlia(String name, int level) {
        super(name, level);
        setStats(HP, ATTACK, DEFENSE, SPECIAL_ATTACK, SPECIAL_DEFENCE, SPEED);
        addMove(new DrainingKiss());
    }
}
