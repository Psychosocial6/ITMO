import com.fastcgi.*;
import utils.FilePrinter;
import utils.RequestHandler;

import java.io.IOException;
import java.nio.file.Path;

public class Server {
    private static FilePrinter filePrinter = new FilePrinter(Path.of("logs/logs.txt"));

    public static void main(String[] args) {
        try {
            System.setProperty("FCGI_PORT", "25565");
            filePrinter.getPrintWriter().println("port 25565 set successfully");
            FCGIInterface fcgi = new FCGIInterface();

            while (fcgi.FCGIaccept() >= 0) {
                filePrinter.getPrintWriter().println("got a new request");
                FCGIRequest request = FCGIInterface.request;
                try {
                    RequestHandler.handleRequest(request);
                }
                catch (Exception e) {
                    filePrinter.getPrintWriter().println(e.getMessage());

                }
                finally {
                    //closeServer()
                }
            }
        }
        catch (Exception e) {
            filePrinter.getPrintWriter().println(e.getMessage());
        }

    }

    public static void closeServer() {
        try {
            FCGIInterface.request.outStream.flush();
            FCGIInterface.request.outStream.close();
            FCGIInterface.request.inStream.close();
        }
        catch (IOException e) {
            filePrinter.getPrintWriter().println(String.format("Exception while trying to close the server: %s", e.getMessage()));
        }
    }
}
