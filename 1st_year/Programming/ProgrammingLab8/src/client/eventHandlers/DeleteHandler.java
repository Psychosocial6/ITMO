package client.eventHandlers;

import java.io.IOException;

import client.Client;
import client.ClientRequestSender;
import client.GUI.ElementInfoPageGUI;
import client.dataStorage.CurrentMovie;
import client.interfaces.DeleteHandlerInterface;

public class DeleteHandler implements DeleteHandlerInterface {
    private ElementInfoPageGUI elementInfoPageGUI;
    private ClientRequestSender sender;

    public DeleteHandler(ElementInfoPageGUI elementInfoPageGUI, ClientRequestSender sender) {
        this.elementInfoPageGUI = elementInfoPageGUI;
        this.sender = sender;
    }

    @Override
    public void delete() {
    	 try {
			sender.send(new Object[]{"remove_by_id", new Object[]{Long.parseLong(CurrentMovie.getId())}, Client.currentClient.getUserName(), Client.currentClient.getUserPassword()});
			
    	 } catch (IOException e) {
			e.printStackTrace();
		}
    
    }
}
