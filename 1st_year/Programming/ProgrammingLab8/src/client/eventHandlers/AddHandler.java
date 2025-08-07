package client.eventHandlers;

import client.Client;
import client.ClientRequestSender;
import client.ClientResponseReceiver;
import client.GUI.AddPageGUI;
import client.interfaces.AddHandlerInterface;
import client.other.DrawableObject;
import client.other.InsertCondition;

import java.awt.*;
import java.io.IOException;
import java.util.Map;

import static java.lang.Math.abs;

public class AddHandler implements AddHandlerInterface {
    private AddPageGUI addPageGUI;
    private ClientRequestSender sender;
    private ClientResponseReceiver receiver;

    public AddHandler(AddPageGUI addPageGUI, ClientRequestSender sender, ClientResponseReceiver receiver) {
        this.addPageGUI = addPageGUI;
        this.sender = sender;
        this.receiver = receiver;
    }

    @Override
    public void add(Map<String, Object> elementFields, InsertCondition condition) {
        try {
            switch (condition) {
                case InsertCondition.NONE:
                    sender.send(new Object[]{"add", new Object[]{elementFields}, Client.currentClient.getUserName(), Client.currentClient.getUserPassword()});
                    Client.mainPageGUI.getDrawingPanel().addObject(new DrawableObject((int)elementFields.get("Coordinates_X")*10, (long)elementFields.get("Coordinates_Y")*10, 10, new Color(abs(Client.currentClient.getUserName().hashCode()-100)%256, abs(Client.currentClient.getUserName().hashCode()-50)%256, abs(Client.currentClient.getUserName().hashCode())%256)));
                    break;
                case InsertCondition.IF_MAX:
                    sender.send(new Object[]{"add_if_max", new Object[]{elementFields}, Client.currentClient.getUserName(), Client.currentClient.getUserPassword()});
                    break;
                case InsertCondition.IF_MIN:
                    sender.send(new Object[]{"add_if_min", new Object[]{elementFields}, Client.currentClient.getUserName(), Client.currentClient.getUserPassword()});
                    break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}