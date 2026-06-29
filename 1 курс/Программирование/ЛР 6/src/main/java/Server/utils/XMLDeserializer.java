package Server.utils;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import Server.managers.CollectionManager;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;

/**
 * Утилитный класс для десериализации XML
 * @author Андрей
 * */
public class XMLDeserializer {

    /**
     * Метод преобразующий xml файл в коллекцию Vehicle
     * @param XMLLine - строка xml
     * @return collectionManager - Класс управляющий коллекций
     */
    public static CollectionManager deserializeFromXML(String XMLLine) {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new JavaTimeModule());
        CollectionManager collectionManager = null;
        try {
            collectionManager = xmlMapper.readValue(XMLLine, CollectionManager.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return collectionManager;
    }
}