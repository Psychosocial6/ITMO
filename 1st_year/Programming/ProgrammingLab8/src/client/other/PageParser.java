package client.other;

import java.util.*;
import java.util.stream.Collectors;

public class PageParser {

    public static List<Object> parsePage(String response) {
        ArrayList<TableElement> elements = new ArrayList<>();
        Map<Integer, Long> coordinates = new LinkedHashMap<>();
        ArrayList<String> createdBy = new  ArrayList<String>();
        if (response == null || response.trim().isEmpty()) {
            return List.of(elements, coordinates);
        }

        String dataToParse = response;
        if (dataToParse.startsWith("NextPage ")) {
            dataToParse = dataToParse.substring("NextPage ".length());
        }

        List<String> lines = Arrays.stream(dataToParse.split("\\R"))
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .collect(Collectors.toList());

        for (String line : lines) {
            String[] parts = line.split(" ");
            if (parts.length < 5) {
                System.out.println("Invalid movie line: " + line);
                continue;
            }

            try {
                long id = Long.parseLong(parts[0]);
                String name = parts[1];
                int x = Integer.parseInt(parts[2]);
                long y = Long.parseLong(parts[3]);
                String createdByMovie = parts[4];
                
                elements.add(new TableElement(id, name));
                coordinates.put(x, y);
                createdBy.add(createdByMovie);
                
            } catch (NumberFormatException e) {
                System.out.println("Invalid number in line: " + line);
            }
        }

        return List.of(elements, coordinates, createdBy);
    }
}