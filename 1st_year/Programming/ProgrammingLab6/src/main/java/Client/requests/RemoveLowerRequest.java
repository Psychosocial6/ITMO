package Client.requests;

public class RemoveLowerRequest extends Request {
    public RemoveLowerRequest(Object[] args) {
        super("remove_lower", args, 1, true);
    }

    public RemoveLowerRequest() {
        super("remove_lower", 1, true);
    }
}
