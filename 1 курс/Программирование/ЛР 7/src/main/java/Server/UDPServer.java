package Server;

import Common.reponses.Response;
import Common.requests.Request;
import Common.collectionElements.Vehicle;
import Server.exceptions.ExecutionException;
import Server.utils.Invoker;
import Server.utils.RequestDeserializer;
import Server.utils.ResponseSerializer;

import java.io.IOException;
import java.net.*;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UDPServer {
    private int dataSize;
    private DatagramSocket datagramSocket;
    private Invoker invoker;
    private ExecutorService readingPool;
    private ExecutorService processingPool;
    private ExecutorService sendingPool;
    private final int RECEIVING_THREADS = 5;
    private final int PROCESSING_THREADS = 5;

    public UDPServer(int port, Invoker invoker) {
        dataSize = 1024;
        this.invoker = invoker;
        readingPool = Executors.newFixedThreadPool(RECEIVING_THREADS);
        processingPool = Executors.newFixedThreadPool(PROCESSING_THREADS);
        sendingPool = Executors.newCachedThreadPool();
        try {
            datagramSocket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public UDPServer(int port, int dataSize, Invoker invoker) {
        this.dataSize = dataSize;
        this.invoker = invoker;
        readingPool = Executors.newFixedThreadPool(RECEIVING_THREADS);
        processingPool = Executors.newFixedThreadPool(PROCESSING_THREADS);
        sendingPool = Executors.newCachedThreadPool();
        try {
            datagramSocket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void startServer() {
        while (true) {
            DatagramPacket datagramPacket = new DatagramPacket(new byte[dataSize], dataSize);
            try {
                datagramSocket.receive(datagramPacket);
                System.out.println("packet received");
                readingPool.execute(() -> readRequest(datagramPacket));
            } catch (IOException e) {
                System.out.println("packet is not received");
            }
        }
    }

    private void readRequest(DatagramPacket datagramPacket) {
        byte[] message = Arrays.copyOfRange(datagramPacket.getData(),0, datagramPacket.getLength());
        Request request = RequestDeserializer.deserializeRequest(message);
        processingPool.execute(() -> {
            processRequest(request, datagramPacket.getSocketAddress());
        });
    }

    private void processRequest(Request request, SocketAddress socketAddress) {

        if (request.requiresVehicle()) {
            Object[] args = request.getArgs();
            Vehicle vehicle = (Vehicle) args[args.length - 2];
            vehicle.setCreationDate(ZonedDateTime.now());
            args[args.length - 2] = vehicle;
            request.setArgs(args);
        }
        String result;
        String error;
        try {
            result = invoker.executeCommandUsingToken(request.getCommandToken(), request.getArgs());
            error = "";
        } catch (ExecutionException e) {
            result = "";
            error = e.getMessage();
        }
        String finalResult = result;
        String finalError = error;
        sendingPool.execute(() -> sendResponse(finalResult, finalError, socketAddress));
    }

    private void sendResponse(String result, String error, SocketAddress socketAddress) {
        Response response = new Response(error, result);
        byte[] byteResponse = ResponseSerializer.serializeResponse(response);
         DatagramPacket datagramPacket = new DatagramPacket(byteResponse, byteResponse.length, socketAddress);
        try {
            datagramSocket.send(datagramPacket);
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            System.out.println("error while sending");
        }
    }
}
