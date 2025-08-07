package Pokemons;

import Moves.AerialAce;
import Moves.DoubleTeam;
import Moves.Sing;
import ru.ifmo.se.pokemon.*;

public class Swablu extends Pokemon {

    private static final double HP = 45;
    private static final double ATTACK = 40;
    private static final double DEFENSE = 60;
    private static final double SPECIAL_ATTACK = 40;
    private static final double SPECIAL_DEFENCE = 75;
    private static final double SPEED = 50;

    public Swablu(String name, int level) {
        super(name, level);
        setStats(HP, ATTACK, DEFENSE, SPECIAL_ATTACK, SPECIAL_DEFENCE, SPEED);
        addMove(new DoubleTeam());
        addMove(new AerialAce());
        addMove(new Sing());
    }
}
