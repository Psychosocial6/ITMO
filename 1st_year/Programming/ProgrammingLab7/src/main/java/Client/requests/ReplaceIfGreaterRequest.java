package Client.requests;

import Common.requests.Request;

public class ReplaceIfGreaterRequest extends Request {
    public ReplaceIfGreaterRequest(Object[] args, String login, String password) {
        super("replace_if_greater", args, 1, true, login, password);
    }

    public ReplaceIfGreaterRequest() {
        super("replace_if_greater", 1, true);
    }
}
