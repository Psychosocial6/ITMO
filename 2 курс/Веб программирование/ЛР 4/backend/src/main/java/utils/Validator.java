package utils;

import java.math.BigDecimal;

public class Validator {
    public static boolean validate(BigDecimal x, BigDecimal y, BigDecimal r) {
        return (x.compareTo(BigDecimal.valueOf(5)) <= 0 && x.compareTo(BigDecimal.valueOf(-5)) >= 0) &&
                (y.compareTo(BigDecimal.valueOf(3)) <= 0 && y.compareTo(BigDecimal.valueOf(-3)) >= 0) &&
                (r.compareTo(BigDecimal.valueOf(5)) <= 0 && r.compareTo(BigDecimal.valueOf(0)) >= 0);
    }
}
