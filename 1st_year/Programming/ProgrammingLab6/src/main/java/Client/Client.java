package Client;

import Client.requests.Request;
import Client.utils.RequestSerializer;
import Client.utils.ResponseDeserializer;
import Client.responses.Response;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class Client {
    private InetSocketAddress inetSocketAddress;
    private DatagramChannel datagramChannel;

    public Client(int port, InetAddress inetAddress) {
        this.inetSocketAddress = new InetSocketAddress(inetAddress, port);
        try {
            datagramChannel = DatagramChannel.open().bind(null).connect(inetSocketAddress);
            datagramChannel.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Response sendRequestAndGetResponse(Request request) {
        byte[] requestData = RequestSerializer.serializeRequest(request);
        ByteBuffer byteBuffer = ByteBuffer.wrap(requestData);
        try {
            datagramChannel.send(byteBuffer, inetSocketAddress);
            System.out.println("Data sent successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Response response = null;
        ByteBuffer byteBuffer1 = ByteBuffer.allocate(65507);
        long startTime = System.currentTimeMillis();
        final int TIMEOUT = 30000;

        while (System.currentTimeMillis() - startTime < TIMEOUT) {
            byteBuffer1.clear();
            SocketAddress sender = null;
            try {
                sender = datagramChannel.receive(byteBuffer1);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (sender != null) {
                byteBuffer1.flip();
                byte[] responseData = new byte[byteBuffer1.remaining()];
                byteBuffer1.get(responseData);
                return ResponseDeserializer.deserializeResponse(responseData);
            }
        }
        System.out.println("Время ожидания ответа истекло");
        System.exit(0);
        return null;
    }
}
