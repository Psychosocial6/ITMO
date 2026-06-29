package Server;

import Client.requests.Request;
import Server.responses.Response;
import Server.collectionElements.Vehicle;
import Server.exceptions.ExecutionException;
import Server.utils.Invoker;
import Server.utils.RequestDeserializer;
import Server.utils.ResponseSerializer;
import Server.utils.IDGenerator;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.time.ZonedDateTime;
import java.util.Arrays;

public class UDPServer {
    private int dataSize;
    private DatagramSocket datagramSocket;
    Invoker invoker;

    public UDPServer(int port, Invoker invoker) {
        dataSize = 1024;
        this.invoker = invoker;
        try {
            datagramSocket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public UDPServer(int port, int dataSize, Invoker invoker) {
        this.dataSize = dataSize;
        this.invoker = invoker;
        try {
            datagramSocket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void startServer() {
        while (true) {
            receiveRequestAndSendResponse();
        }
    }

    public void receiveRequestAndSendResponse() {
        DatagramPacket datagramPacket = new DatagramPacket(new byte[dataSize], dataSize);
        try {
            datagramSocket.receive(datagramPacket);
            System.out.println("packet received");
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] message = Arrays.copyOfRange(datagramPacket.getData(),0, datagramPacket.getLength());
        Request request = RequestDeserializer.deserializeRequest(message);
        if (request.requiresVehicle()) {
            Object[] args = request.getArgs();
            Vehicle vehicle = (Vehicle) args[args.length - 1];
            vehicle.setId((long) IDGenerator.getNewID());
            vehicle.setCreationDate(ZonedDateTime.now());
            args[args.length - 1] = vehicle;
            request.setArgs(args);
        }
        String result = "";
        String error = "";
        try {
            result = invoker.executeCommandUsingToken(request.getCommandToken(), request.getArgs());
        } catch (ExecutionException e) {
            error = e.getMessage();
        }
        Response response = new Response(error, result);
        byte[] byteResponse = ResponseSerializer.serializeResponse(response);
        datagramPacket = new DatagramPacket(byteResponse, byteResponse.length, datagramPacket.getSocketAddress());
        try {
            datagramSocket.send(datagramPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeServer() {
        File file = new File("src/main/java/Server/files/savedCollection.xml");
        Object[] args = {file};
        try {
            invoker.executeCommandUsingToken("save", args);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        datagramSocket.close();
    }
}
