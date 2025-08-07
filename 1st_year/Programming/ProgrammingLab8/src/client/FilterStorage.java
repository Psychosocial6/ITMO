package client;

public class FilterStorage {
    private static String filter = "";
    private static String sortFilter = "";

    public static String getFilter() {
        return filter;
    }
    public static void setFilter(String filter) {
        FilterStorage.filter = filter;
    }
    public static String getSortFilter() {
        return sortFilter;
    }
    public static void setSortFilter(String sortFilter) {
        FilterStorage.sortFilter = sortFilter;
    }

    public static void resetFilter() {
        FilterStorage.setFilter("");
        FilterStorage.setSortFilter("");
    }

}