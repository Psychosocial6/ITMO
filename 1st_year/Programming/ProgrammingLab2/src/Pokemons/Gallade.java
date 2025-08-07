package Pokemons;

import Moves.LeafBlade;
import ru.ifmo.se.pokemon.*;

public final class Gallade extends Kirlia {

    private static final double HP = 68;
    private static final double ATTACK = 125;
    private static final double DEFENSE = 65;
    private static final double SPECIAL_ATTACK = 65;
    private static final double SPECIAL_DEFENCE = 115;
    private static final double SPEED = 80;

    public Gallade(String name, int level) {
        super(name, level);
        setStats(HP, ATTACK, DEFENSE, SPECIAL_ATTACK, SPECIAL_DEFENCE, SPEED);
        addMove(new LeafBlade());
    }
}
