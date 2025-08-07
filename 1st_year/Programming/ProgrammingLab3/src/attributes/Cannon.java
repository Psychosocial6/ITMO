package attributes;

import enums.WeaponType;
import exceptions.AmmoException;

import java.util.Random;

public class Cannon extends Weapon {

    private int power;
    private double accuracy;

    public Cannon(int ammo, int power, double accuracy) {
        super(WeaponType.CANNON, ammo);
        this.power = power;
        this.accuracy = accuracy;
    }

    @Override
    public void shoot(String username) throws AmmoException {
        if (ammo > 0) {
            ammo -= 1;
            System.out.println(String.format("Матрос %s выстрелил из пушки", username));
        }
        else {
            throw new AmmoException("Снаряды для пушки закончились");
        }
    }

    public boolean checkAccuracy() {
        Random random = new Random();
        if (random.nextDouble(0, 1) < accuracy) {
            return true;
        }
        return false;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }
}
