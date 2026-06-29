package services;

import data.Result;
import entities.ResultEntity;
import exceptions.ValidationException;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import utils.AreaChecker;
import utils.Validator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Stateless
public class PointCheckingService {
    @EJB
    DatabaseManager databaseManager;

    public Result checkPoint(BigDecimal x, BigDecimal y, BigDecimal r, String userLogin)
            throws ValidationException {
        long startTime = System.nanoTime();
        if (Validator.validate(x, y, r)) {
            boolean result = AreaChecker.check(x, y, r);
            LocalDateTime localTime = LocalDateTime.now();
            double requestTime = Math.round(((double) (System.nanoTime() - startTime) / 1e6) * 1e6) / 1e6;
            ResultEntity resultEntity = new ResultEntity(x, y, r, result, requestTime, localTime, userLogin);
            databaseManager.saveResult(resultEntity, userLogin);

            return new Result(x, y, r, result, requestTime, localTime);
        }
        throw new ValidationException("Invalid point");
    }
}