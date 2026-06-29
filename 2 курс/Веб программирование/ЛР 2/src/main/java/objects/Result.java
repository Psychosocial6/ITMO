package objects;

import java.math.BigDecimal;

public class Result {
    private double x;
    private double y;
    private double r;
    private boolean result;
    private double requestTime;
    private String localTime;
    private double cx;
    private double cy;

    public Result(BigDecimal x, BigDecimal y, BigDecimal r, boolean result, double requestTime, String localTime) {
        this.x = x.doubleValue();
        this.y = y.doubleValue();
        this.r = r.doubleValue();
        this.result = result;
        this.requestTime = requestTime;
        this.localTime = localTime;
        cx = 200 + this.x / this.r * 150;
        cy = 200 - this.y / this.r * 150;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getR() {
        return r;
    }

    public boolean isResult() {
        return result;
    }

    public double getRequestTime() {
        return requestTime;
    }

    public String getLocalTime() {
        return localTime;
    }

    public double getCx() {
        return cx;
    }

    public double getCy() {
        return cy;
    }
}
