package Server.threads;

import Server.managers.ServerConsoleManager;

public class ConsoleThread extends Thread {
    private ServerConsoleManager serverConsoleManager;

    public ConsoleThread(ServerConsoleManager serverConsoleManager) {
        this.serverConsoleManager = serverConsoleManager;
    }

    @Override
    public void run() {
        serverConsoleManager.readConsole();
    }
}
