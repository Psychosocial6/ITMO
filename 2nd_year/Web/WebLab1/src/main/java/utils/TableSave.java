package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.ArrayList;

public class TableSave {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static ArrayList<TableItem> table = new ArrayList<>();

    static {
        objectMapper.registerModule(new JavaTimeModule());
    }

    public static String getTable() throws JsonProcessingException {
        return objectMapper.writeValueAsString(table);
    }

    public static void addItem(TableItem tableItem) {
        table.add(tableItem);
    }

    public static int getSize() {
        return table.size();
    }

    public static void clear() {
        table.clear();
    }
}
