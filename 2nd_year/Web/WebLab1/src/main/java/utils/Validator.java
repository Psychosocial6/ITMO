package utils;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;

public class Validator {
    private static final HashSet<Float> availableXValues = new  HashSet<>(List.of(-5F, -4F, -3F, -2F, -1F, 0F, 1F, 2F, 3F));
    private static final HashSet<Float> availableRValues = new  HashSet<>(List.of(1F, 1.5F, 2F, 2.5F, 3F));
    private static final BigDecimal Y_MIN = new BigDecimal("-3");
    private static final BigDecimal Y_MAX = new BigDecimal("3");

    public static boolean validateData(float x, BigDecimal y, float r) {
        return availableXValues.contains(x) && availableRValues.contains(r) && y.compareTo(Y_MIN) >= 0 && y.compareTo(Y_MAX) <= 0;
    }
}
