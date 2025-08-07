package attributes;

import enums.WeaponType;
import exceptions.AmmoException;

public abstract class Weapon {

    protected WeaponType type;
    protected int ammo;

    public Weapon() {
        this.type = null;
        this.ammo = 1;
    }

    public Weapon(WeaponType type, int ammo) {
        this.type = type;
        this.ammo = ammo;
    }

    public abstract void shoot(String username) throws AmmoException;

    public WeaponType getType() {
        return type;
    }

    public void setType(WeaponType type) {
        this.type = type;
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    @Override
    public String toString() {
        return String.format("Оружие типа %s", type);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Weapon weapon = (Weapon) o;
        return type == weapon.type;
    }

    @Override
    public int hashCode() {
        return type.hashCode() + 456;
    }
}
