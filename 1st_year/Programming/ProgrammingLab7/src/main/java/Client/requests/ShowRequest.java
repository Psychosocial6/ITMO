package Client.requests;

import Common.requests.Request;

public class ShowRequest extends Request {
    public ShowRequest(Object[] args, String login, String password) {
        super("show", args, 0, false, login, password);
    }

    public ShowRequest() {
        super("show", 0, false);
    }
}
