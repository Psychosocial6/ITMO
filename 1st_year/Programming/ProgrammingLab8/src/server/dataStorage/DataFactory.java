package server.dataStorage;

import java.io.Serializable;
import java.util.Map;

public interface DataFactory extends Serializable {
    Map<String, String[]> createData();
}
