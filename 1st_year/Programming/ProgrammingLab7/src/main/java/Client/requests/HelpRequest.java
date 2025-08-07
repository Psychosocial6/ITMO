package Client.requests;

import Common.requests.Request;

public class HelpRequest extends Request {

    public HelpRequest(Object[] args, String login, String password) {
        super("help", args, 0, false, login, password);
    }

    public HelpRequest() {
        super("help", 0, false);
    }
}
