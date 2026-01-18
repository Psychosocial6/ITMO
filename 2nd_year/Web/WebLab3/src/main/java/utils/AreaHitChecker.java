package utils;

import java.math.BigDecimal;

public class AreaHitChecker {

    public static boolean checkHit(BigDecimal x, BigDecimal y, BigDecimal r) {
        BigDecimal zero = BigDecimal.ZERO;

        if (x.compareTo(zero) >= 0 && y.compareTo(zero) >= 0) {
            return x.compareTo(r.divide(BigDecimal.valueOf(2))) <= 0 && y.compareTo(x.negate().divide(BigDecimal.valueOf(2)).add(r)) <= 0;
        }
        else if (x.compareTo(zero) <= 0 && y.compareTo(zero) <= 0) {
            return x.compareTo(r.negate()) >= 0 && y.compareTo(r.negate().divide(BigDecimal.valueOf(2))) >= 0;
        }
        else if (x.compareTo(zero) <= 0 && y.compareTo(zero) >= 0) {
            BigDecimal rhs = r.divide(BigDecimal.valueOf(2)).pow(2);
            BigDecimal lhs = x.pow(2).add(y.pow(2));
            return lhs.compareTo(rhs) <= 0;
        }
        return false;
    }
}