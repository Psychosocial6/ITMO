package Server.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import Server.managers.CollectionManager;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Утилитный класс для сериализации xml
 * @author Андрей
 * */
public class XMLSerializer {

    /**
     * Метод для сериализации коллекци в формате XML
     * @param collectionManager - Коллекция для сериализации
     * @return String xml - сериализованный объект
     */
    public static String serializeToXML(CollectionManager collectionManager) {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new JavaTimeModule());
        xmlMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String xml = "";

        try {
            xml = xmlMapper.writeValueAsString(collectionManager);
        } catch (JsonProcessingException e) {
            System.out.println("Error while serialization");
        }
        return xml;
    }

}