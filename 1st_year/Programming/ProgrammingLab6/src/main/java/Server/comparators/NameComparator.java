package Server.comparators;

import java.util.Comparator;

/**
 * Компаратор для сравнения имени т.с.
 * @author Андрей
 * */
public class NameComparator implements Comparator<String> {

    /**
     * Метод сравнения двух полей
     * @param o1 - первое имя
     * @param o2 - второе имя
     * @return int compare, значение compare ><= 0 см. Vehicle
     * */
    @Override
    public int compare(String o1, String o2) {
        return o1.compareTo(o2);
    }
}