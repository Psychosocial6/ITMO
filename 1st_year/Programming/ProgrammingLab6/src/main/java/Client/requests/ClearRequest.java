package Client.requests;

public class ClearRequest extends Request {

    public ClearRequest(Object[] args) {
        super("clear", args, 0, false);
    }

    public ClearRequest() {
        super("clear", 0, false);
    }
}
