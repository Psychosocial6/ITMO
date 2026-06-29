package Client.requests;

public class ReplaceIfGreaterRequest extends Request {
    public ReplaceIfGreaterRequest(Object[] args) {
        super("replace_if_greater", args, 1, true);
    }

    public ReplaceIfGreaterRequest() {
        super("replace_if_greater", 1, true);
    }
}
