package Pokemons;

import Moves.Confide;
import Moves.Extrasensory;
import Moves.SludgeWave;
import Moves.Swagger;
import ru.ifmo.se.pokemon.*;

public final class Tornadus extends Pokemon {

    private static final double HP = 79;
    private static final double ATTACK = 115;
    private static final double DEFENSE = 70;
    private static final double SPECIAL_ATTACK = 125;
    private static final double SPECIAL_DEFENCE = 80;
    private static final double SPEED = 111;

    public Tornadus(String name, int level) {
        super(name, level);
        setStats(HP, ATTACK, DEFENSE, SPECIAL_ATTACK, SPECIAL_DEFENCE, SPEED);
        addMove(new Extrasensory());
        addMove(new SludgeWave());
        addMove(new Confide());
        addMove(new Swagger());
    }
}
