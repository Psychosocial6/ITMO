package Client.collectionElements;

import java.io.Serializable;

/**
 * Перечисление видов топлива
 * @author Андрей
 * */
public enum FuelType implements Serializable {
    ELECTRICITY("ELECTRICITY"),
    DIESEL("DIESEL"),
    MANPOWER("MANPOWER"),
    NUCLEAR("NUCLEAR"),
    ANTIMATTER("ANTIMATTER");

    private String fuelType;

    /**
     * Конструктор
     * @param fuelType - строковое представление элемента перечисления
     * */
    FuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    /**
     * Метод возвращающий тип топлива
     * @return fuelType
     * */
    public String getFuelType() {
        return fuelType;
    }
}