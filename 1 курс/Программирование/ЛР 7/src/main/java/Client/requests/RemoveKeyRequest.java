package Client.requests;

import Common.requests.Request;

public class RemoveKeyRequest extends Request {
    public RemoveKeyRequest(Object[] args, String login, String password) {
        super("remove_key", args, 1, false, login, password);
    }

    public RemoveKeyRequest() {
        super("remove_key", 1, false);
    }
}
