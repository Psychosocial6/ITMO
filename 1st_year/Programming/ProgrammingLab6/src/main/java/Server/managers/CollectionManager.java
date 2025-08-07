package Server.managers;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import Server.collectionElements.Vehicle;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import Server.exceptions.ScriptExecutionException;
import Server.utils.FileWriter;
import Server.utils.Invoker;
import Server.utils.ScriptExecutor;
import Server.utils.XMLSerializer;

import java.io.File;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Класс управляющий коллекцией
 * @author Андрей
 * */
public class CollectionManager {
    @JacksonXmlElementWrapper(useWrapping = false)
    private Hashtable<String, Vehicle> collection;
    @JacksonXmlProperty(localName = "initializationDate")
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
    public String info() {
        return String.format("----------\n Дата создания: %s\n Размер коллекции: %d\n Тип коллекции: %s\n ----------", initializationDate, collection.size(), collection.getClass());
    }

    /**
     * Метод выводящий содержимое коллекции
     */
    public String show() {
        if (collection.isEmpty()) {
            return "Collection is empty";
        }
        String ret = collection.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining("\n"));
        return ret;
    }

    /**
     * Метод вставки элемента по ключу
     * @param key - ключ
     * @param element - элемент
     */
    public String insert(String key, Vehicle element) {
        collection.put(key, element);
        return "Объект успешно вставлен в коллекцию";
    }

    /**
     * Метод замены элемента с заданным id на данный
     * @param id - id
     * @param element - элемент на который заменяется существующий
     */
    public String updateById(int id, Vehicle element) {
        for (String key : collection.keySet()) {
            if (collection.get(key).getId() == id) {
                element.setId(collection.get(key).getId());
                element.setCreationDate(collection.get(key).getCreationDate());
                collection.put(key, element);
            }
        }
        return String.format("Объект с id {%d} успешно обновлен", id);
    }

    /**
     * Метод удаления элемента по ключу
     * @param key - ключ
     */
    public String removeByKey(String key) {
        collection.remove(key);
        return String.format("Элемент с ключом {%s} удален", key);
    }

    /**
     * Метод очистки коллекции
     */
    public String clear() {
        collection.clear();
        return "Коллекция успешно очищена";
    }

    /**
     * Метод для сохранения коллекции в файл
     * @param file - файл для сохранения
     */
    public String save(File file) {
        FileWriter.writeIntoFile(file, XMLSerializer.serializeToXML(this));
        return String.format("Коллекция успешно сохранена в файл %s", file.getPath());
    }

    /**
     * Метод для исполнения скрипта из заданного файла
     * @param file - файл со скриптом
     * @param invoker - исполнитель команд
     */
    public String executeScript(String file, Invoker invoker) {
        String msg = "";
        ScriptExecutor executor = new ScriptExecutor(invoker);
        try {
            msg = executor.executeScript(file);
        } catch (ScriptExecutionException e) {
            System.out.println(e.getMessage());
        }
        return msg;
    }

    /**
     * Метод для завершения работы программы
     */
    public String exit() {
        System.exit(0);
        return "Работа завершена";
    }

    /**
     * Метод для удаления всех элементов коллекции меньше данного
     * @param element - элемент с которым сравнивается
     */
    public String removeIfLower(Vehicle element) {
        String msg = "Ничего не было удалено";
        Hashtable<String, Vehicle> collection2 = (Hashtable<String, Vehicle>) collection.clone();
        for (String key : collection.keySet()) {
            if (collection.get(key).compareTo(element) < 0) {
                collection2.remove(key);
                msg = String.format("Объект с ключом {%s} удален", key);
            }
        }
        collection = collection2;
        return msg;
    }

    /**
     * Метод для замены элемента по ключу, если новый элемент больше
     * @param key - ключ
     * @param element - новое значение
     */
    public String replaceIfLower(String key, Vehicle element) {
        String msg = "Ничего не было заменено";
        if (collection.get(key).compareTo(element) < 0) {
            collection.put(key, element);
            msg = String.format("Объект с ключом {%s} был заменен", key);
        }
        return msg;
    }

    /**
     * Метод для замены элемента по ключу, если новый элемент меньше
     * @param key - ключ
     * @param element - новое значение
     */
    public String replaceIfGreater(String key, Vehicle element) {
        String msg = "Ничего не было заменено";
        if (collection.get(key).compareTo(element) > 0) {
            collection.put(key, element);
            msg = String.format("Объект с ключом {%s} был заменен", key);
        }
        return msg;
    }

    /**
     * Вывод элементов имя которых содержит заданную подстроку
     * @param name - подстрока
     */
    public String filterContainsName(String name) {
        String msg = String.format("Name contains %s:\n", name);
        for (String key: collection.keySet()) {
            if (collection.get(key).getName().contains(name)) {
                msg += collection.get(key) + "\n";
            }
        }
        return msg;
    }

    /**
     * Вывод элементов имя которых начинается на заданную подстроку
     * @param name - подстрока
     */
    public String filterStartsWithName(String name) {
        String msg = String.format("Name starts with %s:\n", name);
        for (String key: collection.keySet()) {
            if (collection.get(key).getName().startsWith(name)) {
                msg += collection.get(key) + "\n";
            }
        }
        return msg;
    }

    /**
     * Метод для вывода полей distanceTravelled в порядке возрастания
     */
    public String printFieldAscendingDistanceTravelled() {
        String msg = "";
        ArrayList<Long> distances = new ArrayList<>();
        for (String key : collection.keySet()) {
            distances.add(collection.get(key).getDistanceTravelled());
        }
        Collections.sort(distances);
        System.out.println("Field distanceTravelled:");
        for (Long dist : distances) {
            msg += dist + "\n";
        }
        return msg;
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