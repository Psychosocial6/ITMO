package Server.comparators;

import java.util.Comparator;

/**
 * Компаратор для сравнения id т.с.
 * @author Андрей
 * */
public class IDComparator implements Comparator<Long> {

    /**
     * Метод сравнения двух полей
     * @param o1 - первый id
     * @param o2 - второй id
     * @return int compare, значение compare ><= 0 см. Vehicle
     * */
    @Override
    public int compare(Long o1, Long o2) {
        return o1.compareTo(o2);
    }
}