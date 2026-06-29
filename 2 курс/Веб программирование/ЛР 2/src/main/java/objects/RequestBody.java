package objects;

import java.math.BigDecimal;

public class RequestBody {
    private final BigDecimal x;
    private final BigDecimal y;
    private final BigDecimal r;
    private final String type;

    public RequestBody(BigDecimal x, BigDecimal y, BigDecimal r, String type) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.type = type;
    }

    public BigDecimal getX() {
        return x;
    }

    public BigDecimal getY() {
        return y;
    }

    public BigDecimal getR() {
        return r;
    }

    public String getType() {
        return type;
    }
}
