package Client.requests;

import Common.requests.Request;

public class ClearRequest extends Request {

    public ClearRequest(Object[] args, String login, String password) {
        super("clear", args, 0, false, login, password);
    }

    public ClearRequest() {
        super("clear", 0, false);
    }
}
