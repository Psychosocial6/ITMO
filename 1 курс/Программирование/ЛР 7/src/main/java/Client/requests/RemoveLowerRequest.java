package Client.requests;

import Common.requests.Request;

public class RemoveLowerRequest extends Request {
    public RemoveLowerRequest(Object[] args, String login, String password) {
        super("remove_lower", args, 1, true, login, password);
    }

    public RemoveLowerRequest() {
        super("remove_lower", 1, true);
    }
}
