package functioning;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

public class ConfigParser {

    public static HashMap<String, HashMap<String, Object>> parseConfig(File file) {
        HashMap<String, HashMap<String, Object>> data = new HashMap<String, HashMap<String, Object>>();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            String line, index;
            index = null;
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.isEmpty()) {
                    if (line.charAt(0) == '#') {
                        index = line.substring(1);
                        data.put(index, new HashMap<String, Object>());
                    }
                    else {
                        String[] values = line.split(" : ");
                        String key = values[0];
                        String value = values[1];
                        HashMap<String, Object> changed = data.get(index);
                        changed.put(key, value);
                        data.put(index, changed);
                    }
                }

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
