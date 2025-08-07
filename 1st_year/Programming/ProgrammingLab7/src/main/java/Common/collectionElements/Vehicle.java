package Common.collectionElements;

import Common.exceptions.WrongDataException;

import java.time.ZonedDateTime;

/**
 * Класс описывающий транспортные средства, хранящиеся в коллекции
 * @author Андрей
 * */
public class Vehicle extends Element implements Comparable<Vehicle> {
    private Long id;
    private String name;
    private Coordinates coordinates;
    private ZonedDateTime creationDate;
    private Long enginePower;
    private double capacity;
    private Long distanceTravelled;
    private FuelType fuelType;
    private int ownerID;

    /**
     * Пустой конструктор
     * */
    public Vehicle() {
    }

    /**
     * Конструктор
     * @param name название
     * @param coordinates класс, хранящий координаты
     * @param enginePower мощность двигателя (null если отсутствует)
     * @param capacity вместительность
     * @param distanceTravelled пробег (null если отсутствует)
     * @param fuelType тип топлива
     * */
    public Vehicle(String name, Coordinates coordinates, Long enginePower, double capacity, Long distanceTravelled, FuelType fuelType) {
        this.name = name;
        this.coordinates = coordinates;
        this.enginePower = enginePower;
        this.capacity = capacity;
        this.distanceTravelled = distanceTravelled;
        this.fuelType = fuelType;
    }

    public Vehicle(Long id, String name, Coordinates coordinates, ZonedDateTime creationDate, Long enginePower, double capacity, Long distanceTravelled, FuelType fuelType, int ownerID) {
        this.name = name;
        this.coordinates = coordinates;
        this.enginePower = enginePower;
        this.capacity = capacity;
        this.distanceTravelled = distanceTravelled;
        this.fuelType = fuelType;
        this.id = id;
        this.creationDate = creationDate;
        this.ownerID = ownerID;
    }

    /**
     * Метод для проверки полей на правильность данных
     * @throws WrongDataException - уведомляет о неверных данных
     * */
    @Override
    public void validate() throws WrongDataException {
        if (name == null || name.isEmpty()) {
            throw new WrongDataException("Wrong name");
        }
        if (coordinates == null) {
            throw new WrongDataException("Wrong coordinates");
        }
        coordinates.validate();
        if (!(enginePower == null) && enginePower <= 0) {
            throw new WrongDataException("Wrong engine power");
        }
        if (capacity <= 0) {
            throw new WrongDataException("Wrong capacity");
        }
        if (!(distanceTravelled == null) && distanceTravelled <= 0) {
            throw new WrongDataException("Wrong distance travelled");
        }
    }

    /**
     * Метод возвращающий значение поля id
     * @return Long id
     * */
    public Long getId() {
        return id;
    }

    /**
     * Метод возвращающий значения поля name
     * @return String name
     * */
    public String getName() {
        return name;
    }

    /**
     * Метод возвращающий поле coordinates
     * @return Coordinates coordinates
     * */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Метод возвращающий поле creationDate
     * @return ZonedDateTime creationDate
     * */
    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * Метод возвращающий поле enginePower
     * @return Long enginePower
     * */
    public Long getEnginePower() {
        return enginePower;
    }

    /**
     * Метод возвращающий поле capacity
     * @return double capacity
     * */
    public double getCapacity() {
        return capacity;
    }

    /**
     * Метод возвращающий поле distanceTravelled
     * @return Long distanceTravelled
     * */
    public Long getDistanceTravelled() {
        return distanceTravelled;
    }

    /**
     * Метод возвращающий поле fuelType
     * @return FuelType fuelType
     * */
    public FuelType getFuelType() {
        return fuelType;
    }

    /**
     * Метод устанавливающий новое значение поля id
     * @param id - новое значени
     * */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Метод устанавливающий новое значение поля creationDate
     * @param creationDate - новое значени
     * */
    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    /**
     * Метод возвращающий строковое предстваление объекта
     * @return String поля класс
     * */
    @Override
    public String toString() {
        return String.format("Vehicle: id=%d, name=%s, coordinates=%s, creationDate=%s, enginePower=%d, capacity=%f, distanceTravelled=%d, fuelType=%s", id, name, coordinates, creationDate, enginePower, capacity, distanceTravelled, fuelType);
    }

    /**
     * Метод позволяющий сравнить 2 объекта Vehicle
     * @param o - объект с которым сравнивается данный
     * @return int >0 если this > o, <0 если this < o, 0 если this == o
     * */
    @Override
    public int compareTo(Vehicle o) {
        if (name.compareTo(o.getName()) == 0) {
            if (enginePower.compareTo(o.getEnginePower()) == 0) {
                if (((Double) capacity).compareTo(o.getCapacity()) == 0) {
                    if (distanceTravelled.compareTo(o.getDistanceTravelled()) == 0) {
                        return 0;
                    }
                    return distanceTravelled.compareTo(o.getDistanceTravelled());
                }
                return ((Double) capacity).compareTo(o.getCapacity());
            }
            return enginePower.compareTo(o.getEnginePower());
        }
        return name.compareTo(o.getName());
    }
}