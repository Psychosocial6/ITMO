package utils;

import java.math.BigDecimal;

public class AreaChecker {
    public static boolean check(BigDecimal x, BigDecimal y, BigDecimal r) {
        if (x.compareTo(BigDecimal.ZERO) <= 0 && y.compareTo(BigDecimal.ZERO) >= 0) {
            BigDecimal left = x.pow(2).add(y.pow(2));
            BigDecimal right = r.pow(2);
            return left.compareTo(right) <= 0;
        }
        else if (x.compareTo(BigDecimal.ZERO) <= 0 && y.compareTo(BigDecimal.ZERO) <= 0) {
            return x.compareTo(r.negate()) >= 0 && y.compareTo(x.negate().add(r.negate())) >= 0;
        }
        else if (x.compareTo(BigDecimal.ZERO) >= 0 && y.compareTo(BigDecimal.ZERO) <= 0) {
            return x.compareTo(r) <= 0 && y.compareTo(r.negate().divide(BigDecimal.valueOf(2))) >= 0;
        }
        return false;
    }
}
