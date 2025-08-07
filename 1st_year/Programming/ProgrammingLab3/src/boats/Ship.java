package boats;

import attributes.Cannon;
import enums.BoatType;
import enums.FlowDirection;
import exceptions.AmmoException;
import exceptions.CapacityException;
import exceptions.NumberOfLifeboatsException;
import functioning.CoordinatesUtil;
import people.Sailor;

import java.util.ArrayList;

public class Ship extends Boat {

    private double enginePower;
    private int numberOfLifeboats;
    public int lifeboatsCapacity;
    private int lifeboatsHp;
    private ArrayList<Cannon> cannons;

    public Ship() {
        super();
        type = BoatType.SHIP;
        enginePower = 10;
        numberOfLifeboats = 2;
        lifeboatsCapacity = 5;
        int cannonsAmmo = 5;
        double cannonsAccuracy = 0.85D;
        int cannonsPower = 20;
        cannons = new ArrayList<Cannon>();
        cannons.add(new Cannon(cannonsAmmo, cannonsPower, cannonsAccuracy));
        cannons.add(new Cannon(cannonsAmmo, cannonsPower, cannonsAccuracy));
    }

    public Ship(int capacity, double xCoordinate, double yCoordinate, int hp, double enginePower, int numberOfLifeboats, int lifeboatsCapacity, int lifeboatsHp, int numberOfCannons, int cannonsAmmo, int cannonsPower, double cannonsAccuracy) {
        super(BoatType.SHIP, capacity, xCoordinate, yCoordinate, hp);
        this.enginePower = enginePower;
        this.numberOfLifeboats = numberOfLifeboats;
        this.lifeboatsCapacity = lifeboatsCapacity;
        this.lifeboatsHp = lifeboatsHp;
        cannons = new ArrayList<Cannon>();
        for (int i = 0; i < numberOfCannons; i++) {
            cannons.add(new Cannon(cannonsAmmo, cannonsPower, cannonsAccuracy));
        }
    }

    public Lifeboat dropLifeboat(int numberOfPeople) throws NumberOfLifeboatsException {
        if (numberOfLifeboats == 0) {
            throw new NumberOfLifeboatsException(toString());
        }
        Lifeboat lifeboat = new Lifeboat(lifeboatsCapacity, xCoordinate, yCoordinate, lifeboatsHp);
        for (int i = 0; i < numberOfPeople; i++) {
            try {
                lifeboat.addSailor(sailors.get(0));
                sailors.remove(0);
            }
            catch (CapacityException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("С корабля спустили шлюпку");
        return lifeboat;
    }

    public Lifeboat dropLifeboat(int[] people) throws NumberOfLifeboatsException {
        if (numberOfLifeboats == 0) {
            throw new NumberOfLifeboatsException(toString());
        }
        Lifeboat lifeboat = new Lifeboat(lifeboatsCapacity, xCoordinate, yCoordinate, lifeboatsHp);
        for (int i : people) {
            try {
                lifeboat.addSailor(sailors.get(i));
                sailors.remove(i);
            }
            catch (CapacityException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("С корабля спустили шлюпку");
        return lifeboat;
    }

    @Override
    public void addSailor(Sailor sailor) throws CapacityException {
        if (sailors.size() < capacity) {
            sailors.add(sailor);
        }
        else {
            throw new CapacityException(toString());
        }
    }

    @Override
    public void swim(double time, double direction, FlowDirection flowDirection, double flowVelocity, double width) {
        if (hp > 0) {
            xCoordinate = CoordinatesUtil.newCoordinates(time, direction, flowDirection, flowVelocity, width, enginePower, xCoordinate, yCoordinate)[0];
            yCoordinate = CoordinatesUtil.newCoordinates(time, direction, flowDirection, flowVelocity, width, enginePower, xCoordinate, yCoordinate)[1];

            if (yCoordinate == width) {
                System.out.println("Корабль пристал к берегу\n");
            } else if (yCoordinate == 0) {
                System.out.println("Корабль пристал к берегу\n");
            } else {
                System.out.printf("Корабль приплыл на координаты x = %f, y = %f\n", xCoordinate, yCoordinate);
            }
        }
        else {
            System.out.println(String.format("%s не может плыть, нужен ремонт", toString()));
        }
    }

    public void useCannon(int cannonsIndex, int sailorsIndex) {
        try {
            cannons.get(cannonsIndex).shoot(sailors.get(sailorsIndex).getName());
        }
        catch (AmmoException e) {
            System.out.println(e.getMessage());
        }
    }

    public void useCannon(int cannonsIndex, int sailorsIndex, Boat boat) {
        if (cannons.get(cannonsIndex).checkAccuracy()) {
            try {
                cannons.get(cannonsIndex).shoot(sailors.get(sailorsIndex).getName());
                if (cannons.get(cannonsIndex).getPower() > boat.getHp()) {
                    boat.setHp(0);
                } else {
                    boat.setHp(boat.getHp() - cannons.get(cannonsIndex).getPower());
                }
            }
            catch (AmmoException e) {
                System.out.println(e.getMessage());
            }
        }
        else if (cannons.get(cannonsIndex).getAmmo() == 0) {
            System.out.println("У пушки закночились снаряды");
        }
        else {
            System.out.println(String.format("Сняряд не попал в %s", boat.toString()));
        }
    }
}
