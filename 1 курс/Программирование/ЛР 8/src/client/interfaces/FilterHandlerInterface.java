package client.interfaces;

import client.other.TableElement;

import java.util.ArrayList;

public interface FilterHandlerInterface {
    ArrayList<TableElement> filter(String parameter, String value);
}
