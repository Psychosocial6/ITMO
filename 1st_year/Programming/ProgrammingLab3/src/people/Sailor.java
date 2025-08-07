package people;

import attributes.Gun;
import exceptions.AmmoException;

public class Sailor extends Human {

    private Gun gun;
    private double strength;
    public boolean isRowing;

    public Sailor() {
        super();
        this.gun = null;
        this.strength = 0;
        this.isRowing = false;

    }

    public Sailor(String name, Gun gun, double strength, boolean isRowing) {
        super(name);
        this.gun = gun;
        this.isRowing = isRowing;
        this.strength = strength;
    }

    public void useGun() {
        try {
            gun.shoot(name);
            System.out.printf("Матрос %s выстрелил из %s\n", name, gun.getType());
        }
        catch (AmmoException e) {
            System.out.println(e.getMessage());
        }
    }

    public Gun getGun() {
        return gun;
    }

    public void setGun(Gun gun) {
        this.gun = gun;
    }

    public double getStrength() {
        return strength;
    }

    public void setStrength(double strength) {
        this.strength = strength;
    }
}
