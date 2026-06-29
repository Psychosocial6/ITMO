package server.dataStorage;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class EnumDataFactory implements DataFactory,Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public Map<String, String[]> createData() {
        Map<String, String[]> data = new HashMap<>();
        data.put("colors", Arrays.stream(Color.values()).map(Enum::name).toArray(String[]::new));
        data.put("countries", Arrays.stream(Country.values()).map(Enum::name).toArray(String[]::new));
        data.put("genres", Arrays.stream(MovieGenre.values()).map(Enum::name).toArray(String[]::new));
        return data;
    }
}
