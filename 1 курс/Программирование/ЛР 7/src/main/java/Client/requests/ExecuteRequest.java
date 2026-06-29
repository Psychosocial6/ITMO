package Client.requests;

import Common.requests.Request;

public class ExecuteRequest extends Request {
    public ExecuteRequest(Object[] args, String login, String password) {
        super("execute_script", args, 1, false, login, password);
    }

    public ExecuteRequest() {
        super("execute_script", 1, false);
    }
}
