package Client.requests;

import Common.requests.Request;

public class InsertRequest extends Request {
    public InsertRequest(Object[] args, String login, String password) {
        super("insert", args, 1, true, login, password);
    }

    public InsertRequest() {
        super("insert", 1, true);
    }
}
