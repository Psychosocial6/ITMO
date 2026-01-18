package utils;

import java.math.BigDecimal;

public class AreaHitChecker {

    public static boolean checkHit(float xFloat, BigDecimal y, float rFloat) {
        BigDecimal x = BigDecimal.valueOf(xFloat);
        BigDecimal r = BigDecimal.valueOf(rFloat);
        BigDecimal zero = BigDecimal.ZERO;

        if (x.compareTo(zero) >= 0 && y.compareTo(zero) >= 0) {
            BigDecimal lhs = x.pow(2).add(y.pow(2));
            BigDecimal rhs = r.pow(2).divide(BigDecimal.valueOf(4));
            return lhs.compareTo(rhs) <= 0;
        }
        else if (x.compareTo(zero) <= 0 && y.compareTo(zero) >= 0) {
            return x.compareTo(r.negate()) >= 0 && y.compareTo(r) <= 0;
        }
        else if (x.compareTo(zero) <= 0 && y.compareTo(zero) <= 0) {
            BigDecimal rhs = new BigDecimal("-0.5").multiply(x).subtract(r);
            return y.compareTo(rhs) >= 0;
        }

        return false;
    }
}