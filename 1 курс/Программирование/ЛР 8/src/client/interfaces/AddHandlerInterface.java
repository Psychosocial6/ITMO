package client.interfaces;

import client.other.InsertCondition;

import java.util.Map;

public interface AddHandlerInterface {
    void add(Map<String, Object> elementFields, InsertCondition condition);
}