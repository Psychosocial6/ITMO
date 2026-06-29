package Client.requests;

public class RemoveKeyRequest extends Request {
    public RemoveKeyRequest(Object[] args) {
        super("remove_key", args, 0, false);
    }

    public RemoveKeyRequest() {
        super("remove_key", 0, false);
    }
}
