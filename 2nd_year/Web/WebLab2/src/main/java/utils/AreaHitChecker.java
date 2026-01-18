package utils;

import objects.RequestBody;

import java.math.BigDecimal;

public class AreaHitChecker {

    public static boolean checkHit(RequestBody values) {
        BigDecimal x = values.getX();
        BigDecimal y = values.getY();
        BigDecimal r = values.getR();
        BigDecimal zero = BigDecimal.ZERO;

        if (x.compareTo(zero) >= 0 && y.compareTo(zero) >= 0) {
            BigDecimal lhs = x.pow(2).add(y.pow(2));
            BigDecimal rhs = r.pow(2).divide(BigDecimal.valueOf(4));
            return lhs.compareTo(rhs) <= 0;
        }
        else if (x.compareTo(zero) <= 0 && y.compareTo(zero) <= 0) {
            return x.compareTo(r.negate()) >= 0 && y.compareTo(r.negate().divide(BigDecimal.valueOf(2))) >= 0;
        }
        else if (x.compareTo(zero) <= 0 && y.compareTo(zero) >= 0) {
            BigDecimal rhs = x.add(r.divide(BigDecimal.valueOf(2)));
            return y.compareTo(rhs) <= 0;
        }

        return false;
    }
}