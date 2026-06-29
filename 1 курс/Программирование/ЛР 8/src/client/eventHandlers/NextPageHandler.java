package client.eventHandlers;

import client.Client;
import client.ClientRequestSender;
import client.ClientResponseReceiver;
import client.FilterStorage;
import client.GUI.MainPageGUI;
import client.dataStorage.CollectionView;
import client.interfaces.NextPageHandlerInterface;
import client.other.PageParser;
import client.other.TableElement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class NextPageHandler implements NextPageHandlerInterface {
    private MainPageGUI mainPageGUI;
    private ClientRequestSender sender;
    private ClientResponseReceiver receiver;

    public NextPageHandler(MainPageGUI mainPageGUI, ClientRequestSender sender, ClientResponseReceiver receiver) {
        this.mainPageGUI = mainPageGUI;
        this.sender = sender;
        this.receiver = receiver;
    }

    @Override
    public ArrayList<TableElement> getNextPage() {
        Client.pageCounter++;
        try {
            sender.send(new Object[]{"load_next_page", new Object[]{Client.pageCounter}, Client.currentClient.getUserName(), Client.currentClient.getUserPassword()});
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Map<Long, String> response = CollectionView.getMovieView();
            ArrayList<TableElement> elements = new ArrayList<>();
            for (Long id : response.keySet()) {
                elements.add(new TableElement(id, response.get(id)));
            }
            FilterStorage.resetFilter();
            return elements;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
