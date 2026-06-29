package utils;

import java.math.BigDecimal;

public class RequestData {
    private float x;
    private BigDecimal y;
    private float r;

    public RequestData(float x, BigDecimal y, float r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public RequestData() {
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public BigDecimal getY() {
        return y;
    }

    public void setY(BigDecimal y) {
        this.y = y;
    }

    public float getR() {
        return r;
    }

    public void setR(float r) {
        this.r = r;
    }
}
