package attributes;

import enums.WeaponType;
import exceptions.AmmoException;

public class Gun extends Weapon {

    public Gun(WeaponType type, int ammo) {
        super(type, ammo);
    }

    @Override
    public void shoot(String username) throws AmmoException {
        if (ammo > 0) {
            ammo -= 1;
        }
        else {
            throw new AmmoException(String.format("У матроса %s закончились патроны", username));
        }
    }
}
