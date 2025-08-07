package Server.utils;

import Client.requests.Request;

import java.io.*;

public class RequestDeserializer {

    public static Request deserializeRequest(byte[] data) {
        Request request = null;
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            request = (Request) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return request;
    }
}
