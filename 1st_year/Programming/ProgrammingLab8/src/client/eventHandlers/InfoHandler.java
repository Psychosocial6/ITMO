package client.eventHandlers;

import java.io.IOException;

import client.Client;
import client.ClientRequestSender;
import client.dataStorage.CurrentMovie;
import client.interfaces.InfoHandlerInterface;

public class InfoHandler implements InfoHandlerInterface {
	private ClientRequestSender sender;

	public InfoHandler(ClientRequestSender sender) {
        this.sender = sender;

    }
	@Override
	public String info(Long id) {
	 try {
		sender.send(new Object[]{"show", new Object[]{id}, Client.currentClient.getUserName(), Client.currentClient.getUserPassword()});
		Thread.sleep(800);
		return CurrentMovie.movieToString();
	} catch (IOException | InterruptedException e) {
		e.printStackTrace();
		return "Ошибка: не удалось вывести данные о фильме";
		}
	}

}
