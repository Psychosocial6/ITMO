package Server.comparators;

import java.util.Comparator;

/**
 * Компаратор для сравнения вместимости т.с.
 * @author Андрей
 * */
public class CapacityComparator implements Comparator<Double> {

    /**
     * Метод сравнения двух полей
     * @param o1 - первая вместимость
     * @param o2 - вторая вместимость
     * @return int compare, значение compare ><= 0 см. Vehicle
     * */
    @Override
    public int compare(Double o1, Double o2) {
        return o1.compareTo(o2);
    }
}