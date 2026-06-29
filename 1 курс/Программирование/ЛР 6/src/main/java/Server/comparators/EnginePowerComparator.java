package Server.comparators;

import java.util.Comparator;

/**
 * Компаратор для сравнения мощности т.с.
 * @author Андрей
 * */
public class EnginePowerComparator implements Comparator<Long> {

    /**
     * Метод сравнения двух полей
     * @param o1 - первая мощность
     * @param o2 - вторая мощность
     * @return int compare, значение compare ><= 0 см. Vehicle
     * */
    @Override
    public int compare(Long o1, Long o2) {
        return o1.compareTo(o2);
    }
}