package client.eventHandlers;

import client.Client;
import client.interfaces.EditHandlerInterface;

import java.io.IOException;
import java.util.Map;

public class EditHandler implements EditHandlerInterface {
    private Long elementID;

    public EditHandler(Long elementID) {
        this.elementID = elementID;
    }

    @Override
    public void edit(Map<String, Object> elementFields) {
        try {
            Client.sender.send(new Object[]{"update", new Object[]{elementID, elementFields}, Client.currentClient.getUserName(), Client.currentClient.getUserPassword()});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
