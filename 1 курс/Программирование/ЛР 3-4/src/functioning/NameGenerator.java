package functioning;

import java.util.Random;

public class NameGenerator {

    private static final String[] NAMES = {"Билли", "Джо", "Иосиф", "Майкл", "Перун", "Иван", "Владислав", "Алибаба", "Петр", "Сергей", "Аристотель", "Райан Гослинг"};

    public static String getName() {
        Random random = new Random();
        return NAMES[random.nextInt(0, NAMES.length)];
    }
}