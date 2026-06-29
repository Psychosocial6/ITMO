package Pokemons;

import Moves.Flamethrower;
import ru.ifmo.se.pokemon.*;

public final class Altaria extends Swablu {

    private static final double HP = 75;
    private static final double ATTACK = 70;
    private static final double DEFENSE = 90;
    private static final double SPECIAL_ATTACK = 70;
    private static final double SPECIAL_DEFENCE = 105;
    private static final double SPEED = 80;

    public Altaria(String name, int level) {
        super(name, level);
        setStats(HP, ATTACK, DEFENSE, SPECIAL_ATTACK, SPECIAL_DEFENCE, SPEED);
        addMove(new Flamethrower());
    }
}
