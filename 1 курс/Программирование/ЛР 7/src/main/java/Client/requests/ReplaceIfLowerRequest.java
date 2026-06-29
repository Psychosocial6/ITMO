package Client.requests;

import Common.requests.Request;

public class ReplaceIfLowerRequest extends Request {
    public ReplaceIfLowerRequest(Object[] args, String login, String password) {
        super("replace_if_lower", args, 1, true, login, password);
    }

    public ReplaceIfLowerRequest() {
        super("replace_if_lower", 1, true);
    }
}
