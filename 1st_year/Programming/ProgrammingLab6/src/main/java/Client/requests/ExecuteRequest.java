package Client.requests;

public class ExecuteRequest extends Request {
    public ExecuteRequest(Object[] args) {
        super("execute_script", args, 1, false);
    }

    public ExecuteRequest() {
        super("execute_script", 1, false);
    }
}
