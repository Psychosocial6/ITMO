package beans;

import entities.ResultEntity;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import utils.AreaHitChecker;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Named("pointBean")
@SessionScoped
public class PointBean implements Serializable {
    private BigDecimal x;
    private BigDecimal y;
    private BigDecimal r;
    private String type;
    private boolean result;
    private double requestTime;
    private LocalDateTime localTime;

    @Inject
    private HistoryBean historyBean;

    @PostConstruct
    public void init() {
        setX(new BigDecimal(0));
        setR(new BigDecimal(1));
    }

    public void check() {
        long startTime = System.nanoTime();
        result = AreaHitChecker.checkHit(x, y, r);
        localTime = LocalDateTime.now();
        long endTime = System.nanoTime();
        requestTime = Math.round(((double) (endTime - startTime) / 1e6) * 1e6) / 1e6;

        ResultEntity requestResult = new ResultEntity(x, y, r, result, requestTime, localTime);
        historyBean.addResult(requestResult);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public double getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(double requestTime) {
        this.requestTime = requestTime;
    }

    public LocalDateTime getLocalTime() {
        return localTime;
    }

    public void setLocalTime(LocalDateTime localTime) {
        this.localTime = localTime;
    }

    public BigDecimal getX() {
        return x;
    }

    public void setX(BigDecimal x) {
        this.x = x;
    }

    public BigDecimal getY() {
        return y;
    }

    public void setY(BigDecimal y) {
        this.y = y;
    }

    public BigDecimal getR() {
        return r;
    }

    public void setR(BigDecimal r) {
        this.r = r;
    }
}
