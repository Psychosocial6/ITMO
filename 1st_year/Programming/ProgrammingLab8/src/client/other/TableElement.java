package client.other;

public class TableElement {
    private long id;
    private String name;

    public TableElement(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
