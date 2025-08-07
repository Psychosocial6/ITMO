package boats;

import enums.BoatType;
import exceptions.CapacityException;
import people.Sailor;

import java.util.ArrayList;

public abstract class Boat implements Swimable {

    protected BoatType type;
    protected int capacity;
    protected double xCoordinate;
    protected double yCoordinate;
    protected ArrayList<Sailor> sailors;
    protected int hp;
    protected final int MAX_HP;

    public Boat() {
        type = null;
        capacity = 10;
        xCoordinate = 0;
        yCoordinate = 0;
        sailors = new ArrayList<Sailor>(capacity);
        hp = 100;
        MAX_HP = 100;
    }

    public Boat(BoatType type, int capacity, double xCoordinate, double yCoordinate, int hp) {
        this.type = type;
        this.capacity = capacity;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.sailors = new ArrayList<Sailor>(capacity);
        this.hp = hp;
        MAX_HP = hp;
    }

    public abstract void addSailor(Sailor sailor) throws CapacityException;

    public void fix() {
        double power = 0;
        for (int i = 0; i < sailors.size(); i++) {
            power += sailors.get(i).getStrength();
        }
        if (hp + (int) power >= MAX_HP) {
            hp = MAX_HP;
            System.out.println(String.format("%s полностью отремонтировано", toString()));
        }
        else {
            hp += (int) power;
            System.out.println(String.format("Произведен ремонт %s, прочность %d из %d", toString(), hp, MAX_HP));
        }
    }

    public BoatType getType() {
        return type;
    }

    public void setType(BoatType type) {
        this.type = type;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(double xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public double getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(double yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public ArrayList<Sailor> getSailors() {
        return sailors;
    }

    public void setSailors(ArrayList<Sailor> sailors) {
        this.sailors = sailors;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    @Override
    public String toString() {
        return String.format("Плавсредство типа %s", type);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Boat boat = (Boat) o;
        return capacity == boat.capacity && type == boat.type;
    }

    @Override
    public int hashCode() {
        return 666 * type.hashCode() + capacity;
    }
}
