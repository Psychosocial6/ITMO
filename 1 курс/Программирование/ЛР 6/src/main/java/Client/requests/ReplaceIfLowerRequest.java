package Client.requests;

public class ReplaceIfLowerRequest extends Request {
    public ReplaceIfLowerRequest(Object[] args) {
        super("replace_if_lower", args, 1, true);
    }

    public ReplaceIfLowerRequest() {
        super("replace_if_lower", 1, true);
    }
}
