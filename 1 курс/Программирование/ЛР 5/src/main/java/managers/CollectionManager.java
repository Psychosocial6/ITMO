package managers;

import collectionElements.Vehicle;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import exceptions.ScriptExecutionException;
import utils.FileWriter;
import utils.Invoker;
import utils.ScriptExecutor;
import utils.XMLSerializer;

import java.io.File;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

/**
 * Класс управляющий коллекцией
 * @author Андрей
 * */
public class CollectionManager {
    @JacksonXmlElementWrapper(useWrapping = false)
    private Hashtable<String, Vehicle> collection;
    @JsonIgnore
    private final ZonedDateTime initializationDate;

    /**
     * Пустой конструктор
     * */
    public CollectionManager() {
        this.collection = new Hashtable<>();
        initializationDate = ZonedDateTime.now();
    }

    /**
     * Конструктор
     * @param collection - коллекция хранящаяся в классе
     * */
    public CollectionManager(Hashtable<String, Vehicle> collection) {
        this.collection = collection;
        initializationDate = ZonedDateTime.now();
    }

    /**
     * Метод выводящий информацию о коллекции
     */
    public void info() {
        System.out.println("----------");
        System.out.println(String.format("Дата создания: %s", initializationDate));
        System.out.println(String.format("Размер коллекции: %d", collection.size()));
        System.out.println(String.format("Тип коллекции: %s", collection.getClass()));
        System.out.println("----------");
    }

    /**
     * Метод выводящий содержимое коллекции
     */
    public void show() {
        if (collection.isEmpty()) {
            System.out.println("Collection is empty");
        }
        else {
            System.out.println("----------");
            for (String key : collection.keySet()) {
                System.out.println(String.format("%s : %s", key, collection.get(key)));
                System.out.println("----------");
            }
        }

    }

    /**
     * Метод вставки элемента по ключу
     * @param key - ключ
     * @param element - элемент
     */
    public void insert(String key, Vehicle element) {
        collection.put(key, element);
    }

    /**
     * Метод замены элемента с заданным id на данный
     * @param id - id
     * @param element - элемент на который заменяется существующий
     */
    public void updateById(int id, Vehicle element) {
        for (String key : collection.keySet()) {
            if (collection.get(key).getId() == id) {
                element.setId(collection.get(key).getId());
                element.setCreationDate(collection.get(key).getCreationDate());
                collection.put(key, element);
            }
        }
    }

    /**
     * Метод удаления элемента по ключу
     * @param key - ключ
     */
    public void removeByKey(String key) {
        collection.remove(key);
        System.out.println(String.format("Elem with key {%s} removed", key));
    }

    /**
     * Метод очистки коллекции
     */
    public void clear() {
        collection.clear();
        System.out.println("Collection cleared");
    }

    /**
     * Метод для сохранения коллекции в файл
     * @param file - файл для сохранения
     */
    public void save(File file) {
        FileWriter.writeIntoFile(file, XMLSerializer.serializeToXML(this));
    }

    /**
     * Метод для исполнения скрипта из заданного файла
     * @param file - файл со скриптом
     * @param invoker - исполнитель команд
     */
    public void executeScript(File file, Invoker invoker) {
        ScriptExecutor executor = new ScriptExecutor(invoker);
        try {
            executor.executeScript(file);
        } catch (ScriptExecutionException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Метод для завершения работы программы
     */
    public void exit() {
        System.exit(0);
    }

    /**
     * Метод для удаления всех элементов коллекции меньше данного
     * @param element - элемент с которым сравнивается
     */
    public void removeIfLower(Vehicle element) {
        Hashtable<String, Vehicle> collection2 = (Hashtable<String, Vehicle>) collection.clone();
        for (String key : collection.keySet()) {
            if (collection.get(key).compareTo(element) < 0) {
                collection2.remove(key);
            }
        }
        collection = collection2;
    }

    /**
     * Метод для замены элемента по ключу, если новый элемент больше
     * @param key - ключ
     * @param element - новое значение
     */
    public void replaceIfLower(String key, Vehicle element) {
        if (collection.get(key).compareTo(element) < 0) {
            collection.put(key, element);
        }
    }

    /**
     * Метод для замены элемента по ключу, если новый элемент меньше
     * @param key - ключ
     * @param element - новое значение
     */
    public void replaceIfGreater(String key, Vehicle element) {
        if (collection.get(key).compareTo(element) > 0) {
            collection.put(key, element);
        }
    }

    /**
     * Вывод элементов имя которых содержит заданную подстроку
     * @param name - подстрока
     */
    public void filterContainsName(String name) {
        System.out.println(String.format("Name contains %s", name));
        for (String key: collection.keySet()) {
            if (collection.get(key).getName().contains(name)) {
                System.out.println(collection.get(key));
            }
        }
    }

    /**
     * Вывод элементов имя которых начинается на заданную подстроку
     * @param name - подстрока
     */
    public void filterStartsWithName(String name) {
        System.out.println(String.format("Name starts with %s", name));
        for (String key: collection.keySet()) {
            if (collection.get(key).getName().startsWith(name)) {
                System.out.println(collection.get(key));
            }
        }
    }

    /**
     * Метод для вывода полей distanceTravelled в порядке возрастания
     */
    public void printFieldAscendingDistanceTravelled() {
        ArrayList<Long> distances = new ArrayList<>();
        for (String key : collection.keySet()) {
            distances.add(collection.get(key).getDistanceTravelled());
        }
        Collections.sort(distances);
        System.out.println("Field distanceTravelled:");
        for (Long dist : distances) {
            System.out.println(dist);
        }
    }

    /**
     * Метод возвращающий коллекцию элементов
     * @return collection - коллекция
     * */
    public Hashtable<String, Vehicle> getCollection() {
        return collection;
    }

    /**
     * Метод устанавливающий значение коллекции
     * @param collection
     */
    public void setCollection(Hashtable<String, Vehicle> collection) {
        this.collection = collection;
    }

    /**
     * Метод возвращающий дату создания коллекции
     * @return initializationDate - дата и время
     * */
    public ZonedDateTime getInitializationDate() {
        return initializationDate;
    }
}