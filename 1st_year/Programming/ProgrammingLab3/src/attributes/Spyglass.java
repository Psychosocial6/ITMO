package attributes;

import java.util.ArrayList;
import java.util.Objects;

import static java.lang.Math.*;

import boats.Boat;
import environment.River;
import people.Human;
import people.Sailor;

public class Spyglass {

    private double distance;
    private double effectiveDistance;

    public Spyglass(double distance, double effectiveDistance) {
        this.distance = distance;
        this.effectiveDistance = effectiveDistance;
    }

    public ArrayList<Boat> lookForBoats(River river) {
        ArrayList<Boat> all = river.getBoats();
        ArrayList<Boat> canSee = new ArrayList<Boat>();
        for (Boat boat : all) {
            if (sqrt(pow(boat.getxCoordinate(), 2) + pow(boat.getyCoordinate(), 2)) <= distance) {
                canSee.add(boat);
            }
        }
        return canSee;
    }

    public ArrayList<Human> lookForPeople(River river) {
        ArrayList<Boat> all = river.getBoats();
        ArrayList<Human> canSee = new ArrayList<Human>();
        for (Boat boat : all) {
            if (sqrt(pow(boat.getxCoordinate(), 2) + pow(boat.getyCoordinate(), 2)) <= effectiveDistance) {
                canSee.addAll(boat.getSailors());
            }
        }
        return canSee;
    }

    @Override
    public String toString() {
        return String.format("Подзорная труба с дальностью %f метров и эффективной дальностью %f метров", distance, effectiveDistance);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Spyglass spyglass = (Spyglass) o;
        return Double.compare(distance, spyglass.distance) == 0 && Double.compare(effectiveDistance, spyglass.effectiveDistance) == 0;
    }

    @Override
    public int hashCode() {
        return 12 * (int) distance - 14 * (int) effectiveDistance;
    }
}
