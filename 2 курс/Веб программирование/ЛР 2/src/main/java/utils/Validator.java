package utils;

import objects.RequestBody;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Validator {
    private static final Set<BigDecimal> availableXValues = new HashSet<>(List.of(new BigDecimal("-3"),
            new BigDecimal("-2"),
            new BigDecimal("-1"),
            new BigDecimal("0"),
            new BigDecimal("1"),
            new BigDecimal("2"),
            new BigDecimal("3"),
            new BigDecimal("4"),
            new BigDecimal("5")));
    private static final Set<BigDecimal> availableRValues = new HashSet<>(List.of(new BigDecimal("1"),
            new BigDecimal("2"),
            new BigDecimal("3"),
            new BigDecimal("4"),
            new BigDecimal("5")));
    private static final BigDecimal Y_MIN = new BigDecimal("-5");
    private static final BigDecimal Y_MAX = new BigDecimal("3");

    public static boolean validateData(RequestBody requestBody) {
        if (requestBody.getType().equals("btn")) {
            return availableXValues.contains(requestBody.getX()) && availableRValues.contains(requestBody.getR()) && requestBody.getY().compareTo(Y_MIN) >= 0 && requestBody.getY().compareTo(Y_MAX) <= 0;
        }
        else if(requestBody.getType().equals("img")) {
            return availableRValues.contains(requestBody.getR());
        }
        return false;
    }
}
