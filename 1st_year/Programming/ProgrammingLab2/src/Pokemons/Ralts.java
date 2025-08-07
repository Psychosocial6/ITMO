package Pokemons;

import Moves.CalmMind;
import Moves.DazzlingGleam;
import ru.ifmo.se.pokemon.*;

public class Ralts extends Pokemon {

    private static final double HP = 28;
    private static final double ATTACK = 25;
    private static final double DEFENSE = 25;
    private static final double SPECIAL_ATTACK = 45;
    private static final double SPECIAL_DEFENCE = 35;
    private static final double SPEED = 40;

    public Ralts(String name, int level) {
        super(name, level);
        setStats(HP, ATTACK, DEFENSE, SPECIAL_ATTACK, SPECIAL_DEFENCE, SPEED);
        addMove(new CalmMind());
        addMove(new DazzlingGleam());
    }
}
