package client;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServerPoller {

    private final ScheduledExecutorService scheduler;
    private final ClientRequestSender sender;
    private final String username;
    private final String password;
    private Object[] arg = new Object[]{};
    public ServerPoller(ClientRequestSender sender, String username, String password) {
        this.sender = sender;
        this.username = username;
        this.password = password;
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
    }

    public void startPolling() {
        scheduler.scheduleAtFixedRate(() -> {
            try {
                sender.send(new Object[]{"check_update_collection",arg , username, password});
            } catch (Exception e) {
                System.err.println("Ошибка при опросе сервера: " + e.getMessage());
            }
        }, 0, 1500, TimeUnit.MILLISECONDS);
    }

    public void stopPolling() {
        scheduler.shutdownNow();
    }
}