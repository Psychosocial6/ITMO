package Client.requests;

import Common.requests.Request;

public class InfoRequest extends Request {
    public InfoRequest(Object[] args, String login, String password) {
        super("info", args, 0, false, login, password);
    }

    public InfoRequest() {
        super("info", 0, false);
    }
}
